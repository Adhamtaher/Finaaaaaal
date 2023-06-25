package com.example.myapplication.fragment.patient.activity.doctor

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
import com.example.myapplication.databinding.FragmentDoctorActivityBinding
import java.util.ArrayList

class DoctorActivity : Fragment(), DoctorActivityAdapter.MyClickListener, DoctorActivityAdapter.MyListener {

    lateinit var adapter: DoctorActivityAdapter
    lateinit var doctorActivityList: ArrayList<DoctorActivityList>

    lateinit var imageId: Array<Int>
    lateinit var heading : Array<String>
    lateinit var time : Array<String>
    lateinit var fee : Array<String>
    lateinit var spec : Array<String>

    private lateinit var binding: FragmentDoctorActivityBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDoctorActivityBinding.inflate(inflater, container, false)
        dataIntialize()
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)
        adapter = DoctorActivityAdapter(doctorActivityList,this@DoctorActivity, this@DoctorActivity)
        binding.recyclerView.adapter = adapter

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_doctorActivity_to_activity)
        }
        binding.history.setOnClickListener {
            findNavController().navigate(R.id.action_doctorActivity_to_doctorHistory)
        }

        return binding.root
    }
    private fun dataIntialize() {
        doctorActivityList = arrayListOf<DoctorActivityList>()
        imageId = arrayOf(
            R.drawable.smilingdoctor
        )
        heading = arrayOf(
            getString(R.string.dr1)
        )
        time = arrayOf(
            getString(R.string.next_visit_1)
        )
        fee = arrayOf(
            getString(R.string.fees1)
        )
        spec = arrayOf(
            getString(R.string.specialty1)
        )
        for (i in imageId.indices) {
            val doctorsActivity = DoctorActivityList(imageId[i], heading[i], time[i], fee[i], spec[i])
            doctorActivityList.add(doctorsActivity)
    }
}
    override fun onClick(position: Int) {
        when(position){
            0-> findNavController().navigate(R.id.action_doctorActivity_to_doctorDetailsActivity)
            1-> findNavController().navigate(R.id.action_doctorActivity_to_doctorDetailsActivity)
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
