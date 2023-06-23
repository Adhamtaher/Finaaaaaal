package com.example.myapplication.fragment.main.domain

import com.google.gson.annotations.SerializedName

data class ResponseAuth(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("token")
	val token: String? = null
)

data class PatientInfo1(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("birthDate")
	val birthDate: String? = null,

	@field:SerializedName("favDoctors")
	val favDoctors: List<Any?>? = null
)

data class User(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("notes")
	val notes: List<Any?>? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("confirmedEmail")
	val confirmedEmail: Boolean? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("isLoggedIn")
	val isLoggedIn: Boolean? = null,

	@field:SerializedName("files")
	val files: List<Any?>? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("patientInfo")
	val patientInfo: PatientInfo1? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
