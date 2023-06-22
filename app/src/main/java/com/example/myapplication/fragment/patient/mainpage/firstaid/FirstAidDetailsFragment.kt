package com.example.myapplication.fragment.patient.mainpage.firstaid

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.common.Constants.Companion.dataStore
import com.example.myapplication.databinding.FragmentFirstAidDetailsBinding
import kotlinx.coroutines.flow.first
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.myapplication.common.Constants
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorInfo1
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.Filter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FirstAidDetailsFragment : Fragment() {

    lateinit var binding: FragmentFirstAidDetailsBinding
    val viewModel: FirstAidViewModel by viewModels()
    private lateinit var dataStore: DataStore<Preferences>
    val args: FirstAidDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFirstAidDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectState()
        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    fun collectState() {
        lifecycleScope.launch {
            viewModel.getFirstAid(
                DoctorInfo1(Filter(_id = args.id)),
                "Bearer ${getToken(Constants.userToken)}"
            )

            viewModel.loginState.collect {
                binding.txtTitle.text = it.firstAid?.get(0)?.title
                binding.txtDescription.text = it.firstAid?.get(0)?.description
                Glide.with(requireContext()).load(it.firstAid?.get(0)?.files?.get(0)?.path)
                    .into(binding.aidImage)
                binding.txtLink.setOnClickListener { view ->
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data =
                        it.firstAid?.get(0)?.link?.let { it1 -> android.net.Uri.parse(it1) }
                    startActivity(intent)
                }
            }
        }
    }

    private suspend fun getToken(key: String): String? {
        dataStore = requireContext().dataStore
        val dataStoreKey: Preferences.Key<String> = stringPreferencesKey(key)
        val preference = dataStore.data.first()
        return preference[dataStoreKey]
    }


}