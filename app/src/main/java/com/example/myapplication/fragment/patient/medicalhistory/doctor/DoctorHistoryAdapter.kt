package com.example.myapplication.fragment.patient.medicalhistory.doctor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentDoctorHistoryListBinding

class DoctorHistoryAdapter(private val doctorsHistoryList: ArrayList<DoctorHistoryList>, val listener: DoctorHistoryAdapter.MyClickListener) :
    RecyclerView.Adapter<DoctorHistoryAdapter.MyView>() {

    inner class MyView(val itemBinding: FragmentDoctorHistoryListBinding): RecyclerView.ViewHolder(itemBinding.root){
        init {
            itemBinding.details.setOnClickListener {
                val position = adapterPosition
                listener.onClick(position)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        return MyView(FragmentDoctorHistoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return doctorsHistoryList.size
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.itemBinding.doctorImage.setImageResource(doctorsHistoryList[position].titleImage)
        holder.itemBinding.doctorName.text = doctorsHistoryList[position].heading
        holder.itemBinding.lastvisit.text = doctorsHistoryList[position].time
        holder.itemBinding.fees.text = doctorsHistoryList[position].fee
        holder.itemBinding.speciality.text = doctorsHistoryList[position].spec
    }
    interface MyClickListener{
        fun onClick(position: Int)
    }
}