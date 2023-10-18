package com.ajitesh.drape.ui.manage

import androidx.lifecycle.ViewModel
import com.ajitesh.drape.data.datasource.local.entity.Clothing
import com.ajitesh.drape.domain.repository.MasterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

sealed class TabUiState {
    object Fresh : TabUiState()
    object Hanger : TabUiState()
    object Basket : TabUiState()
}

@HiltViewModel
class ManageViewModel @Inject constructor(
    masterRepository: MasterRepository,
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

    fun updateTabUiState(tabUiState: TabUiState) {
        _tabUiState.value = tabUiState
    }
}