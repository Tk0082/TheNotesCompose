@file:Suppress("UNUSED_VARIABLE")

package com.betrend.cp.thenotes.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.betrend.cp.thenotes.R
import com.betrend.cp.thenotes.model.NoteViewModel
import com.betrend.cp.thenotes.ui.theme.BackTxtD
import com.betrend.cp.thenotes.ui.theme.BorderBtn
import com.betrend.cp.thenotes.ui.theme.ButtonD
import com.betrend.cp.thenotes.ui.theme.ButtonL
import com.betrend.cp.thenotes.ui.theme.Graffit
import com.betrend.cp.thenotes.ui.theme.GraffitD
import com.betrend.cp.thenotes.ui.theme.GraffitL
import com.betrend.cp.thenotes.ui.theme.Transparent
import com.betrend.cp.thenotes.ui.theme.White
import com.betrend.cp.thenotes.ui.theme.YellowNote
import com.betrend.cp.thenotes.ui.theme.YellowNote1
import com.betrend.cp.thenotes.ui.theme.YellowNote2
import com.betrend.cp.thenotes.ui.theme.YellowNote4
import com.google.android.material.textfield.TextInputLayout

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteScreen(viewModel: NoteViewModel = viewModel()){
    val notes by viewModel.notes.collectAsState(initial = emptyList())
    val pinnedNote by viewModel.pinnedNotes.collectAsState(initial = emptyList())
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.background(YellowNote2)
    ) {
        Column (
            Modifier
                .fillMaxSize()
                .background(YellowNote1)
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            val backTxshape = listOf(White, BackTxtD)
            val brushtx = Brush.verticalGradient(backTxshape)

            val backNote = listOf(White, YellowNote4)
            val brushnote = Brush.verticalGradient(backNote)

            val backBtn = listOf(ButtonL, ButtonD)
            val backbtn = Brush.verticalGradient(backBtn)

            var name by remember { mutableStateOf(TextFieldValue("")) }
            var note by remember { mutableStateOf(TextFieldValue("")) }
            Row (
                modifier = Modifier.padding(5.dp)
            ){
                BasicTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier
                        .height(50.dp)
                        .padding(horizontal = 5.dp)
                        .weight(1f, true)
                        .shadow(3.dp, RoundedCornerShape(50.dp)),
                    singleLine = true,
                    textStyle = TextStyle(
                        color = GraffitD,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    ),
                    cursorBrush = SolidColor(YellowNote),
                    decorationBox = { innerTextField ->
                        Box (
                            modifier = Modifier
                                .fillMaxSize()
                                .background(brushtx, RoundedCornerShape(50.dp)),
                            contentAlignment = Alignment.Center
                        ){
                            if (name.text.isEmpty()) {
                                Text(
                                    text = "Título",
                                    color = GraffitL,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            }
                            innerTextField()
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Words
                    )
                )
                Box (
                    Modifier
                        .padding(horizontal = 5.dp)
                        .border(1.dp, BorderBtn, RoundedCornerShape(50.dp))
                        .shadow(10.dp, RoundedCornerShape(50.dp), clip = true )
                ) {
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .background(backbtn, RoundedCornerShape(50.dp))
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(Transparent)
                    ) {
                        Text(
                            text = "SALVAR",
                            fontSize = 18.sp,
                            color = GraffitD,
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            ),
                        )
                        Image(
                            painter = painterResource(id = R.mipmap.thenotes),
                            contentDescription = null,
                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp)
                                .padding(5.dp, 0.dp, 0.dp, 6.dp)
                        )
                    }
                }
            }
            BasicTextField(
                modifier = Modifier
                    .shadow(2.dp, RoundedCornerShape(8.dp)),
                value = note,
                textStyle = TextStyle(
                    color = Graffit,
                    fontSize = 16.sp,
                ),
                onValueChange = { note = it },
                cursorBrush = SolidColor(YellowNote),
                decorationBox = { innerTextField ->
                    Box (
                        modifier = Modifier
                            .fillMaxSize()
                            .background(brushnote, RoundedCornerShape(8.dp)),
                    ){
                        if (note.text.isEmpty()) {
                            Text(
                                text = "Digite sua nota..",
                                modifier = Modifier.padding(5.dp),
                                color = GraffitL,
                                style = TextStyle(
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 16.sp
                                ),
                            )
                        }
                        innerTextField()
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Default
                )

            )
        }
    }
}

@Preview(name = "NoteAct", showSystemUi = true, showBackground = true)
@Composable
fun NotePreview(){
    NoteScreen()
}