package me.trujillo.foodplaner3000.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon // Richtiger Icon-Import
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.trujillo.foodplaner3000.Viewmodels.ShoppingViewModel
import me.trujillo.foodplaner3000.data.db.entities.ShoppingList
import me.trujillo.foodplaner3000.data.enums.Unit1


@Composable
fun IconButtonAdd(viewModel: ShoppingViewModel) {
    var showAddDialog by remember { mutableStateOf(false) }


    Box( // <- Jetzt gibt es einen BoxScope
        modifier = Modifier.fillMaxSize() // oder z. B. fillMaxWidth()
    ) {
        IconButton(
            onClick = {
                showAddDialog = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd) // Rechts platzieren
                .padding(25.dp)
                .size(30.dp) // Kleinere Größe des Buttons
                .background(Color(0xff8BC34A), shape = RoundedCornerShape(50)) // Runder Hintergrund
        ) {
            Icon(
                imageVector = Icons.Filled.Add, // Das "X"-Icon
                contentDescription = "Add",
                tint = Color.White,
                modifier = Modifier.size(16.dp) // Kleinere Größe des Icons
            )
        }
        if (showAddDialog) {
            AddItemDialog(

                onConfirm = { name, quantity, unit ->
                    val newItem = ShoppingList(
                        name = name,
                        quantity = quantity,
                        unit = unit
                    )
                    viewModel.addItem(newItem)
                    showAddDialog = false // Dialog schließen
                },
                onDismiss = {
                    showAddDialog = false // Dialog schließen
                }
            )
        }

    }

}

@Composable
fun IconButtonAddDish(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomEnd
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .padding(16.dp)
                .size(56.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Dish",
                tint = Color.White
            )
        }
    }
}

@Composable
fun AddDishDialog(
    initialName: String = "",
    initialDescription: String = "",
    initialInstructions: String = "",
    initialCategories: List<String> = emptyList(),
    initialIngredients: List<Pair<String, Pair<Double, Unit1>>> = emptyList(),
    onDismiss: () -> Unit,
    onSave: (
        String,                 // name
        String?,                // description
        String?,                // instructions
        List<String>,           // categories
        List<Pair<String, Pair<Double, Unit1>>>
    ) -> Unit
) {


    var name by remember { mutableStateOf(initialName) }
    var description by remember { mutableStateOf(initialDescription) }
    var instructions by remember { mutableStateOf(initialInstructions) }

    var categories by remember { mutableStateOf(initialCategories) }
    var ingredients by remember { mutableStateOf(initialIngredients) }


    // Kategorien

    var newCategory by remember { mutableStateOf("") }

    // Zutaten

    var newIngredientName by remember { mutableStateOf("") }
    var newIngredientQuantity by remember { mutableStateOf("") }
    var newIngredientUnit by remember { mutableStateOf(Unit1.g) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Neues Gericht") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {

                // -------------------------------
                // NAME
                // -------------------------------
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                // -------------------------------
                // DESCRIPTION
                // -------------------------------
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Beschreibung") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                // -------------------------------
                // INSTRUCTIONS
                // -------------------------------
                OutlinedTextField(
                    value = instructions,
                    onValueChange = { instructions = it },
                    label = { Text("Anleitung") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(20.dp))


                // ===================================================
                //                KATEGORIEN
                // ===================================================
                Text("Kategorien:", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)

                categories.forEach { cat ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("• $cat", color = Color.DarkGray)
                        Spacer(Modifier.width(8.dp))
                        IconButton(onClick = {
                            categories = categories - cat
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "delete", tint = Color.Red)
                        }
                    }
                }


                Spacer(Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = newCategory,
                        onValueChange = { newCategory = it },
                        label = { Text("Neue Kategorie") },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(Modifier.width(8.dp))

                    androidx.compose.material3.Button(
                        onClick = {
                            if (newCategory.isNotBlank()) {
                                categories = categories + newCategory.trim()
                                newCategory = ""
                            }
                        }
                    ) {
                        Text("Add")
                    }
                }

                Spacer(Modifier.height(20.dp))


                // ===================================================
                //                ZUTATEN
                // ===================================================
                Text("Zutaten:", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)

                ingredients.forEach { ing ->
                    val (iname, qtyUnit) = ing
                    val (qty, unit) = qtyUnit

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("• $iname ($qty ${unit.name})", color = Color.DarkGray)
                        Spacer(Modifier.width(8.dp))

                        IconButton(onClick = {
                            ingredients = ingredients - ing
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "delete ingredient", tint = Color.Red)
                        }
                    }
                }


                Spacer(Modifier.height(10.dp))

                // Eingabefelder für neue Zutat
                OutlinedTextField(
                    value = newIngredientName,
                    onValueChange = { newIngredientName = it },
                    label = { Text("Zutat") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = newIngredientQuantity,
                        onValueChange = { newIngredientQuantity = it },
                        label = { Text("Menge") },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(Modifier.width(8.dp))

                    // Dropdown für Unit
                    UnitDropdown(
                        selectedUnit = newIngredientUnit,
                        onSelect = { newIngredientUnit = it }
                    )
                }

                Spacer(Modifier.height(8.dp))

                androidx.compose.material3.Button(
                    onClick = {
                        val qty = newIngredientQuantity.toDoubleOrNull()
                        if (qty != null && newIngredientName.isNotBlank()) {
                            ingredients = ingredients + (
                                    newIngredientName to (qty to newIngredientUnit)
                                    )

                            newIngredientName = ""
                            newIngredientQuantity = ""
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Zutat hinzufügen")
                }
            }
        },
        confirmButton = {
            Text(
                "Speichern",
                modifier = Modifier
                    .clickable {
                        if (name.isNotBlank()) {
                            onSave(
                                name,
                                description,
                                instructions,
                                categories,
                                ingredients
                            )
                        }
                        onDismiss()
                    }
                    .padding(8.dp)
            )
        },
        dismissButton = {
            Text(
                "Abbrechen",
                modifier = Modifier
                    .clickable { onDismiss() }
                    .padding(8.dp)
            )
        }
    )
}


@Composable
fun UnitDropdown(
    selectedUnit: Unit1,
    onSelect: (Unit1) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Text(
            text = selectedUnit.name,
            modifier = Modifier
                .clickable { expanded = true }
                .padding(8.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Unit1.values().forEach { unit ->
                DropdownMenuItem(
                    text = { Text(unit.name) },
                    onClick = {
                        onSelect(unit)
                        expanded = false
                    }
                )
            }
        }
    }
}
