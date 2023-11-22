package com.noval.jetgameapp.data.di

import com.noval.jetgameapp.data.repository.GameRepository

object Injection {
    fun provideRepository(): GameRepository {
        return GameRepository.getInstance()
    }
}