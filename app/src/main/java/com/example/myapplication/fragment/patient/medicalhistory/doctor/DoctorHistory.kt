package com.example.myapplication.fragment.patient.medicalhistory.doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDoctorHistoryBinding
import java.util.ArrayList

class DoctorHistory : Fragment(), DoctorHistoryAdapter.MyClickListener {

    lateinit var adapter: DoctorHistoryAdapter
    lateinit var doctorHistoryList: ArrayList<DoctorHistoryList>

    lateinit var imageId: Array<Int>
    lateinit var heading : Array<String>
    lateinit var time : Array<String>
    lateinit var fee : Array<String>
    lateinit var spec : Array<String>

    private lateinit var binding: FragmentDoctorHistoryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDoctorHistoryBinding.inflate(inflater, container, false)
        dataIntialize()
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)
        adapter = DoctorHistoryAdapter(doctorHistoryList, this@DoctorHistory)
        binding.recyclerView.adapter = adapter

        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.upcoming.setOnClickListener {
            findNavController().navigate(R.id.action_doctorHistory_to_doctorActivity)
        }
        return binding.root
    }
    private fun dataIntialize() {
        doctorHistoryList = arrayListOf<DoctorHistoryList>()
        imageId = arrayOf(
            R.drawable.smilingdoctor,
            R.drawable.smilingdoctor
        )
        heading = arrayOf(
            getString(R.string.dr1),
            getString(R.string.dr2)
        )
        time = arrayOf(
            getString(R.string.last_visit_1),
            getString(R.string.last_visit_2)
        )
        fee = arrayOf(
            getString(R.string.fees1),
            getString(R.string.fees2)
        )
        spec = arrayOf(
            getString(R.string.specialty1),
            getString(R.string.specialty2)
        )
        for (i in imageId.indices) {
            val doctorsHistory = DoctorHistoryList(imageId[i], heading[i], time[i], fee[i], spec[i])
            doctorHistoryList.add(doctorsHistory)
        }
    }
    override fun onClick(position: Int) {
        when(position){
            0-> findNavController().navigate(R.id.action_doctorHistory_to_doctorDetalisHistory)
            1-> findNavController().navigate(R.id.action_doctorHistory_to_doctorDetalisHistory)
        }
    }
}
