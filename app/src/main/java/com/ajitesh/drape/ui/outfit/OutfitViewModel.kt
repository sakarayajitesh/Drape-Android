package com.ajitesh.drape.ui.outfit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajitesh.drape.data.datasource.local.entity.Outfit
import com.ajitesh.drape.domain.repository.OutfitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class OutfitUiState {
    data class OutfitList(val outfits: List<Outfit>) : OutfitUiState()
}

@HiltViewModel
class OutfitViewModel @Inject constructor(private val outfitRepository: OutfitRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow<OutfitUiState>(OutfitUiState.OutfitList(emptyList()))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            outfitRepository.getAll().collect{
                updateState(OutfitUiState.OutfitList(it.reversed()))
            }
        }
    }

    private fun updateState(newState: OutfitUiState) {
        _uiState.value = newState
    }
}