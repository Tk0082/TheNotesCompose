package com.betrend.cp.thenotes.ui.screen.auth

import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.betrend.cp.thenotes.data.remote.entities.UserData
import com.betrend.cp.thenotes.ui.theme.Graffit
import com.betrend.cp.thenotes.ui.theme.GraffitD
import com.betrend.cp.thenotes.ui.theme.GraffitL
import com.betrend.cp.thenotes.ui.theme.GraffitLLL
import com.betrend.cp.thenotes.ui.theme.Transparent
import com.betrend.cp.thenotes.ui.theme.White
import com.betrend.cp.thenotes.ui.theme.YellowNote
import com.betrend.cp.thenotes.utils.brushBackButton
import com.betrend.cp.thenotes.utils.brushBorderButton

@Composable
fun ProfileScreen(
    user: UserData,
    onLogout: () -> Unit,
    onUpdateNotes: () -> Unit,
    onRestoreNotes: () -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(scrollState)
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Foto do usuário
        if (user.photoUrl != null) {
            Box(modifier = Modifier
                .border(1.dp, YellowNote, RoundedCornerShape(60.dp))
                .shadow(3.dp, RoundedCornerShape(60.dp), true, Graffit)
                .background(GraffitLLL, RoundedCornerShape(60.dp))
            ){
                AsyncImage(
                    model = user.photoUrl,
                    contentDescription = "Foto do usuário",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(GraffitLLL),
                    contentScale = ContentScale.Crop
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(GraffitLLL),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = user.username.take(1).uppercase(),
                    color = White,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Nome do usuário
        Text(
            text = user.username,
            color = Graffit,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        // Email
        Text(
            text = user.email,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        HorizontalDivider(color = GraffitLLL, thickness = 1.dp)

        Spacer(modifier = Modifier.height(40.dp))

        // Botão para atualizar notas
        Button(
            onClick = onUpdateNotes,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(.9f)
                .padding(5.dp)
                .shadow(3.dp, RoundedCornerShape(50.dp), true, GraffitD)
                .border(1.dp, brushBorderButton(), RoundedCornerShape(50.dp))
                .background(
                    brush = brushBackButton(),
                    shape = RoundedCornerShape(50.dp)
                ),
            colors = ButtonColors(
                containerColor = Transparent,
                contentColor = Graffit,
                disabledContentColor = GraffitL,
                disabledContainerColor = Transparent
            ),
        ) {
            Text(
                text = "Atualizar Notas no Drive",
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    shadow = Shadow(
                        GraffitL,
                        offset = Offset.VisibilityThreshold,
                        blurRadius = 5f
                    )
                )
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = onRestoreNotes,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(.9f)
                .padding(5.dp)
                .shadow(3.dp, RoundedCornerShape(50.dp), true, GraffitD)
                .border(1.dp, brushBorderButton(), RoundedCornerShape(50.dp))
                .background(
                    brush = brushBackButton(),
                    shape = RoundedCornerShape(50.dp)
                ),
            colors = ButtonColors(
                containerColor = Transparent,
                contentColor = Graffit,
                disabledContentColor = GraffitL,
                disabledContainerColor = Transparent
            ),
        ) {
            Text(
                text = "Restaurar Notas do Drive",
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    shadow = Shadow(
                        GraffitL,
                        offset = Offset.VisibilityThreshold,
                        blurRadius = 5f
                    )
                )
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        // Botão de logout
        Text(
            text = "Sair",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onLogout() }
        )
    }
}

@Preview
@Composable
fun ProfileScreenPreview(){
    val user = UserData("username", "email@gmail.com", "https://cdn.iconscout.com/icon/free/png-256/free-user-icon-download-in-svg-png-gif-file-formats--profile-avatar-account-person-app-interface-pack-icons-1401302.png?f=webp")
    ProfileScreen(
        user,
        onLogout = {},
        onUpdateNotes = {},
        onRestoreNotes = {}
    )
}