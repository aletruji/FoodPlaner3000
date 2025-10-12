package me.trujillo.foodplaner3000.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.trujillo.foodplaner3000.data.db.entities.ShoppingList
import me.trujillo.foodplaner3000.data.enums.Unit1

@Composable
fun EditItemDialog(
    item: ShoppingList,
    onDismiss: () -> Unit,
    onConfirm: (String, Int, Unit1) -> Unit
) {
    var name by remember { mutableStateOf(item.name) }
    var quantity by remember { mutableStateOf(item.quantity.toString()) }
    var selectedUnit by remember { mutableStateOf(item.unit) }


    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Edit Item") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Quantity") },
                    modifier = Modifier.fillMaxWidth()
                )
                UnitDropdownMenu(selectedUnit = selectedUnit,
                    onUnitSelected = { selectedUnit = it } )

            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (name.isNotBlank() && quantity.isNotBlank()) {
                    onConfirm(name, quantity.toInt(), selectedUnit)
                }
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}