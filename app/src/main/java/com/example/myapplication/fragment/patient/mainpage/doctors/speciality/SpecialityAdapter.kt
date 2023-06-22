package com.example.myapplication.fragment.patient.mainpage.doctors.speciality

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSpecialtyListBinding
import com.example.myapplication.fragment.patient.medicalhistory.medicalcondition.RecordsItem
import com.example.myapplication.fragment.patient.mainpage.examinations.Result

class SpecialityAdapter() :
    ListAdapter<Result, SpecialityAdapter.MyView>(DiffCallBack()) {


    class DiffCallBack : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem._id == newItem._id
        }
    }

    inner class MyView(val itemBinding: FragmentSpecialtyListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {


        fun bind(item: Result) {
            itemBinding.specialityName.text = item.name
            itemBinding.specialityImage.setImageResource(R.drawable.dermatology)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        return MyView(
            FragmentSpecialtyListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.bind(getItem(position))
        holder.itemBinding.root.setOnClickListener {
            val action = SpecialityFragmentDirections.actionSpecialityToDoctors(getItem(position).name!!)
            it.findNavController().navigate(action)
        }

    }

}

