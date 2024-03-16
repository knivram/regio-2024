package ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import service.PrioritizeService
import ui.component.input.AddInput

class HomeScreen: Screen {
    @Composable
    override fun Content() {
        Column {
            Text("Analysis", style = MaterialTheme.typography.h6)
            AddInput(
                onAdd = PrioritizeService.AnalyzeRepository::new,
                label = { Text("New analysis") },
            )
            LazyColumn {
                items(PrioritizeService.AnalyzeRepository.getAll()) {
                    Row {
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