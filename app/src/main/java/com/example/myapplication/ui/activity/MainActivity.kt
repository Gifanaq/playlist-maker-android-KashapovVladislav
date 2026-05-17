package com.example.myapplication.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.navigation.PlaylistHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Настройка Edge-to-Edge (отображение за системными панелями)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                // Запускаем навигацию, которая сама подтянет нужный MainScreen
                PlaylistHost(navController = navController)
            }
        }
    }
}
