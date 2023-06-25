package com.example.myapplication.fragment.patient.activity.analysis

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAnalysisActivityBinding
import java.util.ArrayList

class AnalysisActivity : Fragment(), AnalysisActivityAdapter.MyClickListener, AnalysisActivityAdapter.MyListener  {

    lateinit var adapter: AnalysisActivityAdapter
    lateinit var analysisActivityList: ArrayList<AnlaysisActivityList>

    lateinit var imageId: Array<Int>
    lateinit var heading : Array<String>
    lateinit var time : Array<String>
    lateinit var fee : Array<String>

    private lateinit var binding: FragmentAnalysisActivityBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnalysisActivityBinding.inflate(inflater, container, false)
        dataIntialize()
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)
        adapter = AnalysisActivityAdapter(analysisActivityList, this@AnalysisActivity, this@AnalysisActivity)
        binding.recyclerView.adapter = adapter

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_analysisActivity_to_activity)
        }
        binding.history.setOnClickListener {
            findNavController().navigate(R.id.action_analysisActivity_to_labFragment)
        }
        return binding.root
    }
    private fun dataIntialize() {
        analysisActivityList = arrayListOf<AnlaysisActivityList>()
        imageId = arrayOf(
            R.drawable.blood2
        )
        heading = arrayOf(
            getString(R.string.typeana1)
        )
        time = arrayOf(
            getString(R.string.date1)
        )
        fee = arrayOf(
            getString(R.string.fees3)
        )
        for (i in imageId.indices) {
            val analysisActivity = AnlaysisActivityList(imageId[i], heading[i], time[i], fee[i])
            analysisActivityList.add(analysisActivity)
        }
    }
    override fun onClick(position: Int) {
        when(position){
            0-> findNavController().navigate(R.id.action_analysisActivity_to_analysisDetails)
            1-> findNavController().navigate(R.id.action_analysisActivity_to_analysisDetails)
        }
    }
    override fun onDelete(position: Int) {
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
