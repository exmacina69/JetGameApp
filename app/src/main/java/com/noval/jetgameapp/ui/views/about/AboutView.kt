package com.noval.jetgameapp.ui.views.about

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.noval.jetgameapp.R
import com.noval.jetgameapp.ui.theme.JetGameAppTheme

@Composable
fun AboutScreen(modifier: Modifier = Modifier, navigateBack: () -> Unit) {
    AboutContent(modifier = modifier, onBackClick = navigateBack)
}

@Composable
fun AboutContent(modifier: Modifier = Modifier, onBackClick: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(onBackClick = onBackClick)
        Spacer(modifier = Modifier.height(100.dp))
        ProfileInformation()
    }
}

@Composable
fun TopBar(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
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
                text = stringResource(R.string.about),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            )
        }
    }
}

@Composable
fun ProfileInformation() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = stringResource(R.string.picture_profile),
            contentDescription = stringResource(R.string.profile),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(15.dp)
                .size(200.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
        Text(
            text = stringResource(R.string.my_name),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = stringResource(R.string.my_univ),
            style = TextStyle(
                fontSize = 12.sp,
                color = Color.Green,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(5.dp)
        )
        Text(
            text = stringResource(R.string.my_email),
            style = TextStyle(
                fontSize = 12.sp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAboutContent() {
    JetGameAppTheme {
        AboutContent {}
    }
}
