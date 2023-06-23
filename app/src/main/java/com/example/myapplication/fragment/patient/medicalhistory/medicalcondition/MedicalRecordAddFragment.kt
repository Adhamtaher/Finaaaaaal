package com.example.myapplication.fragment.patient.medicalhistory.medicalcondition

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Switch
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.common.Constants
import com.example.myapplication.common.Constants.Companion.dataStore
import com.example.myapplication.databinding.FragmentMedicalRecordAddBinding
import com.example.myapplication.utils.getFilePathFromUri
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MedicalRecordAddFragment : Fragment() {

    lateinit var binding: FragmentMedicalRecordAddBinding
    val viewModel: MedicalRecordAddViewModel by viewModels()
    val args: MedicalRecordAddFragmentArgs by navArgs()
    private lateinit var dataStore: DataStore<Preferences>
     var booleanValueChronic:Boolean = false
    var booleanValueStill:Boolean = false
    lateinit var radioChronic: RadioButton
    lateinit var radioStill: RadioButton
    var fileUri: Uri? = null
    var recordId: Deferred<String>? = null
    private lateinit var fileLauncher: ActivityResultLauncher<Intent>
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
        binding = FragmentMedicalRecordAddBinding.inflate(inflater, container, false)
        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.txtTitle.text = args.type

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controlViews(args.type)

        binding.btnAdd.setOnClickListener {
            collectState(args.type)

        }
        binding.txtUpload.setOnClickListener {
            openGallery()

        }

        fileLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                    val selectedFileUri: Uri? = result.data?.data
                    if (selectedFileUri != null) {
                        val fileType = requireActivity().contentResolver.getType(selectedFileUri)
                        if (fileType != null) {
                            if (fileType.startsWith("image/")) {
                                // Handle the selected image file
                                handleImage(selectedFileUri)
                                fileUri = selectedFileUri
                            } else if (fileType == "application/pdf") {
                                // Handle the selected PDF file
                                handlePDF(selectedFileUri)
                                fileUri = selectedFileUri
                            }
                        }
                    }
                }
            }


    }

    fun collectState(type: String) {
        lifecycleScope.launch {
            when (type) {
                "Diagnosis" -> {

                    val checkedRadioButtonId = binding.radioGroupChronic.checkedRadioButtonId
                    if (checkedRadioButtonId != -1) {
                        val radioButton = requireActivity().findViewById<RadioButton>(checkedRadioButtonId)
                        val selectedText = radioButton.text.toString()
                        booleanValueChronic = selectedText.toBoolean()
                    }

                    val checkedRadioButtonId2 = binding.radioGroupStill.checkedRadioButtonId

                    if (checkedRadioButtonId2 != -1) {
                        val radioButton = requireActivity().findViewById<RadioButton>(checkedRadioButtonId2)
                        val selectedText = radioButton.text.toString()
                        booleanValueStill = selectedText.toBoolean()
                    }


                    viewModel.postMedicalRecord(
                        MedicalRecordInfo(
                            type = "diagnose",
                            name = binding.txtName.text.toString(),
                            date = binding.txtDate.text.toString(),
                            endDate = binding.txtEndDate.text.toString(),
                            doctorName = binding.txtDocName.text.toString(),
                            still = booleanValueStill,
                            chronic = booleanValueChronic,
                            patientId = getUserId(Constants.userId)
                        ), "Bearer ${getToken(Constants.userToken)}"
                    )
                }

                "Lab analysis" -> {
                    viewModel.postMedicalRecord(
                        MedicalRecordInfo(
                            type = "lab",
                            name = binding.txtName.text.toString(),
                            date = binding.txtDate.text.toString(),
                            doctorName = binding.txtDocName.text.toString(),
                            patientId = getUserId(Constants.userId)
                        ), "Bearer ${getToken(Constants.userToken)}"
                    )
                }

                "Radiations" -> {
                    viewModel.postMedicalRecord(
                        MedicalRecordInfo(
                            type = "rad",
                            name = binding.txtName.text.toString(),
                            date = binding.txtDate.text.toString(),
                            doctorName = binding.txtDocName.text.toString(),
                            patientId = getUserId(Constants.userId)
                        ), "Bearer ${getToken(Constants.userToken)}"
                    )
                }

                "Medication" -> {
                    val checkedRadioButtonId2 = binding.radioGroupStill.checkedRadioButtonId
                    Log.e( "collectStateeeeeee: ",checkedRadioButtonId2.toString() )

                    if (checkedRadioButtonId2 != -1) {
                        val radioButton = requireActivity().findViewById<RadioButton>(checkedRadioButtonId2)
                        val selectedText = radioButton.text.toString()
                        Log.e( "collectStateeeeeee: ",selectedText )
                        booleanValueStill = selectedText.toBoolean()

                        viewModel.postMedicalRecord(
                            MedicalRecordInfo(
                                type = "medication",
                                name = binding.txtName.text.toString(),
                                date = binding.txtDate.text.toString(),
                                endDate = binding.txtEndDate.text.toString(),
                                doctorName = binding.txtDocName.text.toString(),
                                still = booleanValueStill,
                                dosage = binding.txtDosage.text.toString(),
                                patientId = getUserId(Constants.userId)
                            ), "Bearer ${getToken(Constants.userToken)}"
                        )
                    }


                }

                "Surgeries" -> {
                    viewModel.postMedicalRecord(
                        MedicalRecordInfo(
                            type = "operation",
                            name = binding.txtName.text.toString(),
                            date = binding.txtDate.text.toString(),
                            doctorName = binding.txtDocName.text.toString(),
                            patientId = getUserId(Constants.userId)
                        ), "Bearer ${getToken(Constants.userToken)}"
                    )
                }

                else -> {}

            }

            viewModel.loginState.collect {
                if (it.success == "added") {
                    recordId = async { it.record?.id.toString() }
                    recordId!!.join()
                    Log.e("collectState: ", recordId!!.await())

                    if (fileUri != null) {
                        uploadFile(fileUri!!, recordId!!.await())
                    } else {
                        Toast.makeText(requireContext(), it.success, Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.medicalCondition)
                    }

                }

            }
        }
    }

    private fun controlViews(type: String) {
        when (type) {
            "Diagnosis" -> {
                binding.txtDosageContainer.visibility = View.GONE
            }

            "Medication" -> {
                binding.txtChronic.visibility = View.GONE
                binding.radioGroupChronic.visibility = View.GONE
            }

            "Surgeries" -> {
                binding.txtDosageContainer.visibility = View.GONE
                binding.txtChronic.visibility = View.GONE
                binding.radioGroupChronic.visibility = View.GONE
                binding.txtEndDateContainer.visibility = View.GONE
                binding.txtStill.visibility = View.GONE
                binding.radioGroupStill.visibility = View.GONE
            }

            "Lab analysis" -> {
                binding.txtDosageContainer.visibility = View.GONE
                binding.txtChronic.visibility = View.GONE
                binding.radioGroupChronic.visibility = View.GONE
                binding.txtEndDateContainer.visibility = View.GONE
                binding.txtStill.visibility = View.GONE
                binding.radioGroupStill.visibility = View.GONE
            }

            "Radiations" -> {
                binding.txtDosageContainer.visibility = View.GONE
                binding.txtChronic.visibility = View.GONE
                binding.radioGroupChronic.visibility = View.GONE
                binding.txtEndDateContainer.visibility = View.GONE
                binding.txtStill.visibility = View.GONE
                binding.radioGroupStill.visibility = View.GONE
            }

            else -> {
            }
        }

    }

    private suspend fun getToken(key: String): String? {
        dataStore = requireContext().dataStore
        val dataStoreKey: Preferences.Key<String> = stringPreferencesKey(key)
        val preference = dataStore.data.first()
        return preference[dataStoreKey]
    }

    private suspend fun getUserId(key: String): String? {
        dataStore = requireContext().dataStore
        val dataStoreKey: Preferences.Key<String> = stringPreferencesKey(key)
        val preference = dataStore.data.first()
        return preference[dataStoreKey]
    }


    private fun openGallery() {
        val mimeTypes = arrayOf("image/*", "application/pdf")
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        fileLauncher.launch(intent)
    }

    private fun handleImage(uri: Uri) {
        lifecycleScope.launch {
            viewModel.getName(uri, requireContext())

            viewModel.fileNameState.collect {
                binding.txtUpload.text = it.fileName
            }
        }
    }

    private fun handlePDF(uri: Uri) {
        lifecycleScope.launch {
            viewModel.getName(uri, requireContext())

            viewModel.fileNameState.collect {
                binding.txtUpload.text = it.fileName
            }
        }
    }

    private fun uploadFile(uri: Uri, id: String) {
        lifecycleScope.launch {
            viewModel.uploadFile(
                fileUri = uri,
                recId = id,
                fieldName = "medicRecord",
                id = getUserId(Constants.userId).toString(),
                fileRealPath = getFilePathFromUri(
                    requireContext(),
                    fileUri!!,
                    viewModel
                ).toString(),
                context = requireContext()
            )

            viewModel.uploadState.collect {
                binding.progress.isVisible = it.isLoading
                if (it.success != null) {
                    Toast.makeText(requireContext(), it.success, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.medicalCondition)
                }
            }

        }
    }


}