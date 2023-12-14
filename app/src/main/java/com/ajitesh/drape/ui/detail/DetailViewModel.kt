package com.ajitesh.drape.ui.detail

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ajitesh.drape.data.datasource.local.entity.Clothing
import com.ajitesh.drape.data.datasource.local.entity.Outfit
import com.ajitesh.drape.domain.repository.DetailRepository
import com.ajitesh.drape.domain.repository.OutfitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DetailUiState {
    object Error : DetailUiState()
    data class OpenDetail(
        val clothing: Clothing,
        val laundryCount: Int? = null,
        val lastLaundryDate: Long? = null
    ) : DetailUiState()
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val application: Application,
    private val outfitRepository: OutfitRepository,
    private val detailRepository: DetailRepository
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Error)
    val uiState = _uiState.asStateFlow()

    private val clothingId: Int = checkNotNull(savedStateHandle["clothingId"])

    init {
        viewModelScope.launch {
            val clothing = detailRepository.get(clothingId)
            updateState(DetailUiState.OpenDetail(clothing))
            detailRepository.getLaundry(clothingId).collect { laundryList ->

                if (laundryList.isNotEmpty())
                    _uiState.update { state ->
                        when (state) {
                            is DetailUiState.OpenDetail -> {
                                val clo = state.clothing
                                val laundryCount = laundryList.size
                                val lastLaundryDate = laundryList.last().timestamp
                                state.copy(
                                    clothing = clo,
                                    laundryCount = laundryCount,
                                    lastLaundryDate = lastLaundryDate
                                )
                            }

                            is DetailUiState.Error -> state
                        }
                    }
            }
        }
    }

    private fun updateState(newState: DetailUiState) {
        _uiState.value = newState
    }

    fun addToOutfit(clothing: Clothing) {
        viewModelScope.launch {
            val outfit = Outfit(clothingId = clothing.id, image = clothing.image)
            outfitRepository.insertAll(listOf(outfit))
            Toast.makeText(application, "Added to outfit", Toast.LENGTH_SHORT).show()
        }
    }

    fun addToLaundry() {
        viewModelScope.launch {
            detailRepository.addLaundry(clothingId)
            Toast.makeText(application, "Added to laundry", Toast.LENGTH_SHORT).show()
        }
    }

}