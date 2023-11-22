package com.noval.jetgameapp.data.model

data class ListGameResponse(
    val id: String,
    val gameName: String,
    val desc: String,
    val year: String,
    val publisher: String,
    val bannerUrl: String,
    val publisherUrl: String,
    val genreGame: String,
    val awards: String,
)