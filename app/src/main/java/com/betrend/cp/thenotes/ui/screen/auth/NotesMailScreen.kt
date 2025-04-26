package com.betrend.cp.thenotes.ui.screen.auth

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.betrend.cp.thenotes.NotesInfoActivity
import com.betrend.cp.thenotes.R
import com.betrend.cp.thenotes.data.remote.auth.AuthState
import com.betrend.cp.thenotes.data.remote.auth.GoogleAuthManager
import com.betrend.cp.thenotes.data.remote.entities.UserData
import com.betrend.cp.thenotes.ui.theme.GraffitL
import com.betrend.cp.thenotes.ui.theme.NoteError
import com.betrend.cp.thenotes.ui.theme.TheNotesTheme
import com.betrend.cp.thenotes.ui.theme.White
import com.betrend.cp.thenotes.ui.theme.YellowNote
import com.betrend.cp.thenotes.ui.theme.YellowNoteDD
import com.betrend.cp.thenotes.ui.theme.YellowNoteDDD
import com.betrend.cp.thenotes.ui.theme.YellowNoteLL
import com.betrend.cp.thenotes.ui.theme.YellowNoteLLL
import com.betrend.cp.thenotes.ui.viewmodel.NotesMailViewModel
import com.betrend.cp.thenotes.ui.viewmodel.factory.NotesMailViewModelFactory
import com.betrend.cp.thenotes.utils.brushMenu
import com.betrend.cp.thenotes.utils.showMessage
import com.betrend.cp.thenotes.utils.showToast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.delay
import java.io.File

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotesMailScreen(){
    val context = LocalContext.current
    val viewModel: NotesMailViewModel = viewModel(factory = NotesMailViewModelFactory(context) )
    var authState by remember { mutableStateOf<AuthState>(AuthState.Login) }
    val googleAuthManager = remember { GoogleAuthManager(context) }

    var showDialog by remember { mutableStateOf(false) }
    var exportResult by remember { mutableStateOf<File?>(null) }

    // Launcher para o resultado do login
    val signInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            val account = googleAuthManager.handleSignInResult(task)
            account?.let {
                viewModel.updateAccount(it)
                authState = AuthState.LoggedIn(
                    UserData(
                        username = it.displayName ?: "Usuário Google",
                        email = it.email ?: "",
                        photoUrl = it.photoUrl?.toString()
                    )
                )
            }
        }
    }

    val downloadState = viewModel.downloadState

    // Obter a conta atual do ViewModel
    val currentAccount = viewModel.uiState.account

    // Efeito de verificação de Usuári Logado
    LaunchedEffect(Unit) {
        val lastAccount = googleAuthManager.getLastSignedInAccount()
        lastAccount?.let { account ->
            viewModel.updateAccount(account)
            authState = AuthState.LoggedIn(
                UserData(
                    username = account.displayName ?: "Usuário Google",
                    email = account.email ?: "",
                    photoUrl = account.photoUrl?.toString()
                )
            )
        }
    }

    TheNotesTheme {
        Scaffold (
            modifier = Modifier
                .fillMaxSize()
                .background(YellowNoteLL)
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(YellowNoteLL)
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = {
                        Intent(context, NotesInfoActivity::class.java).also {
                            context.startActivity(it)
                        }
                    },
                    content = {
                        Image(painter = painterResource(id = R.drawable.ic_info),
                            contentDescription = null
                        )
                    },
                )
                Box(
                    modifier = Modifier
                        .background(YellowNoteLL)
                ) {
                    when (val currentState = authState) {
                        is AuthState.Login -> LoginScreen(
                            onLoginClick = {
                                signInLauncher.launch(googleAuthManager.getSignInIntent().signInIntent)
                            },
                            context = context
                        )
                        is AuthState.LoggedIn -> {
                            if (currentAccount != null) {
                                ProfileScreen(
                                    user = currentState.user,
                                    onLogout = {
                                        googleAuthManager.signOut()
                                        authState = AuthState.Login
                                    },
                                    onUpdateNotes = {
                                        showDialog = true
                                        // Chamar a função de exportação em uma coroutine
                                        viewModel.exportNotes { file ->
                                            exportResult = file
                                        }
                                    },
                                    onRestoreNotes = {
                                        viewModel.uiState.account?.let { account ->
                                            viewModel.downloadAndUpdateNotesFromDrive(account)
                                        }
                                    }
                                )
                            }
                            if (showDialog) {
                                AlertDialog(
                                    onDismissRequest = { showDialog = false },
                                    title = {
                                        Text(if (exportResult != null) "Backup de Notas" else "Backup falhou")
                                    },
                                    text = {
                                        Text(
                                            if (exportResult != null)
                                                "Notas exportadas para: \n${exportResult?.name} \n\nConfirmar para subir JSON ao Drive."
                                            else
                                                "Falha ao exportar notas"
                                            ,
                                            style = TextStyle(
                                                textAlign = TextAlign.Start,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Normal
                                            )

                                        )
                                    },
                                    confirmButton = {
                                        Button(onClick = {
                                            currentAccount?.let {
                                                viewModel.uploadNotesToDrive(
                                                    it
                                                )
                                            }
                                            showDialog = false },
                                            colors = ButtonDefaults.buttonColors(containerColor = YellowNoteDD,
                                                contentColor = YellowNoteLLL
                                            )
                                        ) {
                                            Text("OK")
                                        }
                                    },
                                    containerColor = YellowNoteLL,
                                    modifier = Modifier.background(brush = brushMenu(), shape = RoundedCornerShape(28.dp)),
                                    titleContentColor = YellowNoteDDD,
                                    textContentColor = GraffitL,
                                )
                            }
                        }
                    }
                    // Mostrar feedback do upload
                    when (val state = viewModel.uploadState) {
                        is NotesMailViewModel.UploadState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center),
                                color = YellowNoteDDD
                            )
                            showToast(context, "Fazendo Upload das Notas para o Drive!")
                        }
                        is NotesMailViewModel.UploadState.Success -> {
                            LaunchedEffect(state) {
                                showToast(context, "Backup realizado com sucesso!")
                                viewModel.resetUploadState()
                            }
                        }
                        is NotesMailViewModel.UploadState.Error -> {
                            LaunchedEffect(state) {
                                showToast(context, "Erro: ${state.message}")
                                viewModel.resetUploadState()
                            }
                        }
                        else -> {}
                    }
                    // Feedback do estado de download
                    when (downloadState) {
                        is NotesMailViewModel.DownloadState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center),
                                color = YellowNoteDDD
                            )
                            showToast(context, "Restaurando as Notas do Drive!")
                        }
                        is NotesMailViewModel.DownloadState.Error -> {
                            val error =
                                downloadState.message
                            Text("Erro: $error", color = NoteError)
                            Button(onClick = { viewModel.resetDownloadState() }) {
                                Text("OK")
                            }
                        }
                        is NotesMailViewModel.DownloadState.Success -> {
                            LaunchedEffect(Unit) {
                                // Fechar o diálogo de sucesso após 2 segundos
                                delay(2000)
                                viewModel.resetDownloadState()
                            }
                            showMessage(context, "Notas restauradas com sucesso!")
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingDialog() {
    Dialog(onDismissRequest = {}) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(100.dp)
                .background(White, shape = RoundedCornerShape(8.dp))
        ) {
            CircularProgressIndicator(color = YellowNote)
        }
    }
}

@Preview(name = "MailScreen")
@Composable
fun MailPreview(){
    NotesMailScreen()
}