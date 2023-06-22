package com.example.myapplication.common

import com.example.myapplication.fragment.main.domain.ReserveInfo
import com.example.myapplication.fragment.main.domain.ReserveResponse
import com.example.myapplication.fragment.main.domain.ResponseAuth
import com.example.myapplication.fragment.main.domain.UserInfo
import com.example.myapplication.fragment.main.login.LoginInfo
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorInfo1
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorsResponse
import com.example.myapplication.fragment.patient.mainpage.examinations.CategoriesResponse
import com.example.myapplication.fragment.patient.mainpage.firstaid.FirstAidFragment
import com.example.myapplication.fragment.patient.mainpage.firstaid.FirstAidResponse
import com.example.myapplication.fragment.patient.medicalhistory.medicalcondition.MedicalRecordInfo
import com.example.myapplication.fragment.patient.medicalhistory.medicalcondition.MedicalRecordResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface HospiService {

    @POST("api/v1//signUp")
    suspend fun registerUser(@Body userInfo: UserInfo): ResponseAuth

    @POST("api/v1//signIn")
    suspend fun loginUser(@Body loginInfo: LoginInfo): ResponseAuth

    @POST("api/v1/getAllUsers")
    suspend fun getDoctors(@Body doctorInfo: DoctorInfo1): DoctorsResponse

    @POST("api/v1//patient/reserve/reserve")
    suspend fun reserve(
        @Body reserveInfo: ReserveInfo,
        @Header("Authorization") token: String
    ): ReserveResponse

    @POST("api/v1/patient/category/get")
    suspend fun getCategories(
        @Body doctorInfo: DoctorInfo1,
        @Header("Authorization") token: String
    ): CategoriesResponse

    @POST("api/v1/patient/product/get")
    suspend fun getProducts(
        @Body doctorInfo: DoctorInfo1,
        @Header("Authorization") token: String
    ): CategoriesResponse

    @POST("api/v1/patient/firstAid/get")
    suspend fun getFirstAid(
        @Body doctorInfo: DoctorInfo1,
        @Header("Authorization") token: String
    ): FirstAidResponse

    @POST("api/v1/medicalRecord")
    suspend fun postMedicalRecord(
        @Body medicalRecordInfo: MedicalRecordInfo,
        @Header("Authorization") token: String
    ): MedicalRecordResponse


    @POST("api/v1/medicalRecord/get")
    suspend fun getMedicalRecord(
        @Body doctorInfo: DoctorInfo1,
        @Header("Authorization") token: String
    ): MedicalRecordResponse

    @Multipart
    @POST("api/v1/fileUpload/uploadFiles")
    suspend fun uploadFile(
        @Part files: MultipartBody.Part?,
        @Part("fieldName") fieldName: RequestBody,
        @Part("recId") recId: RequestBody,
        @Part("id") id: RequestBody

    ): MedicalRecordResponse

}

