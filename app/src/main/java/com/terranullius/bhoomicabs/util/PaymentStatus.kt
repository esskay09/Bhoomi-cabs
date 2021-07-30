package com.terranullius.bhoomicabs.util

sealed class PaymentStatus {
    object Started: PaymentStatus()
    object NewBooking: PaymentStatus()
    object None: PaymentStatus()
    object Failed : PaymentStatus()
}