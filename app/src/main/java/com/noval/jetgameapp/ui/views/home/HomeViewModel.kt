package com.noval.jetgameapp.ui.views.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noval.jetgameapp.data.model.GameResponse
import com.noval.jetgameapp.data.repository.GameRepository
import com.noval.jetgameapp.ui.common.Hasil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: GameRepository) : ViewModel() {

    val hasil: StateFlow<Hasil<Map<Char, List<GameResponse>>>> get() = _hasil

    private val _hasil: MutableStateFlow<Hasil<Map<Char, List<GameResponse>>>> =
        MutableStateFlow(Hasil.Loading)

    private val _searchResult = MutableStateFlow<List<GameResponse>>(emptyList())
    val searchResult: StateFlow<List<GameResponse>> get() = _searchResult

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    fun getGames() {
        viewModelScope.launch {
            repository.getSortedAndGroupedGames()
                .catch {
                    _hasil.value = Hasil.Error(it.message.toString())
                }
                .collect { groupedGameItems ->
                    _hasil.value = Hasil.Success(groupedGameItems)
                }
        }
    }

    fun searchGames() {
        val currentQuery = _query.value
        viewModelScope.launch {
            repository.getSearchGames(currentQuery)
                .catch {
                    _hasil.value = Hasil.Error(it.message.toString())
                }
                .collect { searchResult ->
                    _searchResult.value = searchResult
                }
        }
    }

    fun setQuery(newQuery: String) {
        _query.value = newQuery
    }
}

