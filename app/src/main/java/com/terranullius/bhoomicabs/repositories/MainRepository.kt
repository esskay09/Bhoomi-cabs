package com.terranullius.bhoomicabs.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.terranullius.bhoomicabs.data.Booking
import com.terranullius.bhoomicabs.data.Car
import com.terranullius.bhoomicabs.data.*
import com.terranullius.bhoomicabs.network.ApiEmptyResponse
import com.terranullius.bhoomicabs.network.ApiErrorResponse
import com.terranullius.bhoomicabs.network.ApiSuccessResponse
import com.terranullius.bhoomicabs.network.PickCabApi
import com.terranullius.bhoomicabs.other.Constants
import com.terranullius.bhoomicabs.ui.viewmodels.BaseViewModel
import com.terranullius.bhoomicabs.util.Event
import com.terranullius.bhoomicabs.util.PaymentStatus
import com.terranullius.bhoomicabs.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await

class MainRepository {

    private val _phoneNumberStateFlow = MutableStateFlow(0L)
    val phoneNumberStateFlow: StateFlow<Long>
        get() = _phoneNumberStateFlow

    private val _paymentStatusStatusFLow = MutableStateFlow<PaymentStatus>(PaymentStatus.None)
    val paymentStatusStatusFLow: StateFlow<PaymentStatus>
    get() = _paymentStatusStatusFLow


    private var bookingList: List<Booking> = emptyList()

    fun setNumber(number: Long) {
        _phoneNumberStateFlow.value = number
    }

    fun getCarsFlow() = flow {
        emit(Resource.Loading)
        val cars =
            FirebaseFirestore.getInstance().collection(Constants.FIRESTORE_CARS_COLLECTION)
                .get()
                .await()
                .toObjects(CarDto::class.java).map {
                    it.toCar()
                }
        emit(Resource.Success(cars))
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    fun getBookingsFlow(cars: List<Car>) = callbackFlow {
        trySend(Resource.Loading)
        val fireStoreListener =
            FirebaseFirestore.getInstance().collection(Constants.FIRESTORE_COLLECTION_BOOKINGS)
                .whereEqualTo(
                    Constants.FS_FIELD_USERID,
                    _phoneNumberStateFlow.value.toString()
                ).addSnapshotListener { value, error ->
                    if (error != null) {
                        trySend(Resource.Error(error))
                    } else {
                        value?.let { queryShot ->
                            bookingList = queryShot.documents.mapNotNull { document ->
                                document.toBookingDto()?.toBooking(cars)
                            }
                            trySend(Resource.Success(bookingList))
                        }
                    }
                }
        awaitClose {
            fireStoreListener.remove()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun updateBooking(booking: Booking): Flow<Resource<Unit>> {
        val bookingDto = booking.toBookingDto(phoneNumberStateFlow.value.toString())
        return callbackFlow {
            trySend(Resource.Loading)
            FirebaseFirestore.getInstance().collection(Constants.FIRESTORE_COLLECTION_BOOKINGS)
                .document(booking.id).set(bookingDto).addOnSuccessListener {
                    trySend(Resource.Success(Unit))
                }.addOnFailureListener {
                    trySend(Resource.Error(it))
                }
            awaitClose {

            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun addBooking(booking: Booking) = callbackFlow<Event<Resource<Unit>>> {
        trySend(Event(Resource.Loading))
        val bookingDto = booking.toBookingDto(BaseViewModel.phoneNumberStateFlow.value.toString())
        FirebaseFirestore.getInstance().collection(Constants.FIRESTORE_COLLECTION_BOOKINGS)
            .add(bookingDto)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(Event(Resource.Success(Unit)))
                } else {
                    trySend(Event(Resource.Error(NullPointerException())))
                }
            }
        awaitClose {

        }
    }

    fun setPaymentStatus(paymentStatus: PaymentStatus){
        _paymentStatusStatusFLow.value = paymentStatus
    }

    @ExperimentalCoroutinesApi
    fun initiatePayment(amount: Long): Flow<Resource<GenerateOrderResponse>> {
        return callbackFlow<Resource<GenerateOrderResponse>> {
            trySend(Resource.Loading)
            PickCabApi.retrofitService.generateOrder(amount).observeForever{
                when(it){
                    is ApiSuccessResponse -> {
                        if (it.body.result == Constants.SERVER_RES_ORDER_ERROR){
                            trySend(Resource.Error(NullPointerException()))
                        } else {
                            trySend(Resource.Success(it.body))
                        }
                    }
                    is ApiEmptyResponse -> {
                        trySend(Resource.Error(NullPointerException()))
                    }
                    is ApiErrorResponse -> {
                        trySend(Resource.Error(NullPointerException()))
                    }
                }
            }
            awaitClose{
            }
        }
    }

    fun updateBookingOrderId(booking: Booking, orderId: String, onComplete: (Boolean) -> Unit){
        FirebaseFirestore.getInstance().collection(Constants.FIRESTORE_COLLECTION_BOOKINGS).document(booking.id).update(
            Constants.FS_FIELD_ORDER_ID, orderId
        ).addOnCompleteListener {
            onComplete(it.isSuccessful)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun calculateDistance() = flow<Resource<Long>> {
        //TODO INCOMPLETE

        emit(Resource.Loading)

        //SIMULATE DELAY
        delay(3000L)

        emit(Resource.Success(1454L))
    }
}