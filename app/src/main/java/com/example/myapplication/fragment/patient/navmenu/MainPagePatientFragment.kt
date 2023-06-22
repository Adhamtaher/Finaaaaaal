package com.example.myapplication.fragment.patient.navmenu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.common.Constants
import com.example.myapplication.common.Constants.Companion.dataStore
import com.example.myapplication.databinding.FragmentMainPagePatientBinding
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorInfo1
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.Filter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Calendar

@AndroidEntryPoint
class MainPagePatientFragment : Fragment() {

    private lateinit var binding: FragmentMainPagePatientBinding
    private val REQUEST_CODE_IMAGE_PICKER = 100
    val viewModel: MainPagePatientViewModel by viewModels()
    private lateinit var dataStore: DataStore<Preferences>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainPagePatientBinding.inflate(inflater, container, false)

        val calendar = Calendar.getInstance().time
        val dateFormat = DateFormat.getDateInstance(DateFormat.LONG).format(calendar)
        binding.textViewDate.text = dateFormat

        binding.clinicVisit.setOnClickListener {
             findNavController().navigate(R.id.action_mainPagePatient_to_speciality)
        }
        binding.homeVisit.setOnClickListener {
            findNavController().navigate(R.id.action_mainPagePatient_to_speciality)
        }
        binding.doctorCall.setOnClickListener {
            findNavController().navigate(R.id.action_mainPagePatient_to_speciality)
        }
        binding.analysis.setOnClickListener {
            findNavController().navigate(R.id.action_mainPagePatient_to_analysis)
        }
        binding.examinatons.setOnClickListener {
            findNavController().navigate(R.id.action_mainPagePatient_to_radiations)
        }
        binding.ask.setOnClickListener {
            findNavController().navigate(R.id.action_mainPagePatient_to_speciality)
        }
        binding.ordermedicine.setOnClickListener {
            findNavController().navigate(R.id.action_mainPagePatient_to_medicineTypes)
        }
        binding.sendPrescription.setOnClickListener {
            pickImageFromGallery()
        }
        binding.searchbtn.setOnClickListener {
            findNavController().navigate(R.id.action_mainPagePatient_to_medicineTypes)
        }
        binding.firstaid.setOnClickListener {
            findNavController().navigate(R.id.action_mainPagePatient_to_firstAid)
        }
        collectState()

        return binding.root
    }
    private fun pickImageFromGallery() {
        // Intent to pick image from gallery
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_IMAGE_PICKER)
    }

    private suspend fun getUserId(key: String): String? {
        dataStore = requireContext().dataStore
        val dataStoreKey: Preferences.Key<String> = stringPreferencesKey(key)
        val preference = dataStore.data.first()
        return preference[dataStoreKey]
    }

    fun collectState() {
        lifecycleScope.launch {
             getUserId(Constants.userId)
                viewModel.getDoctor(DoctorInfo1(Filter(_id = getUserId(Constants.userId))))

            viewModel.loginState.collect{
                binding.helloView.text = it.doctorInfo?.name
            }

        }
    }
}