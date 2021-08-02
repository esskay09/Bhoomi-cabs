package com.terranullius.bhoomicabs.data

import com.google.firebase.firestore.DocumentSnapshot
import com.terranullius.bhoomicabs.other.Constants.FS_FIELD_CAR_ID
import com.terranullius.bhoomicabs.other.Constants.FS_FIELD_DISTANCE
import com.terranullius.bhoomicabs.other.Constants.FS_FIELD_END_CITY
import com.terranullius.bhoomicabs.other.Constants.FS_FIELD_END_DATE
import com.terranullius.bhoomicabs.other.Constants.FS_FIELD_ONE_WAY
import com.terranullius.bhoomicabs.other.Constants.FS_FIELD_ORDER_ID
import com.terranullius.bhoomicabs.other.Constants.FS_FIELD_PAID_AMOUNT
import com.terranullius.bhoomicabs.other.Constants.FS_FIELD_PICKUP_TIME
import com.terranullius.bhoomicabs.other.Constants.FS_FIELD_START_CITY
import com.terranullius.bhoomicabs.other.Constants.FS_FIELD_START_DATE
import com.terranullius.bhoomicabs.other.Constants.FS_FIELD_TOTAL_AMOUNT
import com.terranullius.bhoomicabs.other.Constants.FS_FIELD_USERID

fun CarDto.toCar() = Car(
    id = id,
    oneWayCostPerKM = oneWayCostPerKM,
    roundTripCostPerKM = roundTripCostPerKM,
    image = image
)

fun BookingDto.toBooking(cars: List<Car>): Booking {
    val car = cars.find {
        it.id == this.carId
    } ?: Car(
        id = "null",
        oneWayCostPerKM = 0f,
        roundTripCostPerKM = 0f,
        image = ""
    )
    return Booking(
        id = id,
        startCity = startCity,
        endCity = endCity,
        distance = distance,
        oneWay = oneWay,
        startDate = startDate,
        endDate = endDate,
        pickupTime = pickupTime,
        totalAmount = totalAmount,
        paidAmount = paidAmount,
        car = car,
        orderId = orderId
    )
}

fun Booking.toBookingDto(userId: String) = BookingDto(
    id = id,
    userId = userId,
    startCity = startCity,
    endCity = endCity,
    distance = distance,
    startDate = startDate,
    endDate = endDate,
    pickupTime = pickupTime,
    oneWay = oneWay,
    totalAmount = totalAmount,
    paidAmount = paidAmount,
    carId = car.id,
    orderId = orderId
)

fun DocumentSnapshot.toBookingDto(): BookingDto? {

    return try{
        val id = id
        val userId = getString(FS_FIELD_USERID)!!
        val startCity = getString(FS_FIELD_START_CITY)!!
        val endCity = getString(FS_FIELD_END_CITY)!!
        val distance = getLong(FS_FIELD_DISTANCE)!!
        val startDate = getString(FS_FIELD_START_DATE)!!
        val endDate = getString(FS_FIELD_END_DATE)!!
        val pickupTime = getString(FS_FIELD_PICKUP_TIME)!!
        val oneWay = getBoolean(FS_FIELD_ONE_WAY)!!
        val totalAmount = getLong(FS_FIELD_TOTAL_AMOUNT)!!
        val paidAmount = getLong(FS_FIELD_PAID_AMOUNT)!!
        val carId = getString(FS_FIELD_CAR_ID)!!
        val orderId = getString(FS_FIELD_ORDER_ID)!!

        BookingDto(
            id = id,
            userId = userId,
            startCity = startCity,
            endCity = endCity,
            startDate = startDate,
            endDate = endDate,
            distance = distance,
            pickupTime = pickupTime,
            oneWay = oneWay,
            totalAmount = totalAmount,
            paidAmount = paidAmount,
            carId = carId,
            orderId = orderId
        )
    } catch (e: Exception){
        null
    }
}