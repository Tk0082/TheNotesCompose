package com.betrend.cp.thenotes.ui.screen.auth

import android.content.Context
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.betrend.cp.thenotes.R
import com.betrend.cp.thenotes.ui.theme.Graffit
import com.betrend.cp.thenotes.ui.theme.GraffitD
import com.betrend.cp.thenotes.ui.theme.GraffitL
import com.betrend.cp.thenotes.ui.theme.GraffitLL
import com.betrend.cp.thenotes.ui.theme.Transparent
import com.betrend.cp.thenotes.utils.brushBackButton
import com.betrend.cp.thenotes.utils.brushBorderButton

@Composable
fun LoginScreen(
    context: Context,
    onLoginClick: () -> Unit
) {
    val scroolState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroolState)
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.mipmap.drive_ic_yellow),
            contentDescription = "Google Drive Logo",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 32.dp)
        )
        Text(
            text = "Acesse seu Google Drive",
            color = Graffit,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Conecte-se à sua conta do Google para fazer upload de suas notas no Drive, e resgate em qualquer aparelho.",
            color = GraffitL,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 48.dp)
        )
        // Login
        Button(
            onClick = {
                onLoginClick()
            },
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
            content = {
                Text(
                    text = "Continuar com Google",
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        shadow = Shadow(
                            GraffitL,
                            offset = Offset.VisibilityThreshold,
                            blurRadius = 5f
                        )
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Ao continuar, você concorda com nossos Termos de Serviço e Política de Privacidade",
            color = GraffitLL,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 32.dp)
        )
    }

}

@Preview
@Composable
fun LoginScreenPreview(){
    LoginScreen(
        context = LocalContext.current,
        onLoginClick = { }
    )
}