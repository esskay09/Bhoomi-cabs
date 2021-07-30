package com.terranullius.bhoomicabs.ui.fragments.newbooking.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.terranullius.bhoomicabs.data.Car
import com.terranullius.bhoomicabs.ui.composables.ErrorComposable
import com.terranullius.bhoomicabs.ui.composables.LoadingComposable
import com.terranullius.bhoomicabs.ui.composables.theme.selectCarBackgroundColor
import com.terranullius.bhoomicabs.ui.viewmodels.NewBookingViewModel
import com.terranullius.bhoomicabs.util.NavigationEvent
import com.terranullius.bhoomicabs.util.Resource

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SelectCarScreen(viewModel: NewBookingViewModel, modifier: Modifier = Modifier) {

    val carsState = viewModel.carStateFlow.collectAsState(initial = Resource.Loading)

    val currentCar: MutableState<Car?> = remember{ mutableStateOf(null) }

    val distanceState = viewModel.distanceStateFlow.collectAsState(Resource.Loading)

    Scaffold(modifier,
        bottomBar = {
            BottomAppBar() {
                Button(modifier = Modifier.fillMaxSize(), onClick = {
                    currentCar.value?.let {
                        viewModel.setCar(it)
                    }
                    viewModel.navigate(NavigationEvent.SelectCarToSelectPayment)
                }) {
                    Text(text = "BOOK")
                }
            }
        }) {

        Box(modifier = modifier.padding(it)) {
            when(distanceState.value){
                is Resource.Success -> CarScreen(carsState, currentCar, viewModel = viewModel)
                is Resource.Error -> {
                    Box(modifier = Modifier.fillMaxSize()){
                        ErrorComposable(Modifier.align(Alignment.Center))
                    }
                }
                Resource.Loading -> {
                    Box(modifier = Modifier.fillMaxSize()){
                        LoadingComposable(Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CarScreen(carsState: State<Resource<List<Car>>>, currentCar: MutableState<Car?>, viewModel: NewBookingViewModel) {
    val bgColor = selectCarBackgroundColor

    when (carsState.value) {
        is Resource.Success -> {
            val cars = remember {
                (carsState.value as Resource.Success<List<Car>>).data
            }
            val pagerState = rememberPagerState(
                pageCount = cars.size,
                infiniteLoop = true,
                initialOffscreenLimit = 2
            )

            LaunchedEffect(key1 = pagerState.currentPage) {
                currentCar.value = cars[pagerState.currentPage]
            }

            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = bgColor), state = pagerState
            ) {
                CarCard(
                    car = cars[currentPage], modifier = Modifier
                        .fillMaxHeight(0.9f)
                        .fillMaxWidth(0.9f)
                        .background(color = bgColor)
                        .padding(12.dp), viewModel = viewModel
                )
            }
        }
        is Resource.Error -> {
            Box(modifier = Modifier.fillMaxSize()){
                ErrorComposable(Modifier.align(Alignment.Center))
            }
        }
        Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize()){
                LoadingComposable(Modifier.align(Alignment.Center))
            }
        }
    }
}
