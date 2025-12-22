package com.example.myapplication.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.common.SettingsScreenResources
import com.example.myapplication.ui.components.CustomSwitch
import com.example.myapplication.ui.common.rememberSettingsScreenResources


@Composable
fun SettingsScreen(
    onBackClick: () -> Unit = {}
) {
    val context = LocalContext.current
    var isDarkThemeEnabled by remember { mutableStateOf(false) }

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
                    contentDescription = resources.backString,
                    tint = resources.black,
                    modifier = Modifier.size(resources.iconSizeMedium)
                )
            }

            Spacer(modifier = Modifier.weight(0.02f))

            Text(
                text = resources.settingsTitle,
                color = resources.black,
                fontSize = resources.searchTitleFontSize,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.weight(0.04f))

        Box(
            modifier = Modifier.weight(1f)
        ) {
            SettingsList(
                resources = resources,
                isDarkThemeEnabled = isDarkThemeEnabled,
                onDarkThemeChanged = { isDarkThemeEnabled = it },
                context = context
            )
        }
    }
}

@Composable
private fun SettingsList(
    resources: SettingsScreenResources,
    isDarkThemeEnabled: Boolean,
    onDarkThemeChanged: (Boolean) -> Unit,
    context: android.content.Context
) {
    val settingsItems = listOf(
        SettingsItem.ThemeItem(
            title = resources.darkThemeString,
            isEnabled = isDarkThemeEnabled,
            onToggle = onDarkThemeChanged
        ),
        SettingsItem.ActionItem(
            title = resources.shareAppString,
            iconRes = R.drawable.ic_share,
            onClick = {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, resources.shareText)
                    putExtra(Intent.EXTRA_SUBJECT, resources.appName)
                }
                context.startActivity(
                    Intent.createChooser(
                        shareIntent,
                        resources.shareAppString
                    )
                )
            }
        ),
        SettingsItem.ActionItem(
            title = resources.supportString,
            iconRes = R.drawable.ic_support,
            onClick = {
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(resources.supportEmail))
                    putExtra(Intent.EXTRA_SUBJECT, resources.supportSubject)
                    putExtra(Intent.EXTRA_TEXT, resources.supportBody)
                }

                try {
                    context.startActivity(
                        Intent.createChooser(
                            emailIntent,
                            resources.contactSupport
                        )
                    )
                } catch (_: Exception) {
                    val fallbackIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "message/rfc822"
                        putExtra(Intent.EXTRA_EMAIL, arrayOf(resources.supportEmail))
                        putExtra(Intent.EXTRA_SUBJECT, resources.supportSubject)
                        putExtra(Intent.EXTRA_TEXT, resources.supportBody)
                    }
                    context.startActivity(
                        Intent.createChooser(
                            fallbackIntent,
                            resources.contactSupport
                        )
                    )
                }
            }
        ),
        SettingsItem.ActionItem(
            title = resources.termsString,
            iconRes = R.drawable.ic_arrow,
            onClick = {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(resources.termsUrl))
                context.startActivity(browserIntent)
            }
        )
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = resources.bottomSheetPaddingHorizontal,
            end = resources.bottomSheetPaddingHorizontal,
            top = 8.dp,
            bottom = 16.dp
        )
    ) {
        items(settingsItems) { item ->
            when (item) {
                is SettingsItem.ThemeItem -> ThemeSettingItem(
                    item = item,
                    resources = resources
                )
                is SettingsItem.ActionItem -> ActionSettingItem(
                    item = item,
                    resources = resources
                )
            }
        }
    }
}

@Composable
private fun ThemeSettingItem(
    item: SettingsItem.ThemeItem,
    resources: SettingsScreenResources
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(resources.settingsItemHeight),
        color = resources.white,
        onClick = { item.onToggle(!item.isEnabled) }
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
            Text(
                text = item.title,
                style = TextStyle(
                    fontSize = resources.settingsTextFontSize,
                    fontWeight = FontWeight.Normal,
                    color = resources.black,
                    lineHeight = resources.settingsTextLineHeight
                ),
                modifier = Modifier.weight(1f)
            )
            CustomSwitch(
                checked = item.isEnabled,
                onCheckedChange = item.onToggle
            )
        }
    }
}

@Composable
private fun ActionSettingItem(
    item: SettingsItem.ActionItem,
    resources: SettingsScreenResources
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(resources.settingsItemHeight),
        color = resources.white,
        onClick = item.onClick
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
            Text(
                text = item.title,
                style = TextStyle(
                    fontSize = resources.settingsTextFontSize,
                    fontWeight = FontWeight.Normal,
                    color = resources.black,
                    lineHeight = resources.settingsTextLineHeight
                ),
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = item.iconRes),
                contentDescription = item.title,
                modifier = Modifier.size(resources.iconSizeMedium),
                tint = resources.gray
            )
        }
    }
}

sealed class SettingsItem {
    data class ThemeItem(
        val title: String,
        val isEnabled: Boolean,
        val onToggle: (Boolean) -> Unit
    ) : SettingsItem()

    data class ActionItem(
        val title: String,
        val iconRes: Int,
        val onClick: () -> Unit
    ) : SettingsItem()
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun PreviewSettingsScreen() {
    MaterialTheme {
        SettingsScreen(
            onBackClick = {}
        )
    }
}