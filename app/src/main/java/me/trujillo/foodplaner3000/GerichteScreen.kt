package me.trujillo.foodplaner3000

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun GerichteScreen(navController: NavHostController){


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
                    text = "Plates...               ",

                    color = Color.White
                )


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



@Preview(showBackground = true)
@Composable
fun PreviewNewScreen() {
    val navController = rememberNavController()
    GerichteScreen(navController = navController)

}