package ui.screen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp

@Composable
fun AnalysisWindow() {
    var variants by remember { mutableStateOf(sampleVariants) }
    val criteria by remember { mutableStateOf(sampleCriteria) }

    // Vertical scroll state for the criteria
    val verticalScrollState = rememberScrollState()

    Column {
        // Header (Variant Titles)
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(Modifier.width(200.dp)) // Empty space for the criteria column
            LazyRow { // This allows the variant headers to scroll horizontally without explicit horizontalScroll modifier
                items(variants) { variant ->
                    VariantHeader(variant) { updatedVariant ->
                        variants = variants.map { if (it.id == updatedVariant.id) updatedVariant else it }
                    }
                }
            }
        }

        // Main content with horizontal scrolling for variants and vertical scrolling for criteria
        Row(modifier = Modifier.weight(1f)) {
            // Fixed Criteria Column, this does not scroll horizontally
            CriteriaColumn(criteria, Modifier.verticalScroll(verticalScrollState))

            // Horizontal Scrolling for Variants
            LazyRow {
                items(variants) { variant ->
                    VariantColumn(variant, criteria)
                }
            }
        }

        // Footer (Total row)
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(Modifier.width(200.dp)) // Empty space for the criteria column
            LazyRow { // Use LazyRow for horizontal scrolling of the totals
                items(variants) { variant ->
                    TotalRow(variant)
                }
            }
        }
    }
}


@Composable
fun VariantHeader(variant: Variant, onVariantUpdated: (Variant) -> Unit) {
    Column(modifier = Modifier.width(200.dp).padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        var text by remember { mutableStateOf(TextFieldValue(variant.name)) }

        OutlinedTextField(
            value = text,
            onValueChange = { newText ->
                text = newText
                onVariantUpdated(variant.copy(name = newText.text))
            },
            label = { Text("Variant Name") }
        )
        Text("Ranking: ${variant.ranking}", style = MaterialTheme.typography.caption)
        // Implement appearance change for top 3 variants here
    }
}

@Composable
fun CriteriaColumn(criteria: List<Criterion>, modifier: Modifier) {
    Column(modifier) {
        criteria.forEach { criterion ->
            Text(text = criterion.name, modifier = Modifier.padding(8.dp))
            // If you have more properties to display, add them here.
        }
    }
}

@Composable
fun VariantColumn(variant: Variant, criteria: List<Criterion>) {
    LazyColumn(modifier = Modifier.width(IntrinsicSize.Min)) {
        item { Text(variant.name, style = MaterialTheme.typography.h6, modifier = Modifier.padding(8.dp)) }
        criteria.forEach { criterion ->
            item {
                Text(
                    text = "${criterion.name}: [Variant Specific Value]", // Replace with actual data retrieval logic
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        item { TotalRow(variant) } // This can be moved out if the total row is not supposed to scroll with criteria.
    }
}

@Composable
fun TotalRow(variant: Variant) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Total for ${variant.name}: [Calculate Total]", // Replace with actual total calculation logic
            style = MaterialTheme.typography.subtitle1
        )
    }
}

// Sample data - Replace with actual data
val sampleVariants = listOf(
    Variant(1, "Pixel 8 Pro", 1),
    Variant(2, "iPhone 15 Pro", 2),
    Variant(3, "Galaxy S23", 3)
)

val sampleCriteria = listOf(
    Criterion(1, "Price"),
    Criterion(2, "Manufacturer"),
    // Add other criteria as needed
)

data class Variant(
    val id: Int,
    var name: String,
    val ranking: Int
    // Other properties
)

data class Criterion(
    val id: Int,
    var name: String
    // Other properties
)