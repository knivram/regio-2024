import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cafe.adriel.voyager.navigator.Navigator
import ui.screen.HomeScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        Box(
            modifier = Modifier.padding(16.dp),
        ) {
            Navigator(HomeScreen())
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Prioritize") {
        App()
    }
}
