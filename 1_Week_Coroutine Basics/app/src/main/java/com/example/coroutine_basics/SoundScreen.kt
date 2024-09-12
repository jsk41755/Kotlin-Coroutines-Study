package com.example.coroutine_basics

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Composable
fun SoundScreen(
    modifier: Modifier
){
    var selectedBird by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Button(onClick = { selectedBird = "Coo" }) {
                Text("Coo Bird")
            }
            Button(onClick = { selectedBird = "Caw" }) {
                Text("Caw Bird")
            }
            Button(onClick = { selectedBird = "Chirp" }) {
                Text("Chirp Bird")
            }

            selectedBird?.let { bird ->
                when (bird) {
                    "Coo" -> CooBird()
                    "Caw" -> CawBird()
                    "Chirp" -> ChirpBird()
                }
            }
        }
    }
}

@Composable
fun CooBird() {
    BirdSound(birdName = "Coo", sound = "Coo", interval = 1000L)
}

@Composable
fun CawBird() {
    BirdSound(birdName = "Caw", sound = "Caw", interval = 2000L)
}

@Composable
fun ChirpBird() {
    BirdSound(birdName = "Chirp", sound = "Chirp", interval = 3000L)
}

@Composable
fun BirdSound(birdName: String, sound: String, interval: Long) {

    LaunchedEffect(birdName) {
        launch {
            while (isActive) {
                println(sound)
                delay(interval)
            }
        }
    }

    Text(text = birdName)
}