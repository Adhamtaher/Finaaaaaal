package com.example.myapplication.fragment.patient.activity.surgeries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSurgeriesActivityBinding
import java.util.ArrayList

class SurgeriesActivity : Fragment(), SurgeriesActivityAdapter.MyClickListener {

    lateinit var adapter: SurgeriesActivityAdapter
    lateinit var surgeriesActivityList: ArrayList<SurgeriesActivityList>

    lateinit var imageId: Array<Int>
    lateinit var heading : Array<String>
    lateinit var time : Array<String>
    lateinit var fee : Array<String>

    private lateinit var binding: FragmentSurgeriesActivityBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSurgeriesActivityBinding.inflate(inflater, container, false)
        dataIntialize()
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)
        adapter = SurgeriesActivityAdapter(surgeriesActivityList, this@SurgeriesActivity)
        binding.recyclerView.adapter = adapter

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_surgeriesActivity_to_activity)
        }

        return binding.root
    }
    private fun dataIntialize() {
        surgeriesActivityList = arrayListOf<SurgeriesActivityList>()
        imageId = arrayOf(
            R.drawable.heartsurgery
        )
        heading = arrayOf(
            getString(R.string.typesur2)
        )
        time = arrayOf(
            getString(R.string.date2)
        )
        fee = arrayOf(
            "5000"
        )
        for (i in imageId.indices) {
            val surgeriesActivity = SurgeriesActivityList(imageId[i], heading[i], time[i], fee[i])
            surgeriesActivityList.add(surgeriesActivity)
        }
    }
    override fun onClick(position: Int) {
        when(position){
            0-> findNavController().navigate(R.id.action_surgeriesActivity_to_surgeriesDetails)
            1-> findNavController().navigate(R.id.action_surgeriesActivity_to_surgeriesDetails)
        }
    }

}
