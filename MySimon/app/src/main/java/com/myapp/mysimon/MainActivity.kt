package com.myapp.mysimon

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
import com.myapp.mysimon.ui.theme.MySimonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display on API level < 35
        enableEdgeToEdge()

        // Set and display the UI content
        setContent {
            MySimonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreenOne(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Function of the first screen of the app
// Contains colored buttons, current sequence, delete button and end-game button
@Composable
fun MainScreenOne(modifier: Modifier = Modifier) {
    Text()
}

// Function of the second screen of the app
// Contains numbers of clicks and sequences
@Composable
fun MainScreenTwo(modifier: Modifier = Modifier) {
    Text()
}

@Preview(showBackground = true)
@Composable
fun MainScreenOnePreview() {
    MainScreenOne()
}

@Preview(showBackground = true)
@Composable
fun MainScreenTwoPreview() {
    MainScreenTwo()
}