package com.example.noshassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.noshassignment.presentation.core.MainScreen
import com.example.noshassignment.ui.theme.NoshAssignmentTheme
import com.example.noshassignment.viewmodel.DishViewModel

class MainActivity : ComponentActivity() {

    val dishViewModel: DishViewModel = DishViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoshAssignmentTheme {
                MainScreen(dishViewModel = dishViewModel)
            }
        }
    }
}


