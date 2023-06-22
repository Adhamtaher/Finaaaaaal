package com.example.myapplication.fragment.main.domain

import com.google.gson.annotations.SerializedName

data class ReserveInfo(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("speciality")
	val speciality: String? = null,

	@field:SerializedName("docName")
	val docName: String? = null,

	@field:SerializedName("fees")
	val fees: String? = null,

	@field:SerializedName("doctorId")
	val doctorId: String? = null,

	@field:SerializedName("patientId")
	val patientId: String? = null,

	@field:SerializedName("anotherPerson")
	val anotherPerson: Boolean? = null,

	@field:SerializedName("patName")
	val patName: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("day")
	val day: String? = null,

	@field:SerializedName("visitType")
	val visitType: String? = null,

	@field:SerializedName("productId")
	val productId: String? = null,

	@field:SerializedName("productName")
	val productName: String? = null

)
