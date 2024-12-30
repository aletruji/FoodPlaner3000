package me.trujillo.foodplaner3000

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch


@Composable
fun BottomIconButtons(navController: NavHostController) {
    // Row für die Icons am unteren Rand der App
    Row(
        modifier = Modifier
            .fillMaxWidth()  // Füllt die gesamte Breite aus
            .padding(16.dp), // Optionales Padding
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        // Erster Button
        IconButton(onClick = { navController.navigate("screen1") }) {
            Icon(Icons.Filled.Home, contentDescription = "Home", tint = Color.White)
        }
        // Zweiter Button
        IconButton(onClick = { navController.navigate("screen2") }) {
            Icon(Icons.Filled.Favorite, contentDescription = "Favorite", tint = Color.White)
        }
        // Dritter Button
        IconButton(onClick = { /* Aktion für den dritten Button */ }) {
            Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color.White)
        }
        // Vierter Button
        IconButton(onClick = { /* Aktion für den vierten Button */ }) {
            Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = Color.White)
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavHostController,
               viewModel: ViewModel = viewModel()){

    val items = viewModel.items
    val scope = rememberCoroutineScope()


    Column(modifier = Modifier
        .fillMaxSize()
    ){

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f)
                .background(Color(0xFFFFA500))
                .padding(7.dp)
        ) {
            Text(
                text = "Food Planer 3000",
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (-20.dp)),
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic


            )
            Box(
                modifier = Modifier
                    .background(Color(0xFF212121),shape = RoundedCornerShape(16.dp))
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            ) {
                Text(
                    text = "Shoppinglist...               ",

                    color = Color.White
                )

              /*  Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .background(Color.White, shape = RoundedCornerShape(50))
                        .size(15.dp))
                {
                    Icon(
                        imageVector = Icons.Filled.Close, // Das "X"-Icon
                        contentDescription = "Delete",
                        tint = Color.Black,
                        modifier = Modifier.size(15.dp) // Größe des Icons anpassen
                    )
              //  }*/
            }
        }

        Divider(
            thickness = 15.dp, // Dicke der Linie
            color = Color(0xff8BC34A) // Farbe der Linie
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFF212121))

        ){



            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(items = viewModel.items,
                    key = { _, item -> item.name } // Verwende `item.name` als eindeutigen Schlüssel
                ) { index, item ->
                    val dismissState = rememberDismissState()
                    var showEditDialog by remember { mutableStateOf(false) }
                    var itemToEdit by remember { mutableStateOf<Item?>(null) }
                    var showDeleteDialog by remember { mutableStateOf(false) }
                    var itemToDelete by remember { mutableStateOf<Item?>(null) }


                    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                        viewModel.removeItem(index) // Lösche das Element über das ViewModel
                    }
                    if (showEditDialog == true) {
                        EditItemDialog(
                            item = itemToEdit!!,
                            onDismiss = { showEditDialog = false },
                            onConfirm = { newName, newQuantity ->
                                viewModel.onEditItem(itemToEdit!!, newName, newQuantity)
                                showEditDialog = false
                            }
                        )
                    }
                    if (showDeleteDialog && itemToDelete != null) {
                        AlertDialog(
                            onDismissRequest = { showDeleteDialog = false },
                            title = { Text("Confirm Delete") },
                            text = { Text("Do you want to delete ${itemToDelete!!.name}?") },
                            confirmButton = {
                                TextButton(onClick = {
                                    viewModel.removeItem(items.indexOf(itemToDelete!!)) // Lösche das Item
                                    showDeleteDialog = false // Schließe den Dialog
                                }) {
                                    Text("Delete")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDeleteDialog = false }) {
                                    Text("Cancel")
                                }
                            }
                        )
                    }

                        SwipeToDismiss(
                            state = dismissState,
                            directions = setOf(
                                DismissDirection.EndToStart,
                                DismissDirection.StartToEnd
                            ),
                            background = {
                                val direction = dismissState.dismissDirection
                                val text = when (direction) {
                                    DismissDirection.StartToEnd -> "Edit"
                                    DismissDirection.EndToStart -> "Delete"
                                    else -> ""
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color(0xFF212121))
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "Delete",
                                        color = Color(0xFF8B0000),
                                        modifier = Modifier.align(Alignment.CenterEnd)
                                    )
                                }
                            },
                            dismissContent = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .background(
                                            Color(0xff565a47),
                                            shape = MaterialTheme.shapes.medium
                                        )
                                        .padding(16.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "${item.name}: ${item.quantity}",
                                            fontSize = 18.sp
                                        )
                                        Row(

                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ){
                                        IconButton(onClick = {
                                            itemToEdit = item
                                            showEditDialog = true},
                                            modifier = Modifier.size(24.dp)) {
                                            Icon(Icons.Filled.Edit, contentDescription = "Edit")

                                        }
                                            Spacer(modifier = Modifier.width(16.dp))

                                            IconButton(onClick = {
                                                itemToDelete = item
                                               showDeleteDialog = true},
                                                modifier = Modifier.size(24.dp)) {
                                                Icon(Icons.Filled.Delete, contentDescription = "Delete")

                                            }
                                        }
                                    }
                                }
                            }
                        )


                }

            }


            var showAddDialog by remember { mutableStateOf(false) }


            IconButton(
                onClick = {
                    showAddDialog = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd) // Rechts platzieren
                    .padding(25.dp)
                    .size(30.dp) // Kleinere Größe des Buttons
                    .background(Color(0xff8BC34A), shape = RoundedCornerShape(50)) // Runder Hintergrund
            ) {
                Icon(
                    imageVector = Icons.Filled.Add, // Das "X"-Icon
                    contentDescription = "Add",
                    tint = Color.White                    ,
                    modifier = Modifier.size(16.dp) // Kleinere Größe des Icons
                )
            }
            if (showAddDialog) {
                AddItemDialog(
                    onDismiss = { showAddDialog = false },
                    onAddItem = { name, quantity ->
                        viewModel.addItem(name, quantity)
                        showAddDialog = false
                    }
                )
            }

        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
                .background(Color(0xFFFFA500))
        ){

            BottomIconButtons(navController = navController)
        }
    }

}

@Composable
fun AddItemDialog(
    onDismiss: () -> Unit,
    onAddItem: (String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }

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
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("quantity") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (name.isNotBlank() && quantity.isNotBlank()) {
                    onAddItem(name, quantity)
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
@Composable
fun EditItemDialog(
    item: Item,
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var name by remember { mutableStateOf(item.name) }
    var quantity by remember { mutableStateOf(item.quantity) }

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
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (name.isNotBlank() && quantity.isNotBlank()) {
                    onConfirm(name, quantity)
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

@Preview(showBackground = true)
@Composable
fun showPrewiev() {
    val navController = rememberNavController()
    ListScreen(navController = navController)
}