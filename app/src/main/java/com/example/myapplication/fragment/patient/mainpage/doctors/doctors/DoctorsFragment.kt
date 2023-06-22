package com.example.myapplication.fragment.patient.mainpage.doctors.doctors

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.common.Constants
import com.example.myapplication.common.Constants.Companion.dataStore
import com.example.myapplication.databinding.FragmentDoctorsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DoctorsFragment : Fragment() {
    val args : DoctorsFragmentArgs by navArgs()
    val adapter: DoctorsAdapter by lazy {

        DoctorsAdapter(args.speciality)
    }
    lateinit var searchView: SearchView
    val viewModel by viewModels<DoctorsViewModel>()
    private lateinit var dataStore: DataStore<Preferences>


    private lateinit var binding: FragmentDoctorsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDoctorsBinding.inflate(inflater, container, false)


        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)

        collectState()
        binding.searchview.clearFocus()
        binding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                filterList(newText)
                return true
            }

        })

        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        return binding.root
    }


//    private fun filterList(query: String?) {
//        if (query != null) {
//            val filteredList = ArrayList<DoctorsList>()
//            for (i in doctorList) {
//                if (i.heading.lowercase(Locale.ROOT).contains(query)) {
//                    filteredList.add(i)
//                }
//            }
//            if (filteredList.isEmpty()) {
//                Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show()
//            } else {
//                adapter.setFilteredList(filteredList)
//
//            }
//        }
//    }





    private fun collectState(){
        lifecycleScope.launch {
            viewModel.getAllDoctors(DoctorInfo1(Filter(doctorInfoSpeciality = args.speciality)))

            viewModel.loginState.collect{
                adapter.submitList(it.allDoctors)
                binding.recyclerView.adapter = adapter
                binding.progress.isVisible = it.isLoading
                if (it.success != null) {
                   binding.constraintLayout.isVisible = true
                }
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