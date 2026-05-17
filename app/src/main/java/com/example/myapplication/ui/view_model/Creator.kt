package com.example.myapplication.ui.view_model

import com.example.myapplication.data.network.ITunesApiService
import com.example.myapplication.data.network.PlaylistsRepositoryImpl
import com.example.myapplication.data.network.RetrofitNetworkClient
import com.example.myapplication.data.network.TracksRepositoryImpl
import com.example.myapplication.domain.PlaylistsRepository
import com.example.myapplication.domain.TracksRepository
import kotlinx.coroutines.CoroutineScope
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Creator {
    private const val BASE_URL = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val iTunesService = retrofit.create(ITunesApiService::class.java)
    
    private fun getNetworkClient(): RetrofitNetworkClient {
        return RetrofitNetworkClient(iTunesService)
    }

    fun getTracksRepository(scope: CoroutineScope): TracksRepository {
        return TracksRepositoryImpl(getNetworkClient(), scope)
    }

    fun getPlaylistsRepository(scope: CoroutineScope): PlaylistsRepository {
        return PlaylistsRepositoryImpl(scope)
    }
}