package com.betrend.cp.thenotes.data.remote.drive


import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import com.betrend.cp.thenotes.data.local.dao.NotesDao
import com.betrend.cp.thenotes.data.local.entities.Note
import com.google.api.client.http.ByteArrayContent
import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.File
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.util.*

// Serviço de Backup no Drive
class DriveBackupService(
    private val driveService: Drive,
    private val notesDao: NotesDao,
    private val packageName: String
) {
    companion object {
        private const val BACKUP_FOLDER_NAME = "TheNotesBackup"
        private const val BACKUP_FILE_PREFIX = "notes_backup"
        private const val MIME_TYPE_JSON = "application/json"
    }

    private var backupFolderId: String? = null

    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun backupNotes(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            // Obter ou criar a pasta de backup
            val folderId = getOrCreateBackupFolder()

            // Obter todas as notas do banco de dados
            val notes = notesDao.getAll().first()

            // Converter para JSON
            val jsonNotes = notes.toJson()

            // Criar nome do arquivo com timestamp
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(Date())

            val fileName = "$BACKUP_FILE_PREFIX$timestamp.json"

            // Fazer upload para o Drive
            uploadFileToDrive(folderId, fileName, jsonNotes)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun restoreNotes(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            // Obter a pasta de backup
            val folderId = getBackupFolderId() ?: return@withContext Result.failure(
                IllegalStateException("Pasta de backup não encontrada")
            )
            // Listar arquivos de backup
            val backupFiles = listBackupFiles(folderId)
            val latestBackup = backupFiles.maxByOrNull { it.createdTime }
                ?: return@withContext Result.failure(
                    IllegalStateException("Nenhum arquivo de backup encontrado")
                )

            // Baixar o arquivo mais recente
            val jsonContent = downloadFileContent(latestBackup.id)

            // Converter JSON para lista de notas
            val notes = jsonContent.toNotes()

            // Limpar banco de dados atual e inserir as notas do backup
            notesDao.getAll().first().forEach { notesDao.deleteNote(it.id) }
            notes.forEach { notesDao.insert(it) }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun getOrCreateBackupFolder(): String {
        return backupFolderId ?: run {
            val folderId = findBackupFolder() ?: createBackupFolder()
            backupFolderId = folderId
            folderId
        }
    }

    private fun findBackupFolder(): String? {
        val result = driveService.files().list()
            .setQ("name='$BACKUP_FOLDER_NAME' and mimeType='application/vnd.google-apps.folder' and trashed=false")
            .setSpaces("drive")
            .execute()
        return result.files.firstOrNull()?.id
    }

    private fun createBackupFolder(): String {
        val folderMetadata = File().apply {
            name = BACKUP_FOLDER_NAME
            mimeType = "application/vnd.google-apps.folder"
        }

        val folder = driveService.files().create(folderMetadata)
            .setFields("id")
            .execute()
        return folder.id
    }

    private fun getBackupFolderId(): String? {
        return backupFolderId ?: findBackupFolder().also { backupFolderId = it }
    }

    private fun listBackupFiles(folderId: String): List<DriveFile> {
        val result = driveService.files().list()
            .setQ("'$folderId' in parents and name contains '$BACKUP_FILE_PREFIX' and trashed=false")
            .setSpaces("drive")
            .setFields("files(id, name, createdTime)")
            .execute()
        return result.files.map {
            DriveFile(it.id, it.name, it.createdTime.toString())
        }
    }

    private fun uploadFileToDrive(folderId: String, fileName: String, content: String) {
        val fileMetadata = File().apply {
            name = fileName
            mimeType = MIME_TYPE_JSON
            parents = listOf(folderId)
        }
        val mediaContent = ByteArrayContent(MIME_TYPE_JSON, content.toByteArray())

        driveService.files().create(fileMetadata, mediaContent)
            .setFields("id")
            .execute()
    }

    private fun downloadFileContent(fileId: String): String {
        val outputStream = ByteArrayOutputStream()
        driveService.files().get(fileId).executeMediaAndDownloadTo(outputStream)
        return outputStream.toString()
    }

    // Conversão para Json
    private fun List<Note>.toJson(): String {
        return Gson().toJson(this)
    }

    // Conversão para Notes
    private fun String.toNotes(): List<Note> {
        return try {
            Gson().fromJson(this, Array<Note>::class.java).toList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    data class DriveFile(
        val id: String,
        val name: String,
        val createdTime: String
    )
}