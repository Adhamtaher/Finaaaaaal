package com.example.myapplication.fragment.patient.medicalhistory.radiation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRadiationHistoryListBinding
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorItem
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorsAdapter
import com.example.myapplication.fragment.patient.medicalhistory.medicalcondition.RecordsItem
import java.text.SimpleDateFormat
import java.util.Locale

class RadiationHistoryAdapter(private val listener: OnItemClickListener) :
    ListAdapter<RecordsItem, RadiationHistoryAdapter.MyView>(DiffCallBack()) {

    class DiffCallBack : DiffUtil.ItemCallback<RecordsItem>() {
        override fun areItemsTheSame(oldItem: RecordsItem, newItem: RecordsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RecordsItem, newItem: RecordsItem): Boolean {
            return oldItem.id == newItem.id
        }
    }

    inner class MyView(val itemBinding: FragmentRadiationHistoryListBinding): RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.details.setOnClickListener{
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }


       fun bind(item: RecordsItem) {
            itemBinding.type.text=item.name
            itemBinding.date.text=convertDateFormat(item.date.toString())
           itemBinding.radImage.setImageResource(R.drawable.bone2)
        }

    }

    fun convertDateFormat(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d-M-yyyy", Locale.getDefault())
        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        return MyView(FragmentRadiationHistoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }



    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.bind(getItem(position))

    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}