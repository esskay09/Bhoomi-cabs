package com.terranullius.bhoomicabs.ui.fragments.bookings.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.terranullius.bhoomicabs.data.Booking
import com.terranullius.bhoomicabs.ui.fragments.newbooking.composables.components.BookingDetailCard
import com.terranullius.bhoomicabs.ui.fragments.newbooking.composables.components.FareDetails

@Composable
fun BookingDetailScreen(modifier: Modifier = Modifier, booking: Booking) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomAppBar() {
                Button(modifier = Modifier
                    .fillMaxSize(), onClick = { /*TODO*/ }) {
                    Text(text = "PAY")
                }
            }
        }) {
        Box(modifier = modifier.padding(it)) {
            LazyColumn(Modifier.fillMaxSize()) {
                item {
                    val carImage = rememberCoilPainter(request = booking.car.image)
                    Image(
                        painter = carImage, contentDescription = "Car Image",
                        Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                }
                item { Spacer(modifier = Modifier.height(15.dp)) }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        BookingDetailCard(booking = booking, modifier = Modifier.fillMaxWidth())
                    }
                }
                item { Spacer(modifier = Modifier.height(10.dp)) }
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp)
                    ) {
                        val chargesAfterDistance =
                            if (booking.oneWay) booking.car.oneWayCostPerKM else booking.car.roundTripCostPerKM
                        FareDetails(
                            distance = booking.distance,
                            chargeAfterDistance = chargesAfterDistance
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }

}