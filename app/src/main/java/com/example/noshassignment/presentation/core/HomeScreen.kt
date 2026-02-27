package com.example.noshassignment.presentation.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noshassignment.R
import com.example.noshassignment.presentation.sharedComposable.DishCard
import com.example.noshassignment.presentation.sharedComposable.PreviousDishCard
import com.example.noshassignment.presentation.sharedComposable.TabRow
import com.example.noshassignment.presentation.sharedComposable.TopIconBar
import com.example.noshassignment.ui.theme.NoshAssignmentTheme
import com.example.noshassignment.viewmodel.DishViewModel
import kotlin.concurrent.timer


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    dishViewModel: DishViewModel = DishViewModel()
) {

    var searchingText by remember { mutableStateOf("") }
    val dishes by dishViewModel.dishData.collectAsState()
    val isLoading by dishViewModel.isLoading.collectAsState()
    val errorMessage by dishViewModel.errorMessage.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(vertical = 12.dp, horizontal = 8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TopIconBar(
            modifier = Modifier
                .fillMaxWidth(),
            checkedIcons = listOf(
                R.drawable.ic_spoon,
                R.drawable.ic_catalog,
                R.drawable.ic_third,
                R.drawable.ic_forth
            ),
            plainIcons = listOf(
                R.drawable.ic_wifi,
                R.drawable.ic_mode_off,
                R.drawable.ic_mode_off
            )
        )

        DishSearchBar(
            query = searchingText,
            onQueryChange = { searchingText = it }
        )
        Spacer(modifier.height(10.dp))

        var selectedTab by remember { mutableStateOf("Recommended") }

        TabRow(
            modifier = Modifier
                .fillMaxWidth(),
            tabs = listOf("Recommended", "Favourites"),
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        Spacer(modifier.height(10.dp))

        LazyRow(
            modifier = modifier
                .fillMaxWidth()
        ) {
            items(dishes){dish->
                DishCard(
                    dishId = dish.dishId,
                    dishName = dish.dishName,
                    imageUrl = dish.imageUrl,
                    isVeg = dish.isVeg,
                    isPublished = dish.isPublished,
                    time = dish.Time,
                    dishCategory = dish.dishCategory,
                    ingredientCategory = dish.IngredientCategory,
                    modifier = modifier.padding(horizontal = 5.dp)
                )
            }
        }

        Spacer(modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_cooking_history),
                contentDescription = "Previously cooked",
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Previously Cooked",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
            )
        }

        Spacer(modifier.height(10.dp))

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(10){
                PreviousDishCard(
                    imageUrl = "https://nosh-assignment.s3.ap-south-1.amazonaws.com/paneer-tikka.jpg",
                    dishName = "Jeera Rice",
                    date = "Yesterday",
                    time = "4:33 pm",
                    rating = null,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                )
            }
        }

        Spacer(modifier.height(10.dp))

        var selectedSection by remember { mutableStateOf("Ingredients") }

        TabRow(
            modifier = Modifier
                .fillMaxWidth(),
            tabs = listOf("Dish", "Ingredients"),
            selectedTab = selectedSection,
            onTabSelected = { selectedSection = it }
        )

        IngredientFilterSection(
            modifier = Modifier.fillMaxWidth()
        )


    }
}

