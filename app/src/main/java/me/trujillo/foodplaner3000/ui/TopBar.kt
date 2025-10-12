package me.trujillo.foodplaner3000.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TopSection(snackbarHostState: SnackbarHostState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .background(Color(0xFFFFA500))
            .padding(7.dp)
    ) {
        Text(
            text = "Food Planer 3000",
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-20).dp),
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
                text = "Einkaufsliste...",
                color = Color.White
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .size(180.dp, 80.dp)
                .align(Alignment.BottomEnd)
                .padding(top = 6.dp)
        )
    }


}