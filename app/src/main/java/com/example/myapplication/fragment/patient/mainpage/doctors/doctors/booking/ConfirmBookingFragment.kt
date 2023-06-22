package com.example.myapplication.fragment.patient.mainpage.doctors.doctors.booking

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.common.Constants
import com.example.myapplication.common.Constants.Companion.dataStore
import com.example.myapplication.databinding.FragmentConfirmBookingBinding
import com.example.myapplication.fragment.main.domain.ReserveInfo
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.ConfirmBookingViewModel
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorInfo1
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.Filter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.abs

@AndroidEntryPoint
class ConfirmBookingFragment : Fragment() {
    private lateinit var binding: FragmentConfirmBookingBinding
    val viewModel: ConfirmBookingViewModel by viewModels()
    val args: ConfirmBookingFragmentArgs by navArgs()
    private lateinit var dataStore: DataStore<Preferences>
    var docName: String? = null
    var userName :String? = null
    var fees :String? = null
    var hashMap: HashMap<String, Int> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmBookingBinding.inflate(inflater, container, false)

        binding.username.isEnabled = false
        binding.phonenumber.isEnabled = false

        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            binding.username.isEnabled = isChecked
            binding.phonenumber.isEnabled = isChecked
        }

        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.confirm.setOnClickListener {
            collectReserveState()

        }
        putHashMap()
        collectDoctorState()
        collectUserState()

        Log.e( "onCreateView: ",Calendar.FRIDAY.toString() )
        Log.e( "onCreateView: ",getNearestDayOfWeek(hashMap[args.schedule.day]!!) )

        return binding.root
    }

    private fun collectDoctorState() {
        lifecycleScope.launch {
            viewModel.getDoctor(DoctorInfo1(Filter(_id = args.id)))

            viewModel.loginState.collect{
                binding.doctorName.text = it.doctorInfo?.name
                Glide.with(requireContext()).load(it.doctorInfo?.image).into(binding.doctorImage)
                binding.txtSpecialty.text = "Specialty: ${args.specialty}"
                docName = it.doctorInfo?.name
                fees = it.doctorInfo?.doctorInfo?.fees?.examin?.toString()
                binding.txtFees.text = "${fees} EGP"
                binding.txtDate.text = "${args.schedule.day} ${getNearestDayOfWeek(hashMap[args.schedule.day]!!)} ${args.schedule.time?.from}"
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
                binding.progress.isVisible = it.isLoading

            }
        }


    }

    private fun collectReserveState() {
        lifecycleScope.launch {
            viewModel.reverse(
                ReserveInfo(
                    patName = getName(),
                    docName = docName,
                    type = "doctor",
                    visitType = "examination",
                    anotherPerson = binding.checkBox.isChecked,
                    speciality = args.specialty,
                    date = getNearestDayOfWeek(hashMap[args.schedule.day]!!),
                    day = args.schedule.day,
                    fees = fees,
                    doctorId = args.id,
                    patientId = getUserId(Constants.userId)
                ),
                "Bearer ${getToken(Constants.userToken)}"
            )

            viewModel.reverseState.collect{
                if (it.success!=null){
                    val action = ConfirmBookingFragmentDirections.actionConfirmBookingToConfirmRound(it.reserveInfo?.turnNum!!)
                    findNavController().navigate(action)
                    Toast.makeText(requireContext(), it.success, Toast.LENGTH_SHORT).show()
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

    private suspend fun getUserId(key: String): String? {
        dataStore = requireContext().dataStore
        val dataStoreKey: Preferences.Key<String> = stringPreferencesKey(key)
        val preference = dataStore.data.first()
        return preference[dataStoreKey]
    }

    private suspend fun getToken(key: String): String? {
        dataStore = requireContext().dataStore
        val dataStoreKey: Preferences.Key<String> = stringPreferencesKey(key)
        val preference = dataStore.data.first()
        return preference[dataStoreKey]
    }

    private fun getNearestDayOfWeek(targetDayOfWeek: Int): String {
        val calendar = Calendar.getInstance()
        val currentDate = calendar.time

        // Find the next target day of the week
        while (calendar.get(Calendar.DAY_OF_WEEK) != targetDayOfWeek) {
            calendar.add(Calendar.DAY_OF_WEEK, 1)
        }
        val nextTargetDay = calendar.time



        // Compare the differences between current date and the nearest target days
        val differenceToNext = abs(nextTargetDay.time - currentDate.time)


        // Determine the nearest target day
        val nearestTargetDay = if (differenceToNext == 0L) {
            calendar.add(Calendar.DAY_OF_WEEK, 7)
            calendar.time
        } else {
            nextTargetDay
        }

        // Format the nearest target day as "dd-mm-yyyy"
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return formatter.format(nearestTargetDay)
    }

    private fun putHashMap(){
        hashMap["saturday"] = Calendar.SATURDAY
        hashMap["sunday"] = Calendar.SUNDAY
        hashMap["monday"] = Calendar.MONDAY
        hashMap["tuesday"] = Calendar.TUESDAY
        hashMap["wednesday"] = Calendar.WEDNESDAY
        hashMap["thursday"] = Calendar.THURSDAY
        hashMap["friday"] = Calendar.FRIDAY
    }


}

