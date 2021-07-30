package com.terranullius.bhoomicabs.ui.fragments.bookings.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.terranullius.bhoomicabs.ui.composables.EmptyComposable
import com.terranullius.bhoomicabs.ui.composables.ErrorComposable
import com.terranullius.bhoomicabs.ui.composables.LoadingComposable
import com.terranullius.bhoomicabs.ui.viewmodels.BookingsViewModel
import com.terranullius.bhoomicabs.util.NavigationEvent
import com.terranullius.bhoomicabs.util.Resource

@Composable
fun BookingsScreen(
    modifier: Modifier = Modifier,
    viewModel: BookingsViewModel
) {
    val bookingsState = viewModel.bookingsStateFlow.collectAsState(Resource.Loading)

    Scaffold(modifier, topBar = {
        TopAppBar(
            title = {
                Text(text = "My bookings")
            }
        )
    }, floatingActionButton = {
        ExtendedFloatingActionButton(
            onClick = {
                viewModel.navigate(NavigationEvent.BookingsToNewBooking)
            },
            icon = {Icon(Icons.Default.Add, "")},
            text = { Text(text = "NEW BOOKING") })
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = 12.dp,
                    end = 12.dp
                )
        ) {
            when (bookingsState.value) {
                is Resource.Success -> LazyColumn(Modifier.fillMaxSize()) {
                    val bookings = (bookingsState.value as Resource.Success).data
                    if (bookings.isEmpty()) {
                        item {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Column(Modifier.align(Alignment.Center)) {
                                    val isEmptyTextShown = remember {
                                        mutableStateOf(false)
                                    }
                                    val emptyTextAlpha = animateFloatAsState(targetValue = if (isEmptyTextShown.value) 1.0f else 0f)
                                    EmptyComposable(modifier = Modifier.size(400.dp),onEnd = {
                                        isEmptyTextShown.value = true
                                    })
                                    Text(text = "No Bookings yet",
                                        style = MaterialTheme.typography.subtitle1.copy(
                                            fontWeight = FontWeight.SemiBold
                                        ),
                                        modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .graphicsLayer {
                                            alpha = emptyTextAlpha.value
                                        })
                                }  
                            }
                        }
                    } else {
                        items(bookings) {
                            Spacer(modifier = Modifier.height(10.dp))
                            BookingCard(booking = it, modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp), onClick = { booking ->
                                viewModel.setSelectedBooking(booking)
                                viewModel.navigate(NavigationEvent.BookingsToBookingDetail)
                            })
                        }
                    }
                }
                is Resource.Error -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        ErrorComposable(Modifier.align(Alignment.Center))
                    }
                }
                Resource.Loading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        LoadingComposable(Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}