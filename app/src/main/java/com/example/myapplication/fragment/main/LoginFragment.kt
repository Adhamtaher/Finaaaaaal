package com.example.myapplication.fragment.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.activity.MainPagePatient
import com.example.myapplication.common.Constants
import com.example.myapplication.common.Constants.Companion.dataStore
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.fragment.main.domain.PatientInfo
import com.example.myapplication.fragment.main.domain.UserInfo
import com.example.myapplication.fragment.main.login.LoginInfo
import com.example.myapplication.fragment.main.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    val viewModel by viewModels<LoginViewModel>()
    private lateinit var dataStore: DataStore<Preferences>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.login.setOnClickListener {
            collectState()
        }
        binding.bkRegister.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
        }

        return binding.root
    }

    private fun collectState() {
        lifecycleScope.launch {
            viewModel.login(
                LoginInfo(
                    email = binding.email.text.toString(),
                    password = binding.password.text.toString(),
                    rememberMe = binding.checkbox.isChecked

                )
            )
            viewModel.loginState.collect{
                if (it.success!=null){
                    Toast.makeText(requireContext(), it.success, Toast.LENGTH_SHORT).show()
                    saveToken(Constants.userToken, it.userInfo?.token.toString())
                    saveUserId(Constants.userId, it.userInfo?.user?.id.toString())
                    startActivity(Intent(requireContext(), MainPagePatient::class.java))
                    Log.e( "collectState: ",it.success.toString() )
                }
                 if (it.error!=null){
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                }
                binding.progress.isVisible = it.isLoading

            }
        }

    }

    private suspend fun saveToken(key: String, value: String) {
        dataStore = requireContext().dataStore
        val dataStoreKey = stringPreferencesKey(key)
        dataStore.edit {
            it[dataStoreKey] = value
        }
    }

    private suspend fun saveUserId(key: String, value: String) {
        dataStore = requireContext().dataStore
        val dataStoreKey = stringPreferencesKey(key)
        dataStore.edit {
            it[dataStoreKey] = value
        }
    }


}