package me.trujillo.foodplaner3000.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon // Richtiger Icon-Import
import androidx.compose.material3.IconButton
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
