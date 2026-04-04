package com.myapp.mysimon

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
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
                    MainScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        buttonAction = {
                            val myIntent = Intent(this, EndgameActivity::class.java)
                            // aggiungere passaggio stringa
                            // Button to pass to the endgame activity
                            startActivity(myIntent)

                            // Note: if you need more control over the starting process,
                            // consider the alternative method startActivity(Intent!,Bundle?).
                            // If you need to pass data to the starting activity,
                            // consider adding them as intent extras.
                            // If you need to receive a result from the starting activity,
                            // look up https://developer.android.com/training/basics/intents/result
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
fun MainScreen(modifier: Modifier = Modifier, buttonAction : () -> Unit) {
    // Orientation of the device
    val orientation = LocalConfiguration.current.orientation

    // Strings used on this activity
    val delete = stringResource(R.string.delete)
    val endgame = stringResource(R.string.endgame)
    val newSequence = stringResource(R.string.new_sequence)

    // String with the sequence of the actual game
    var t by rememberSaveable { mutableStateOf(newSequence) }

    // Boolean value that identifies if a new game is started
    var gameStarted = false

    // Value used to make the sequence scrollable and not expandable
    val scrollState = rememberScrollState()

    // Value used to proportion items to the screen
    val configuration = LocalConfiguration.current

    // Layout of the game activity
    ConstraintLayout(modifier = modifier) {
        val (spacer, matrix, sequence, deleteBut, endBut) = createRefs()

        // Space on the top if orientation = portrait
        /*if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .constrainAs(spacer) {
                        top.linkTo(parent.top)
                    }
            )
        }*/

        // Column that contain the 3x2 matrix with the 6 buttons
        // It always cover half of the screen
        Column(modifier = Modifier
            .padding(8.dp)
            .constrainAs(matrix) {
                start.linkTo(parent.start)
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    top.linkTo(spacer.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(sequence.top)
                    width = Dimension.value(configuration.screenWidthDp.dp)
                    height= Dimension.value((configuration.screenHeightDp / 2).dp)
                } else {
                    top.linkTo(parent.top)
                    end.linkTo(sequence.start)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.value((configuration.screenWidthDp / 2).dp)
                    height= Dimension.value((configuration.screenHeightDp).dp)
                }
            },
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // All button colors and their respective initial letters
            val colors = listOf(Color.Red, Color.Magenta, Color.Green, Color.Yellow, Color.Blue, Color.Cyan)
            val colorsLetters = listOf("R", "M", "G", "Y", "B", "C")

            // Loop used to create the 6 buttons inside the column
            // All the rows have the same weight, as do the buttons which are therefore the same size
            var index = 0
            repeat(3) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(2) {
                        Button(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            onClick = {
                                if (!gameStarted) {
                                    t = ""
                                    gameStarted = true
                                }
                                t = t + ", " + colorsLetters[index]
                            },
                            shape = RoundedCornerShape(4.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colors[index])
                        ) {}
                        index++
                    }
                }
            }
        }

        // Border color in the text box
        val gradientBrush = Brush.horizontalGradient(
            colors = listOf(Color.Red, Color.Magenta, Color.Green, Color.Yellow, Color.Blue, Color.Cyan),
            startX = 0.0f,
            endX = 500.0f,
            tileMode = TileMode.Repeated
        )

        // Text view with the string of the current game
        Text(
            text = t,
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(scrollState)
                .border(width = 2.dp, brush = gradientBrush, shape = RectangleShape)
                .constrainAs(sequence) {
                    end.linkTo(parent.end, margin = 8.dp)
                    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                        start.linkTo(parent.start, margin = 8.dp)
                        top.linkTo(matrix.bottom, margin = 2.dp)
                    } else {
                        start.linkTo(matrix.end, margin = 8.dp)
                        top.linkTo(parent.top, margin = 24.dp)
                    }
                },
            fontSize = 21.sp,
            fontWeight = FontWeight.Medium
        )

        // Button to delete the current game
        Button(
            modifier = Modifier.constrainAs(deleteBut) {
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    start.linkTo(parent.start, margin = 8.dp)
                    end.linkTo(endBut.start, margin = 2.dp)
                    bottom.linkTo(parent.bottom, margin = 24.dp)
                } else {
                    start.linkTo(matrix.end, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                    top.linkTo(sequence.bottom)
                    bottom.linkTo(endBut.top, margin = 4.dp)
                }
            },
            onClick = {
                gameStarted = false
                t = newSequence
            }
        ) {
            Text(text = delete)
        }

        // Button to end the current game
        Button(
            modifier = Modifier.constrainAs(endBut) {
                bottom.linkTo(parent.bottom, margin = 24.dp)
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    start.linkTo(deleteBut.end, margin = 8.dp)
                    end.linkTo(parent.end, margin = 2.dp)
                    bottom.linkTo(parent.bottom, margin = 24.dp)
                } else {
                    start.linkTo(matrix.end, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                    top.linkTo(deleteBut.bottom, margin = 4.dp)
                }
            },
            onClick = {
                gameStarted = false
                t = newSequence
                buttonAction()
            }
        ) {
            Text(text = endgame)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen( buttonAction = {} )
}