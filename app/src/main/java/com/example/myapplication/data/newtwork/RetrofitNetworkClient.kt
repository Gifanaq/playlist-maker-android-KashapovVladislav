package com.example.myapplication.data.newtwork


import com.example.myapplication.creator.Storage
import com.example.myapplication.data.dto.*
import com.example.myapplication.domain.NetworkClient




class RetrofitNetworkClient(private val storage: Storage) : NetworkClient {

    override fun doRequest(dto: Any): TracksSearchResponse {
        val searchList = storage.search((dto as TracksSearchRequest).expression)
        return TracksSearchResponse(searchList).apply { resultCode = 200 }
    }
}