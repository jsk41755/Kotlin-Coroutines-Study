package com.example.coroutine_basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.coroutine_basics.ui.theme.Coroutine_BasicsTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // [Easy] Assignment #1
        /*val cooBird = GlobalScope.launch {
            repeat(4) {
                delay(1000L)
                println("Coo")
            }
        }

        val cawBird = GlobalScope.launch {
            repeat(4) {
                delay(2000L)
                println("Caw")
            }
        }

        val chipBird = GlobalScope.launch {
            repeat(4) {
                delay(3000L)
                println("Chirp")
            }
        }

        runBlocking {
            cooBird.join()
            cawBird.join()
            chipBird.join()
        }
         */

        // [Medium] Assignment #2
        /*lifecycleScope.launch {
            withTimeout(10_000) {
                val cooBird = launch {
                    while (isActive) {
                        delay(1000L)
                        println("Coo")
                    }
                }
                val cawBird = launch {
                    while (isActive) {
                        delay(2000L)
                        println("Caw")
                    }
                }
                val chipBird = launch {
                    while (isActive) {
                        delay(3000L)
                        println("Chirp")
                    }
                }

                val countJob = launch {
                    var count = 10
                    while (count > 0) {
                        delay(1000L)
                        count--
                        println("countjob isActive: $isActive")
                    }
                }

                countJob.join()
                println("countjob isActive: ${countJob.isActive}")
                cancel()
            }
            println("All coroutines cancelled after 10 seconds")
        }*/

        setContent {
            Coroutine_BasicsTheme {
                SoundScreen(modifier = Modifier)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Coroutine_BasicsTheme {
        Greeting("Android")
    }
}