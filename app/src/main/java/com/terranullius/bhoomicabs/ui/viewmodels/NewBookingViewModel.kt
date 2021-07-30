package com.terranullius.bhoomicabs.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.terranullius.bhoomicabs.data.Booking
import com.terranullius.bhoomicabs.data.Car
import com.terranullius.bhoomicabs.other.Constants.PH_END_CITY
import com.terranullius.bhoomicabs.other.Constants.PH_END_DATE
import com.terranullius.bhoomicabs.other.Constants.PH_PICK_UP_TIME
import com.terranullius.bhoomicabs.other.Constants.PH_START_CITY
import com.terranullius.bhoomicabs.other.Constants.PH_START_DATE
import com.terranullius.bhoomicabs.repositories.MainRepository
import com.terranullius.bhoomicabs.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewBookingViewModel @Inject constructor(val repository: MainRepository) : BaseViewModel() {

    private val _carsStateFlow: MutableStateFlow<Resource<List<Car>>> =
        MutableStateFlow(Resource.Loading)
    val carStateFlow: StateFlow<Resource<List<Car>>>
        get() = _carsStateFlow

    init {
        viewModelScope.launch {
            repository.getCarsFlow().collect {
                _carsStateFlow.value = it
            }
        }
    }

    private val _startCity: MutableStateFlow<String> = MutableStateFlow(PH_START_CITY)
    val startCity: StateFlow<String>
        get() = _startCity

    private val _endCity: MutableStateFlow<String> = MutableStateFlow(PH_END_CITY)
    val endCity: StateFlow<String>
        get() = _endCity

    private val _oneWay: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val oneWay: StateFlow<Boolean>
        get() = _oneWay

    private val _startDate: MutableStateFlow<String> = MutableStateFlow(PH_START_DATE)
    val startDate: StateFlow<String>
        get() = _startDate

    private val _endDate: MutableStateFlow<String> = MutableStateFlow(PH_END_DATE)
    val endDate: StateFlow<String>
        get() = _endDate

    private val _pickupTime: MutableStateFlow<String> = MutableStateFlow(PH_PICK_UP_TIME)
    val pickupTime: StateFlow<String>
        get() = _pickupTime

    private val _distanceStateFlow: MutableStateFlow<Resource<Long>> =
        MutableStateFlow(Resource.Loading)
    val distanceStateFlow: StateFlow<Resource<Long>>
        get() = _distanceStateFlow


    private val _totalAmountStateFlow: MutableStateFlow<Long> =
        MutableStateFlow(0L)
    val totalAmountStateFlow: StateFlow<Long>
        get() = _totalAmountStateFlow


    private val _currentBooking: MutableStateFlow<Booking?> = MutableStateFlow(null)
    val currentBooking: StateFlow<Booking?>
        get() = _currentBooking

    private val _selectedPaymentType: MutableStateFlow<PaymentType> =
        MutableStateFlow(PaymentType.OTHER)
    val selectedPaymentType: StateFlow<PaymentType>
        get() = _selectedPaymentType

    fun setStartCity(city: String) {
        _startCity.value = city
    }

    fun setEndCity(city: String) {
        _endCity.value = city
    }

    fun setStartDate(date: String) {
        _startDate.value = date
    }

    fun setEndDate(date: String) {
        _endDate.value = date
    }

    fun setPickupTime(time: String) {
        _pickupTime.value = time
    }

    fun setOneWay(oneWay: Boolean) {
        _oneWay.value = oneWay
    }

    fun getDistance() {
        //TODO INCOMPLETE
        viewModelScope.launch {
            repository.calculateDistance().collect {
                _distanceStateFlow.value = it
            }
        }
    }

    fun setTotalAmount(amount: Long) {
        _totalAmountStateFlow.value = amount
    }

    fun setPaymentType(paymentType: PaymentType) {
        _selectedPaymentType.value = paymentType
    }

    fun setCar(car: Car) {

        val distance = (_distanceStateFlow.value as Resource.Success<Long>).data
        val totalAmount = getTotalAmount(car, distance)

        setTotalAmount(totalAmount)

        setBooking(
            Booking(
                startDate = _startDate.value,
                endDate = _endDate.value,
                oneWay = _oneWay.value,
                startCity = _startCity.value,
                endCity = _endCity.value,
                distance = distance, //TODO
                pickupTime = _pickupTime.value,
                totalAmount = totalAmount,
                paidAmount = 1344L,
                car = car
            )
        )
    }

    private fun getTotalAmount(car: Car, distance: Long) = if (oneWay.value) {
        distance * car.oneWayCostPerKM.toLong()
    } else {
        distance * car.roundTripCostPerKM.toLong()
    }


    private fun setBooking(booking: Booking) {
        _currentBooking.value = booking
    }

    fun finishBooking() {
        _currentBooking.value?.let {
            addBooking(it)
        }
    }

    private val _showDialogEvent: MutableStateFlow<Event<DialogShowEvent>> =
        MutableStateFlow(Event(DialogShowEvent.None))
    val showDialogEvent: StateFlow<Event<DialogShowEvent>>
        get() = _showDialogEvent

    private val _bookingAddedEvent: MutableStateFlow<Event<Resource<Unit>>> =
        MutableStateFlow(Event(Resource.Loading))
    val bookingAddedEvent: StateFlow<Event<Resource<Unit>>>
        get() = _bookingAddedEvent

    fun showDialog(dialogShowEvent: DialogShowEvent) {
        viewModelScope.launch {
            _showDialogEvent.value = Event(dialogShowEvent)
        }
    }

    private fun addBooking(booking: Booking) {
        viewModelScope.launch {
            repository.addBooking(booking).collect {
                _bookingAddedEvent.value = it
            }
        }
    }

    @ExperimentalCoroutinesApi
    fun initiatePayment(totalAmount: Long, paymentType: PaymentType, otherAmount: Long = 0L){
        val amount = when(paymentType){
             PaymentType.QUARTER -> totalAmount / 4
                PaymentType.FULL -> totalAmount
            PaymentType.HALF -> totalAmount/2
            PaymentType.OTHER -> otherAmount
        }
        viewModelScope.launch {
            repository.initiatePayment(amount).collect {
                when(it){
                    is Resource.Loading -> repository.setPaymentStatus(PaymentStatus.Started)
                    is Resource.Error -> repository.setPaymentStatus(PaymentStatus.Failed)
                    is Resource.Success -> {
                        val orderId = it.data.orderID
                        _currentBooking.value?.let { currentBooking ->
                            repository.updateBookingOrderId(
                                currentBooking, orderId, onComplete = { isSuccess ->
                                    if (isSuccess){
                                        repository.setPaymentStatus(PaymentStatus.InitiateCheckout(amount, orderId))
                                    } else {
                                        repository.setPaymentStatus(PaymentStatus.Failed)
                                    }
                                })
                        } ?: repository.setPaymentStatus(PaymentStatus.Failed)
                    }
                }
            }
        }
    }
}