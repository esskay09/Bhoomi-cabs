package com.terranullius.bhoomicabs.util

sealed class PaymentStatus {
    object Started: PaymentStatus()
    object NewBooking: PaymentStatus()
    object None: PaymentStatus()
    data class Failed(val errorMessage: String) : PaymentStatus()
    data class InitiateCheckout(val amount: Long, val orderId: String) : PaymentStatus()
    object Successful: PaymentStatus()
}