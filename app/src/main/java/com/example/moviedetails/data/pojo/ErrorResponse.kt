package com.example.moviedetails.data.pojo

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("Response")
    val response: String,
    @SerializedName("Error")
    val message: String,
)