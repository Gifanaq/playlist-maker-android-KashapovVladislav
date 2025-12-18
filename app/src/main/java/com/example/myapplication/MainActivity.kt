package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MaterialTheme {
                PlaylistMakerScreen()
            }
        }
    }
}

@Composable
fun PlaylistMakerScreen() {
    val context = LocalContext.current
    val menuItems = listOf(
        MenuItem("Поиск", R.drawable.ic_search),
        MenuItem("Плейлисты", R.drawable.ic_playlist),
        MenuItem("Избранное", R.drawable.ic_favorite),
        MenuItem("Настройки", R.drawable.ic_settings)
    )

    val blueColor = Color(0xFF3772E7)
    val whiteColor = Color.White
    val textColor = Color(0xFF1A1B22)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(blueColor)
    ) {
        Text(
            text = "Playlist maker",
            color = whiteColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 24.dp, start = 16.dp)
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(730.dp)
                .align(Alignment.BottomStart),
            color = whiteColor,
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 8.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(menuItems) { item ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(66.dp),
                            color = whiteColor,
                            onClick = {
                                when (item.title) {
                                    "Поиск" -> {
                                        val intent = Intent(context, SearchActivity::class.java)
                                        context.startActivity(intent)
                                    }
                                    "Плейлисты" -> {
                                        Toast.makeText(
                                            context,
                                            "Нажата кнопка \"${item.title}\"",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    "Избранное" -> {
                                        Toast.makeText(
                                            context,
                                            "Нажата кнопка \"${item.title}\"",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    "Настройки" -> {
                                        val intent = Intent(context, SettingsActivity::class.java)
                                        context.startActivity(intent)
                                    }
                                }
                            }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(
                                        top = 18.dp,
                                        bottom = 18.dp,
                                        start = 12.dp,
                                        end = 12.dp
                                    ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = item.iconRes),
                                    contentDescription = item.title,
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.Black
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = item.title,
                                    style = TextStyle(
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = textColor,
                                        lineHeight = 22.sp
                                    ),
                                    modifier = Modifier.weight(1f)
                                )

                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow),
                                    contentDescription = "Стрелка",
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
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
        PlaylistMakerScreen()
    }
}