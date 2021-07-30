package com.terranullius.bhoomicabs.network

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class ConfirmationRequest(
 @Json(name = "forMail") val forMail : Boolean,
 @Json(name = "number") val number: Long,
 @Json(name = "startDate") val startDate: String,
 @Json(name = "endDate") val endDate: String,
 @Json(name = "time") val time: String,
 @Json(name = "oneWay") val oneWay: Boolean,
 @Json(name = "identityUrl") val identityUrl: String,
 @Json(name = "startDestination") val startDestination: String,
 @Json(name = "endDestination") val endDestination: String,
 @Json(name = "forAdmin") val forAdmin: Boolean
)