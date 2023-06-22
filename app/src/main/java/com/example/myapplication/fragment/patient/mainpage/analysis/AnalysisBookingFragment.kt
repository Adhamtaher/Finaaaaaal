package com.example.myapplication.fragment.patient.mainpage.analysis

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.common.Constants
import com.example.myapplication.common.Constants.Companion.dataStore
import com.example.myapplication.databinding.FragmentAnalysisBookingBinding
import com.example.myapplication.fragment.main.domain.ReserveInfo
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorInfo1
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.Filter
import com.example.myapplication.fragment.patient.mainpage.examinations.Products
import com.example.myapplication.fragment.patient.mainpage.examinations.RadiationBookingFragmentArgs
import com.example.myapplication.fragment.patient.mainpage.examinations.RadiationBookingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AnalysisBookingFragment : Fragment() {
    private lateinit var binding: FragmentAnalysisBookingBinding
    val args: AnalysisBookingFragmentArgs by navArgs()
    val viewModel: AnalysisBookingViewModel by viewModels()
    private lateinit var dataStore: DataStore<Preferences>
    var product : Products? = null
    var userName :String? = null
    val myCalender = Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnalysisBookingBinding.inflate(inflater, container, false)

        binding.username.isEnabled = false
        binding.phonenumber.isEnabled = false
        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            binding.username.isEnabled = isChecked
            binding.phonenumber.isEnabled = isChecked
        }

        val datePicker = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
            myCalender.set(Calendar.YEAR, year)
            myCalender.set(Calendar.MONTH, month)
            myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "dd-MM-yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            binding.datepick2.text = sdf.format(myCalender.time)
        }
        binding.datepick2.setOnClickListener {
            DatePickerDialog(requireContext(), datePicker, myCalender.get(Calendar.YEAR), myCalender.get(
                Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show()
        }
        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.confirm.setOnClickListener {
            collectReserveState()
            Log.e( "onCreateView: ",SimpleDateFormat("dd-MM-yyyy", Locale.US).format(myCalender.time) )
        }
        collectState()
        collectUserState()
        return binding.root
    }

    private fun collectState(){
        lifecycleScope.launch {
            viewModel.getProducts(
                DoctorInfo1(Filter(categoryId = args.id , available = true)) ,"Bearer ${getToken(
                    Constants.userToken)}")

            viewModel.loginState.collect{
                if (it.allProducts?.isNotEmpty() == true){
                    fillSpinner(it.allProducts)

                    binding.spinner.onItemSelectedListener = object :android.widget.AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: View?, position: Int, id: Long) {
                            product = it.allProducts[position]
                            binding.txtFees.text = product?.price.toString()
                        }

                        override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {
                            product = it.allProducts[0]
                            binding.txtFees.text = product?.price.toString()
                        }

                    }
                }


            }

        }
    }
    private fun collectUserState() {
        lifecycleScope.launch {
            viewModel.getUser(DoctorInfo1(Filter(_id = getUserId(Constants.userId))))

            viewModel.userState.collect{
                if (it.doctorInfo?.name!=null){
                    userName = it.doctorInfo.name
                    Log.e( "collectUserState: ",userName.toString() )
                }


            }
        }


    }

    private fun collectReserveState(){
        lifecycleScope.launch {
            viewModel.reserves(
                ReserveInfo(
                patName = getName(),
                type = "lab",
                productName = product?.name.toString(),
                fees = product?.price.toString(),
                date = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(myCalender.time),
                anotherPerson = binding.checkBox.isChecked,
                productId = product?._id.toString()

            ), "Bearer ${getToken(Constants.userToken)}")
            viewModel.reserveState.collect{
                Log.e( "collectReserveState: ",it.success.toString() )
                if (it.success!=null){
                    Toast.makeText(requireContext(), it.success, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_analysisBooking_to_mainPagePatient)
                }
                if (it.error!=null){
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                }
                binding.progress.isVisible = it.isLoading
            }
        }
    }

    private fun getName():String{
        if (binding.username.text.isBlank()){
            return userName.toString()
        }
        return binding.username.text.toString()
    }

    private suspend fun getToken(key: String): String? {
        dataStore = requireContext().dataStore
        val dataStoreKey: Preferences.Key<String> = stringPreferencesKey(key)
        val preference = dataStore.data.first()
        return preference[dataStoreKey]
    }

    private fun fillSpinner(data:List<Products>){
        val adapter = ArrayAdapter(requireContext() , android.R.layout.simple_spinner_item, data.map { it.name })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
    }

    private suspend fun getUserId(key: String): String? {
        dataStore = requireContext().dataStore
        val dataStoreKey: Preferences.Key<String> = stringPreferencesKey(key)
        val preference = dataStore.data.first()
        return preference[dataStoreKey]
    }
}