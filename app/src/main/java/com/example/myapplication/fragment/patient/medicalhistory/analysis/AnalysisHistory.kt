package com.example.myapplication.fragment.patient.medicalhistory.analysis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAnalysisHistoryBinding
import java.util.ArrayList

class AnalysisHistory : Fragment(), AnalysisHistoryAdapter.MyClickListener {

    lateinit var adapter: AnalysisHistoryAdapter
    lateinit var analysisHistoryList: ArrayList<AnalysisHistoryList>

    lateinit var imageId: Array<Int>
    lateinit var heading : Array<String>
    lateinit var time : Array<String>
    lateinit var fee : Array<String>

    private lateinit var binding: FragmentAnalysisHistoryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnalysisHistoryBinding.inflate(inflater, container, false)
        dataIntialize()
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)
        adapter = AnalysisHistoryAdapter(analysisHistoryList, this@AnalysisHistory)
        binding.recyclerView.adapter = adapter

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_analysisHistory_to_medicalHistory)
        }
        binding.upcoming.setOnClickListener {
            findNavController().navigate(R.id.action_analysisHistory_to_analysisActivity)
        }

        return binding.root
    }
    private fun dataIntialize() {
        analysisHistoryList = arrayListOf<AnalysisHistoryList>()
        imageId = arrayOf(
            R.drawable.blood2,
            R.drawable.urinalysis2
        )
        heading = arrayOf(
            getString(R.string.typeana1),
            getString(R.string.typeana2)
        )
        time = arrayOf(
            getString(R.string.day1),
            getString(R.string.day2)
        )
        fee = arrayOf(
            getString(R.string.fees1),
            getString(R.string.fees2)
        )
        for (i in imageId.indices) {
            val analysisHistory = AnalysisHistoryList(imageId[i], heading[i], time[i], fee[i])
            analysisHistoryList.add(analysisHistory)
        }
    }

    override fun onClick(position: Int) {
        when(position){

        }
    }

}
