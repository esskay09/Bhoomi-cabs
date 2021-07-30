package com.terranullius.bhoomicabs.ui.fragments.newbooking.composables.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun FareDetailIItem(modifier: Modifier = Modifier, icon: ImageVector, detail: String) {
    Row(modifier = modifier.padding(4.dp)) {
        Icon(imageVector = icon, contentDescription = "", tint = MaterialTheme.colors.primary.copy(alpha = 0.7f),
        modifier = Modifier
            .size(16.dp)
            .clip(CircleShape)
            .border(width = 1.dp, color = MaterialTheme.colors.primary.copy(alpha = 0.7f)))

        Text(text = "  $detail", style = MaterialTheme.typography.subtitle1)
    }
}