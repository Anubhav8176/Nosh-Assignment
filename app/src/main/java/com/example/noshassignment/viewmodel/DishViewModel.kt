package com.example.noshassignment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noshassignment.network.RetrofitInstance
import com.example.noshassignment.network.model.DishResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DishViewModel : ViewModel() {

    private val _dishData = MutableStateFlow<List<DishResponseItem>>(emptyList())
    val dishData: StateFlow<List<DishResponseItem>> = _dishData.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        getDish()
    }

    fun getDish() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val dishResponse = RetrofitInstance.api.getDishes()
                _dishData.value = dishResponse
            } catch (e: Exception) {
                Log.e("DishViewModel", "Error fetching dishes: ${e.message}")
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}