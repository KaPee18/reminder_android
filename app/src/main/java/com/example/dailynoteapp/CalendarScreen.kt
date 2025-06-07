package com.example.dailynoteapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarHeader(
    currentMonth: YearMonth,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    // 1. Lista skrótów dni tygodnia w języku polskim
    val daysOfWeek = listOf("Pn", "Wt", "Śr", "Cz", "Pt", "So", "Nd")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // gorny wiersz: przycisk ←, nazwa miesiąca, przycisk →
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Przycisk poprzedniego miesiąca
            IconButton(onClick = onPreviousClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Poprzedni miesiąc")
            }


            Spacer(modifier = Modifier.weight(1f))

            // Nazwa bieżącego miesiąca
            Text(
                text = currentMonth.month
                    .getDisplayName(TextStyle.FULL, Locale("pl"))
                    .replaceFirstChar { it.uppercaseChar() } + " " + currentMonth.year,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))

            // Przycisk następnego miesiąca
            IconButton(onClick = onNextClick) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Następny miesiąc")
            }
        }



        // dolny wiersz: skróty dni tygodnia
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))


    }
}


@Composable
fun PrzyciskDnia(
    number: Int,
    size: Dp = 48.dp,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(size),  // szerokość = wysokość = size (np. 48×48 dp)
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.LightGray  //  tło
        ),
        shape = CircleShape,                     // kształt: koło
        contentPadding = PaddingValues(0.dp)     // usuń domyślne paddingi
    ) {
        Box(
            contentAlignment = Alignment.Center, // wyśrodkuj zawartość w przycisku
            modifier = Modifier
                .size(size)                      // żeby Box miał ten sam rozmiar co przycisk
        ) {
            Text(
                text = number.toString(),
                color = Color.White
            )
        }
    }
}


@Composable
fun CalendarGrid(
    currentMonth: YearMonth,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    buttonScreen: () -> Unit
    ) {

    val pierwszyDzien: LocalDate = currentMonth.atDay(1)
    val dayOfWeek: DayOfWeek = pierwszyDzien.dayOfWeek
    val numerDniaTygodnia: Int = dayOfWeek.value
    val dniWMiesiacu = currentMonth.lengthOfMonth()  // np. 30 albo 31
    var numerDnia = 1
    var nowaKolumna = true
    var pozycjaPoczatkowa = 1
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
            while (nowaKolumna==true){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    for (dzien in 1..7) {
                        if (pozycjaPoczatkowa<numerDniaTygodnia){
                            Box(modifier = Modifier
                                .weight(1f)
                            ){
                            pozycjaPoczatkowa=pozycjaPoczatkowa+1
                            }
                        }
                        else if(numerDnia<=dniWMiesiacu){
                            Box(modifier = Modifier
                                .weight(1f)
                            ){
                                PrzyciskDnia(numerDnia, 40.dp) { buttonScreen() }
                                numerDnia = numerDnia + 1
                            }
                        }
                        else{
                            Box(modifier = Modifier
                                .weight(1f)
                            ){
                                numerDnia = numerDnia + 1
                            }
                        }
                    }
                    if (numerDnia > dniWMiesiacu){
                        nowaKolumna = false
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

            }

    }
}
@Composable
fun SecondScreen(onBack: () -> Unit,
ButtonScreen: () -> Unit
) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Kalendarz")
        Button(onClick = { onBack() }) {
            Text("Powrót")
        }
        Column {
            CalendarHeader(
                currentMonth = currentMonth,
                onPreviousClick = { currentMonth = currentMonth.minusMonths(1) },
                onNextClick = { currentMonth = currentMonth.plusMonths(1) }
            )
            CalendarGrid(
                currentMonth = currentMonth,
                onPreviousClick = { currentMonth = currentMonth.minusMonths(1) },
                onNextClick = { currentMonth = currentMonth.plusMonths(1) },
                buttonScreen = ButtonScreen,
            )

        }
    }
}