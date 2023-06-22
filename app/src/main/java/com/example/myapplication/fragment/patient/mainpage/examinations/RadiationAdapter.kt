package com.example.myapplication.fragment.patient.mainpage.examinations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRadiationListBinding
import com.example.myapplication.databinding.FragmentSpecialtyListBinding
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorItem
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorsAdapter

class RadiationAdapter :
    ListAdapter<Result, RadiationAdapter.MyView>(DiffCallBack()) {

    class DiffCallBack : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem._id == newItem._id
        }
    }
    inner class MyView(val itemBinding: FragmentRadiationListBinding): RecyclerView.ViewHolder(itemBinding.root){




    }
//    fun setFilteredList(radiationList: java.util.ArrayList<RadiationList>){
//        this.radiationList = radiationList
//        notifyDataSetChanged()
//
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        return MyView(FragmentRadiationListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.itemBinding.radImage.setImageResource(R.drawable.abdominal)
        holder.itemBinding.radName.text = getItem(position).name
        holder.itemBinding.root.setOnClickListener {
            val action = RadiationsFragmentDirections.actionRadiationsToRadiationBooking(getItem(position)._id.toString())
            it.findNavController().navigate(action)
        }

    }

}

