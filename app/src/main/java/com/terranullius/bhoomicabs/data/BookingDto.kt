package com.terranullius.bhoomicabs.data

//TODO ADD BOOKING ID
data class BookingDto(
    val id: String = "unassigned",
    val userId: String = "",
    val startCity: String = "",
    val endCity: String = "",
    val distance: Long = 0L,
    val pickupTime: String = "",
    val oneWay: Boolean = true,
    val startDate: String = "",
    val endDate: String = "",
    val totalAmount: Long = 0L,
    val paidAmount: Long = 0L,
    val carId: String = ""
)


