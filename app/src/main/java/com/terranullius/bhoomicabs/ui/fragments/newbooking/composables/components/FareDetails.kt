package com.terranullius.bhoomicabs.ui.fragments.newbooking.composables.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.terranullius.bhoomicabs.ui.composables.theme.grayButtonColor
import com.terranullius.bhoomicabs.ui.composables.theme.lightBlueHeadline

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FareDetails(modifier: Modifier = Modifier, distance: Long, chargeAfterDistance: Float) {

    var isInclusionsClicked by remember {
        mutableStateOf(true)
    }

    var isShown by remember {
        mutableStateOf(true)
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp), elevation = 3.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            TextButton(
                onClick = { isShown = !isShown }, colors = ButtonDefaults.textButtonColors(
                    contentColor = lightBlueHeadline
                )
            ) {
                Text(text = "Fare Details ", style = MaterialTheme.typography.h6)
                Icon(
                    imageVector = if (isShown) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                    tint = lightBlueHeadline,
                    contentDescription = "arrow"
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            AnimatedVisibility(visible = isShown) {
                Card(shape = RoundedCornerShape(4.dp), elevation = 1.dp) {
                    Column(Modifier.fillMaxWidth()) {
                        Row(Modifier.fillMaxWidth()) {
                            BookingButton(modifier = Modifier
                                .weight(0.5f, fill = true),
                                selected = isInclusionsClicked,
                                shape = RoundedCornerShape(0.dp),
                                enabledColor = grayButtonColor,
                                disabledColor = grayButtonColor.copy(alpha = 0.2f),
                                onClick = { isInclusionsClicked = true }) {
                                Text(text = "Inclusions")
                            }
                            BookingButton(modifier = Modifier
                                .weight(0.5f, fill = true),
                                selected = !isInclusionsClicked,
                                shape = RoundedCornerShape(0.dp),
                                enabledColor = grayButtonColor,
                                disabledColor = grayButtonColor.copy(alpha = 0.2f),
                                onClick = { isInclusionsClicked = false }) {
                                Text(text = "Exclusions")
                            }

                        }
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {

                            if (isInclusionsClicked) Inclusions() else Exclusions(distance, chargeAfterDistance)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Inclusions() {
    FareDetailIItem(icon = Icons.Default.Money, detail = "Fuel Charges")
    FareDetailIItem(icon = Icons.Default.FoodBank, detail = "Driver Allowance")
    FareDetailIItem(icon = Icons.Default.Toll, detail = "Toll/State Tax")
    FareDetailIItem(icon = Icons.Default.Pages, detail = "GST (5%)")
}

@Composable
fun Exclusions(distance: Long, chargeAfterDistance: Float) {
    FareDetailIItem(icon = Icons.Default.Album, detail = "Pay ₹$chargeAfterDistance/km after ${distance}km")
    FareDetailIItem(icon = Icons.Default.LocalParking, detail = "Parking")
}