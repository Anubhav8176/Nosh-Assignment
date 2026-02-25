package com.example.noshassignment

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
import com.example.noshassignment.presentation.sharedComposable.DishCard
import com.example.noshassignment.presentation.sharedComposable.PreviousDishCard
import com.example.noshassignment.ui.theme.NoshAssignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoshAssignmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PreviousDishCard(
                        imageUrl = "https://nosh-assignment.s3.ap-south-1.amazonaws.com/paneer-tikka.jpg",
                        dishName = "Jeera Rice",
                        date = "Yesterday",
                        time = "4:33 pm",
                        rating = null,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
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
    NoshAssignmentTheme {
        Greeting("Android")
    }
}