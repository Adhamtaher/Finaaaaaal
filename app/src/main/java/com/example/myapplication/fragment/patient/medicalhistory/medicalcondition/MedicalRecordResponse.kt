package com.example.myapplication.fragment.patient.medicalhistory.medicalcondition

import com.google.gson.annotations.SerializedName

data class MedicalRecordResponse(

	@field:SerializedName("records")
	val records: List<RecordsItem>? = null,

	@field:SerializedName("added")
	val recordsAdded: List<RecordsItem>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("files")
	val files :List<Files>?=null
)

data class RecordsItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("doctorName")
	val doctorName: String? = null,

	@field:SerializedName("patientId")
	val patientId: String? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null,

	@field:SerializedName("still")
	val still: Boolean? = null,

	@field:SerializedName("chronic")
	val chronic: Boolean? = null,

	@field:SerializedName("files")
	val files :List<Files>?=null
)
data class Files(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("path")
	val path: String? = null
)
