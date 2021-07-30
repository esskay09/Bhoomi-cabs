package com.terranullius.bhoomicabs.ui.fragments.newbooking.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.terranullius.bhoomicabs.other.Constants.PH_END_CITY
import com.terranullius.bhoomicabs.other.Constants.PH_END_DATE
import com.terranullius.bhoomicabs.other.Constants.PH_PICK_UP_TIME
import com.terranullius.bhoomicabs.other.Constants.PH_START_CITY
import com.terranullius.bhoomicabs.other.Constants.PH_START_DATE
import com.terranullius.bhoomicabs.ui.fragments.newbooking.composables.components.BookingButton
import com.terranullius.bhoomicabs.ui.fragments.newbooking.composables.components.BookingField
import com.terranullius.bhoomicabs.ui.viewmodels.NewBookingViewModel
import com.terranullius.bhoomicabs.util.DialogShowEvent
import com.terranullius.bhoomicabs.util.NavigationEvent

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NewBookingScreen(modifier: Modifier = Modifier, viewModel: NewBookingViewModel) {

    val oneWay = viewModel.oneWay.collectAsState(true)

    val startCity = viewModel.startCity.collectAsState(PH_START_CITY)
    val endCity = viewModel.endCity.collectAsState(PH_END_CITY)
    val pickTime = viewModel.pickupTime.collectAsState(PH_PICK_UP_TIME)

    val startDate = viewModel.startDate.collectAsState(PH_START_DATE)
    val endDate = viewModel.endDate.collectAsState(PH_END_DATE)


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomAppBar() {
                val context = LocalContext.current
                Button(
                    onClick = {
                        onGetCarsClicked(context, viewModel)
                    },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = " Get Cars ")
                }
            }
        }
    ) {
        Box(
            modifier = modifier.padding(40.dp)
        ) {
            Column(Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(50.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(Color(0xFFE1E2DF), shape = RoundedCornerShape(35.dp))
                ) {
                    BookingButton(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.5f),
                        selected = !oneWay.value,
                        onClick = {
                            viewModel.setOneWay(false)
                        }) {
                        Text(text = "Round Trip")
                    }
                    BookingButton(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.5f),
                        selected = oneWay.value,
                        onClick = {
                            viewModel.setOneWay(true)
                        }) {
                        Text(text = "One Way Trip")
                    }
                }
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {
                    Spacer(modifier = Modifier.height(35.dp))
                    Row(Modifier.fillMaxWidth()) {
                        BookingField(
                            modifier = Modifier.weight(0.46f, fill = true),
                            text = startDate.value,
                            label = "Pickup Date"
                        ) {
                            viewModel.showDialog(DialogShowEvent.ShowDatePicker)
                        }
                        Spacer(modifier = Modifier.weight(0.08f, fill = true))
                        BookingField(
                            modifier = Modifier.weight(0.46f, fill = true),
                            text = pickTime.value,
                            label = "Time"
                        ) {
                            viewModel.showDialog(DialogShowEvent.ShowTimePicker)
                        }
                    }
                    AnimatedVisibility(visible = !oneWay.value) {
                        Column {
                            Spacer(modifier = Modifier.height(35.dp))
                            BookingField(
                                label = "Return Date",
                                text = endDate.value
                            ) {
                                viewModel.showDialog(DialogShowEvent.ShowDatePicker)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    Row() {
                        Column(Modifier.height(175.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .background(Color.Black, shape = CircleShape)
                            )
                            Column(
                                Modifier.fillMaxHeight(0.9f)
                            ) {
                                (0..80).forEach { _ ->
                                    Text(text = " |")
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .background(Color.Gray, shape = CircleShape)
                            )
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        Column() {
                            BookingField(text = startCity.value, label = "From")
                            Spacer(modifier = Modifier.height(40.dp))
                            BookingField(text = endCity.value, label = "To")
                        }
                    }
                }
            }
        }
    }
}

fun onGetCarsClicked(context: Context, viewModel: NewBookingViewModel) {
    if (areAllFieldsValid(viewModel)) {
        viewModel.getDistance()
        viewModel.navigate(NavigationEvent.NewBookingToSelectCar)
    } else {
        Toast.makeText(context, "Please enter all info", Toast.LENGTH_SHORT).show()
    }
}

fun areAllFieldsValid(viewModel: NewBookingViewModel): Boolean {
    //TODO ADD CITIES CHECK
    if (viewModel.oneWay.value){
        if (viewModel.startDate.value == PH_START_DATE) return false
    } else {
        if (viewModel.startDate.value == PH_START_DATE || viewModel.endDate.value == PH_END_DATE) return false
    }
    return true
}
