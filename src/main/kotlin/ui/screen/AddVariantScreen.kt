package ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import service.Analyze
import service.Criterion
import service.PrioritizeService
import service.Property
import service.Variant
import ui.component.input.DropDown
import java.util.UUID

data class AddVariantScreen(
    val analyzeId: UUID
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val analyze = PrioritizeService.AnalyzeRepository.getOne(analyzeId)
        var variantName by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf<String?>(null) }
        var isAddingProperty by remember { mutableStateOf(false) }
        var properties = remember { mutableStateListOf<Property>() }

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
            Text("Add Variant to ${analyze.name}")

            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = variantName,
                    onValueChange = { variantName = it },
                    label = { Text("Criterion Name") },
                    singleLine = true
                )
                if (isAddingProperty) {
                    AddProperty(
                        analyze = analyze,
                        onClose = { isAddingProperty = false },
                        onAdd = {newProperty ->
                            if (properties.find { it.criterionId == newProperty.criterionId} == null) {
                                properties.add(newProperty)
                                isAddingProperty = false
                            } else {
                                throw RuntimeException("There is already a property for this criterion")
                            }
                        }
                    )
                } else {
                    Button(
                        onClick = { isAddingProperty = true }
                    ) {
                        Text("Add property")
                    }
                }
                LazyColumn {
                    items(properties) {
                        Row {
                            Text("${analyze.criterionRepo.getOne(it.criterionId)!!.name}: ${it.value}")
                            Spacer(modifier = Modifier.width(16.dp))
                            IconButton(
                                onClick = { properties.remove(it) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Button"
                                )
                            }
                        }
                    }
                }
                Row {
                    Button(
                        onClick = { navigator.pop() }
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            if (properties.size == analyze.criterionRepo.getSize()) {
                                analyze.variantRepo.new(Variant(
                                    id = UUID.randomUUID(),
                                    name = variantName,
                                    properties = properties
                                ))
                                navigator.pop()
                            } else {
                                errorMessage = "Please provide a value for all criterion"
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

@Composable
fun AddProperty(
    analyze: Analyze,
    onClose: () -> Unit,
    onAdd: (property: Property) -> Unit
) {
    var selectedCriterion by remember { mutableStateOf<Criterion?>(null) }
    var propertyValue by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.border(BorderStroke(1.dp, Color.Black)),
    ) {

        Row {
            DropDown(
                items = analyze.criterionRepo.getAll().associate { it.id to it.name },
                onSelect = { selectedCriterion = analyze.criterionRepo.getOne(it) },
                value = selectedCriterion?.name ?: "",
                label = { Text("Criterion") },
            )
            OutlinedTextField(
                value = propertyValue,
                onValueChange = { propertyValue = it },
                label = { Text("Property Value") },
                singleLine = true
            )
        }

        Row {
            Button(
                onClick = onClose
            ) {
                Text("Cancel")
            }
            Button(
                onClick = {
                    val newProperty = Property(
                        value = propertyValue,
                        criterionId = selectedCriterion!!.id
                    )
                    try {
                        onAdd(newProperty)
                        errorMessage = null
                    } catch (e: Exception) {
                        errorMessage = e.message
                    }
                },
                enabled = selectedCriterion != null && propertyValue.isNotEmpty()
            ) {
                Text("Save Result")
            }
        }

        errorMessage?.let { Text(it, color = MaterialTheme.colors.error) }
    }
}