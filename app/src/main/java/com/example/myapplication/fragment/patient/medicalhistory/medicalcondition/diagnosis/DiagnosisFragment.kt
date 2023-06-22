package com.example.myapplication.fragment.patient.medicalhistory.medicalcondition.diagnosis

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.common.Constants
import com.example.myapplication.common.Constants.Companion.dataStore
import com.example.myapplication.databinding.FragmentDiagnosisBinding
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorInfo1
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.Filter
import com.example.myapplication.fragment.patient.medicalhistory.medicalcondition.MedicalRecordViewModel
import com.example.myapplication.fragment.patient.medicalhistory.medicalcondition.lab.LabFragmentDirections
import com.example.myapplication.fragment.patient.medicalhistory.radiation.RadiationHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.ArrayList

@AndroidEntryPoint
class DiagnosisFragment : Fragment(), DiagnosisAdapter.OnItemClickListener {


    lateinit var diagnosisList: ArrayList<DiagnosisList>

    lateinit var adapter: DiagnosisAdapter


    val viewModel: MedicalRecordViewModel by viewModels()
    private lateinit var dataStore: DataStore<Preferences>

    lateinit var type: Array<String>
    lateinit var time: Array<String>
    lateinit var chronic: Array<String>
    lateinit var active: Array<String>

    private lateinit var binding: FragmentDiagnosisBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiagnosisBinding.inflate(inflater, container, false)
//        dataIntialize()
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)
        adapter= DiagnosisAdapter(this)

        binding.btnAdd.setOnClickListener {
            val action =
                DiagnosisFragmentDirections.actionDiagnosis2ToMedicalRecordAddFragment(binding.textView6.text.toString())
            findNavController().navigate(action)
        }

        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
        collectState()
        return binding.root
    }

    private fun dataIntialize() {
        diagnosisList = arrayListOf<DiagnosisList>()
        type = arrayOf(
            getString(R.string.diagnosis1),
            getString(R.string.diagnosis2)
        )
        time = arrayOf(
            getString(R.string.diagnose_date1),
            getString(R.string.diagnose_date2)
        )
        chronic = arrayOf(
            getString(R.string.chronic1),
            getString(R.string.chronic2)
        )
        active = arrayOf(
            getString(R.string.active),
            getString(R.string.active2)
        )
        for (i in type.indices) {
            val diagnosis = DiagnosisList(type[i], time[i], chronic[i], active[i])
            diagnosisList.add(diagnosis)
        }
    }

    fun collectState() {
        lifecycleScope.launch {
            viewModel.getAllRecords(
                DoctorInfo1(
                    Filter(
                        patientId = getUserId(Constants.userId),
                        type = "diagnose"
                    )
                ), "Bearer ${getToken(Constants.userToken)}"
            )

            viewModel.loginState.collect {
                adapter.submitList(it.Allrecords)
                binding.recyclerView.adapter = adapter
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
        val action= DiagnosisFragmentDirections.actionDiagnosis2ToDiagnosisDetails(adapter.currentList[position].id.toString())
        findNavController().navigate(action)
    }
}
