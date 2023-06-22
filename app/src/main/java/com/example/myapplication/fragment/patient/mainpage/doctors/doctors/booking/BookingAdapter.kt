package com.example.myapplication.fragment.patient.mainpage.doctors.doctors.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentBookingListBinding
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.ScheduleItem

class BookingAdapter(private var scheduleItem: List<ScheduleItem>, val id:String, private val specialty :String):
    RecyclerView.Adapter<BookingAdapter.MyView>() {

    inner class MyView(val itemBinding: FragmentBookingListBinding): RecyclerView.ViewHolder(itemBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        return MyView(FragmentBookingListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return scheduleItem.size
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        val time = scheduleItem[position].time
        holder.itemBinding.appDate.text = "${scheduleItem[position].day}\n ${time?.from} to\n ${time?.to}"

        holder.itemBinding.book.setOnClickListener {
            val action = BookingFragmentDirections.actionBookingToConfirmBooking(id,specialty,
                scheduleItem[position]
            )
            it.findNavController().navigate(action)
        }
    }

}