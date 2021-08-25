package com.bakhus.androidtest.repository

import android.net.Uri
import com.bakhus.androidtest.data.InterfaceApi
import com.bakhus.androidtest.data.requests.AccountRequests
import com.bakhus.androidtest.data.requests.ProfileRequest
import com.bakhus.androidtest.data.requests.UserBody
import com.bakhus.androidtest.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: InterfaceApi
) {

    suspend fun updateProfile(avatar:Uri,name:String,surname: String,birthPlace:String,birthDate:String,organization:String,position:String,hobby:String) = withContext(Dispatchers.IO){
        try {
            val response = api.updateProfile(ProfileRequest(avatar,name,surname,birthPlace,birthDate,organization,position, hobby))
            if (response.isSuccessful){
                Resource.success(response.body()?.message)
            }else{
                Resource.error(response.message(),null)
            }
        }catch (e:Exception){
            Resource.error("Coudn't connect to server check your internet connection", null)
        }
    }

    suspend fun login(email: String, password: String) =
        withContext(Dispatchers.IO) {
            try {
                val response =
                    api.login(
                        AccountRequests(
                            email = email,
                            password = password
                        )
                    )
                if (response.isSuccessful) {
                    Resource.success(response.body()?.message)
                } else {
                    Resource.error(response.message(), null)
                }
            } catch (e: Exception) {
                Resource.error("Coudn't connect to server check your internet connection", null)
            }
        }

    suspend fun register(email: String, password: String, name: String, surname: String, middlename: String) =
        withContext(Dispatchers.IO) {
            try {
                val response =
                    api.register(
                        UserBody(
                            name = name,
                            surname = surname,
                            middlename = middlename,
                            email = email,
                            password = password
                        )
                    )
                if (response.isSuccessful) {
                    Resource.success(response.body()?.message)
                } else {
                    Resource.error(response.message(), null)
                }
            } catch (e: Exception) {
                Resource.error("Coudn't connect to server check your internet connection", null)
            }
        }

}