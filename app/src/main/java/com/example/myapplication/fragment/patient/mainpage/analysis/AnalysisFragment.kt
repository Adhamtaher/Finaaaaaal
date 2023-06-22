package com.example.myapplication.fragment.patient.mainpage.analysis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
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
import com.example.myapplication.databinding.FragmentAnalysisBinding
import com.example.myapplication.fragment.mainpage.doctors.speciality.AnalysisList
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorInfo1
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.Filter
import com.example.myapplication.fragment.patient.mainpage.examinations.RadiationAdapter
import com.example.myapplication.fragment.patient.mainpage.examinations.RadiationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.ArrayList
import java.util.Locale

@AndroidEntryPoint
class AnalysisFragment : Fragment() {


    lateinit var analysisList: ArrayList<AnalysisList>
    private lateinit var dataStore: DataStore<Preferences>

    val viewModel: AnalysisViewModel by viewModels()
    val adapter: AnalysisAdapter by lazy {
        AnalysisAdapter()
    }

    private lateinit var binding: FragmentAnalysisBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnalysisBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)

        collectState()

//        binding.searchview.clearFocus()
//        binding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                filterList(newText)
//                return true
//            }
//
//        })

        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        return binding.root
    }


    //    private fun filterList(query: String?){
//        if (query != null){
//            val filteredList = ArrayList<AnalysisList>()
//            for (i in analysisList){
//                if (i.heading.lowercase(Locale.ROOT).contains(query)){
//                    filteredList.add(i)
//                }
//            }
//            if (filteredList.isEmpty()){
//                Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show()
//            }
//            else{
//                adapter.setFilteredList(filteredList)
//
//            }
//        }
//    }
    private fun collectState() {
        lifecycleScope.launch {
            viewModel.getCategories(
                DoctorInfo1(Filter(type = "lab")),
                "Bearer ${getToken(Constants.userToken)}"

            )

            viewModel.loginState.collect {
                adapter.submitList(it.allCategories)
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

}