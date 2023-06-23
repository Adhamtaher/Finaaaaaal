package com.example.myapplication.fragment.main.login

import com.google.gson.annotations.SerializedName

data class LoginAdded(
    @SerializedName("email")
    val email: String?,
    @SerializedName("password")
    val password: String?
)
