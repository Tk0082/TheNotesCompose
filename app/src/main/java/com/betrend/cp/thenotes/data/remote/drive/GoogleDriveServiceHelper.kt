package com.betrend.cp.thenotes.data.remote.drive

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.ByteArrayContent
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.util.Collections

class GoogleDriveServiceHelper(context: Context) {
    private val context = context.applicationContext

    private val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestScopes(Scope(DriveScopes.DRIVE_FILE))
        .build()

    val googleSignInClient = GoogleSignIn.getClient(context, signInOptions)

    private fun getDriveService(account: GoogleSignInAccount): Drive {
        val credential = GoogleAccountCredential.usingOAuth2(
            context,
            Collections.singleton(DriveScopes.DRIVE_FILE)
        )
        credential.selectedAccount = account.account

        return Drive.Builder(
            NetHttpTransport(),
            GsonFactory(),
            credential
        )
            .setApplicationName("TheNotes")
            .build()
    }

    suspend fun uploadFileToDrive(
        account: GoogleSignInAccount,
        filePath: String,
        fileName: String
    ): String? = withContext(Dispatchers.IO) {
        try {

            val driveService = getDriveService(account)
            val fileContent = java.io.File(filePath).readBytes()
            val mediaContent = ByteArrayContent("application/json", fileContent)

            // Verificar se existe arquivo no DRIVE (Criar ou Sobrescrever)
            val existingFileId = findFileIdByName(getDriveService(account), fileName)
            if(existingFileId != null){
                val fileMetadata = File().setName(fileName)

                driveService.files().update(existingFileId, fileMetadata, mediaContent)
                    .setFields("id")
                    .execute()
                    .id
            } else {
                val fileMetadata = File().apply {
                    name = fileName
                    mimeType = "application/json"
                    parents = listOf("root")
                }
                driveService.files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute()
                    .id
            }
        } catch (e: Exception) {
            Log.e("DRIVE_SERVICE", "Erro no upload", e)
            e.printStackTrace()
            null
        }
    }

    // Função usada no Upload
    private suspend fun findFileIdByName(driveService: Drive, fileName: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val result = driveService.files().list()
                    .setQ("name = '$fileName' and trashed = false")
                    .setSpaces("drive")
                    .setFields("files(id)")
                    .execute()

                result.files.firstOrNull()?.id
            } catch (e: Exception) {
                Log.e("DRIVE_SEARCH", "Erro ao buscar arquivo", e)
                null
            }
        }
    }

    // Função usada no Download
    suspend fun findFileByName(account: GoogleSignInAccount, fileName: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val credential = GoogleAccountCredential.usingOAuth2(
                    context,
                    listOf(DriveScopes.DRIVE_FILE)
                )
                credential.selectedAccount = account.account

                val drive = Drive.Builder(
                    NetHttpTransport(),
                    GsonFactory(),
                    credential
                ).build()

                val result = drive.files().list()
                    .setQ("name = '$fileName' and trashed = false")
                    .setSpaces("drive")
                    .setFields("files(id, name)")
                    .execute()

                result.files.firstOrNull()?.id
            } catch (e: Exception) {
                Log.e("DRIVE_SEARCH", "Erro ao buscar arquivo", e)
                null
            }
        }
    }

    suspend fun downloadFile(account: GoogleSignInAccount, fileId: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val credential = GoogleAccountCredential.usingOAuth2(
                    context,
                    listOf(DriveScopes.DRIVE_FILE)
                )
                credential.selectedAccount = account.account

                val drive = Drive.Builder(
                    NetHttpTransport(),
                    GsonFactory(),
                    credential
                ).build()

                val outputStream = ByteArrayOutputStream()
                drive.files().get(fileId)
                    .executeMediaAndDownloadTo(outputStream)

                String(outputStream.toByteArray())
            } catch (e: Exception) {
                Log.e("DRIVE_DOWNLOAD", "Erro ao baixar arquivo", e)
                null
            }
        }
    }
}