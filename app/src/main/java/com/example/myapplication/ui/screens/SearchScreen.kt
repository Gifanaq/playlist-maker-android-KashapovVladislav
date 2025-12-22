package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.ui.common.SearchScreenResources
import com.example.myapplication.ui.common.rememberSearchScreenResources
import com.example.myapplication.ui.components.TrackListItem
import com.example.myapplication.ui.view_model.SearchState
import com.example.myapplication.ui.view_model.SearchViewModel


@Composable
fun SearchScreen(
    onBackClick: () -> Unit = {}
) {

    val viewModel: SearchViewModel = viewModel(factory = SearchViewModel.getViewModelFactory())
    val screenState by viewModel.searchScreenState.collectAsState()
    var searchText by remember { mutableStateOf("") }

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
                .padding(
                    top = resources.bottomSheetPaddingTop,
                    start = resources.bottomSheetPaddingHorizontal,
                    end = resources.bottomSheetPaddingHorizontal
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = resources.backString,
                    tint = resources.black,
                    modifier = Modifier.size(resources.iconSizeMedium)
                )
            }

            Spacer(modifier = Modifier.weight(0.02f))

            Text(
                text = resources.searchString,
                color = resources.black,
                fontSize = resources.searchTitleFontSize,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.weight(0.02f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = resources.bottomSheetPaddingHorizontal)
                .height(resources.searchBarHeight)
                .background(
                    color = resources.searchBackground,
                    shape = RoundedCornerShape(resources.searchBarCornerRadius)
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = resources.searchBarPaddingHorizontal),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = resources.searchHint,
                    tint = resources.gray,
                    modifier = Modifier.size(resources.iconSizeSmall)
                )

                Spacer(modifier = Modifier.weight(0.02f))

                BasicTextField(
                    value = searchText,
                    onValueChange = { newText ->
                        searchText = newText
                        if (newText.isEmpty()) {
                            viewModel.clearSearch()
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    textStyle = TextStyle(
                        fontSize = resources.searchTextFontSize,
                        color = resources.black
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (searchText.isEmpty()) {
                                Text(
                                    text = resources.searchHint,
                                    fontSize = resources.searchHintFontSize,
                                    color = resources.gray
                                )
                            }
                            innerTextField()
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            if (searchText.isNotBlank()) {
                                viewModel.search(searchText)
                            }
                        }
                    )
                )

                if (searchText.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            searchText = ""
                            viewModel.clearSearch()
                        },
                        modifier = Modifier.size(resources.iconSizeSmall)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = resources.clearString,
                            tint = resources.gray,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(0.04f))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = resources.bottomSheetPaddingHorizontal)
        ) {
            SearchContent(
                screenState = screenState,
                searchText = searchText,
                resources = resources,
                onRetry = { if (searchText.isNotBlank()) viewModel.search(searchText) }
            )
        }
    }
}

@Composable
private fun SearchContent(
    screenState: SearchState,
    searchText: String,
    resources: SearchScreenResources,
    onRetry: () -> Unit
) {
    if (searchText.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = resources.searchInitialMessage,
                color = resources.gray,
                modifier = Modifier.padding(top = 80.dp)
            )
        }
    } else {
        when (screenState) {
            is SearchState.Initial -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = resources.searchInitialMessage,
                        color = resources.gray,
                        modifier = Modifier.padding(top = 80.dp)
                    )
                }
            }

            is SearchState.Searching -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = resources.searchingString)
                    }
                }
            }

            is SearchState.Success -> {
                val tracks = screenState.list
                if (tracks.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Text(
                            text = resources.noResultsString,
                            color = resources.gray,
                            modifier = Modifier.padding(top = 80.dp)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(1.dp)
                    ) {
                        items(tracks) { track ->
                            TrackListItem(track = track)
                            HorizontalDivider(
                                thickness = 0.5.dp,
                                color = resources.divider
                            )
                        }
                    }
                }
            }

            is SearchState.Fail -> {
                val error = screenState.error
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = resources.searchErrorString,
                            color = Color.Red,
                            modifier = Modifier.padding(top = 80.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = error,
                            color = resources.gray
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onRetry) {
                            Text(text = resources.retryString)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun PreviewSearchScreen() {
    MaterialTheme {
        SearchScreen(
            onBackClick = {}
        )
    }
}