package com.example.myapplication.ui.activity

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.CustomSwitch


@Composable
fun SettingsScreen(
    onBackClick: () -> Unit = {}
) {
    val context = LocalContext.current
    var isDarkTheme by remember { mutableStateOf(false) }

    val shareText = stringResource(R.string.share_text)
    val supportEmail = stringResource(R.string.support_email)
    val supportSubject = stringResource(R.string.support_subject)
    val supportBody = stringResource(R.string.support_body)
    val termsUrl = stringResource(R.string.terms_url)

    val settingsItems = listOf(
        SettingsItem(
            title = stringResource(R.string.dark_theme),
            iconRes = null,
            isSwitch = true,
            switchState = isDarkTheme,
            onSwitchChange = { isDarkTheme = it },
            onClick = {}
        ),
        SettingsItem(
            title = stringResource(R.string.share_app),
            iconRes = R.drawable.ic_share,
            isSwitch = false,
            switchState = false,
            onSwitchChange = {},
            onClick = {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, shareText)
                    putExtra(Intent.EXTRA_SUBJECT, "Playlist Maker")
                }
                context.startActivity(
                    Intent.createChooser(
                        shareIntent,
                        "Поделиться приложением"
                    )
                )
            }
        ),
        SettingsItem(
            title = stringResource(R.string.support),
            iconRes = R.drawable.ic_support,
            isSwitch = false,
            switchState = false,
            onSwitchChange = {},
            onClick = {
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(supportEmail))
                    putExtra(Intent.EXTRA_SUBJECT, supportSubject)
                    putExtra(Intent.EXTRA_TEXT, supportBody)
                }

                try {
                    context.startActivity(
                        Intent.createChooser(
                            emailIntent,
                            "Написать в поддержку"
                        )
                    )
                } catch (_: Exception) {
                    val fallbackIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "message/rfc822"
                        putExtra(Intent.EXTRA_EMAIL, arrayOf(supportEmail))
                        putExtra(Intent.EXTRA_SUBJECT, supportSubject)
                        putExtra(Intent.EXTRA_TEXT, supportBody)
                    }
                    context.startActivity(
                        Intent.createChooser(
                            fallbackIntent,
                            "Написать в поддержку"
                        )
                    )
                }
            }
        ),
        SettingsItem(
            title = stringResource(R.string.terms_of_use),
            iconRes = R.drawable.ic_arrow,
            isSwitch = false,
            switchState = false,
            onSwitchChange = {},
            onClick = {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(termsUrl))
                context.startActivity(browserIntent)
            }
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back_arrow),
                        contentDescription = "Назад",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = stringResource(R.string.settings),
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(settingsItems) { item ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(61.dp),
                        color = Color.White,
                        onClick = {
                            if (!item.isSwitch) {
                                item.onClick()
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
                            Text(
                                text = item.title,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W400,
                                    color = Color.Black,
                                    lineHeight = 16.sp
                                ),
                                modifier = Modifier.weight(1f)
                            )

                            if (item.isSwitch) {
                                CustomSwitch(
                                    checked = item.switchState,
                                    onCheckedChange = item.onSwitchChange
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = item.iconRes!!),
                                    contentDescription = item.title,
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

data class SettingsItem(
    val title: String,
    val iconRes: Int?,
    val isSwitch: Boolean,
    val switchState: Boolean,
    val onSwitchChange: (Boolean) -> Unit,
    val onClick: () -> Unit
)

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun PreviewSettingsScreen() {
    MaterialTheme {
        SettingsScreen(
            onBackClick = {}
        )
    }
}