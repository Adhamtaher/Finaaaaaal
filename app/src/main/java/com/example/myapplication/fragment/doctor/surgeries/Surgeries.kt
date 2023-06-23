package com.example.myapplication.fragment.doctor.surgeries

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentPatientsBinding
import com.example.myapplication.databinding.FragmentSurgeriesBinding
import com.example.myapplication.fragment.doctor.schedule.PatientAdapter
import com.example.myapplication.fragment.doctor.schedule.PatientList
import java.util.ArrayList
import java.util.Locale

class Surgeries : Fragment(), SurgeriesAdapter.MyClickListener, SurgeriesAdapter.MyListener {


    lateinit var adapter: SurgeriesAdapter
    lateinit var surgeriesList: ArrayList<SurgeriesList>
    lateinit var searchView: SearchView

    lateinit var date: Array<String>
    lateinit var imageId: Array<Int>
    lateinit var heading: Array<String>
    lateinit var time: Array<String>
    lateinit var last: Array<String>
    lateinit var diagnosis: Array<String>

    private lateinit var binding: FragmentSurgeriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSurgeriesBinding.inflate(inflater, container, false)
        dataIntialize()

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)
        adapter = SurgeriesAdapter(surgeriesList, this@Surgeries, this@Surgeries)
        binding.recyclerView.adapter = adapter


        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_surgeries_to_mainPageDoctor)
        }

        return binding.root
    }

    private fun dataIntialize() {
        surgeriesList = arrayListOf<SurgeriesList>()
        imageId = arrayOf(
            R.drawable.paitent,
            R.drawable.paitent
        )
        heading = arrayOf(
            getString(R.string.pat1),
            getString(R.string.pat2)
        )
        time = arrayOf(
            getString(R.string.operation_time1),
            getString(R.string.operation_time2)
        )
        last = arrayOf(
            getString(R.string.last_visit_1),
            getString(R.string.last_visit_2)
        )
        diagnosis = arrayOf(
            getString(R.string.operation1),
            getString(R.string.operation2)
        )

        for (i in imageId.indices) {
            val surgeries = SurgeriesList(imageId[i], heading[i], time[i], last[i], diagnosis[i])
            surgeriesList.add(surgeries)
        }
    }

    override fun onClickSur(position: Int) {
        findNavController().navigate(R.id.action_surgeries_to_patientDetails)
    }

    override fun onDeleteSur(position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setView(R.layout.alertbox1)
        val dialog = builder.create()
        dialog.show()
        dialog.findViewById<Button>(R.id.btn_yes)?.setOnClickListener {
            adapter.removeItem(position)
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.btn_cancel)?.setOnClickListener {
            // Do something when the user clicks No button
            dialog.dismiss()
        }
    }
}