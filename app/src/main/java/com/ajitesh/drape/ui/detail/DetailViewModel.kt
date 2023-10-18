package com.ajitesh.drape.ui.detail

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ajitesh.drape.data.datasource.local.entity.Clothing
import com.ajitesh.drape.data.datasource.local.entity.Laundry
import com.ajitesh.drape.data.datasource.local.entity.Outfit
import com.ajitesh.drape.domain.repository.ClosetRepository
import com.ajitesh.drape.domain.repository.LaundryRepository
import com.ajitesh.drape.domain.repository.OutfitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DetailUiState{
    object Error: DetailUiState()
    data class OpenDetail(val clothing: Clothing): DetailUiState()
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val application: Application,
    private val closetRepository: ClosetRepository,
    private val outfitRepository: OutfitRepository,
    private val laundryRepository: LaundryRepository
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Error)
    val uiState = _uiState.asStateFlow()

    private val clothingId: Int = checkNotNull(savedStateHandle["clothingId"])

    init {
        viewModelScope.launch {
            val clothing = closetRepository.get(clothingId)
            updateState(DetailUiState.OpenDetail(clothing))
        }
    }

    private fun updateState(newState: DetailUiState) {
        _uiState.value = newState
    }

    fun addToOutfit(clothing: Clothing){
        viewModelScope.launch {
            val outfit = Outfit(clothingId = clothing.id, image = clothing.image)
            outfitRepository.insertAll(listOf(outfit))
            Toast.makeText(application, "Added to outfit", Toast.LENGTH_SHORT).show()
        }
    }

    fun addToLaundry(clothing: Clothing){
        viewModelScope.launch {
            val laundry = Laundry(clothingId = clothing.id, image = clothing.image)
            laundryRepository.insertAll(listOf(laundry))
            Toast.makeText(application, "Added to laundry", Toast.LENGTH_SHORT).show()
        }
    }

}