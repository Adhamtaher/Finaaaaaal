package com.example.myapplication.fragment.patient.mainpage.analysis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAnalysisListBinding
import com.example.myapplication.fragment.mainpage.doctors.speciality.AnalysisList
import com.example.myapplication.fragment.patient.mainpage.examinations.RadiationsFragmentDirections
import com.example.myapplication.fragment.patient.mainpage.examinations.Result

class AnalysisAdapter :
    ListAdapter<Result, AnalysisAdapter.MyView>(DiffCallBack()) {
    class DiffCallBack : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem._id == newItem._id
        }
    }

    inner class MyView(val itemBinding: FragmentAnalysisListBinding): RecyclerView.ViewHolder(itemBinding.root){




    }
//    fun setFilteredList(analysisList: java.util.ArrayList<AnalysisList>){
//        this.analysisList = analysisList
//        notifyDataSetChanged()
//
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        return MyView(FragmentAnalysisListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }



    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.itemBinding.analysisImage.setImageResource(R.drawable.blood)
        holder.itemBinding.analysisName.text = getItem(position).name
        holder.itemBinding.root.setOnClickListener {
            val action = AnalysisFragmentDirections.actionAnalysisToAnalysisBooking(getItem(position)._id.toString())
            it.findNavController().navigate(action)
        }

    }

}

