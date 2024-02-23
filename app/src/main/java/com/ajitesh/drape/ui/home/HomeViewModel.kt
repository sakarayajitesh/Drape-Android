package com.ajitesh.drape.ui.home

import androidx.lifecycle.ViewModel
import com.ajitesh.drape.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

sealed class HomeUiState(val title: String, val icon: Int, val position: Int){
    object Explore : HomeUiState("Explore", R.drawable.explore, 0)
    object Outfit : HomeUiState("Outfit", R.drawable.outfit, 0)
    object Closet : HomeUiState("Closet", R.drawable.closet, 1)
    object Manage : HomeUiState("Manage", R.drawable.manage, 2)
}

val HomeUiStateList = listOf(
//    HomeUiState.Explore,
    HomeUiState.Outfit,
    HomeUiState.Closet,
    HomeUiState.Manage,
)

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Closet)
    val uiState = _uiState.asStateFlow()
    fun updateUiState(homeUiState: HomeUiState) {
        _uiState.value = homeUiState
    }
}