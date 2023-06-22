package com.example.myapplication.fragment.patient.mainpage.doctors.doctors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDoctorsListBinding
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorsFragmentDirections

class DoctorsAdapter(private val args :String) :
    ListAdapter<DoctorItem, DoctorsAdapter.MyView>(DiffCallBack()) {

    class DiffCallBack : DiffUtil.ItemCallback<DoctorItem>() {
        override fun areItemsTheSame(oldItem: DoctorItem, newItem: DoctorItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DoctorItem, newItem: DoctorItem): Boolean {
            return oldItem.id == newItem.id
        }
    }

    inner class MyView(val itemBinding: FragmentDoctorsListBinding): RecyclerView.ViewHolder(itemBinding.root){


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        return MyView(FragmentDoctorsListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }



    override fun onBindViewHolder(holder: MyView, position: Int) {

        holder.itemBinding.doctorName.text = getItem(position).name
        holder.itemBinding.nextvisit.text = "Waiting time: ${position +45}minutes"
        holder.itemBinding.fees.text = getItem(position).doctorInfo?.fees?.examin.toString()
        holder.itemBinding.speciality.text = "Availability ${getItem(position).doctorInfo?.available.toString()}"
        Glide.with(holder.itemBinding.root).load(getItem(position).image).placeholder(R.drawable.smilingdoctor).into(holder.itemBinding.doctorImage)

        holder.itemBinding.book.setOnClickListener {

            val action = DoctorsFragmentDirections.actionDoctorsToBooking(getItem(position).id.toString()  , args)
            it.findNavController().navigate(action)





        }


    }

}