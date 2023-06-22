package com.example.myapplication.fragment.patient.medicalhistory.surgeries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSurgeriesHistoryBinding
import java.util.ArrayList

class SurgeriesHistory : Fragment(), SurgeriesHistoryAdapter.MyClickListener {

    lateinit var adapter: SurgeriesHistoryAdapter
    lateinit var surgeriesHistoryList: ArrayList<SurgeriesHistoryList>

    lateinit var imageId: Array<Int>
    lateinit var heading : Array<String>
    lateinit var time : Array<String>
    lateinit var fee : Array<String>

    private lateinit var binding: FragmentSurgeriesHistoryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSurgeriesHistoryBinding.inflate(inflater, container, false)
        dataIntialize()
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)
        adapter = SurgeriesHistoryAdapter(surgeriesHistoryList, this@SurgeriesHistory)
        binding.recyclerView.adapter = adapter

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_surgeriesHistory_to_medicalHistory)
        }

        return binding.root
    }
    private fun dataIntialize() {
        surgeriesHistoryList = arrayListOf<SurgeriesHistoryList>()
        imageId = arrayOf(
            R.drawable.bone2,
            R.drawable.heartsurgery
        )
        heading = arrayOf(
            getString(R.string.typesur1),
            getString(R.string.typesur2)
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
            val surgeriesHistory = SurgeriesHistoryList(imageId[i], heading[i], time[i], fee[i])
            surgeriesHistoryList.add(surgeriesHistory)
        }
    }
    override fun onClick(position: Int) {
        when(position){

        }
    }
}
