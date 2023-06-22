package com.example.myapplication.fragment.patient.medicalhistory.radiation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRadiationHistoryBinding
import java.util.ArrayList

class RadiationHistoryFragment : Fragment() {

    lateinit var adapter: RadiationHistoryAdapter
//    lateinit var radiationHistoryList: ArrayList<RadiationHistoryList>

    lateinit var imageId: Array<Int>
    lateinit var heading : Array<String>
    lateinit var time : Array<String>
    lateinit var fee : Array<String>

    private lateinit var binding: FragmentRadiationHistoryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRadiationHistoryBinding.inflate(inflater, container, false)
//        dataIntialize()
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)
//        adapter = RadiationHistoryAdapter(radiationHistoryList)
        binding.recyclerView.adapter = adapter

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_radiationHistory_to_medicalHistory)
        }
        binding.upcoming.setOnClickListener {
            findNavController().navigate(R.id.action_radiationHistory_to_radiationActivity)
        }
        return binding.root
    }
//    private fun dataIntialize() {
//        radiationHistoryList = arrayListOf<RadiationHistoryList>()
//        imageId = arrayOf(
//            R.drawable.bone2,
//            R.drawable.chest2
//        )
//        heading = arrayOf(
//            getString(R.string.typerad1),
//            getString(R.string.typerad2)
//        )
//        time = arrayOf(
//            getString(R.string.day1),
//            getString(R.string.day2)
//        )
//        fee = arrayOf(
//            getString(R.string.fees1),
//            getString(R.string.fees2)
//        )
//        for (i in imageId.indices) {
//            val radiationHistory = RadiationHistoryList(imageId[i], heading[i], time[i], fee[i])
//            radiationHistoryList.add(radiationHistory)
//        }
//    }

}
