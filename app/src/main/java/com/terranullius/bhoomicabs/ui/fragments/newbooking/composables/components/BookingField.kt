package com.terranullius.bhoomicabs.ui.fragments.newbooking.composables.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BookingField(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    onClick: () -> Unit = {},
) {
    Column(modifier = modifier) {
        Text(text = " $label")
        Spacer(modifier = Modifier.height(6.dp))
        Column(
            Modifier.clickable {
                onClick()
            }
        ){
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = text, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(5.dp))
            Divider(Modifier.fillMaxWidth(), thickness = 2.dp)
        }
    }
}