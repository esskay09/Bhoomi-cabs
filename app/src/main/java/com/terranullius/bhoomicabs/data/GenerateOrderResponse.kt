package com.terranullius.bhoomicabs.data

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class GenerateOrderResponse(
    @Json(name = "result") val result: String = "error",
    @Json(name = "orderID") val orderID: String = "null",
    @Json(name = "amount") val amount: Long = 0L
)
