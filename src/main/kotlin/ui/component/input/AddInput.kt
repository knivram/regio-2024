package ui.component.input

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon

@Composable
fun AddInput(
    onAdd: (input: String) -> Unit,
    placeHolder: @Composable() (() -> Unit)? = null,
    label: @Composable() (() -> Unit)? = null,
) {
    var newEventName by remember { mutableStateOf("") }
    var exceptionMessage by remember { mutableStateOf<String?>(null) }
    Column {
        OutlinedTextField(
            placeholder = placeHolder,
            label = label,
            value = newEventName,
            onValueChange = {
                newEventName = it
            },
            singleLine = true,
            trailingIcon = {
                IconButton(
                    onClick = {
                        try {
                            onAdd(newEventName)
                            exceptionMessage = null
                        } catch (e: Exception) {
                            exceptionMessage = e.message
                        }
                        newEventName = ""
                    },
                    modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Button"
                    )
                }
            },
        )
        exceptionMessage?.let { Text(it, color = MaterialTheme.colors.error) }
    }
}