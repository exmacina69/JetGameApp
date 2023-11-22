package com.noval.jetgameapp.ui.common

sealed class Hasil<out T : Any> {
    object Loading : Hasil<Nothing>()
    data class Success<out T : Any>(val data: T) : Hasil<T>()
    data class Error(val errorMessage: String) : Hasil<Nothing>()
}