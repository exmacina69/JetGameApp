package com.noval.jetgameapp.ui.views.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.noval.jetgameapp.R
import com.noval.jetgameapp.data.di.Injection
import com.noval.jetgameapp.data.helper.ViewModelFactory
import com.noval.jetgameapp.data.model.GameResponse
import com.noval.jetgameapp.ui.common.Hasil
import com.noval.jetgameapp.ui.components.GameListItem
import com.noval.jetgameapp.ui.theme.JetGameAppTheme

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())),
    navigateBack: () -> Unit,
    navigateToDetail: (String) -> Unit
) {
    val favGames by viewModel.favoriteGames.collectAsState(emptyList())

    val hasil by viewModel.hasil.collectAsState(initial = Hasil.Loading)

    when (hasil) {
        is Hasil.Loading -> {
            viewModel.getAllFavGames()
        }

        is Hasil.Success -> {
            FavoriteContent(
                favoriteGames = favGames,
                modifier = modifier,
                onBackClick = navigateBack,
                navigateToDetail = navigateToDetail
            )
        }

        is Hasil.Error -> {
            // Handle error state
        }
    }
}

@Composable
fun FavoriteContent(
    favoriteGames: List<GameResponse>,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit
) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        TopBar(onBackClick = onBackClick)
        Spacer(modifier = Modifier.height(8.dp))

        if (favoriteGames.isEmpty()) {
            EmptyFavoriteText()
        } else {
            GameList(favoriteGames = favoriteGames, navigateToDetail = navigateToDetail)
        }
    }
}

@Composable
fun TopBar(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                tint = Color.Black,
                contentDescription = stringResource(R.string.back),
                modifier = Modifier
                    .padding(5.dp)
                    .clickable { onBackClick() }
            )
            Text(
                text = "Favorite",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            )
        }
    }
}

@Composable
fun EmptyFavoriteText() {
    Text(
        text = stringResource(R.string.empty_fav),
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        color = Color.LightGray,
        textAlign = TextAlign.Justify
    )
}

@Composable
fun GameList(favoriteGames: List<GameResponse>, navigateToDetail: (String) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(favoriteGames) { data ->
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

@Preview(showBackground = true)
@Composable
fun PreviewFavoriteContent() {
    JetGameAppTheme {
        FavoriteContent(
            favoriteGames = listOf(),
            onBackClick = {},
            navigateToDetail = {}
        )
    }
}
