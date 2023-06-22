package com.example.myapplication.fragment.patient.medicalhistory.medicalcondition

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
class MedicalRecordAddViewModel @Inject constructor(
    private val repository: UserRepo
) : ViewModel() {

    private val _loginState = MutableStateFlow(DoctorState())
    val loginState = _loginState.asStateFlow()

    private val _fileNameState = MutableStateFlow(DoctorState())
    val fileNameState = _fileNameState.asStateFlow()

    private val _uploadState = MutableStateFlow(DoctorState())
    val uploadState = _uploadState.asStateFlow()

    private val _fileName = MutableLiveData("")

    // new added
    val fileName: LiveData<String>
        get() = _fileName

    // new added
    fun setFileName(name:String) {
        _fileName.value = name
    }

    suspend fun postMedicalRecord(medicalRecordInfo: MedicalRecordInfo, token: String) =
        viewModelScope.launch {
            repository.postMedicalRecord(medicalRecordInfo, token).collect { resource ->
                when (resource) {
                    is Status.Loading -> {
                        _loginState.value = loginState.value.copy(
                            isLoading = true
                        )
                    }

                    is Status.Success -> {
                        _loginState.value = loginState.value.copy(
                            isLoading = false,
                            success = resource.data.message,
                            record = resource.data.recordsAdded?.get(0)
                        )

                    }

                    is Status.Error -> {
                        _loginState.value = loginState.value.copy(
                            isLoading = false,
                            error = resource.message
                        )
                    }
                }
            }
        }

    suspend fun getName(uri: Uri, context: Context) = viewModelScope.launch {
        repository.getFileName(uri, context).collect { resource ->
            when (resource) {
                is Status.Loading -> {
                    _fileNameState.value = fileNameState.value.copy(
                        isLoading = true
                    )
                }

                is Status.Success -> {
                    _fileNameState.value = fileNameState.value.copy(
                        isLoading = false,
                        fileName = resource.data
                    )
                }

                is Status.Error -> {
                    _fileNameState.value = fileNameState.value.copy(
                        isLoading = false,
                        error = resource.message
                    )
                }
            }
        }
    }

    suspend fun uploadFile(
        fileUri: Uri,
        fileRealPath: String, id: String, fieldName: String, recId: String, context: Context
    ) = viewModelScope.launch {
        repository.uploadFiles(fileUri, fileRealPath, id, fieldName, recId, context)
            .collect { resource ->
                when (resource) {
                    is Status.Loading -> {
                        _uploadState.value = uploadState.value.copy(
                            isLoading = true
                        )
                    }

                    is Status.Success -> {
                        _uploadState.value = uploadState.value.copy(
                            isLoading = false,
                            success = resource.data.message,
                            file = resource.data.files?.get(0)
                        )
                    }

                    is Status.Error -> {
                        _uploadState.value = uploadState.value.copy(
                            isLoading = false,
                            error = resource.message
                        )
                    }
                }
            }
    }
}