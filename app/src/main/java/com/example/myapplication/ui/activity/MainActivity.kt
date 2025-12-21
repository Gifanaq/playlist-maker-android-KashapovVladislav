package com.example.myapplication.ui.activity

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
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.PlaylistHost
import com.example.myapplication.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                PlaylistHost(navController = navController)
            }
        }
    }
}

@Composable
fun MainScreen(
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
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
            .statusBarsPadding()
    ) {
        Text(
            text = "Playlist maker",
            color = whiteColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 10.dp, start = 12.dp)
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(700.dp)
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
                                    "Поиск" -> onSearchClick()
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
                                    "Настройки" -> onSettingsClick()
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
                                        fontWeight = FontWeight.W500,
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
        MainScreen(
            onSearchClick = {},
            onSettingsClick = {}
        )
    }
}