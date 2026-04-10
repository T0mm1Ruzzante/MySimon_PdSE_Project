package com.myapp.mysimon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapp.mysimon.ui.theme.*

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
                        // Get the arrays from the first activity and pass them to the second activity
                        sequenceList = intent.getStringArrayExtra("sequenceList") ?: Array(0) {""},
                        counterList = intent.getIntArrayExtra("counterList") ?: IntArray(0)
                    )
                }
            }
        }
    }
}

// Function of the second screen of the app
// Contain the sequences of the previous games and how many times buttons were clicked in each sequence
@Composable
fun EndScreen(modifier: Modifier = Modifier, sequenceList: Array<String>, counterList: IntArray) {
    // String used on this activity
    val allSequences = stringResource(R.string.all_sequences)

    // Check if the arrays have the same size, and close the activity if not
    if (sequenceList.size != counterList.size) {
        return
    }

    // Layout of the endgame activity
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // On top of the layout there is a text with the "title" of the activity
        // This text will not scroll up or down with the lazy column
        Text(
            text = allSequences,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = if (isSystemInDarkTheme()) OrangeA400 else Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )

        // The lazy column contains the sequences and it's scrollable
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Every game is inserted into a row, containing the number of clicks and the text of the sequence
            items(sequenceList.size) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = LightBlueGrey50, shape = RoundedCornerShape(4.dp)),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Number of buttons pressed in that sequence
                    // The font make the number a little more bigger than the font of the sequence,
                    Text(
                        text = counterList[index].toString(),
                        modifier = Modifier.weight(1f),
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    // Sequence of that game
                    // The sequence is cut to 4 lines to fit the screen
                    Text(
                        text = sequenceList[index],
                        modifier = Modifier.weight(9f),
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 4
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun EndScreenPreview() {
    EndScreen(Modifier, Array<String>(0) {""}, IntArray(0))
}