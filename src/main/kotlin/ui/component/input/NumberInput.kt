package ui.component.input

import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*

@Composable
fun NumberInputField(
    value: Int,
    onValueChange: (Int) -> Unit,
    placeHolder: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
) {
    var text by remember { mutableStateOf(value.toString()) }

    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            text = newText.filter { it.isDigit() }
            onValueChange(text.toIntOrNull() ?: value)
        },
        label = label,
        placeholder = placeHolder,
        singleLine = true
    )
}