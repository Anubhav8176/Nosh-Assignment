package com.example.noshassignment.presentation.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
            Box(modifier = Modifier
                .width(80.dp)
                .fillMaxHeight()
            ) {
                NoshNavigationRail(
                    navItems = navItems,
                    selectedItem = selectedItem,
                    onItemSelected = { selectedItem = it }
                )
            }

            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                color = Color(0xFFE0E0E0),
                thickness = 1.dp
            )

            HomeScreen(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
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

    NavigationRail(
        modifier = modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        containerColor = Color.White,
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        topItems.forEachIndexed { index, item ->
            NavigationRailItem(
                selected = selectedItem == index,
                onClick = { onItemSelected(index) },
                icon = {
                    Image(
                        painter = painterResource(id = item.selectedIcon as Int),
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(
                            if (selectedItem == index) Color(0xFFE65100)
                            else Color(0xFF9E9E9E)
                        )
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 10.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = Color(0xFFE65100),
                    selectedTextColor = Color(0xFFE65100),
                    unselectedIconColor = Color(0xFF9E9E9E),
                    unselectedTextColor = Color(0xFF9E9E9E),
                    indicatorColor = Color.Transparent
                )
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            color = Color(0xFFE0E0E0)
        )

        Spacer(modifier = Modifier.weight(1f))

        bottomItems.forEachIndexed { index, item ->
            val actualIndex = topItems.size + index
            NavigationRailItem(
                selected = selectedItem == actualIndex,
                onClick = { onItemSelected(actualIndex) },
                icon = {
                    Image(
                        painter = painterResource(id = item.selectedIcon as Int),
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(
                            if (selectedItem == actualIndex) Color(0xFFE65100)
                            else Color(0xFF9E9E9E)
                        )
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 10.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = Color(0xFFE65100),
                    selectedTextColor = Color(0xFFE65100),
                    unselectedIconColor = Color(0xFF9E9E9E),
                    unselectedTextColor = Color(0xFF9E9E9E),
                    indicatorColor = Color.Transparent
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}