package com.example.myapplication.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.R
import com.example.myapplication.ui.common.MainScreenResources
import com.example.myapplication.ui.common.rememberMainScreenResources


@Composable
fun MainScreen(
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val context = LocalContext.current
    val resources = rememberMainScreenResources()

    val menuItems = listOf(
        MenuItem(resources.searchString, R.drawable.ic_search),
        MenuItem(resources.playlistsString, R.drawable.ic_playlist),
        MenuItem(resources.favoritesString, R.drawable.ic_favorite),
        MenuItem(resources.settingsString, R.drawable.ic_settings)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(resources.primaryBlue)
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = resources.titlePaddingTop)
        ) {
            Text(
                text = resources.appName,
                color = resources.white,
                fontSize = resources.titleFontSize,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(start = resources.titlePaddingStart)
            )

            Spacer(modifier = Modifier.weight(0.02f))

            Surface(
                modifier = Modifier.weight(0.98f),
                color = resources.white,
                shape = RoundedCornerShape(
                    topStart = resources.bottomSheetCornerRadius,
                    topEnd = resources.bottomSheetCornerRadius
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = resources.bottomSheetPaddingTop,
                            start = resources.bottomSheetPaddingHorizontal,
                            end = resources.bottomSheetPaddingHorizontal
                        )
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(menuItems) { item ->
                            MenuItemCard(
                                item = item,
                                resources = resources,
                                onItemClick = {
                                    when (item.title) {
                                        resources.searchString -> onSearchClick()
                                        resources.playlistsString -> {
                                            Toast.makeText(
                                                context,
                                                resources.playlistsToast,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        resources.favoritesString -> {
                                            Toast.makeText(
                                                context,
                                                resources.favoritesToast,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        resources.settingsString -> onSettingsClick()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MenuItemCard(
    item: MenuItem,
    resources: MainScreenResources,
    onItemClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(resources.menuItemHeight),
        color = resources.white,
        onClick = onItemClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = resources.menuItemPaddingVertical,
                    horizontal = resources.menuItemPaddingHorizontal
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = item.iconRes),
                contentDescription = item.title,
                modifier = Modifier.size(resources.menuItemIconSize),
                tint = resources.black
            )
            Spacer(modifier = Modifier.weight(0.02f))
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = resources.menuItemTextFontSize,
                    lineHeight = resources.menuItemTextLineHeight
                ),
                color = resources.textPrimary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = null,
                modifier = Modifier.size(resources.menuItemIconSize),
                tint = resources.gray
            )
        }
    }
}

data class MenuItem(
    val title: String,
    val iconRes: Int
)

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun PreviewPlaylistMakerScreen() {
    MaterialTheme {
        MainScreen(
            onSearchClick = {},
            onSettingsClick = {}
        )
    }
}