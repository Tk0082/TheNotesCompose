package com.betrend.cp.thenotes.data.remote.auth

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.FileContent
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.File
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
            Collections.singletonList(DriveScopes.DRIVE_FILE)
        )
        credential.selectedAccount = account.account

        return Drive.Builder(
            NetHttpTransport(),
            GsonFactory.getDefaultInstance(),
            credential
        )
            .setApplicationName("TheNotes")
            .build()
    }

    fun uploadFileToDrive(
        account: GoogleSignInAccount,
        filePath: String,
        fileName: String
    ): String? {
        return try {
            val driveService = getDriveService(account)
            val fileMetadata = File().apply {
                name = fileName
                parents = listOf("root")
            }
            val fileContent = java.io.File(filePath)
            val mediaContent = FileContent(
                "application/x-sqlite3",
                fileContent
            )

            val file = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute()
            file.id
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}