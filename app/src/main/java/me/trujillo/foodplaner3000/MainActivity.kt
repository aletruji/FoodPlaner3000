package me.trujillo.foodplaner3000

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.trujillo.foodplaner3000.ui.theme.FoodPlaner3000Theme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            FoodPlaner3000Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}


@Composable
fun CustomText(text: String) {
    Text(text = text, style = TextStyle(fontSize = 20.sp, color = Color.Red))
}

@Composable
fun BottomIconButtons() {
    // Row für die Icons am unteren Rand der App
    Row(
        modifier = Modifier
            .fillMaxWidth()  // Füllt die gesamte Breite aus
            .padding(16.dp), // Optionales Padding
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        // Erster Button
        IconButton(onClick = { /* Aktion für den ersten Button */ }) {
            Icon(Icons.Filled.Home, contentDescription = "Home", tint = Color.White)
        }
        // Zweiter Button
        IconButton(onClick = { /* Aktion für den zweiten Button */ }) {
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
data class ShoppingItem(val id:Int,
                        var name:String,
                        var quantity:Int,
                        var isEditing: Boolean = false)

@SuppressLint("CoroutineCreationDuringComposition")

@Composable
fun MyApp() {


    /*var items by remember { mutableStateOf(listOf("14 Eier", "50g Mehl", "200g Fleisch", "150g Butter", "1 Brockoli",
        "500g Mandeln", "50g Chilli", "1l Milch", "20g Curry", "500g Salat", )) } */
    val scope = rememberCoroutineScope()
    var sItems by remember{ mutableStateOf(listOf<ShoppingItem>()) }
    var showDialog by remember { mutableStateOf(false)}
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("")}






    if(showDialog) {
        AlertDialog(onDismissRequest = { showDialog = false },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    Button(onClick = {
                        if (itemName.isNotBlank()){
                            val newItem = ShoppingItem( id = sItems.size+1,
                                name = itemName,
                                quantity = itemQuantity.toInt())
                            sItems = sItems + newItem
                            showDialog  = false
                            itemName = ""

                    }
                }){Text("Add")}
                    Button(onClick = {showDialog = false}) {
                        Text("Cancel")
                    }

                }},
            title = { Text("Add Shopping Item") },
            text = { 
                Column {
                    OutlinedTextField(value = itemName,
                        onValueChange ={ itemName = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().padding(16.dp))

                    OutlinedTextField(value = itemQuantity,
                        onValueChange ={ itemQuantity = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().padding(16.dp))
                }
            })
    

        }



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
                    .background(Color(0xFF212121), shape = RoundedCornerShape(16.dp))
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            ) {
            Text(
                text = "Einkaufsliste...               ",

                color = Color.White
            )

                Box(
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
                }
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
                // Verwende die `itemsIndexed`-Funktion, um über die Liste zu iterieren
                items(sItems) {
                              ShoppingListItem(it, {})
                }




    }
            IconButton(
                onClick = { showDialog = true },
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
                )}

        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
                .background(Color(0xFFFFA500))
        ){

            BottomIconButtons()
        }
    }
}

@Composable
fun ShoppingListItem(
    item: ShoppingItem,
    onEditClick: () -> Unit
) {

}



@Preview(showBackground = true)
@Composable
fun Preview() {
    FoodPlaner3000Theme {
        MyApp()
    }
}