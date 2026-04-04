package com.myapp.mysimon

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.myapp.mysimon.ui.theme.MySimonTheme

class EndgameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display on API level < 35
        enableEdgeToEdge()

        // Set and display the UI content
        setContent {
            MySimonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    EndScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        buttonAction = {
                            // End the current activity and return to MainActivity
                            this.finish()
                        }
                    )
                }
            }
        }
    }
}

// Function of the first screen of the app
// Contains colored buttons, current sequence, delete button and end-game button
@Composable
fun EndScreen(modifier: Modifier = Modifier, buttonAction : () -> Unit) {
    ConstraintLayout(modifier = Modifier) {
        Button(
            modifier = Modifier,
            onClick = buttonAction) {}
    }
}

@Preview(showBackground = true)
@Composable
fun EndScreenPreview() {
    EndScreen( buttonAction = {} )
}