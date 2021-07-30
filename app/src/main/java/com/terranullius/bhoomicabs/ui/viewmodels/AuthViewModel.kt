package com.terranullius.bhoomicabs.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.terranullius.bhoomicabs.network.*
import com.terranullius.bhoomicabs.repositories.MainRepository
import com.terranullius.bhoomicabs.util.Event
import com.terranullius.bhoomicabs.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(val repository: MainRepository) : BaseViewModel() {

    private val _phoneNumberSetEvent = MutableLiveData<Event<Resource<Long>>>()

    private val _verificationStartedEvent = MutableLiveData<Event<Unit>>()
    val verificationStartedEvent: LiveData<Event<Unit>>
        get() = _verificationStartedEvent

    private val _otpSetEvent = MutableLiveData<Event<Resource<Long>>>()
    val otpSetEvent: LiveData<Event<Resource<Long>>>
        get() = _otpSetEvent

    private val _showNumberChooser = MutableLiveData<Event<Unit>>()
    val showNumberChooser: LiveData<Event<Unit>>
        get() = _showNumberChooser

    init {
        viewModelScope.launch {
            repository.phoneNumberStateFlow.collect {
                _phoneNumberStateFlow.value = it
            }
        }
    }

    fun showNumbersHint() {
        _showNumberChooser.value = Event(Unit)
    }

    fun setNumber(number: Long) {
        repository.setNumber(number)
    }

    fun startVerification(number: Long) {

        _verificationStartedEvent.value = Event(Unit)

        setNumber(number)

        PickCabApi.retrofitService.startVerification(number).observeForever {

            Log.d("fuck", "start verification response : $it")

            when (it) {
                is ApiSuccessResponse -> {
                    _phoneNumberSetEvent.value = Event(Resource.Success(number))
                }
                is ApiEmptyResponse -> {
                    _phoneNumberSetEvent.value = Event(Resource.Error(Exception()))
                }
                is ApiErrorResponse -> {
                    _phoneNumberSetEvent.value = Event(Resource.Error(Exception()))
                }
                null -> {
                    _phoneNumberSetEvent.value = Event(Resource.Error(Exception()))
                }
            }
        }

    }

    fun verifyOtp(otp: Int) {

        _otpSetEvent.value = Event(Resource.Loading)

   /*     //TODO REMOVE AND UNCOMMENT
        _otpSetEvent.value = Event(Resource.Success(phoneNumberStateFlow.value))*/

        PickCabApi.retrofitService.verifyOtp(phoneNumberStateFlow.value, otp).observeForever {

            when (it) {
                is ApiSuccessResponse -> {
                    if (it.body.result == "not verified" || it.body.result == "error") {
                        _otpSetEvent.value = Event(Resource.Error(NullPointerException()))
                    } else if (it.body.result == "verified") {
                        _otpSetEvent.value = Event(Resource.Success(phoneNumberStateFlow.value))
                    }
                }
                is ApiEmptyResponse -> {
                    _otpSetEvent.value = Event(Resource.Error(NullPointerException()))
                }
                is ApiErrorResponse -> {
                    _otpSetEvent.value = Event(Resource.Error(NullPointerException()))
                }
                null -> {
                    _otpSetEvent.value = Event(Resource.Error(NullPointerException()))
                }

            }
        }
    }

    fun getPaymentStatus() = repository.paymentStatusStatusFLow

}

