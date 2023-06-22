package com.example.myapplication.fragment.patient.mainpage.firstaid

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
class FirstAidViewModel@Inject constructor(
    private val repository: UserRepo
) : ViewModel() {

    private val _loginState = MutableStateFlow(DoctorState())
    val loginState = _loginState.asStateFlow()

    suspend fun getFirstAid(doctorInfo1: DoctorInfo1, token:String) =viewModelScope.launch {
        repository.getFirstAid(doctorInfo1 , token).collect { resource->
            when(resource){
                is Status.Loading->{
                    _loginState.value = loginState.value.copy(
                        isLoading = true
                    )
                }
                is Status.Success->{
                    _loginState.value = loginState.value.copy(
                        isLoading = false,
                        firstAid = resource.data.aids,
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
}