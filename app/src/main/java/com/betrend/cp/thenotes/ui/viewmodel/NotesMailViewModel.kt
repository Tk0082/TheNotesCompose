package com.betrend.cp.thenotes.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.betrend.cp.thenotes.data.remote.auth.GoogleDriveServiceHelper
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.launch

data class DriveUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val account: GoogleSignInAccount? = null,
    val backupFileName: String = "notes_backup.db"
)

class NotesMailViewModel(context: Context): ViewModel() {

    val context: Context by lazy { context.applicationContext }
    var uiState by mutableStateOf(DriveUiState())
    private val driveServiceHelper = GoogleDriveServiceHelper(context)

    // Estado para controlar o upload
    var uploadState by mutableStateOf<UploadState>(UploadState.Idle)
        private set

    sealed class UploadState {
        data object Idle : UploadState()
        data object Loading : UploadState()
        data class Success(val fileId: String) : UploadState()
        data class Error(val message: String) : UploadState()
    }

    // Função para fazer upload do banco de dados
    fun uploadNotesToDrive(
        account: GoogleSignInAccount
    ) {
        viewModelScope.launch {
            uploadState = UploadState.Loading
            try {
                // Obtém o caminho do banco de dados Room
                val dbFile = context.getDatabasePath("notesdb")

                if (!dbFile?.exists()!!) {
                    throw Exception("Banco de dados local não encontrado")
                }

                val fileId = driveServiceHelper.uploadFileToDrive(
                    account = account,
                    filePath = dbFile.absolutePath,
                    fileName = dbFile.toString()
                )

                uploadState = if (fileId != null) {
                    UploadState.Success(fileId)
                } else {
                    UploadState.Error("Falha ao fazer upload")
                }
            } catch (e: Exception) {
                uploadState = UploadState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    // Resetar o estado do upload
    fun resetUploadState() {
        uploadState = UploadState.Idle
    }

    fun updateAccount(account: GoogleSignInAccount?) {
        uiState = uiState.copy(account = account)
    }
}

