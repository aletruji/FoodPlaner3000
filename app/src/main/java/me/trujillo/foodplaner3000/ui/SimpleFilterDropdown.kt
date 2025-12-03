package me.trujillo.foodplaner3000.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun SimpleFilterDropdown(
    selected: String,
    onSelected: (String) -> Unit,
    modifier: Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.Center,



    ){

        Card(
            modifier = modifier

        ) {Box(
modifier = Modifier.fillMaxSize()
        ){
            Text(
                text = selected,
                modifier = Modifier
                    .clickable { expanded = true }
                    .padding(12.dp)
                    .align(Alignment.Center)

            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Dish") },
                    onClick = {
                        onSelected("Dish")
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Ingredient") },
                    onClick = {
                        onSelected("Ingredient")
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Category") },
                    onClick = {
                        onSelected("Category")
                        expanded = false
                    }
                )
            }
        }}}
}
