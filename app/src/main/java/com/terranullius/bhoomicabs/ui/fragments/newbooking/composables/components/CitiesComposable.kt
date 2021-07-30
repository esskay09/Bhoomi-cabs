package com.terranullius.bhoomicabs.ui.fragments.newbooking.composables.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.terranullius.bhoomicabs.ui.composables.theme.lightBlueHeadline


@Composable
fun CitiesComposable(startCity: String, endCity: String, modifier: Modifier = Modifier, distance: Long) {

    Column(
        modifier = modifier
    ) {
        Text(
            text = "$distance Km",
            color = lightBlueHeadline,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Row(
            Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(Color.Black, shape = CircleShape)
            )
            Row(Modifier.fillMaxWidth(0.9f)) {
                (0..280).forEach { _ ->
                    Text(text = "- ", style = MaterialTheme.typography.caption)
                }
            }
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(Color.Gray, shape = CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
            Text(text = startCity)
            Text(text = endCity)
        }

    }
}













































