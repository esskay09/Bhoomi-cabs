package com.terranullius.bhoomicabs.network

import androidx.lifecycle.LiveData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.terranullius.bhoomicabs.data.GenerateOrderResponse
import com.terranullius.bhoomicabs.network.ConfirmationRequest
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://bhumi-cabs.herokuapp.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addCallAdapterFactory(LiveDataCallAdapterFactory())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface PickCabApiService {

    @POST("SendConfirmation")
    fun sendConfirmation(
        @Body
        request: ConfirmationRequest
    ) : LiveData<GenericApiResponse<ServerResponse>>

    @POST("verify/number/otp")
    @FormUrlEncoded
    fun verifyOtp(
        @Field("number") number: Long,
        @Field("otp") otp: Int
    ) : LiveData<GenericApiResponse<ServerResponse>>


    @POST("verify/number")
    @FormUrlEncoded
     fun startVerification(
        @Field("number") number: Long
    ) : LiveData<GenericApiResponse<ServerResponse>>

     @POST("generateOrder")
     @FormUrlEncoded
     fun generateOrder(
         @Field("amount") amount : Long
     ) : LiveData<GenericApiResponse<GenerateOrderResponse>>
}

object PickCabApi {
    val retrofitService: PickCabApiService by lazy { retrofit.create(PickCabApiService::class.java) }
}