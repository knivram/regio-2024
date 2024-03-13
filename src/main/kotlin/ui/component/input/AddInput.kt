package ui.component.input

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
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
                    onAdd(newEventName)
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
}