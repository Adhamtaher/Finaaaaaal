package com.example.myapplication.fragment.patient.medicalhistory.medicalcondition.operation

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
import com.example.myapplication.common.Constants
import com.example.myapplication.common.Constants.Companion.dataStore
import com.example.myapplication.databinding.FragmentOperationBinding
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorInfo1
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.Filter
import com.example.myapplication.fragment.patient.medicalhistory.medicalcondition.MedicalRecordViewModel
import com.example.myapplication.fragment.patient.medicalhistory.radiation.RadiationHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OperationFragment : Fragment(),RadiationHistoryAdapter.OnItemClickListener {

    lateinit var binding: FragmentOperationBinding
    lateinit var adapter: RadiationHistoryAdapter


    val viewModel: MedicalRecordViewModel by viewModels()
    private lateinit var dataStore: DataStore<Preferences>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOperationBinding.inflate(inflater, container, false)
        adapter= RadiationHistoryAdapter(this)
        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.btnAdd.setOnClickListener {
            val action =
                OperationFragmentDirections.actionOperationFragmentToMedicalRecordAddFragment(
                    binding.txtTitle.text.toString()
                )
            findNavController().navigate(action)
        }
        collectState()
        return binding.root
    }
    fun collectState() {
        lifecycleScope.launch {
            viewModel.getAllRecords(
                DoctorInfo1(
                    Filter(
                        patientId = getUserId(Constants.userId),
                        type = "operation"
                    )
                ), "Bearer ${getToken(Constants.userToken)}"
            )

            viewModel.loginState.collect{
                adapter.submitList(it.Allrecords)
                binding.recycler.adapter = adapter
            }
        }
    }

    private suspend fun getToken(key: String): String? {
        dataStore = requireContext().dataStore
        val dataStoreKey: Preferences.Key<String> = stringPreferencesKey(key)
        val preference = dataStore.data.first()
        return preference[dataStoreKey]
    }

    private suspend fun getUserId(key: String): String? {
        dataStore = requireContext().dataStore
        val dataStoreKey: Preferences.Key<String> = stringPreferencesKey(key)
        val preference = dataStore.data.first()
        return preference[dataStoreKey]
    }

    override fun onItemClick(position: Int) {
        val action= OperationFragmentDirections.actionOperationFragmentToSurgeriesResults(binding.txtTitle.text.toString() ,adapter.currentList[position].id.toString())
        findNavController().navigate(action)
    }

}