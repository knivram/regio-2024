package ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import service.ExampleService

class HomeScreen: Screen {
    @Composable
    override fun Content() {
        Column {
            Text(ExampleService.database.version.toString())
            Text("Home Page")
        }
    }
}