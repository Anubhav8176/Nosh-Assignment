package com.example.noshassignment.presentation.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noshassignment.R
import com.example.noshassignment.navigation.NavigationItem
import com.example.noshassignment.presentation.sharedComposable.TopIconBar
import com.example.noshassignment.viewmodel.DishViewModel


@Composable
fun MainScreen(dishViewModel: DishViewModel) {
    var selectedItem by remember { mutableStateOf(0) }

    val navItems = listOf(
        NavigationItem("Home", R.drawable.ic_home, R.drawable.ic_home),
        NavigationItem("Reheat", R.drawable.ic_reheat, R.drawable.ic_reheat),
        NavigationItem("Preset", R.drawable.ic_preset, R.drawable.ic_preset),
        NavigationItem("Copilot", R.drawable.ic_copilot, R.drawable.ic_copilot),
        NavigationItem("Flavour", R.drawable.ic_flavour, R.drawable.ic_flavour),
        NavigationItem("Care Mode", R.drawable.ic_care_mode, R.drawable.ic_care_mode),
        NavigationItem("Support", R.drawable.ic_support, R.drawable.ic_support),
    )

    Scaffold { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NoshNavigationRail(
                navItems = navItems,
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it }
            )

            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp),
                color = Color(0xFFE0E0E0)
            )

            HomeScreen(
                modifier = Modifier.weight(1f),
                dishViewModel = dishViewModel
            )
        }
    }
}

@Composable
fun NoshNavigationRail(
    navItems: List<NavigationItem>,
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val topItems = navItems.take(5)
    val bottomItems = navItems.drop(5)

    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(80.dp)
            .background(Color.White)
            .padding(vertical = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        topItems.forEachIndexed { index, item ->
            NoshNavItem(
                item = item,
                isSelected = selectedItem == index,
                onClick = { onItemSelected(index) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            color = Color(0xFFE0E0E0)
        )

        Spacer(modifier = Modifier.height(8.dp))

        bottomItems.forEachIndexed { index, item ->
            val actualIndex = topItems.size + index
            NoshNavItem(
                item = item,
                isSelected = selectedItem == actualIndex,
                onClick = { onItemSelected(actualIndex) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun NoshNavItem(
    item: NavigationItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(72.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            painter = painterResource(
                id = if (isSelected) item.selectedIcon as Int else item.unselectedIcon as Int
            ),
            contentDescription = item.title,
            modifier = Modifier.size(26.dp),
            colorFilter = if (isSelected) {
                ColorFilter.tint(Color(0xFFE65100))
            } else {
                ColorFilter.tint(Color(0xFF9E9E9E))
            }
        )
        Text(
            text = item.title,
            style = MaterialTheme.typography.labelSmall.copy(
                color = if (isSelected) Color(0xFFE65100) else Color(0xFF9E9E9E),
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                fontSize = 10.sp
            ),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}