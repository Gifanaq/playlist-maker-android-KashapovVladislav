package com.example.myapplication.ui.view_model


import com.example.myapplication.creator.Storage
import com.example.myapplication.data.newtwork.RetrofitNetworkClient
import com.example.myapplication.data.newtwork.TracksRepositoryImpl
import com.example.myapplication.domain.TracksRepository



object Creator {
    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient(Storage()))
    }
}