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


@Composable
fun IngredientItem(
    imageRes: Int,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = label,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp,
                color = Color.Black
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(64.dp)
        )
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

@Composable
fun IngredientFilterSection(
    modifier: Modifier = Modifier
) {
    val ingredients = listOf(
        Pair(R.drawable.eggs, "Eggs"),
        Pair(R.drawable.chicken, "Chicken"),
        Pair(R.drawable.palak, "Palak"),
        Pair(R.drawable.raagi, "Raagi"),
        Pair(R.drawable.tofu, "Tofu"),
        Pair(R.drawable.rajma, "Rajma"),
        Pair(R.drawable.cauliflower, "Cauliflower"),
        Pair(R.drawable.pumpkin, "Pumpkin"),
        Pair(R.drawable.noodle, "Noodle"),
        Pair(R.drawable.rajma, "Rajma"),
    )

    val filterChips = listOf(
        Pair(R.drawable.ic_sort, "Sort"),
        Pair(R.drawable.ic_cooking_history, "Previously Cooked"),
        Pair(R.drawable.ic_quick_recipes, "Quick Recipes"),
        Pair(R.drawable.ic_cuisines, "Cuisines"),
        Pair(R.drawable.ic_diet_type, "Diet Type"),
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Ingredients horizontal scroll
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(ingredients) { (imageRes, label) ->
                IngredientItem(
                    imageRes = imageRes,
                    label = label
                )
            }
        }

        // Filter chips horizontal scroll
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filterChips) { (iconRes, label) ->
                FilterChip(
                    label = label,
                    iconRes = iconRes,
                    onClick = { /* Handle filter click */ }
                )
            }
        }
    }
}