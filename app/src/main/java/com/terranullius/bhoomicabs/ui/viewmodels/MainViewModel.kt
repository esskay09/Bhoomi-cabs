package com.terranullius.bhoomicabs.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.terranullius.bhoomicabs.repositories.MainRepository
import com.terranullius.bhoomicabs.util.Event
import com.terranullius.bhoomicabs.util.PaymentStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: MainRepository): BaseViewModel() {

    init {
        viewModelScope.launch {
            repository.phoneNumberStateFlow.collect {
                _phoneNumberStateFlow.value = it
            }
        }
    }

    fun showNumbersHint() {
        repository.showNumbersHint()
    }

    fun getNumberChooserEvent() = repository.showNumberChooser

    fun setNumber(number: Long) {
        repository.setNumber(number)
    }

    fun getVerificationStartedLiveData() = repository.verificationStartedEvent

    fun setPaymentStatus(paymentStatus: PaymentStatus){
        repository.setPaymentStatus(paymentStatus)
    }

    fun getPaymentStatus() = repository.paymentStatusStatusFLow

}