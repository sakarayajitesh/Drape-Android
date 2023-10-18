package com.ajitesh.drape.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class HomeUiState(val title: String, val position: Int){
    object Explore: HomeUiState("Explore", 0)
    object Outfit: HomeUiState("Outfit", 1)
    object Closet: HomeUiState("Closet", 2)
    object Manage: HomeUiState("Manage", 3)
}

val HomeUiStateList = listOf(
    HomeUiState.Explore,
    HomeUiState.Outfit,
    HomeUiState.Closet,
    HomeUiState.Manage,
)

class HomeViewModel: ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Explore)
    val uiState = _uiState.asStateFlow()

    fun updateUiState(homeUiState: HomeUiState){
        _uiState.value = homeUiState
    }

}