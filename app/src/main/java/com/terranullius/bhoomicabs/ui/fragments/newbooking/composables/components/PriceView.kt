package com.terranullius.bhoomicabs.ui.fragments.newbooking.composables.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.terranullius.bhoomicabs.data.Car
import com.terranullius.bhoomicabs.ui.viewmodels.NewBookingViewModel
import com.terranullius.bhoomicabs.util.Resource


@Composable
fun PriceView(
    modifier: Modifier = Modifier,
    car: Car,
    viewModel: NewBookingViewModel
) {
    val distance = (viewModel.distanceStateFlow.value as Resource.Success<Long>).data
    val originalPrice = distance * if (viewModel.oneWay.value) car.oneWayCostPerKM.toLong() else car.roundTripCostPerKM.toLong()
    val discountedPrice = (1.33 * originalPrice).toLong()
    Box(modifier = modifier.fillMaxWidth()) {
        Column() {
            Text(
                text = "₹$originalPrice", style = MaterialTheme.typography.h3.copy(
                    MaterialTheme.colors.primary
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row() {
                Text(
                    text = "₹ $discountedPrice", style = MaterialTheme.typography.h6.copy(
                        color = Color.DarkGray, textDecoration = TextDecoration.LineThrough
                    )
                )
                Text(text = "  25% off", color = Color.Green)
            }
        }
//        Text(text = "upto ${distance}km", Modifier.align(Alignment.BottomEnd))
    }
}