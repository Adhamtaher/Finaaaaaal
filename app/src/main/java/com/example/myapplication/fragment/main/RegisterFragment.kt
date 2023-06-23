package com.example.myapplication.fragment.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRegisterBinding
import com.example.myapplication.fragment.main.domain.PatientInfo
import com.example.myapplication.fragment.main.domain.UserInfo
import com.example.myapplication.fragment.main.login.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.register.setOnClickListener {
            collectState()
        }

        binding.bkLogin.setOnClickListener {
            findNavController().navigate(R.id.action_register_to_login)
        }

        return binding.root
    }

    private fun collectState() {
        lifecycleScope.launch {
            viewModel.register(
                UserInfo(
                    name = binding.name.text.toString(),
                    email = binding.mail.text.toString(),
                    phone = binding.Phone.text.toString(),
                    password = binding.password.text.toString(),
                    gender = "male",
                    patientInfo = PatientInfo(
                        address = binding.address.text.toString(),
                        city = binding.city.text.toString(),
                        birthDate = binding.Birthday.text.toString()

                    ),
                    role = "patient"
                )
            )
            viewModel.registerState.collect{
                if (it.success!=null){
                    Toast.makeText(requireContext(), it.success, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_register_to_login)
                }
                else if (it.error!=null){
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                }
                binding.progress.isVisible = it.isLoading

            }
        }

    }


    private fun handleError(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}