package com.example.myapplication.fragment.patient.mainpage.firstaid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFirstAidListBinding
import com.example.myapplication.databinding.FragmentRadiationListBinding
import com.example.myapplication.databinding.FragmentSpecialtyListBinding
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorItem
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorsAdapter

class FirstAidAdapter :
    ListAdapter<AidsItem, FirstAidAdapter.MyView>(DiffCallBack()) {

    class DiffCallBack : DiffUtil.ItemCallback<AidsItem>() {
        override fun areItemsTheSame(oldItem: AidsItem, newItem: AidsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AidsItem, newItem: AidsItem): Boolean {
            return oldItem.id == newItem.id
        }
    }
    inner class MyView(val itemBinding: FragmentFirstAidListBinding): RecyclerView.ViewHolder(itemBinding.root){




    }
//    fun setFilteredList(radiationList: java.util.ArrayList<RadiationList>){
//        this.radiationList = radiationList
//        notifyDataSetChanged()
//
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        return MyView(FragmentFirstAidListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.itemBinding.radImage.setImageResource(R.drawable.augmentin)
        holder.itemBinding.radName.text = getItem(position).title
        holder.itemBinding.root.setOnClickListener {
            val action = FirstAidFragmentDirections.actionFirstAidToFirstAidDetailsFragment(getItem(position).id.toString())
            it.findNavController().navigate(action)
        }

    }

}

