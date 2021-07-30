package com.terranullius.bhoomicabs.ui.fragments.newbooking.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.terranullius.bhoomicabs.ui.fragments.newbooking.composables.components.BookingDetailCard
import com.terranullius.bhoomicabs.ui.composables.theme.lightBlueHeadline
import com.terranullius.bhoomicabs.ui.viewmodels.NewBookingViewModel
import com.terranullius.bhoomicabs.util.NavigationEvent
import com.terranullius.bhoomicabs.util.PaymentType
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun SelectPaymentScreen(modifier: Modifier = Modifier, viewModel: NewBookingViewModel) {

    val currentBooking = viewModel.currentBooking.collectAsState()

    val totalAmount = viewModel.totalAmountStateFlow.collectAsState()

    Scaffold(
        bottomBar = {
            BottomAppBar() {
                Button(
                    modifier = Modifier.fillMaxSize(), onClick = {
                        viewModel.initiatePayment(totalAmount.value, viewModel.selectedPaymentType.value)
                    }) {
                    Text(text = "PAY")
                }
            }
        }
    ) {
        Column(modifier = modifier.padding(8.dp)) {

            currentBooking.value?.let { booking ->
                Spacer(modifier = Modifier.height(16.dp))
                BookingDetailCard(booking = booking, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))
                PaymentSelectBox(viewModel = viewModel, modifier = Modifier.fillMaxWidth(), totalAmount = totalAmount.value)
            }
        }
    }
}


@Composable
fun PaymentItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    text: String,
    onCLick: () -> Unit
) {
    Row(modifier = modifier.clickable {
        onCLick()
    }) {
        RadioButton(selected = isSelected, onClick = onCLick)
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = text)
    }
}

@Composable
fun PaymentSelectBox(modifier: Modifier = Modifier, viewModel: NewBookingViewModel, totalAmount: Long) {

    var selectedPosition by remember {
        mutableStateOf(0)
    }

    Card(
        modifier = modifier
            .fillMaxWidth(), elevation = 5.dp, shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(text = "Payment Details ", style = MaterialTheme.typography.h6.copy(
                color = lightBlueHeadline
            ))

            Spacer(modifier = Modifier.height(16.dp))
            PaymentType.values().forEach {
                val paymentDescription = when (it) {
                    PaymentType.FULL -> "Pay Full Amount ₹${totalAmount} and the rest later"
                    PaymentType.HALF -> "Pay ₹${totalAmount.div(2)} and the rest later"
                    PaymentType.QUARTER -> "Pay ₹${totalAmount.div(4)} and the rest later"
                    PaymentType.OTHER -> "Pay Other Amount"
                }
                Spacer(modifier = Modifier.height(16.dp))
                PaymentItem(
                    isSelected = it.ordinal == selectedPosition,
                    text = paymentDescription
                ) {
                    selectedPosition = it.ordinal
                    viewModel.setPaymentType(it)
                }
            }
        }
    }
}

