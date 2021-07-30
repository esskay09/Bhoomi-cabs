package com.terranullius.bhoomicabs.network

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
data class ServerResponse(
     @Json(name = "result") val result: String
)
