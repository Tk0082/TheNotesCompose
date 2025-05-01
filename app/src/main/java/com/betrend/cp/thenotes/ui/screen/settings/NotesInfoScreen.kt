package com.betrend.cp.thenotes.ui.screen.settings

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.betrend.cp.thenotes.ui.theme.TheNotesTheme
import com.betrend.cp.thenotes.ui.theme.Transparent
import com.betrend.cp.thenotes.ui.theme.YellowNote
import com.betrend.cp.thenotes.utils.brushBackButton
import com.betrend.cp.thenotes.utils.brushBorderButton
import com.betrend.cp.thenotes.utils.brushPanel

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "IntentReset")
@Composable
fun NotesInfoScreen(){
    TheNotesTheme {
        val context = LocalContext.current
        var i: Intent
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(10.dp)
        ) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.app_name),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = TextStyle(
                        fontSize = 45.sp,
                        fontWeight = FontWeight.Bold,
                        shadow = Shadow(
                            MaterialTheme.colorScheme.onSurfaceVariant,
                            offset = Offset.VisibilityThreshold,
                            blurRadius = 15f
                        )
                    ),
                    modifier = Modifier
                        .padding(top = 50.dp, bottom = 20.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = brushPanel(),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .border(1.dp, YellowNote, RoundedCornerShape(20.dp))
                        .shadow(3.dp, RoundedCornerShape(20.dp), true, Graffit)
                        .background(
                            brush = brushPanel(),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Image(
                            modifier = Modifier
                                .size(390.dp)
                                .rotate(25f)
                                .padding(top = 50.dp, start = 50.dp)
                                .align(Alignment.TopCenter)
                                .alpha(.3f),
                            painter = painterResource(id = R.mipmap.thenotes),
                            contentDescription = ""
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(R.string.tx_presentation_app),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 30.dp,
                                        start = 20.dp,
                                        end = 20.dp,
                                        bottom = 30.dp
                                    ),
                                style = TextStyle(
                                    color = Graffit,
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
                            Text(
                                text = "AUTORES",
                                style = TextStyle(
                                    color = GraffitD,
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    shadow = Shadow(
                                        GraffitL,
                                        offset = Offset.VisibilityThreshold,
                                        blurRadius = 5f
                                    )
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                            )
                            Button(
                                onClick = {
                                    val url = "https://github.com/gabrieldiferreira/"
                                    i = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    }
                                    context.startActivity(i)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .alpha(.9f)
                                    .padding(vertical = 5.dp, horizontal = 50.dp)
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
                                        text = "Gabriel Ferreira",
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
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            )
                            Button(
                                onClick = {
                                    val url = "https://github.com/Tk0082/"
                                    i = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    }
                                    context.startActivity(i)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .alpha(.9f)
                                    .padding(vertical = 5.dp, horizontal = 50.dp)
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
                                        text = "Alan Souza",
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
                            )
                            Text(
                                text = "NOVIDADES",
                                style = TextStyle(
                                    color = GraffitD,
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    shadow = Shadow(
                                        GraffitL,
                                        offset = Offset.VisibilityThreshold,
                                        blurRadius = 5f
                                    )
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                            )
                            Button(
                                onClick = {
                                    val url = "https://play.google.com/store/apps/details?id=com.gbferking.thenotes"
                                    i = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    }
                                    context.startActivity(i)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .alpha(.9f)
                                    .padding(vertical = 5.dp, horizontal = 50.dp)
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
                                        text = "Google-PlayStore",
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
                            )
                            Text(
                                text = "CONTATO",
                                style = TextStyle(
                                    color = GraffitD,
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    shadow = Shadow(
                                        GraffitL,
                                        offset = Offset.VisibilityThreshold,
                                        blurRadius = 5f
                                    )
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                            )
                            Button(
                                onClick = {
                                    val email = "thenotes.app00@gmail.com"
                                    i = Intent(Intent.ACTION_SENDTO).apply {
                                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                        type = "text/plain"
                                        data = Uri.parse("mailto:$email")
                                        putExtra(Intent.EXTRA_SUBJECT, "Feedback TheNotes App")
                                        putExtra(Intent.EXTRA_TEXT, "Envie seu Feedback..")
                                    }
                                    context.startActivity(Intent.createChooser(i, "Feedback TheNotes"))
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .alpha(.9f)
                                    .padding(
                                        top = 5.dp, start = 50.dp, end = 50.dp, bottom = 50.dp
                                    )
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
                                        text = "Gmail TheNotes",
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
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(name = "Info")
@Composable
fun InfoPreview(){
    NotesInfoScreen()
}