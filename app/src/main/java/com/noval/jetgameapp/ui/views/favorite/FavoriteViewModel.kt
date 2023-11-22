package com.noval.jetgameapp.ui.views.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noval.jetgameapp.data.model.GameResponse
import com.noval.jetgameapp.data.repository.GameRepository
import com.noval.jetgameapp.ui.common.Hasil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: GameRepository) : ViewModel() {
    private val _hasil: MutableStateFlow<Hasil<List<GameResponse>>> =
        MutableStateFlow(Hasil.Loading)

    val hasil: StateFlow<Hasil<List<GameResponse>>> get() = _hasil

    val favoriteGames: Flow<List<GameResponse>> = repository.getFavGames()

    fun getAllFavGames() {
        viewModelScope.launch {
            repository.getFavGames()
                .catch {
                    _hasil.value = Hasil.Error(it.message.toString())
                }
                .collect { favGameItems ->
                    _hasil.value = Hasil.Success(favGameItems)
                }
        }
    }
}