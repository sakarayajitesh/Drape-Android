package com.ajitesh.drape.ui.closet

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajitesh.drape.data.datasource.local.entity.Clothing
import com.ajitesh.drape.toClothingList
import com.ajitesh.drape.domain.repository.ClosetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ClosetUiState {

    class PhotoList(val photos: List<Clothing>) : ClosetUiState()
}

@HiltViewModel
class ClosetViewModel @Inject constructor(private val repository: ClosetRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<ClosetUiState>(ClosetUiState.PhotoList(emptyList()))
    val uiState: StateFlow<ClosetUiState> = _uiState

    init {
        viewModelScope.launch {
            repository.getAll().collect {
                updateState(ClosetUiState.PhotoList(it))
            }
        }
    }

    private fun updateState(newState: ClosetUiState) {
        _uiState.value = newState
    }

    fun addPhotos(photos: List<Uri>) {
        viewModelScope.launch {
            repository.insertAll(photos.toClothingList())
        }
    }
}