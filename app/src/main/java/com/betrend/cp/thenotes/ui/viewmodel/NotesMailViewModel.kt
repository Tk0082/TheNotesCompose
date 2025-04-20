package com.betrend.cp.thenotes.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.betrend.cp.thenotes.data.local.NotesDatabase
import com.betrend.cp.thenotes.data.local.entities.Note
import com.betrend.cp.thenotes.data.remote.auth.GoogleDriveServiceHelper
import com.google.android.gms.auth.GoogleAuthException
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

data class DriveUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val account: GoogleSignInAccount? = null,
    val backupFileName: String = "notes_backup.db"
)

class NotesMailViewModel(context: Context, private val database: NotesDatabase): ViewModel() {

    val context: Context by lazy { context.applicationContext }
    var uiState by mutableStateOf(DriveUiState())
    private val driveServiceHelper = GoogleDriveServiceHelper(context)


    private val fileName: String = "notes_backup.json"

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
    @Suppress("KotlinConstantConditions")
    fun uploadNotesToDrive(account: GoogleSignInAccount) {
        viewModelScope.launch(Dispatchers.IO) {
            uploadState = UploadState.Loading
            try {
                // Pegar o arquivo JSON com as Notas convertidas do RoomDB
                val dbFile = File(context.filesDir, fileName)

                if (!dbFile.exists() || dbFile.length() == 0L) {
                    throw Exception("Arquivo não encontrado")
                }

                val fileId = driveServiceHelper.uploadFileToDrive(
                    account = account,
                    filePath = dbFile.absolutePath,
                    fileName = dbFile.name
                )

                uploadState = if (fileId != null) {
                    UploadState.Success(fileId)
                } else {
                    UploadState.Error("Falha ao fazer upload")
                }
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is UserRecoverableAuthIOException -> "Autenticação necessária"
                    is GooglePlayServicesAvailabilityIOException -> "Google Play Services não disponível"
                    is GoogleAuthException -> "Erro de autenticação"
                    is IOException -> "Erro de rede: ${e.message}"
                    else -> "Erro desconhecido: ${e.message}"
                }

                Log.e("DRIVE_UPLOAD", errorMessage, e)
                withContext(Dispatchers.Main) {
                    uploadState = UploadState.Error(errorMessage)
                }
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

    // Criar JSON Com notas contidas no RoomDB ====================
    fun exportNotes(callback: (File?) -> Unit) {
        viewModelScope.launch {
            val file = exportNotesToJson(database, context)
            callback(file)
        }
    }

    private suspend fun exportNotesToJson(
        database: NotesDatabase,
        context: Context
    ): File? {
        return withContext(Dispatchers.IO) {
            try {
                // Pegar todas as notas do RoomDB
                val notes = database.notesDao().getAllNotes()

                // Converter para Note
                val notesJson = notes.map { note ->
                    Note(
                        id = note.id,
                        name = note.name,
                        content = note.content,
                        time = note.time,
                        isPinned = note.isPinned
                    )
                }

                // Converter para JSON usando Gson
                val jsonString = Gson().toJson(notesJson)

                // Criar arquivo no diretório interno do app files/
                val file = File(context.filesDir, fileName)
                file.writeText(jsonString)

                file
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
    //================================================================
}

