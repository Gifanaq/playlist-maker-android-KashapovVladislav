package com.example.myapplication.data.network

import com.example.myapplication.data.dto.*
import com.example.myapplication.domain.NetworkClient
import java.io.IOException

class RetrofitNetworkClient(private val api: ITunesApiService) : NetworkClient {

    override suspend fun doRequest(dto: Any): BaseResponse {
        return try {
            when (dto) {
                is TracksSearchRequest -> {
                    api.searchTracks(
                        query = dto.expression
                    ).apply { resultCode = 200 }
                }
                else -> BaseResponse().apply { resultCode = 400 }
            }
        } catch (e: IOException) {
            BaseResponse().apply { 
                resultCode = -1 
                errorMessage = e.message
            }
        } catch (e: Exception) {
            BaseResponse().apply { 
                resultCode = -2 
                errorMessage = e.message
            }
        }
    }
}