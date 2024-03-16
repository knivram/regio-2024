package ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import service.Criterion
import service.PrioritizeService
import ui.component.input.NumberInputField
import java.util.*

data class AddCriterionScreen(val analyzeId: UUID): Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val analyze = PrioritizeService.AnalyzeRepository.getOne(analyzeId)
        var criterionName by remember { mutableStateOf("") }
        var criterionWeight by remember { mutableStateOf(0) }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        Column {
            Button(
                onClick = navigator::pop
            ) {
                Text("Back")
            }
            if (analyze == null) {
                Text("Analyze Not found")
                return
            }

            Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row {
                    OutlinedTextField(
                        value = criterionName,
                        onValueChange = { criterionName = it },
                        label = { Text("Criterion Name") },
                        singleLine = true
                    )
                    NumberInputField(
                        value = criterionWeight,
                        onValueChange = { criterionWeight = it },
                        label = { Text("Criterion Weight") }
                    )
                }
                Row {
                    Button(
                        onClick = { navigator.pop() }
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            val newCriterion = Criterion(
                                id = UUID.randomUUID(),
                                name = criterionName,
                                weight = criterionWeight,
                            )
                            try {
                                analyze.criterionRepo.new(newCriterion)
                                errorMessage = null
                                navigator.pop()
                            } catch (e: RuntimeException) {
                                errorMessage = e.message
                            }
                        }
                    ) {
                        Text("Save Result")
                    }
                }
                Row {
                    errorMessage?.let { Text(it, color = MaterialTheme.colors.error) }
                }
            }

        }

    }


}
