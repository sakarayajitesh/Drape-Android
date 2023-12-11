package com.ajitesh.drape.ui.home

import androidx.lifecycle.ViewModel
import com.ajitesh.drape.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class HomeUiState(val title: String, val icon: Int, val position: Int){
    object Explore: HomeUiState("Explore", R.drawable.explore, 0)
    object Outfit: HomeUiState("Outfit", R.drawable.outfit,1)
    object Closet: HomeUiState("Closet", R.drawable.closet ,2)
    object Manage: HomeUiState("Manage", R.drawable.manage, 3)
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