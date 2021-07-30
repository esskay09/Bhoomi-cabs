package com.terranullius.bhoomicabs.ui.fragments.bookings.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.coil.rememberCoilPainter
import com.terranullius.bhoomicabs.data.Booking
import com.terranullius.bhoomicabs.ui.composables.theme.mainPadding
import com.terranullius.bhoomicabs.ui.fragments.newbooking.composables.components.CitiesComposable

@Composable
fun BookingCard(modifier: Modifier = Modifier, booking: Booking, onClick: (Booking) -> Unit) {
    Card(
        modifier = modifier.clickable {
            onClick(booking)
        }, elevation = 16.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
       Column(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.80f, true)
                    .padding(mainPadding)
            ) {
                val painter = rememberImagePainter(data = booking.car.image)
                Image(
                    painter = painter,
                    contentDescription = "Car Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
           Spacer(modifier = Modifier.weight(0.05f, fill = true))
           CitiesComposable(startCity = "Delhi", endCity = "Mumbai", distance = 1454, modifier = Modifier.fillMaxWidth().padding(8.dp) )
        }
    }
}
