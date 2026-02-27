package com.example.noshassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.noshassignment.presentation.core.HomeScreen
import com.example.noshassignment.presentation.sharedComposable.DishCard
import com.example.noshassignment.presentation.sharedComposable.PreviousDishCard
import com.example.noshassignment.presentation.sharedComposable.TabRow
import com.example.noshassignment.presentation.sharedComposable.TopIconBar
import com.example.noshassignment.ui.theme.NoshAssignmentTheme
import com.example.noshassignment.viewmodel.DishViewModel

class MainActivity : ComponentActivity() {

    val dishViewModel: DishViewModel = DishViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoshAssignmentTheme {
                Scaffold(
                    topBar = {
                        TopIconBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .windowInsetsPadding(WindowInsets.statusBars),
                            checkedIcons = listOf(
                                R.drawable.ic_spoon,
                                R.drawable.ic_catalog,
                                R.drawable.ic_third,
                                R.drawable.ic_forth
                            ),
                            plainIcons = listOf(
                                R.drawable.ic_wifi,
                                R.drawable.ic_mode_off,
                                R.drawable.profile
                            )
                        )
                    }
                ) {innerpadding->

                    NavigationRail {

                    }
                    HomeScreen(
                        modifier = Modifier.padding(innerpadding),
                        dishViewModel = dishViewModel
                    )
                }
            }
        }
    }
}
