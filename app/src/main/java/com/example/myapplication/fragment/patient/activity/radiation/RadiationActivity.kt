package com.example.myapplication.fragment.patient.activity.radiation

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
import com.example.myapplication.databinding.FragmentRadiationActivityBinding
import java.util.ArrayList

class RadiationActivity : Fragment(), RadiationActivityAdapter.MyClickListener, RadiationActivityAdapter.MyListener {

    lateinit var adapter: RadiationActivityAdapter
    lateinit var radiationActivityList: ArrayList<RadiationActivityList>

    lateinit var imageId: Array<Int>
    lateinit var heading : Array<String>
    lateinit var time : Array<String>
    lateinit var fee : Array<String>

    private lateinit var binding: FragmentRadiationActivityBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRadiationActivityBinding.inflate(inflater, container, false)
        dataIntialize()
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)
        adapter = RadiationActivityAdapter(radiationActivityList,this@RadiationActivity, this@RadiationActivity)
        binding.recyclerView.adapter = adapter

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_radiationActivity_to_activity)
        }
        binding.history.setOnClickListener {
            findNavController().navigate(R.id.action_radiationActivity_to_radFragment)
        }
        return binding.root
    }
    private fun dataIntialize() {
        radiationActivityList = arrayListOf<RadiationActivityList>()
        imageId = arrayOf(
            R.drawable.chest2
        )
        heading = arrayOf(
            getString(R.string.typerad2)
        )
        time = arrayOf(
            getString(R.string.date1)
        )
        fee = arrayOf(
            getString(R.string.fees1),
            getString(R.string.fees2)
        )
        for (i in imageId.indices) {
            val radiationActivity = RadiationActivityList(imageId[i], heading[i], time[i], fee[i])
            radiationActivityList.add(radiationActivity)
        }
    }
    override fun onClick(position: Int) {
        when(position){
            0-> findNavController().navigate(R.id.action_radiationActivity_to_radiationDetails)
            1-> findNavController().navigate(R.id.action_radiationActivity_to_radiationDetails)
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
