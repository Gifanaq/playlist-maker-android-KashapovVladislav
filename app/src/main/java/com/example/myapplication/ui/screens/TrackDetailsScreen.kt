package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.view_model.PlaylistsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackDetailsScreen(
    playlistsViewModel: PlaylistsViewModel,
    onBackClick: () -> Unit
) {
    // Получаем выбранный трек из ViewModel
    val selectedTrack by playlistsViewModel.selectedTrack.collectAsState()
    
    // Если трек не выбран (что маловероятно при правильной навигации), выходим
    if (selectedTrack == null) {
        LaunchedEffect(Unit) { onBackClick() }
        return
    }

    val track = selectedTrack!!
    var isFavorite by remember { mutableStateOf(track.favorite) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    
    val playlists by playlistsViewModel.playlists.collectAsState(initial = emptyList())

    // Синхронизируем состояние избранного с базой при открытии
    LaunchedEffect(track) {
        val existingTrack = playlistsViewModel.isExist(track)
        if (existingTrack != null) {
            isFavorite = existingTrack.favorite
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
    ) {
        IconButton(onClick = onBackClick, modifier = Modifier.padding(16.dp)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFF0F0F0)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_music),
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = Color.LightGray
            )
        }

        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = track.trackName, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(text = track.artistName, fontSize = 14.sp, modifier = Modifier.padding(top = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { showBottomSheet = true },
                    modifier = Modifier.background(Color(0xFFF0F0F0), RoundedCornerShape(50))
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Добавить в плейлист")
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = { 
                        isFavorite = !isFavorite
                        scope.launch { 
                            playlistsViewModel.toggleFavorite(track.copy(favorite = isFavorite), isFavorite) 
                        }
                    },
                    modifier = Modifier.background(Color(0xFFF0F0F0), RoundedCornerShape(50))
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Избранное",
                        tint = if (isFavorite) Color.Red else Color.Gray
                    )
                }
            }

            Row(modifier = Modifier.fillMaxWidth().padding(top = 40.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Длительность", color = Color.Gray)
                Text(text = track.trackTime)
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)) {
                Text(
                    text = "Добавить в плейлист",
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                LazyColumn(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    items(playlists) { playlist ->
                        PlaylistListItem(playlist = playlist) {
                            scope.launch {
                                playlistsViewModel.insertTrackToPlaylist(track.copy(favorite = isFavorite), playlist.id)
                                showBottomSheet = false
                            }
                        }
                    }
                }
            }
        }
    }
}
