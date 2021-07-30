package com.terranullius.bhoomicabs.ui.fragments.newbooking.composables.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.terranullius.bhoomicabs.data.Booking
import com.terranullius.bhoomicabs.ui.composables.theme.lightBlueHeadline

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BookingDetailCard(modifier: Modifier = Modifier, booking: Booking) {
    Card(modifier = modifier, elevation = 3.dp, shape = RoundedCornerShape(8.dp)) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {

            var isShown by remember {
                mutableStateOf(true)
            }

            TextButton(
                onClick = { isShown = !isShown }, colors = ButtonDefaults.textButtonColors(
                    contentColor = lightBlueHeadline
                )
            ) {
                Text(text = "Booking Details ", style = MaterialTheme.typography.h6)
                Icon(
                    imageVector = if (isShown) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                    tint = lightBlueHeadline,
                    contentDescription = "arrow"
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            AnimatedVisibility(visible = isShown) {
                Card(shape = RoundedCornerShape(4.dp), elevation = 1.dp) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        CitiesComposable(startCity = "Delhi", endCity = "Mumbai", distance = 1214L)

                        BookingDetailItem(
                            type = "Trip Type",
                            value = if (booking.oneWay) "One Way" else "Round Trip"
                        )
                        BookingDetailItem(
                            type = "Pick up Date",
                            value = booking.startDate
                        )
                        BookingDetailItem(
                            type = "Pick up Time",
                            value = booking.pickupTime
                        )
                        if (!booking.oneWay) BookingDetailItem(
                            type = "Return Date",
                            value = booking.endDate
                        )
                        BookingDetailItem(type = "Car Type", value = booking.car.id)
                        BookingDetailItem(type = "Total Fare", value = "₹${booking.totalAmount}")
                    }
                }
            }
        }
    }
}

@Composable
fun BookingDetailItem(modifier: Modifier = Modifier.fillMaxWidth(), type: String, value: String) {
    Spacer(modifier = Modifier.height(12.dp))
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = type)
        Text(text = value)
    }
}