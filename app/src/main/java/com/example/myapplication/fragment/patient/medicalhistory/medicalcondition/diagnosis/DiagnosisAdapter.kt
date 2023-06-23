package com.example.myapplication.fragment.patient.medicalhistory.medicalcondition.diagnosis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentDiagnosisListBinding
import com.example.myapplication.fragment.patient.medicalhistory.medicalcondition.RecordsItem
import java.text.SimpleDateFormat
import java.util.Locale

class DiagnosisAdapter(private val listener:OnItemClickListener) :
   ListAdapter<RecordsItem, DiagnosisAdapter.MyView>(DiffCallBack()) {

    class DiffCallBack : DiffUtil.ItemCallback<RecordsItem>() {
        override fun areItemsTheSame(oldItem: RecordsItem, newItem: RecordsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RecordsItem, newItem: RecordsItem): Boolean {
            return oldItem.id == newItem.id
        }
    }

    inner class MyView(val itemBinding: FragmentDiagnosisListBinding): RecyclerView.ViewHolder(itemBinding.root){

        init {
            itemBinding.details.setOnClickListener{
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }

        fun bind(item: RecordsItem) {
            itemBinding.type.text="Diagnose: ${item.name}"
            itemBinding.date.text="Date ${convertDateFormat(item.date.toString())}"
            itemBinding.chronic.text="chronic: ${item.chronic}"
            itemBinding.active.text="active: ${item.still}"
        }
    }
    fun convertDateFormat(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d-M-yyyy", Locale.getDefault())
        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        return MyView(FragmentDiagnosisListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }



    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.bind(getItem(position))
    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}