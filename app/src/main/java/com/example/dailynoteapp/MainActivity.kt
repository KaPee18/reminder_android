package com.example.dailynoteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import com.example.dailynoteapp.ui.theme.DailyNoteAppTheme
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationManagerCompat
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel(this)
        setContent {

            var currentScreen by remember { mutableStateOf("MAIN") }
            DailyNoteAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (currentScreen) {
                        "MAIN" -> MainScreen(onGoToSecondScreen = { currentScreen = "SECOND" })
                        "SECOND" -> SecondScreen(onBack = { currentScreen = "MAIN" }, ButtonScreen = {currentScreen = "BUTTONSCREEN"})
                        "BUTTONSCREEN" -> CalendarButtonScreen(onGoToSecondScreen = { currentScreen = "SECOND" })
                    }

                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1001)
            }
        }
    }
}
fun sendNotification(context: Context, message: String, notificationId: Int) {
    val channelId = "my_channel_id"

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("Powiadomienie")
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
        == PackageManager.PERMISSION_GRANTED) {

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())
        }

    } else {
        println("Brak pozwolenia POST_NOTIFICATIONS!")
    }
}

@Composable
fun textZachetyGodziny1() {
    Text("Godzina powiadomienia o opisie dnia")
}

@Composable
fun textGodziny1(userInput: String, onUserInputChange: (String) -> Unit){

        OutlinedTextField(
            value = userInput,
            onValueChange = onUserInputChange

        )


}
@Composable
fun przyciskZatwierdzenia1(godzina1: String){
    var showText by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Button(
        onClick = {
            showText = true
            sendNotification(context, "Zatwierdzono godzinę: $godzina1", notificationId = 1)
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("zatwierdz")
    }
        if (showText) {
            Text("Zatwierdzono $godzina1") }


}

@Composable
fun textZachetyGodziny2() {
    Text("Godzina powiadomienia o planie na jutro")
}

@Composable
fun textGodziny2(userInput: String, onUserInputChange: (String) -> Unit){

        OutlinedTextField(
            value = userInput,
            onValueChange = onUserInputChange
        )


}
@Composable
fun przyciskZatwierdzenia2(godzina2: String){
    var showText by remember { mutableStateOf(false) }
    val context = LocalContext.current
     Button(
        onClick = {
            showText = true
            sendNotification(context, "Zatwierdzono godzinę: $godzina2", notificationId = 2)
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("zatwierdz")
    }
        if (showText) {
            Text("Zatwierdzono $godzina2") }

}

@Composable
fun przyciskKalendarza(onGoToSecondScreen: () -> Unit){

    Button(
        onClick = {
            onGoToSecondScreen()
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Kalendarz")
    }

}
@Composable
fun ui_format_main(
    godzina1: String,
    onGodzina1Change: (String) -> Unit,
    godzina2: String,
    onGodzina2Change: (String) -> Unit,
    onGoToSecondScreen: () -> Unit
) {

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        textZachetyGodziny1()
        textGodziny1(userInput = godzina1, onUserInputChange = onGodzina1Change)
        przyciskZatwierdzenia1(godzina1)
        Spacer(modifier = Modifier.height(40.dp))
        textZachetyGodziny2()
        textGodziny2(userInput = godzina2, onUserInputChange = onGodzina2Change)
        przyciskZatwierdzenia2(godzina2)
        przyciskKalendarza(onGoToSecondScreen)
    }
}

private fun createNotificationChannel(context: Context) {
    val channelId = "my_channel_id"
    val channelName = "Moje Powiadomienia"
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val channel = NotificationChannel(channelId, channelName, importance)
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}

@Composable
fun MainScreen(onGoToSecondScreen: () -> Unit ) {
    var godzina_pow_opis_dnia by remember { mutableStateOf("") }
    var godzina_jutro by remember { mutableStateOf("") }
    ui_format_main(
        godzina1 = godzina_pow_opis_dnia,
        onGodzina1Change = { godzina_pow_opis_dnia = it },
        godzina2 = godzina_jutro,
        onGodzina2Change = { godzina_jutro = it },
        onGoToSecondScreen = onGoToSecondScreen
    )
}


@Preview(showBackground = true)
@Composable
fun TimeEntryScreenPreview() {
    var currentScreen by remember { mutableStateOf("MAIN") }
    DailyNoteAppTheme {
        when (currentScreen) {
            "MAIN" -> MainScreen(onGoToSecondScreen = { currentScreen = "SECOND" })
            "SECOND" -> SecondScreen(onBack = { currentScreen = "MAIN" },ButtonScreen = {currentScreen = "BUTTONSCREEN"})
            "BUTTONSCREEN" -> CalendarButtonScreen(onGoToSecondScreen = { currentScreen = "SECOND" })
        }

    }
}
