package me.trujillo.foodplaner3000

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import me.trujillo.foodplaner3000.data.db.entities.Dish
import me.trujillo.foodplaner3000.ui.SimpleFilterDropdown


@Composable
fun GerichteScreen(navController: NavHostController){
    val testgerichte : List<Dish> = listOf(
        Dish(name = "Reis mit Bohnen", description = null, instructions = null),
        Dish(name = "Enchilladas", description = null, instructions = null),
        Dish(name = "Kötbulla", description = null, instructions = null),
        Dish(name = "Steak", description = null, instructions = null),
        Dish(name = "Lachsnudeln", description = null, instructions = null),
        Dish(name = "Burger", description = null, instructions = null),
        Dish(name = "Tunfisch Salat", description = null, instructions = null),
        Dish(name = "Fischstäbchen mit Spinat", description = null, instructions = null),
        Dish(name = "Pad Thai", description = null, instructions = null),
        Dish(name = "Tofu Bolognese", description = null, instructions = null)
    )

    var showAddPlateDialog by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("")}
    var filter by remember {mutableStateOf("Dish")}

    Column(modifier = Modifier
        .fillMaxSize()
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.125f)
                .background(Color.LightGray),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedTextField(
                value = text,
                onValueChange = {text = it},
                label = { Text("Search", color = Color.Black) },

                textStyle = TextStyle(color = Color.Black),

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    errorContainerColor = Color.White,

                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Black,

                    cursorColor = Color.Black
                ),
                modifier = Modifier
                    .padding(start = 12.dp)
                    .height(56.dp)


            )

            SimpleFilterDropdown(
                selected = filter,
                onSelected = { filter = it },

            )

        }




        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFF212121)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ){
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement =  Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) { items(testgerichte){
                item-> Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 12.dp, vertical = 6.dp)

                ){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${item.name} ",
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(8.dp)
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = {  },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = Color(0xFF4CAF50)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(
                            onClick = {  },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color(0xFFFF5252)
                            )
                        }
                    }
                }
            }
            }

            }

        }



    }
}










@Preview(showBackground = true)
@Composable
fun PreviewNewScreen() {
    val navController = rememberNavController()
    GerichteScreen(navController = navController)

}

