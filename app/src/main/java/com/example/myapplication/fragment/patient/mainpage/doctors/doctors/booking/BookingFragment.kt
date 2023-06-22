package com.example.myapplication.fragment.patient.mainpage.doctors.doctors.booking

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentBookingBinding
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorInfo1
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.Filter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.ArrayList

@AndroidEntryPoint
class BookingFragment : Fragment() {


    lateinit var adapter: BookingAdapter
    lateinit var bookingList: ArrayList<BookingList>


    private val viewModel: BookingViewModel by viewModels()
    private val args: BookingFragmentArgs by navArgs()

    private lateinit var binding: FragmentBookingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookingBinding.inflate(inflater, container, false)


        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)

        collectState()

        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        return binding.root
    }


    private fun collectState() {
        lifecycleScope.launch {

            viewModel.getAllDoctors(DoctorInfo1(Filter(_id = args.id)))

            viewModel.loginState.collect {
                binding.recyclerView.adapter = it.doctorInfo?.doctorInfo?.schedule?.let { it1 ->
                    BookingAdapter(
                        it1,
                        args.id,
                        args.specialty
                    )
                }
                Glide.with(requireContext()).load(it.doctorInfo?.image).into(binding.doctorImage)
                binding.doctorName.text = it.doctorInfo?.name
                binding.txtFees.text = "Fees: ${it.doctorInfo?.doctorInfo?.fees?.examin.toString()}"
                binding.txtSpeciality.text = "Speciality: ${args.specialty}"
                binding.progress.isVisible = it.isLoading
                binding.txtInfo.text = it.doctorInfo?.doctorInfo?.bio
                Log.e( "collectState: ",it.doctorInfo?.doctorInfo?.schedule.toString() )
            }

        }
    }
}