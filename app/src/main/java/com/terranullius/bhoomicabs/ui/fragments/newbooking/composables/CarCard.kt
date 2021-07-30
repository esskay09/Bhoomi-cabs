package com.terranullius.bhoomicabs.ui.fragments.newbooking.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.terranullius.bhoomicabs.data.Car
import com.terranullius.bhoomicabs.ui.composables.theme.mainPadding
import com.terranullius.bhoomicabs.ui.fragments.newbooking.composables.components.FareDetails
import com.terranullius.bhoomicabs.ui.fragments.newbooking.composables.components.PriceView
import com.terranullius.bhoomicabs.ui.viewmodels.NewBookingViewModel
import com.terranullius.bhoomicabs.util.Resource

@Composable
fun CarCard(modifier: Modifier = Modifier, car: Car, viewModel: NewBookingViewModel) {
    val imagePainter = rememberCoilPainter(request = car.image)
    Column(modifier = modifier) {
        Card(
            modifier = Modifier
                .fillMaxSize(), shape = RoundedCornerShape(32.dp), elevation = 8.dp
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(mainPadding)) {
                Image(
                    painter = imagePainter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.38f),
                    contentDescription = "Car Image",
                    contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.height(12.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    item {
                        PriceView(viewModel = viewModel, car = car)
                    }
                    item { Spacer(modifier = Modifier.height(18.dp)) }
                    item {
                        val distance = (viewModel.distanceStateFlow.value as Resource.Success).data
                        val oneWay = viewModel.oneWay.value
                        val chargesAfterDistance =
                            if (oneWay) car.oneWayCostPerKM else car.roundTripCostPerKM
                        FareDetails(
                            Modifier.fillMaxWidth(),
                            distance = distance,
                            chargeAfterDistance = chargesAfterDistance
                        )
                    }
                }
            }
        }
    }
}
