package com.example.myapplication.fragment.patient.mainpage.doctors.doctors.booking

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class BookingViewModel @Inject constructor(
    private val repository: UserRepo
) : ViewModel() {
    private val _loginState = MutableStateFlow(DoctorState())
    val loginState = _loginState.asStateFlow()

    suspend fun getAllDoctors(doctorInfo1: DoctorInfo1) =viewModelScope.launch {
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
}