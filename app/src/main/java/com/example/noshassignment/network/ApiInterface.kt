package com.example.noshassignment.network

import com.example.noshassignment.network.model.DishResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("dev/nosh-assignment")
    suspend fun getDishes(): DishResponse
}