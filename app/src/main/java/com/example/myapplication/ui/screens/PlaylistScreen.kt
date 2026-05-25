package com.example.myapplication.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
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

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { playlistViewModel.updateCoverImage(it.toString()) }
    }

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

            val playlistYear = remember(playlist.createdAt) {
                val calendar = Calendar.getInstance().apply { timeInMillis = playlist.createdAt }
                calendar.get(Calendar.YEAR).toString()
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = padding.calculateBottomPadding())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(360.dp)
                        .background(Color(0xFFE6E6E6))
                        .clickable { imagePickerLauncher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (playlist.coverImageUri != null) {
                        AsyncImage(
                            model = playlist.coverImageUri.toUri(), // Используем расширение KTX
                            contentDescription = stringResource(R.string.playlist_cover),
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add_photo),
                            contentDescription = "Добавить фото",
                            modifier = Modifier.fillMaxSize(),
                            tint = Color.Unspecified
                        )
                    }
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = playlist.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Text(
                        text = playlistYear, 
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    
                    if (playlist.description.isNotEmpty()) {
                        Text(
                            text = playlist.description,
                            fontSize = 18.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

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
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(playlist.tracks) { track ->
                        TrackListItem(
                            track = track,
                            onLongClick = {
                                playlistViewModel.deleteTrack(track.id)
                            }
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(start = 73.dp),
                            thickness = 0.5.dp, 
                            color = Color.LightGray
                        )
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
