package com.example.myapplication.fragment.patient.mainpage.analysis

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.fragment.main.domain.ReserveInfo
import com.example.myapplication.fragment.main.domain.UserRepo
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorInfo1
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorState
import com.example.myapplication.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalysisBookingViewModel@Inject constructor(
    private val repository: UserRepo
) : ViewModel() {

    private val _loginState = MutableStateFlow(DoctorState())
    val loginState = _loginState.asStateFlow()

    private val _reserveState = MutableStateFlow(DoctorState())
    val reserveState = _reserveState.asStateFlow()

    private val _userState = MutableStateFlow(DoctorState())
    val userState = _userState.asStateFlow()

    suspend fun getProducts(doctorInfo1: DoctorInfo1, token:String) =viewModelScope.launch {
        repository.getProducts(doctorInfo1 , token).collect { resource->
            when(resource){
                is Status.Loading->{
                    _loginState.value = loginState.value.copy(
                        isLoading = true
                    )
                }
                is Status.Success->{
                    _loginState.value = loginState.value.copy(
                        isLoading = false,
                        allProducts = resource.data.products,
                        success = resource.data.message
                    )
                }
                is Status.Error->{
                    _loginState.value = loginState.value.copy(
                        isLoading = false,
                        error = resource.message
                    )
                }
            }
        }
    }

    suspend fun reserves(reserveInfo: ReserveInfo, token:String) =viewModelScope.launch {
        repository.reserve(reserveInfo , token).collect { resource->
            when(resource){
                is Status.Loading->{
                    _reserveState.value = reserveState.value.copy(
                        isLoading = true
                    )
                }
                is Status.Success->{
                    _reserveState.value = reserveState.value.copy(
                        isLoading = false,
                        reserveInfo = resource.data.add?.get(0),
                        success = resource.data.message
                    )
                }
                is Status.Error->{
                    _reserveState.value = reserveState.value.copy(
                        isLoading = false,
                        error = resource.message
                    )
                }
            }
        }
    }

    suspend fun getUser(doctorInfo1: DoctorInfo1) =viewModelScope.launch {
        repository.getDoctorInfo(doctorInfo1).collect { resource->
            when(resource){
                is Status.Loading->{
                    _userState.value = userState.value.copy(
                        isLoading = true
                    )
                }
                is Status.Success->{
                    _userState.value = userState.value.copy(
                        isLoading = false,
                        doctorInfo = resource.data.users?.get(0),
                        success = resource.data.message
                    )
                    Log.e( "getAllDoctors: ",resource.data.users.toString() )

                }
                is Status.Error->{
                    _userState.value = userState.value.copy(
                        isLoading = false,
                        error = resource.message
                    )
                }
            }

        }
    }

}