package com.example.myapplication.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val thumbSize = 18.dp
    val trackWidth = 32.dp
    val trackHeight = 12.dp

    val uncheckedTrackColor = Color(0xFFE6E8EB)
    val uncheckedThumbColor = Color(0xFFAEAFB4)

    val checkedTrackColor = Color(0xFF3057A8)
    val checkedThumbColor = Color(0xFF3772E7)

    val thumbOffset by animateDpAsState(
        targetValue = if (checked) trackWidth - thumbSize else 0.dp,
        animationSpec = tween(durationMillis = 200)
    )

    Box(
        modifier = modifier
            .width(trackWidth)
            .height(thumbSize)
            .clickable { onCheckedChange(!checked) }
    ) {
        Box(
            modifier = Modifier
                .width(trackWidth)
                .height(trackHeight)
                .background(
                    color = if (checked) checkedTrackColor else uncheckedTrackColor,
                    shape = RoundedCornerShape(trackHeight / 2)
                )
                .align(Alignment.Center)
        )
        Box(
            modifier = Modifier
                .offset(x = thumbOffset)
                .size(thumbSize)
                .background(
                    color = if (checked) checkedThumbColor else uncheckedThumbColor,
                    shape = CircleShape
                )
                .align(Alignment.CenterStart)
        )
    }
}
