package com.betrend.cp.thenotes.ui.screen

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.betrend.cp.thenotes.ui.screen.auth.NotesMailScreen
import com.betrend.cp.thenotes.ui.screen.notes.NotesListScreen
import com.betrend.cp.thenotes.ui.theme.TheNotesTheme
import com.betrend.cp.thenotes.ui.theme.YellowNoteD
import com.betrend.cp.thenotes.ui.theme.YellowNoteDD
import com.betrend.cp.thenotes.ui.theme.YellowNoteL
import com.betrend.cp.thenotes.ui.theme.YellowNoteLL
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(){
    val context = LocalContext.current
    // Propriedades da BottomBar
    var selectedIndex by remember { mutableIntStateOf(0) }
    val navigationBarItems = remember { NavigationBarItems.entries.toTypedArray() }
    var currentScreen by remember { mutableStateOf(NavigationBarItems.Note) }

    // Finalizar App ou voltar para Home
    BackHandler {
        if (currentScreen == NavigationBarItems.Note){
            (context as? Activity)?.finish()
        } else {
            currentScreen = NavigationBarItems.Note
            selectedIndex = currentScreen.ordinal
        }
    }

    TheNotesTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                AnimatedNavigationBar(
                    selectedIndex = selectedIndex,
                    modifier = Modifier
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.surface),
                    cornerRadius = shapeCornerRadius(topRight = 34.dp, topLeft = 34.dp, bottomRight = 0.dp, bottomLeft = 0.dp),
                    ballAnimation = Parabolic(tween(200)),
                    indentAnimation = Height(tween(180)),
                    barColor = MaterialTheme.colorScheme.secondary,
                    ballColor = MaterialTheme.colorScheme.secondary
                ) {
                    navigationBarItems.forEach { item ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .noRippleClickable {
                                    selectedIndex = item.ordinal
                                    currentScreen =
                                        NavigationBarItems.entries.toTypedArray()[selectedIndex]
                                },
                            contentAlignment = Alignment.Center,
                            content = {
                                Icon(
                                    modifier = Modifier.size(26.dp),
                                    imageVector = item.icon,
                                    contentDescription = item.description,
                                    tint = if (selectedIndex == item.ordinal) {
                                        MaterialTheme.colorScheme.tertiary
                                    } else {
                                        MaterialTheme.colorScheme.onPrimary
                                    }
                                )
                            }
                        )
                    }
                }
            }
        ){innerPadding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)) {
                // Seleção das Screens associada ao BottomnavigationBar Click
                when (currentScreen) {
                    NavigationBarItems.Note -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotesListScreen()
                    }
                    NavigationBarItems.Info -> NotesMailScreen()
                }
            }
        }
    }

}

@Preview(name = "Main")
@Composable
fun MainPreview(){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        MainScreen()
    }
}

// Items de navegação e Screens associadas
enum class NavigationBarItems(val icon: ImageVector, val description: String) {
    Note(icon = Icons.Default.Home, description = "Add Nota"),
    Info(icon = Icons.Default.Person, description = "Sobre o App")
}

@Composable
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed{
    Modifier.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}
