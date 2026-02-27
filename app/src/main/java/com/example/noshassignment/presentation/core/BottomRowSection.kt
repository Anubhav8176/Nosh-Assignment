package com.example.noshassignment.presentation.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noshassignment.R
import com.example.noshassignment.network.model.DishResponseItem
import com.example.noshassignment.presentation.sharedComposable.DishCard


@Composable
fun IngredientItem(
    imageRes: Int,
    label: String,
    isSelected: Boolean,
    onCategoryClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable { onCategoryClicked(label) }
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) Color(0xFFE65100) else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = label,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .border(
                    width = 0.dp,
                    color = Color.Transparent,
                    shape = CircleShape
                )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp,
                color = if (isSelected) Color(0xFFE65100) else Color.Black,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(64.dp)
        )
    }
}

@Composable
fun IngredientFilterSection(
    dishes: List<DishResponseItem>,
    selectedSection: String,
    onCategoryClicked: (String) -> Unit,
    selectedCategory: String?,
    modifier: Modifier = Modifier
) {
    val ingredientList: List<Pair<Int, String>> = remember(dishes) {
        dishes
            .distinctBy { it.IngredientCategory }
            .map { dish -> Pair(R.drawable.eggs, dish.IngredientCategory) }
    }

    val dishList: List<Pair<Int, String>> = remember(dishes) {
        dishes
            .distinctBy { it.dishCategory }
            .map { dish -> Pair(R.drawable.palak, dish.dishCategory) }
    }

    val filterChips = listOf(
        Pair(R.drawable.ic_sort, "Sort"),
        Pair(R.drawable.ic_cooking_history, "Previously Cooked"),
        Pair(R.drawable.ic_quick_recipes, "Quick Recipes"),
        Pair(R.drawable.ic_cuisines, "Cuisines"),
        Pair(R.drawable.ic_diet_type, "Diet Type"),
    )

    val filteredDishes = remember(selectedCategory, selectedSection, dishes) {
        if (selectedCategory == null) {
            dishes
        } else {
            dishes.filter { dish ->
                if (selectedSection == "Ingredients") {
                    dish.IngredientCategory.equals(selectedCategory, ignoreCase = true)
                } else {
                    dish.dishCategory.equals(selectedCategory, ignoreCase = true)
                }
            }
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val finalList = if (selectedSection == "Ingredients") ingredientList else dishList
            items(finalList) { (imageRes, label) ->
                IngredientItem(
                    imageRes = imageRes,
                    label = label,
                    isSelected = label == selectedCategory,
                    onCategoryClicked = onCategoryClicked
                )
            }
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filterChips) { (iconRes, label) ->
                FilterChip(
                    label = label,
                    iconRes = iconRes,
                    onClick = { }
                )
            }
        }

        if (filteredDishes.isEmpty()) {
            Text(
                text = "No dishes found for this category",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun FilterChip(
    label: String,
    iconRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .border(
                width = 1.dp,
                color = Color(0xFFE0E0E0),
                shape = RoundedCornerShape(50.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 13.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
        )
        Image(
            painter = painterResource(id = R.drawable.ic_dropdown_arrow),
            contentDescription = "dropdown",
            modifier = Modifier.size(12.dp)
        )
    }
}
