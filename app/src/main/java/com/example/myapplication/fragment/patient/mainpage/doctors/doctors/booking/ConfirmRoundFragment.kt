package com.example.myapplication.fragment.patient.mainpage.doctors.doctors.booking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentConfirmRoundBinding

class ConfirmRoundFragment : Fragment() {
    private lateinit var binding: FragmentConfirmRoundBinding
    val args : ConfirmRoundFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmRoundBinding.inflate(inflater, container, false)

        binding.okbutton.setOnClickListener {
            findNavController().navigate(R.id.action_confirmRound_to_mainPagePatient)
        }
        binding.txtTurnNum.text = args.turn.toString()

        return binding.root
    }
}
