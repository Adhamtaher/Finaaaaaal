package com.example.myapplication.fragment.patient.medicalhistory.medicalcondition

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMedicalConditionBinding

class MedicalConditionFragment : Fragment() {

    private lateinit var binding: FragmentMedicalConditionBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMedicalConditionBinding.inflate(inflater, container, false)

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_medicalCondition_to_mainPagePatient2)
        }
        binding.diagnosis.setOnClickListener {
            findNavController().navigate(R.id.action_medicalCondition_to_diagnosis2)
        }
        binding.medicines.setOnClickListener {
            findNavController().navigate(R.id.action_medicalCondition_to_medicationFragment)
        }
        binding.btnLab.setOnClickListener {
            findNavController().navigate(R.id.action_medicalCondition_to_labFragment)
        }
        binding.btnRad.setOnClickListener {
            findNavController().navigate(R.id.action_medicalCondition_to_radFragment)
        }

        binding.btnOperation.setOnClickListener {
            findNavController().navigate(R.id.action_medicalCondition_to_operationFragment)
        }


        return binding.root
    }
}