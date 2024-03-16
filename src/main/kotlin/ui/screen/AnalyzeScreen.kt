package ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import service.PrioritizeService
import java.util.UUID

data class AnalyzeScreen(
    val analyzeId: UUID
): Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val analyze = PrioritizeService.AnalyzeRepository.getOne(analyzeId)

        Column {
            Button(
                onClick = {navigator.pop()}
            ) {
                Text("Back")
            }
            if (analyze == null){
                Text("This analyze doesn't exist")
            } else {
                Text(analyze.name, style = MaterialTheme.typography.h3)

                Row {
                    Button(
                        onClick = {navigator.push(AddCriterionScreen(analyzeId))}
                    ) {
                        Text("New Criterion")
                    }
                    Button(
                        onClick = {navigator.push(AddVariantScreen(analyzeId))}
                    ) {
                        Text("New Variant")
                    }

                }

                Row {
                    Column {
                        Text("Criterion")
                        LazyColumn {
                            items(analyze.criterionRepo.getAll().sortedBy { it.id }) {
                                Row {
                                    Text(it.name)
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text("${it.weight}%")
                                }
                            }
                        }
                    }
                    Spacer(Modifier.width(16.dp))
                    LazyRow {
                        items(analyze.variantRepo.getAll().sortedBy { it.name }) {variant ->
                            Column {
                                Text(variant.name)
                                LazyColumn {
                                    items(variant.properties.sortedBy { it.criterionId }) { property ->
                                        Text(property.value)
                                    }
                                }
                            }
                            Spacer(Modifier.width(16.dp))
                        }
                    }
                }
            }
        }
    }
}