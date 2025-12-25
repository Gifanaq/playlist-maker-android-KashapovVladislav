package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.common.rememberSettingsScreenResources


@Composable
fun PlaylistsScreen(
    onBackClick: () -> Unit = {}
) {
    val resources = rememberSettingsScreenResources()

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
                    contentDescription = "Back",
                    tint = resources.black,
                    modifier = Modifier.size(resources.iconSizeMedium)
                )
            }

            Spacer(modifier = Modifier.weight(0.02f))

            Text(
                text = "Плейлисты",
                color = resources.black,
                fontSize = resources.searchTitleFontSize,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Плейлисты",
                color = resources.gray
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun PreviewPlaylistsScreen() {
    MaterialTheme {
        PlaylistsScreen(
            onBackClick = {}
        )
    }
}