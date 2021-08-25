package com.bakhus.androidtest.data.requests

import android.net.Uri

data class ProfileRequest(
    val avatar: Uri,
    val name:String,
    val surname:String,
    val birth_place:String,
    val birth_date:String,
    val organization:String,
    val position:String,
    val hobby:String,
)
