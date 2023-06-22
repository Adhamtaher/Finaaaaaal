package com.example.myapplication.fragment.main.domain

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.example.myapplication.common.HospiService
import com.example.myapplication.fragment.main.login.LoginInfo
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorInfo1
import com.example.myapplication.fragment.patient.medicalhistory.medicalcondition.MedicalRecordInfo
import com.example.myapplication.utils.Status
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject
import kotlin.math.abs

class UserRepo @Inject constructor(private val hospiService: HospiService) {
    private val gson = Gson()

    fun loginUser(user: LoginInfo) = flow {

        try {
            emit(Status.Loading)

                val response = hospiService.loginUser(user)
                emit(Status.Success(response))

                Log.e("loginUser: ", response.toString())

        } catch (e: Throwable) {
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<AuthResponse>() {}.type
                    val errorResponse: AuthResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.message.toString())
                    emit(Status.Error( errorResponse?.message.toString()))
                }
                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error( e.message.toString()))
                }
            }
        }

    }.flowOn(Dispatchers.IO)

    fun registerUser(user: UserInfo) = flow {

        try {
            emit(Status.Loading)

            val response = hospiService.registerUser(user)
            emit(Status.Success(response))

            Log.e("loginUser: ", response.toString())

        } catch (e: Throwable) {
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<AuthResponse>() {}.type
                    val errorResponse: AuthResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.message.toString())
                    emit(Status.Error( errorResponse?.message.toString()))
                }
                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error( e.message.toString()))
                }
            }
        }

    }.flowOn(Dispatchers.IO)


    fun reserve(reserveInfo: ReserveInfo , token:String) = flow {
        emit(Status.Loading)
        try {
            val response = hospiService.reserve(reserveInfo , token)
            emit(Status.Success(response))
        }catch (e:Throwable){
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<AuthResponse>() {}.type
                    val errorResponse: AuthResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.message.toString())
                    emit(Status.Error( errorResponse?.message.toString()))
                }
                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error( e.message.toString()))
                }
            }
        }
    }

    fun getAllDoctors(doctorInfo1: DoctorInfo1) = flow {
        emit(Status.Loading)
        try {
            val response = hospiService.getDoctors(doctorInfo1)
            emit(Status.Success(response))

        }catch (e:Throwable){
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<AuthResponse>() {}.type
                    val errorResponse: AuthResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.message.toString())
                    emit(Status.Error( errorResponse?.message.toString()))
                }
                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error( e.message.toString()))
                }
            }
        }
    }

    fun getDoctorInfo(doctorInfo1: DoctorInfo1) = flow {
        emit(Status.Loading)
        try {
            val response = hospiService.getDoctors(doctorInfo1)
            emit(Status.Success(response))
        }catch (e:Throwable){
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<AuthResponse>() {}.type
                    val errorResponse: AuthResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.message.toString())
                    emit(Status.Error( errorResponse?.message.toString()))
                }
                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error( e.message.toString()))
                }
            }
        }
    }

    fun getCategories(doctorInfo1: DoctorInfo1 , token:String) = flow {
        emit(Status.Loading)
        try {
            val response = hospiService.getCategories(doctorInfo1, token)
            emit(Status.Success(response))
        }catch (e:Throwable){
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<AuthResponse>() {}.type
                    val errorResponse: AuthResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.message.toString())
                    emit(Status.Error( errorResponse?.message.toString()))
                }
                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error( e.message.toString()))
                }
            }
        }
    }

    fun getProducts(doctorInfo1: DoctorInfo1 , token:String) = flow {
        emit(Status.Loading)
        try {
            val response = hospiService.getProducts(doctorInfo1, token)
            emit(Status.Success(response))
        }catch (e:Throwable){
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<AuthResponse>() {}.type
                    val errorResponse: AuthResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.message.toString())
                    emit(Status.Error( errorResponse?.message.toString()))
                }
                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error( e.message.toString()))
                }
            }
        }
    }

    fun getFirstAid(doctorInfo1: DoctorInfo1 , token:String) = flow {
        emit(Status.Loading)
        try {
            val response = hospiService.getFirstAid(doctorInfo1, token)
            emit(Status.Success(response))
        }catch (e:Throwable){
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<AuthResponse>() {}.type
                    val errorResponse: AuthResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.message.toString())
                    emit(Status.Error( errorResponse?.message.toString()))
                }
                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error( e.message.toString()))
                }
            }
        }
    }

    fun postMedicalRecord(medicalRecordInfo: MedicalRecordInfo , token:String) = flow {
        emit(Status.Loading)
        try {
            val response = hospiService.postMedicalRecord(medicalRecordInfo, token)
            emit(Status.Success(response))
        }catch (e:Throwable){
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<AuthResponse>() {}.type
                    val errorResponse: AuthResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.message.toString())
                    emit(Status.Error( errorResponse?.message.toString()))
                }
                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error( e.message.toString()))
                }
            }
        }
    }

    fun getMedicalRecord(doctorInfo1: DoctorInfo1 , token:String) = flow {
        emit(Status.Loading)
        try {
            val response = hospiService.getMedicalRecord(doctorInfo1, token)
            emit(Status.Success(response))
        }catch (e:Throwable){
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<AuthResponse>() {}.type
                    val errorResponse: AuthResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.message.toString())
                    emit(Status.Error( errorResponse?.message.toString()))
                }
                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error( e.message.toString()))
                }
            }
        }
    }

    suspend fun uploadFiles(fileUri: Uri, fileRealPath: String , id:String, fieldName:String , recId:String , context:Context) = flow {
        try {
            emit(Status.Loading)

            val fileToSend = prepareFilePart("files", fileRealPath,fileUri ,context)
            val idRequestBody: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), id)
            val fieldNameBody: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), fieldName)
            val recIdRequestBody: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), recId)
            val response = hospiService.uploadFile(files =  fileToSend , id =  idRequestBody , fieldName =  fieldNameBody , recId =  recIdRequestBody)
            emit(Status.Success(response))
        }catch (e:Throwable){
            when (e) {
                is HttpException -> {
                    val type = object : TypeToken<AuthResponse>() {}.type
                    val errorResponse: AuthResponse? =
                        gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                    Log.e("loginUsereeeee: ", errorResponse?.message.toString())
                    emit(Status.Error( errorResponse?.message.toString()))
                }
                is Exception -> {
                    Log.e("loginUsereeeee: ", e.message.toString())
                    emit(Status.Error( e.message.toString()))
                }
            }
        }
    }

    private fun prepareFilePart(partName: String,fileRealPath: String,fileUri: Uri , ctx:Context): MultipartBody.Part {
        val file: File = File(fileRealPath)
        val requestFile: RequestBody = RequestBody.create(
            ctx.contentResolver.getType(fileUri)!!.toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }


    suspend fun getFileName(uri: Uri,  context: Context) = flow {
        emit(Status.Loading)
        try {
            val uriString: String = uri.toString()
            var pdfName: String? = null

            if (uriString.startsWith("content://")) {
                var myCursor: Cursor? = null
                try {
                    myCursor = context?.contentResolver?.query(uri, null, null, null, null)
                    Log.e("onActivityResult: ", myCursor.toString())
                    if (myCursor != null && myCursor.moveToFirst()) {
                        if (myCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME) >= 0) {
                            pdfName = myCursor.getString(
                                abs(myCursor.getColumnIndex(
                                    OpenableColumns.DISPLAY_NAME))
                            )

                        }

                        Log.e("onActivityResult: ", pdfName.toString())
                        emit(Status.Success(pdfName))
                    }
                } finally {
                    myCursor?.close()
                }
            }


        } catch (e: Exception) {
            emit(Status.Error( e.message.toString()))
        }

    }.flowOn(Dispatchers.IO)

}