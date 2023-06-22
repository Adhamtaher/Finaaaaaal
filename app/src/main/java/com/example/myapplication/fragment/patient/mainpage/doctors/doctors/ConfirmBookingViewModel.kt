package com.example.myapplication.fragment.patient.mainpage.doctors.doctors

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.fragment.main.domain.ReserveInfo
import com.example.myapplication.fragment.main.domain.UserRepo
import com.example.myapplication.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmBookingViewModel @Inject constructor(
    private val repository: UserRepo
) : ViewModel() {

    private val _loginState = MutableStateFlow(DoctorState())
    val loginState = _loginState.asStateFlow()

    private val _userState = MutableStateFlow(DoctorState())
    val userState = _userState.asStateFlow()

    private val _reverseState = MutableStateFlow(DoctorState())
    val reverseState = _reverseState.asStateFlow()




    suspend fun getDoctor(doctorInfo1: DoctorInfo1) =viewModelScope.launch {
        repository.getDoctorInfo(doctorInfo1).collect { resource->
            when(resource){
                is Status.Loading->{
                    _loginState.value = loginState.value.copy(
                        isLoading = true
                    )
                }
                is Status.Success->{
                    _loginState.value = loginState.value.copy(
                        isLoading = false,
                        doctorInfo = resource.data.users?.get(0),
                        success = resource.data.message
                    )
                    Log.e( "getAllDoctors: ",resource.data.users.toString() )

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

    suspend fun reverse(reveseInfo: ReserveInfo , token:String) =viewModelScope.launch {
        repository.reserve(reveseInfo , token).collect { resource->
            when(resource){
                is Status.Loading->{
                    _reverseState.value = reverseState.value.copy(
                        isLoading = true
                    )
                }
                is Status.Success->{
                    _reverseState.value = reverseState.value.copy(
                        isLoading = false,
                        reserveInfo = resource.data.add?.get(0),
                        success = resource.data.message
                    )


                }
                is Status.Error->{
                    _reverseState.value = reverseState.value.copy(
                        isLoading = false,
                        error = resource.message
                    )
                }
            }

        }
    }



}