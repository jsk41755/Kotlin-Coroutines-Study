package com.example.coroutine_contexts

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
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

@Composable
fun BirdApp() {
    var selectedBird by remember { mutableStateOf<Bird?>(null) }

    Column {
        Button(onClick = { selectedBird = Bird.CooBird }) {
            Text("Tweety (Coo Bird)")
        }
        Button(onClick = { selectedBird = Bird.CawBird }) {
            Text("Zazu (Caw Bird)")
        }
        Button(onClick = { selectedBird = Bird.ChirpBird }) {
            Text("Woodstock (Chirp Bird)")
        }

        selectedBird?.let { bird ->
            BirdSound(bird)
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
@Composable
fun BirdSound(bird: Bird) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(bird) {
        coroutineScope.launch(newSingleThreadContext(bird.birdName)) {
            while (isActive) {
                println("Thread: ${Thread.currentThread().name} makes a ${bird.sound} sound")
                delay(bird.interval)
            }
        }
    }

    Text(text = bird.birdName)
}

sealed class Bird(val name: String, val sound: String, val interval: Long, val birdName: String) {
    object CooBird : Bird("Coo", "Coo", 1000L, "Tweety")
    object CawBird : Bird("Caw", "Caw", 2000L, "Zazu")
    object ChirpBird : Bird("Chirp", "Chirp", 3000L, "Woodstock")
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