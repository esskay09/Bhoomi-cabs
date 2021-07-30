package com.terranullius.bhoomicabs.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.terranullius.bhoomicabs.data.Booking
import com.terranullius.bhoomicabs.repositories.MainRepository
import com.terranullius.bhoomicabs.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingsViewModel @Inject constructor(repository: MainRepository): BaseViewModel() {

    private val _bookingsStateFlow: MutableStateFlow<Resource<List<Booking>>> =
        MutableStateFlow(Resource.Loading)
    val bookingsStateFlow: StateFlow<Resource<List<Booking>>>
        get() = _bookingsStateFlow

    private val _selectedBookingStateFlow: MutableStateFlow<Booking?> =
        MutableStateFlow(null)
    val selectedBookingStateFlow: StateFlow<Booking?>
        get() = _selectedBookingStateFlow

    init {
        viewModelScope.launch {
            repository.getCarsFlow().collect { resource ->
                when(resource){
                    is Resource.Success -> {
                        val cars = resource.data
                        repository.getBookingsFlow(cars).collect { resourceBookings ->
                            _bookingsStateFlow.value = resourceBookings
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    fun setSelectedBooking(booking: Booking) {
        _selectedBookingStateFlow.value = booking
    }

}