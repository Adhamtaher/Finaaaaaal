package com.example.myapplication.fragment.patient.activity.doctor

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDoctorDetailsActivityBinding
import com.example.myapplication.fragment.patient.medicalhistory.doctor.DoctorHistoryAdapter
import com.example.myapplication.fragment.patient.medicalhistory.doctor.DoctorHistoryList
import java.util.ArrayList

class DoctorDetailsActivity : Fragment() {
    private lateinit var binding: FragmentDoctorDetailsActivityBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDoctorDetailsActivityBinding.inflate(inflater, container, false)

        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.cancel.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setView(R.layout.alertbox1)
            val dialog = builder.create()
            dialog.show()
            dialog.findViewById<Button>(R.id.btn_yes)?.setOnClickListener {
                dialog.dismiss()
                findNavController().navigate(R.id.action_doctorDetailsActivity_to_mainPagePatient)

                Toast.makeText(context, "Appointment cancelled", Toast.LENGTH_SHORT).show()
            }
            dialog.findViewById<Button>(R.id.btn_cancel)?.setOnClickListener {
                // Do something when the user clicks No button
                dialog.dismiss()
            }
        }

        return binding.root
    }
}