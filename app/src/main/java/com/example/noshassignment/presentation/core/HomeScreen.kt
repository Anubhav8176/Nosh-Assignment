package com.example.noshassignment.presentation.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noshassignment.R
import com.example.noshassignment.network.model.DishResponseItem
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
    dishViewModel: DishViewModel
) {
    var searchingText by remember { mutableStateOf("") }
    val dishes by dishViewModel.dishData.collectAsState()
    var selectedTab by remember { mutableStateOf("Recommended") }
    var selectedSection by remember { mutableStateOf("Ingredients") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    val filteredDishes = remember(searchingText, dishes) {
        if (searchingText.isBlank()) {
            dishes
        } else {
            dishes.filter { dish ->
                dish.dishName.contains(searchingText, ignoreCase = true) ||
                        dish.dishCategory.contains(searchingText, ignoreCase = true) ||
                        dish.IngredientCategory.contains(searchingText, ignoreCase = true)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(vertical = 12.dp, horizontal = 8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DishSearchBar(
                query = searchingText,
                onQueryChange = {
                    searchingText = it
                    selectedCategory = null
                },
                modifier = Modifier.weight(1f)
            )
            TopIconBar(
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


        TabRow(
            modifier = Modifier.fillMaxWidth(),
            tabs = listOf("Recommended", "Favourites"),
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        if (searchingText.isNotBlank()) {
            Text(
                text = if (filteredDishes.isEmpty()) "No dishes found for $searchingText"
                else "${filteredDishes.size} dish${if (filteredDishes.size > 1) "es" else ""} found for $searchingText",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = if (filteredDishes.isEmpty()) Color.Red else Color.Gray,
                    fontSize = 12.sp
                ),
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }

        if (filteredDishes.isEmpty() && searchingText.isNotBlank()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_cooking_history),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "No dishes match your search",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                    )
                }
            }
        } else {
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(filteredDishes) { dish ->
                    DishCard(
                        dishId = dish.dishId,
                        dishName = dish.dishName,
                        imageUrl = dish.imageUrl,
                        isVeg = dish.isVeg,
                        isPublished = dish.isPublished,
                        time = dish.Time,
                        dishCategory = dish.dishCategory,
                        ingredientCategory = dish.IngredientCategory,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )
                }
            }
        }

        if (searchingText.isBlank()) {
            Row(modifier = Modifier.fillMaxWidth()) {
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

            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(10) {
                    PreviousDishCard(
                        imageUrl = "https://nosh-assignment.s3.ap-south-1.amazonaws.com/paneer-tikka.jpg",
                        dishName = "Jeera Rice",
                        date = "Yesterday",
                        time = "4:33 pm",
                        rating = null,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }

            TabRow(
                modifier = Modifier.fillMaxWidth(),
                tabs = listOf("Dish", "Ingredients"),
                selectedTab = selectedSection,
                onTabSelected = {
                    selectedSection = it
                    selectedCategory = null
                }
            )

            IngredientFilterSection(
                dishes = dishes,
                selectedSection = selectedSection,
                selectedCategory = selectedCategory,
                onCategoryClicked = { category ->
                    selectedCategory = if (selectedCategory == category) null else category
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

