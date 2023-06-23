package com.example.myapplication.fragment.doctor

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMainPageDoctorBinding
import com.example.myapplication.databinding.FragmentMainPagePatientBinding
import java.text.DateFormat
import java.util.Calendar

class MainPageDoctor : Fragment() {

    private lateinit var binding: FragmentMainPageDoctorBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainPageDoctorBinding.inflate(inflater, container, false)

        val calendar = Calendar.getInstance().time
        val dateFormat = DateFormat.getDateInstance(DateFormat.LONG).format(calendar)
        binding.textViewDate.text = dateFormat

        binding.pxandreport.setOnClickListener {
            findNavController().navigate(R.id.action_mainPageDoctor_to_prescription)
        }
        binding.schedule.setOnClickListener {
            findNavController().navigate(R.id.action_mainPageDoctor_to_shedule)
        }
        binding.patients.setOnClickListener {
            findNavController().navigate(R.id.action_mainPageDoctor_to_patients2)
        }
        binding.surgeries.setOnClickListener {
            findNavController().navigate(R.id.action_mainPageDoctor_to_surgeriesTypes)
        }
        binding.ordermedicine.setOnClickListener {
            findNavController().navigate(R.id.action_mainPageDoctor_to_medicineTypes)
        }
        val phoneNumber = "01011498307"
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:$phoneNumber")

        binding.phonecall.setOnClickListener {
            startActivity(dialIntent)
        }

        return binding.root
    }
}