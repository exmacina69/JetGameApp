package com.noval.jetgameapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FindMoreInfoButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}