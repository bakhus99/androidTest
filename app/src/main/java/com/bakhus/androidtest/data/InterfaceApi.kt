package com.bakhus.androidtest.data

import com.bakhus.androidtest.data.requests.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface InterfaceApi {

    @POST("registerUser")
    suspend fun register(
       @Body registerUser:UserBody
    ):Response<SimpleResponse>

    @POST("checkLogin")
    suspend fun login(
        @Body loginRequests: AccountRequests
    ):Response<SimpleResponse>

    @POST("updateProfile")
    suspend fun updateProfile(
        @Body updateProfile: ProfileRequest
    ):Response<SimpleResponse>

    @POST("uploadAvatar")
    suspend fun uploadAvatar(
        @Body sendImage: SendImage
    ):Response<String>




}