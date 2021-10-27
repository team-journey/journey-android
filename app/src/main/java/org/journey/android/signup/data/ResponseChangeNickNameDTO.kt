package org.journey.android.signup.data


import com.google.gson.annotations.SerializedName

data class ResponseChangeNickNameDTO(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)