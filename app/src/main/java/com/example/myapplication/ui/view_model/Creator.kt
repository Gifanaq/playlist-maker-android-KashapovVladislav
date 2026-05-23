package com.example.myapplication.ui.view_model

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.network.ITunesApiService
import com.example.myapplication.data.network.PlaylistsRepositoryImpl
import com.example.myapplication.data.network.RetrofitNetworkClient
import com.example.myapplication.data.network.SearchHistoryRepositoryImpl
import com.example.myapplication.data.network.TracksRepositoryImpl
import com.example.myapplication.data.preferences.SearchHistoryPreferences
import com.example.myapplication.domain.PlaylistsRepository
import com.example.myapplication.domain.SearchHistoryRepository
import com.example.myapplication.domain.TracksRepository
import kotlinx.coroutines.CoroutineScope
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val Context.dataStore by preferencesDataStore(name = "search_history")

object Creator {
    private const val BASE_URL = "https://itunes.apple.com"
    private var database: AppDatabase? = null
    private var searchHistoryPreferences: SearchHistoryPreferences? = null

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val iTunesService = retrofit.create(ITunesApiService::class.java)
    
    fun init(context: Context) {
        if (database == null) {
            database = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "playlists_maker.db"
            ).fallbackToDestructiveMigration().build()
        }
        if (searchHistoryPreferences == null) {
            searchHistoryPreferences = SearchHistoryPreferences(context.dataStore)
        }
    }

    private fun getNetworkClient(): RetrofitNetworkClient {
        return RetrofitNetworkClient(iTunesService)
    }

    fun getTracksRepository(scope: CoroutineScope): TracksRepository {
        return TracksRepositoryImpl(getNetworkClient(), database!!)
    }

    fun getPlaylistsRepository(scope: CoroutineScope): PlaylistsRepository {
        return PlaylistsRepositoryImpl(database!!)
    }

    fun getSearchHistoryRepository(): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(searchHistoryPreferences!!)
    }
}
