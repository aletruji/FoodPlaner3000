package me.trujillo.foodplaner3000.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import me.trujillo.foodplaner3000.data.enums.Unit1

@Composable
fun UnitDropdownMenu(
    selectedUnit: Unit1,
    onUnitSelected: (Unit1) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        // Button zum Öffnen des Menüs
        TextButton(onClick = { expanded = true }) {
            Text("Selected Unit: ${selectedUnit.name}")
        }

        // Dropdown-Menü
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Unit1.values().forEach { unit ->
                DropdownMenuItem(
                    text = { Text(unit.name) }, // Zeigt den Namen der Einheit
                    onClick = {
                        onUnitSelected(unit) // Rückgabe der ausgewählten Einheit
                        expanded = false // Menü schließen
                    }
                )
            }
        }
    }
}