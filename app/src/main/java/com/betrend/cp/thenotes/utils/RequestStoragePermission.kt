package com.betrend.cp.thenotes.utils

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun RequestStoragePermission() {
    val context = LocalContext.current
    val activity = context as Activity
    var showRationale by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            showRationale = true
        }
    }

    if (!PermissionUtils.hasStoragePermission(context)) {
        if (showRationale) {
            AlertDialog(
                onDismissRequest = { showRationale = false },
                title = { Text("Permissão necessária") },
                text = { Text("O app precisa acessar seus arquivos para funcionar corretamente") },
                confirmButton = {
                    Button(onClick = {
                        PermissionUtils.requestStoragePermission(activity, 100)
                        showRationale = false
                    }) {
                        Text("OK")
                    }
                }
            )
        } else {
            LaunchedEffect(Unit) {
                PermissionUtils.requestStoragePermission(activity, 100)
            }
        }
    }
}