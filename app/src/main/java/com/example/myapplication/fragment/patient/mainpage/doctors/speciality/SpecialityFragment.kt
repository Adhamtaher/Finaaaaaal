package com.example.myapplication.fragment.patient.mainpage.doctors.speciality

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
import com.example.myapplication.databinding.FragmentSpecialityBinding
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorInfo1
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.Filter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.ArrayList
import java.util.Locale

@AndroidEntryPoint
class SpecialityFragment : Fragment() {

    val adapter: SpecialityAdapter by lazy {
        SpecialityAdapter()
    }
    val viewModel: SpecialityViewModel by viewModels()
    private lateinit var dataStore: DataStore<Preferences>
    lateinit var specialityList: ArrayList<SpecialtyList>
    lateinit var searchView: SearchView
    lateinit var imageId: Array<Int>
    lateinit var heading: Array<String>

    private lateinit var binding: FragmentSpecialityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSpecialityBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)

        binding.searchview.clearFocus()
        binding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }

        })

        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
        collectState()

        return binding.root
    }


//    private fun filterList(query: String?) {
//        if (query != null) {
//            val filteredList = ArrayList<SpecialtyList>()
//            for (i in specialityList) {
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

    fun collectState(){
        lifecycleScope.launch {
            viewModel.getCategories(DoctorInfo1(Filter(type = "speciality")) , "Bearer ${getToken(Constants.userToken)}")

            viewModel.loginState.collect{
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