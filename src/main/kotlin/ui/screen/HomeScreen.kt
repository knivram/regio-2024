package ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import service.PrioritizeService
import ui.component.input.AddInput

class HomeScreen: Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var isHovered by remember { mutableStateOf(false) }

        Column {
            Text("Analysis", style = MaterialTheme.typography.h6)
            AddInput(
                onAdd = PrioritizeService.AnalyzeRepository::new,
                label = { Text("New analysis") },
            )
            LazyColumn {
                items(PrioritizeService.AnalyzeRepository.getAll()) {
                    Row(
                        modifier = Modifier
                            .border(BorderStroke(1.dp, color = Color.Black))
                            .padding(8.dp)
                            .background(if (isHovered) Color.LightGray else Color.Transparent) // Change background color on hover
                            .pointerMoveFilter(
                                onEnter = { isHovered = true; true },
                                onExit = { isHovered = false; true }
                            )
                            .pointerHoverIcon(PointerIcon.Hand)
                            .clickable(enabled = true) {
                                navigator.push(AnalyzeScreen(it.id))
                            }
                    ) {
                        Text(it.name)
                        Spacer(modifier = Modifier.width(16.dp))
                        IconButton(
                            onClick = { PrioritizeService.AnalyzeRepository.remove(it.id) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Button"
                            )
                        }
                    }
                }
            }
        }
    }
}