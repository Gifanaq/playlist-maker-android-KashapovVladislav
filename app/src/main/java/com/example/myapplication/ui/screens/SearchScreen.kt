package com.example.myapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.data.network.Track
import com.example.myapplication.ui.common.SearchScreenResources
import com.example.myapplication.ui.common.rememberSearchScreenResources
import com.example.myapplication.ui.components.TrackListItem
import com.example.myapplication.ui.view_model.PlaylistsViewModel
import com.example.myapplication.ui.view_model.SearchState
import com.example.myapplication.ui.view_model.SearchViewModel


@Composable
fun SearchScreen(
    playlistsViewModel: PlaylistsViewModel,
    onBackClick: () -> Unit = {},
    onTrackClick: () -> Unit = {}
) {
    val viewModel: SearchViewModel = viewModel()
    val screenState by viewModel.searchScreenState.collectAsState()
    var searchText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val resources = rememberSearchScreenResources()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(resources.white)
            .statusBarsPadding()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = resources.backString,
                    tint = resources.black
                )
            }
            Text(
                text = resources.searchString,
                color = resources.black,
                fontSize = resources.searchTitleFontSize,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 16.dp)
            )
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(resources.searchBarHeight)
                .background(
                    color = resources.searchBackground,
                    shape = RoundedCornerShape(resources.searchBarCornerRadius)
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Search, contentDescription = null, tint = resources.gray)
                BasicTextField(
                    value = searchText,
                    onValueChange = { 
                        searchText = it
                        if (it.isEmpty()) viewModel.clearSearch() else viewModel.searchDebounce(it)
                    },
                    modifier = Modifier.weight(1f).padding(start = 8.dp),
                    textStyle = TextStyle(fontSize = resources.searchTextFontSize, color = resources.black),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { 
                        viewModel.search(searchText)
                        focusManager.clearFocus()
                    })
                )
                if (searchText.isNotEmpty()) {
                    IconButton(onClick = { 
                        searchText = ""
                        viewModel.clearSearch()
                        focusManager.clearFocus()
                    }) {
                        Icon(Icons.Default.Clear, contentDescription = null, tint = resources.gray)
                    }
                }
            }
        }

        // Контентная область
        Box(modifier = Modifier.fillMaxSize()) {
            SearchContent(
                screenState = screenState,
                searchText = searchText,
                resources = resources,
                onTrackClick = { track ->
                    playlistsViewModel.setSelectedTrack(track)
                    onTrackClick()
                },
                onHistoryClick = { query ->
                    searchText = query
                    viewModel.search(query)
                },
                onClearHistory = {
                    viewModel.clearHistory()
                },
                onRetry = { viewModel.search(searchText) }
            )
        }
    }
}

@Composable
private fun SearchContent(
    screenState: SearchState,
    searchText: String,
    resources: SearchScreenResources,
    onTrackClick: (Track) -> Unit,
    onHistoryClick: (String) -> Unit,
    onClearHistory: () -> Unit,
    onRetry: () -> Unit
) {
    if (searchText.isEmpty() && screenState is SearchState.Initial) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = resources.searchInitialMessage, color = resources.gray, textAlign = TextAlign.Center)
        }
        return
    }

    when (screenState) {
        is SearchState.History -> {
            if (searchText.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(top = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Вы искали",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f).padding(top = 16.dp)) {
                        items(screenState.queries) { query ->
                            Text(
                                text = query,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onHistoryClick(query) }
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                    }
                    Button(
                        onClick = onClearHistory,
                        modifier = Modifier.padding(bottom = 24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.Black)
                    ) {
                        Text(text = "Очистить историю", color = Color.Black)
                    }
                }
            }
        }

        is SearchState.Searching -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Blue)
            }
        }
        
        is SearchState.Success -> {
            if (screenState.list.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_not_found),
                            contentDescription = null,
                            modifier = Modifier.size(120.dp)
                        )
                        Text(
                            text = "Ничего не нашлось",
                            modifier = Modifier.padding(top = 16.dp),
                            color = Color.Black,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(screenState.list) { track ->
                        Box(modifier = Modifier.clickable { onTrackClick(track) }) {
                            TrackListItem(track = track)
                        }
                        HorizontalDivider(thickness = 0.5.dp, color = resources.divider)
                    }
                }
            }
        }
        
        is SearchState.Fail -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_network_error),
                        contentDescription = null,
                        modifier = Modifier.size(120.dp)
                    )
                    Text(
                        text = "Проблемы со связью",
                        color = Color.Black,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = "Загрузка не удалась. Проверьте подключение к интернету",
                        color = Color.Black,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Button(
                        onClick = onRetry,
                        modifier = Modifier.padding(top = 24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        shape = RoundedCornerShape(54.dp)
                    ) {
                        Text(text = "Обновить", color = Color.White, modifier = Modifier.padding(horizontal = 4.dp))
                    }
                }
            }
        }
        else -> {}
    }
}
