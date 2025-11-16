package me.trujillo.foodplaner3000.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import me.trujillo.foodplaner3000.data.enums.Unit1

@Composable
fun AddItemDialog(

    onDismiss: () -> Unit,
    onAddItem: (String, Int, Unit1) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) } // Steuert die Sichtbarkeit des Menüs
    var selectedUnit by remember { mutableStateOf(Unit1.x) } // Aktuell ausgewählte Einheit



    AlertDialog(
        onDismissRequest = { onDismiss() },
        containerColor = Color(0xFF212121),
        title = {
            Text(
                text = "add new item"
            ) },
        text = {
            Column {

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = if (quantity == 0) "" else quantity.toString(), // Zeige leeres Feld, wenn 0
                    onValueChange = {

                        quantity = it.toIntOrNull() ?: 1

                    },
                    label = { Text("quantity") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                UnitDropdownMenu(selectedUnit = selectedUnit,
                    onUnitSelected = { selectedUnit = it } )




            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (name.isNotBlank()) {
                    val finalQuantity = if (quantity > 0) quantity else 1 // Standardwert 1 setzen
                    onAddItem(name, finalQuantity, selectedUnit)
                }
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}

