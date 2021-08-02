package com.terranullius.bhoomicabs.ui.fragments.paymentfinished.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.terranullius.bhoomicabs.ui.composables.theme.lightBlueHeadline
import com.terranullius.bhoomicabs.ui.fragments.newbooking.composables.components.BookingDetailCard
import com.terranullius.bhoomicabs.ui.viewmodels.NewBookingViewModel
import com.terranullius.bhoomicabs.util.NavigationEvent
import com.terranullius.bhoomicabs.R
@Composable
fun BookingFinishedScreen(modifier: Modifier = Modifier, viewModel: NewBookingViewModel) {

    LaunchedEffect(key1 = Unit) {
        viewModel.finishBooking()
    }

    LazyColumn(modifier = modifier) {
        item {
            Box(modifier = Modifier.fillMaxWidth().height(300.dp)){
                AndroidView(
                    modifier = Modifier.size(300.dp).align(Alignment.Center),
                    factory = {
                        LottieAnimationView(it).apply {
                            setAnimation(R.raw.done)
                            repeatCount = LottieDrawable.INFINITE
                            repeatMode = LottieDrawable.RESTART
                        }
                    }
                ) {
                    it.playAnimation()
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
        item {
            Text(
                text = "Booking Confirmed!", style = MaterialTheme.typography.h5.copy(
                    color = lightBlueHeadline
                )
            )
        }
        item {
            Spacer(modifier = Modifier.height(6.dp))
        }
        item {
            viewModel.currentBooking.value?.let { BookingDetailCard(booking = it) }
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
        item {
            Box(Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier.align(Alignment.Center), onClick = {
                        viewModel.navigate(NavigationEvent.BoookingFinishedToBookings)
                    }) {
                    Text(text = "My Bookings")
                }
            }
        }
    }
}