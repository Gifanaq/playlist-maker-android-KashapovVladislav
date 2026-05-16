package com.example.myapplication.ui.view_model

import com.example.myapplication.data.network.TracksRepositoryImpl
import com.example.myapplication.domain.TracksRepository
import kotlinx.coroutines.CoroutineScope

object Creator {

    fun getTracksRepository(scope: CoroutineScope): TracksRepository {
        return TracksRepositoryImpl(scope)
    }
}