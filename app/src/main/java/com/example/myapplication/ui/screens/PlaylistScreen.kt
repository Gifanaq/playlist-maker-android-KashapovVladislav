package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.components.TrackListItem
import com.example.myapplication.ui.view_model.PlaylistViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistScreen(
    playlistViewModel: PlaylistViewModel,
    onBackClick: () -> Unit
) {
    val playlistState by playlistViewModel.playlist.collectAsState(initial = null)
    

    val currentYear = remember { Calendar.getInstance().get(Calendar.YEAR).toString() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        playlistState?.let { playlist ->
            val totalMinutes = playlist.tracks.sumOf { 
                val parts = it.trackTime.split(":")
                if (parts.size == 2) parts[0].toInt() * 60 + parts[1].toInt() else 0
            } / 60

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = padding.calculateBottomPadding())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(360.dp)
                        .background(Color(0xFFE6E6E6)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_music),
                        contentDescription = null,
                        modifier = Modifier.size(120.dp),
                        tint = Color.LightGray
                    )
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = playlist.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = currentYear,
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    
                    val timeString = pluralStringResource(id = R.plurals.minutes_count, count = totalMinutes, totalMinutes)
                    val tracksString = pluralStringResource(id = R.plurals.tracks_count, count = playlist.tracks.size, playlist.tracks.size)
                    
                    Text(
                        text = "$timeString • $tracksString",
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    IconButton(
                        onClick = { /* Меню */ },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Меню", tint = Color.Black)
                    }
                }

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(playlist.tracks) { track ->
                        TrackListItem(track = track)
                        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
                    }
                }
            }
        } ?: run {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Blue)
            }
        }
    }
}
