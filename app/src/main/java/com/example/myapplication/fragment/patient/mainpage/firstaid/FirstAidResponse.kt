package com.example.myapplication.fragment.patient.mainpage.firstaid

import com.google.gson.annotations.SerializedName

data class FirstAidResponse(

	@field:SerializedName("aids")
	val aids: List<AidsItem>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class FilesItem(

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)

data class AidsItem(

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("files")
	val files: List<FilesItem?>? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)
