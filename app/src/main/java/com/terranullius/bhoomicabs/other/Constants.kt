package com.terranullius.bhoomicabs.other

import androidx.annotation.Keep

@Keep
object Constants {

    const val PREF_VERIFIED = "isVerified"
    const val PREFS_DIR = "Users"
    const val PREF_NUMBER = "numberphone"
    const val CREDENTIAL__PHONE_PICKER_REQUEST = 132
    const val SMS_USER_CONSENT_REQUEST = 7

    const val PH_START_DATE = "Start Date"
    const val PH_END_DATE = "End Date"
    const val PH_PICK_UP_TIME = "00:00 hrs"
    const val PH_START_CITY = "Start City"
    const val PH_END_CITY = "Destination City"


    const val FIRESTORE_COLLECTION_BOOKINGS = "Bookings"
    const val FIRESTORE_CARS_COLLECTION = "Cars"
    const val FS_FIELD_ID = "id"
    const val FS_FIELD_USERID = "userId"
    const val FS_FIELD_START_CITY = "startCity"
    const val FS_FIELD_END_CITY = "endCity"
    const val FS_FIELD_DISTANCE = "distance"
    const val FS_FIELD_START_DATE = "startDate"
    const val FS_FIELD_END_DATE = "endDate"
    const val FS_FIELD_PICKUP_TIME = "pickupTime"
    const val FS_FIELD_ONE_WAY = "oneWay"
    const val FS_FIELD_TOTAL_AMOUNT = "totalAmount"
    const val FS_FIELD_PAID_AMOUNT = "paidAmount"
    const val FS_FIELD_CAR_ID = "carId"
        const val FS_FIELD_ORDER_ID = "orderId"


    const val SERVER_RES_ORDER_ERROR = "error"
    const val SERVER_RES_ORDER_SUCCESS = "order created"

}