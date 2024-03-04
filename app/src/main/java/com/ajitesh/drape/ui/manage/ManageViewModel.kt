package com.ajitesh.drape.ui.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajitesh.drape.data.datasource.local.entity.Clothing
import com.ajitesh.drape.domain.repository.MasterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class TabUiState {
    object Fresh : TabUiState()
    object Hanger : TabUiState()
    object Basket : TabUiState()
}

@HiltViewModel
class ManageViewModel @Inject constructor(
    private val masterRepository: MasterRepository,
) : ViewModel() {

    private val _tabUiState = MutableStateFlow<TabUiState>(TabUiState.Fresh)
    val tabUiState = _tabUiState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val clothingList: Flow<List<Clothing>> = tabUiState.flatMapLatest {
        when (it) {
            is TabUiState.Fresh -> masterRepository.getFreshClothingList()
            is TabUiState.Hanger -> masterRepository.getHangerClothingList()
            is TabUiState.Basket -> masterRepository.getBasketClothingList()
        }
    }

    private val _selectedClothing = MutableStateFlow<List<Int>>(emptyList())
    val selectedClothing = _selectedClothing.asStateFlow()

    fun updateTabUiState(tabUiState: TabUiState) {
        _tabUiState.value = tabUiState
    }

    fun selectClothing(id: Int) {
        val currentList = _selectedClothing.value.toMutableList()
        if (currentList.contains(id)) {
            currentList.remove(id)
        } else {
            currentList.add(id)
        }
        _selectedClothing.value = currentList
    }

    fun addToLaundry(onComplete: () -> Unit) {
        viewModelScope.launch {
            masterRepository.addAllToLaundry(_selectedClothing.value) {
                _selectedClothing.value = emptyList()
                onComplete()
            }
        }
    }
}