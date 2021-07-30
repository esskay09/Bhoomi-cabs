package com.terranullius.bhoomicabs.ui.fragments.newbooking.composables.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BookingButton(
    modifier: Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    shape: RoundedCornerShape = RoundedCornerShape(35.dp),
    enabledColor: Color = MaterialTheme.colors.primary,
    disabledColor: Color = Color(0xFFE1E2DF),
    content: @Composable () -> Unit
) {
    Button(
        modifier = modifier,
        shape = shape,
        elevation = ButtonDefaults.elevation(
            0.dp,
            0.dp,
            0.dp
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (selected) enabledColor else disabledColor,
            contentColor = if (selected) Color.White else Color.Black
        ),
        onClick = onClick
    ) {
        content()
    }
}