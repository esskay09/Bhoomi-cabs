package com.terranullius.bhoomicabs.data

import java.util.*

data class Booking(
    val id: String = UUID.randomUUID().toString(),
    val startCity: String,
    val endCity: String,
    val distance: Long,
    val pickupTime: String,
    val oneWay: Boolean,
    val startDate: String,
    val endDate: String,
    val totalAmount: Long,
    var paidAmount: Long,
    val car: Car,
    val orderId: String = ""
)
