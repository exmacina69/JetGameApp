package com.noval.jetgameapp.ui.views.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noval.jetgameapp.data.model.GameResponse
import com.noval.jetgameapp.data.repository.GameRepository
import com.noval.jetgameapp.ui.common.Hasil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: GameRepository) : ViewModel() {
    private val _hasil: MutableStateFlow<Hasil<GameResponse>> = MutableStateFlow(Hasil.Loading)

    val hasil: StateFlow<Hasil<GameResponse>> get() = _hasil

    fun getGamesByID(gamesId: String) {
        viewModelScope.launch {
            _hasil.value = Hasil.Loading
            _hasil.value = Hasil.Success(repository.getGameItembyId(gamesId))
        }
    }

    fun addToFavorites(gamesId: String) {
        viewModelScope.launch {
            repository.addToFavorites(gamesId)
        }
    }

    fun removeFromFavorite(gamesId: String) {
        viewModelScope.launch {
            repository.removeFromFavorites(gamesId)
        }
    }

    fun checkFavorite(gamesId: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isFavorite = repository.isFavorite(gamesId)
            onResult(isFavorite)
        }
    }
}