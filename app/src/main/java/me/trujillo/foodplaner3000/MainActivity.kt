package me.trujillo.foodplaner3000

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import kotlinx.coroutines.launch
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
    Text(text = text, style = TextStyle(fontSize = 20.sp, color = Red))
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

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    var items by remember { mutableStateOf(listOf("14 Eier", "50g Mehl",
        "200g Fleisch", "150g Butter", "1 Brockoli", "500g Mandeln",
        "50g Chilli", "1l Milch", "20g Curry", "500g Salat")) }
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
                itemsIndexed(
                    items = items,
                    key = { _, item -> item }  // Verwenden des Item-Inhalts als eindeutigen Schlüssel
                ) { index, item ->
                    val dismissState = rememberDismissState()

                    // Überwachen des `dismissState` mit `LaunchedEffect`
                    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                        LaunchedEffect(key1 = items) {
                            scope.launch {
                                items = items.toMutableList().also { list ->
                                    list.removeAt(index)
                                }
                            }
                        }
                    }

                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.EndToStart),
                        background = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color(0xFF212121))
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "Delete",
                                    color = Color( 0xFF8B0000),
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
                    Text(text = item, fontSize = 18.sp)
                }
            }
        )
    }




            }
            IconButton(
                onClick = { /* Aktion zum Löschen oder Schließen */ },
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
            /*Text(
                text = "dritte Box",
                modifier = Modifier.align(Alignment.Center),
                color = Color.Black
            )*/
            BottomIconButtons()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    FoodPlaner3000Theme {
        MyApp()
    }
}