package com.example.myapplication.fragment.patient.mainpage.examinations

data class CategoriesResponse(
    val message: String? = null,
    val results: List<Result>? = null,
    val products: List<Products>? = null
)

data class Result(
    val __v: Int? = null,
    val _id: String? = null,
    val name: String? = null,
    val type: String? = null
)

data class Products(
    val __v: Int? = null,
    val _id: String? = null,
    val name: String? = null,
    val price: Int? = null
)