package com.terranullius.bhoomicabs.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.terranullius.bhoomicabs.repositories.MainRepository
import com.terranullius.bhoomicabs.util.Event
import com.terranullius.bhoomicabs.util.PaymentStatus
import com.terranullius.bhoomicabs.util.Resource
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

    fun updatePayment(orderId: String?, amount: String) {
        viewModelScope.launch {
            repository.updatePayment(orderId, amount).collect {
                when(it){
                    is Resource.Success -> {
                        repository.setPaymentStatus(PaymentStatus.Successful)
                    }
                    is Resource.Error -> {
                        repository.setPaymentStatus(PaymentStatus.Failed(it.exception.message ?: "Payment error"))
                    }
                }
            }
        }
    }

}