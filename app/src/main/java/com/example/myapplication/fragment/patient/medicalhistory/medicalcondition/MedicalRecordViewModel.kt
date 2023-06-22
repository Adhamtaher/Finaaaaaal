package com.example.myapplication.fragment.patient.medicalhistory.medicalcondition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.fragment.main.domain.UserRepo
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorInfo1
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorState
import com.example.myapplication.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicalRecordViewModel@Inject constructor(
    private val repository: UserRepo
) : ViewModel() {
    private val _loginState = MutableStateFlow(DoctorState())
    val loginState = _loginState.asStateFlow()

    suspend fun getAllRecords(doctorInfo1: DoctorInfo1 , token:String) = viewModelScope.launch {
        repository.getMedicalRecord(doctorInfo1, token).collect{
            when(it){
                is Status.Loading -> {
                    _loginState.value = loginState.value.copy(
                        isLoading = true
                    )
                }
                is Status.Success -> {
                    _loginState.value = loginState.value.copy(
                        isLoading = false,
                        success = it.data.message,
                        Allrecords = it.data.records
                    )
                }
                is Status.Error -> {
                    _loginState.value = loginState.value.copy(
                        isLoading = false,
                        error = it.message
                    )
                }
            }

        }
    }
}