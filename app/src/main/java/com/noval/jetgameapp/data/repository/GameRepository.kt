package com.noval.jetgameapp.data.repository

import com.noval.jetgameapp.data.model.GameResponse
import com.noval.jetgameapp.data.model.DataGames
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GameRepository {

    private val gameResponse = mutableListOf<GameResponse>()
    private val favGames = mutableListOf<String>()

    fun removeFromFavorites(gamesId: String) {
        favGames.remove(gamesId)
    }

    fun isFavorite(gamesId: String): Boolean {
        return favGames.contains(gamesId)
    }

    fun getSortedAndGroupedGames(): Flow<Map<Char, List<GameResponse>>> {
        return flow {
            val sortedGames = gameResponse.sortedBy { it.item.gameName }
            val groupedGames = sortedGames.groupBy { it.item.gameName[0] }
            emit(groupedGames)
        }
    }

    fun getGameItembyId(gamesId: String): GameResponse {
        return gameResponse.first {
            it.item.id == gamesId
        }
    }

    fun getSearchGames(query: String): Flow<List<GameResponse>> {
        return flow {
            val filteredGames = gameResponse.filter {
                it.item.gameName.contains(query, ignoreCase = true)
            }
            emit(filteredGames)
        }
    }

    fun getFavGames(): Flow<List<GameResponse>> {
        return flow {
            val favGameItems = gameResponse.filter { it.item.id in favGames }
            emit(favGameItems)
        }
    }

    fun addToFavorites(gamesId: String) {
        if (!favGames.contains(gamesId)) {
            favGames.add(gamesId)
        }
    }

    init {
        if (gameResponse.isEmpty()) {
            DataGames.listGameResponses.forEach {
                gameResponse.add(GameResponse(it, 0))
            }
        }
    }

    companion object {
        @Volatile
        private var instance: GameRepository? = null

        fun getInstance(): GameRepository = instance ?: synchronized(this) {
            GameRepository().apply {
                instance = this
            }
        }
    }
}