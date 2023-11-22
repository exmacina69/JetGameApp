package com.noval.jetgameapp.ui.views.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.noval.jetgameapp.data.di.Injection
import com.noval.jetgameapp.data.helper.ViewModelFactory
import com.noval.jetgameapp.data.model.GameResponse
import com.noval.jetgameapp.ui.common.Hasil
import com.noval.jetgameapp.ui.components.GameListItem
import com.noval.jetgameapp.ui.components.SearchBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (String) -> Unit,
) {
    val searchResult by viewModel.searchResult.collectAsState(initial = emptyList())
    val query by viewModel.query.collectAsState(initial = "")

    viewModel.hasil.collectAsState(initial = Hasil.Loading).value.let { hasil ->
        when (hasil) {
            is Hasil.Loading -> {
                viewModel.getGames()
            }

            is Hasil.Success -> {
                Column {
                    SearchBar(
                        query = query,
                        onQueryChange = { newQuery ->
                            viewModel.setQuery(newQuery)
                            viewModel.searchGames()
                        },
                        modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                    )
                    HomeContent(
                        groupedGame = if (query.isEmpty()) hasil.data else emptyMap(),
                        searchResult = searchResult,
                        modifier = modifier,
                        navigateToDetail = navigateToDetail,
                    )
                }
            }

            is Hasil.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    groupedGame: Map<Char, List<GameResponse>>,
    searchResult: List<GameResponse>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.testTag("GameList")
    ) {
        if (searchResult.isNotEmpty()) {
            items(searchResult, key = { it.item.id }) { data ->
                GameListItem(
                    gameName = data.item.gameName,
                    bannerUrl = data.item.bannerUrl,
                    modifier = Modifier.clickable {
                        navigateToDetail(data.item.id)
                    }
                )
            }
        } else {
            groupedGame.entries.forEach { (_, gameItems) ->
                items(gameItems) { data ->
                    GameListItem(
                        gameName = data.item.gameName,
                        bannerUrl = data.item.bannerUrl,
                        modifier = Modifier.clickable {
                            navigateToDetail(data.item.id)
                        }
                    )
                }
            }
        }
    }
}