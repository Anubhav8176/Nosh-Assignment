package com.example.noshassignment.network.model

class DishResponse : ArrayList<DishResponseItem>()

data class DishResponseItem(
    val IngredientCategory: String,
    val Time: String,
    val dishCategory: String,
    val dishId: String,
    val dishName: String,
    val imageUrl: String,
    val isPublished: Boolean,
    val isVeg: Boolean
)