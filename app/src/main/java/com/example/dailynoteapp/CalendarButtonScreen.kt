package com.example.dailynoteapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun PrzyciskWyjscia(onGoToSecondScreen: () -> Unit ){
    Column(modifier = Modifier.padding(20.dp)) {
        Row(modifier = Modifier.padding(20.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.End) {
            Button(onClick = onGoToSecondScreen,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                shape = CircleShape
            ) {
                Text(
                    text = "Wyjście",
                    )
            }
        }
    }


}

@Composable
fun CalendarButtonScreen(onGoToSecondScreen: () -> Unit ) {
    Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.Top) {
        PrzyciskWyjscia(onGoToSecondScreen = onGoToSecondScreen)
    }
}