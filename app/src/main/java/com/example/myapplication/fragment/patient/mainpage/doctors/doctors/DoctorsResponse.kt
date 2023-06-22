package com.example.myapplication.fragment.patient.mainpage.doctors.doctors

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DoctorsResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("users")
	val users: List<DoctorItem>? = null
)

data class ScheduleItem(

	@field:SerializedName("limit")
	val limit: Int? = null,

	@field:SerializedName("time")
	val time: Time? = null,

	@field:SerializedName("day")
	val day: String? = null
):Serializable

data class DoctorItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("confirmedEmail")
	val confirmedEmail: Boolean? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,


	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("isLoggedIn")
	val isLoggedIn: Boolean? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("doctorInfo")
	val doctorInfo: DoctorInfo? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class Fees(

	@field:SerializedName("followUp")
	val followUp: Int? = null,

	@field:SerializedName("examin")
	val examin: Int? = null
)

data class DoctorInfo(

	@field:SerializedName("speciality")
	val speciality: String? = null,

	@field:SerializedName("schedule")
	val schedule: List<ScheduleItem>? = null,

	@field:SerializedName("fees")
	val fees: Fees? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("available")
	val available: Boolean? = null,

	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("birthDate")
	val birthDate: String? = null,

	@field:SerializedName("room")
	val room: String? = null
)

data class Time(

	@field:SerializedName("from")
	val from: String? = null,

	@field:SerializedName("to")
	val to: String? = null
)
