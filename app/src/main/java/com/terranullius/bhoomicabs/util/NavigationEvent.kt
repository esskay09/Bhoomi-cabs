package com.terranullius.bhoomicabs.util

sealed class NavigationEvent {
    object LoginToOtp: NavigationEvent()
    object OtpToLogin: NavigationEvent()
    object OtpToBookings: NavigationEvent()

    object BookingsToBookingDetail : NavigationEvent()
    object BookingsToNewBooking: NavigationEvent()

    object NewBookingToSelectCar: NavigationEvent()

    object SelectCarToSelectPayment: NavigationEvent()

    object SplashToLogin: NavigationEvent()
    object SplashToBookings: NavigationEvent()

    object SelectPaymentToBookingFinished: NavigationEvent()

    object BoookingFinishedToBookings: NavigationEvent()

    object None: NavigationEvent()
}
