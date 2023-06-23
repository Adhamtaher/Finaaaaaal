package com.example.myapplication.fragment.patient.medicalhistory.medicalcondition.diagnosis

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.DownloadManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.common.Constants
import com.example.myapplication.common.Constants.Companion.dataStore
import com.example.myapplication.databinding.FragmentDiagnosisDetailsBinding
import com.example.myapplication.databinding.FragmentSurgeriesResultsBinding
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.DoctorInfo1
import com.example.myapplication.fragment.patient.mainpage.doctors.doctors.Filter
import com.example.myapplication.fragment.patient.medicalhistory.medicalcondition.ResultsViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class DiagnosisDetails : Fragment() {
    private lateinit var binding: FragmentDiagnosisDetailsBinding
    val viewModel: ResultsViewModel by viewModels()
    val args: DiagnosisDetailsArgs by navArgs()
    private lateinit var dataStore: DataStore<Preferences>

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiagnosisDetailsBinding.inflate(inflater, container, false)

        binding.imageView1.setOnClickListener {
            val textToEncode = "AUGMENTIN 1GM \nPANADOL COLD & FLU \nCOMETREX"
            val qrCodeSize = 512

            // Generate QR code bitmap
            val bitMatrix = MultiFormatWriter().encode(
                textToEncode,
                BarcodeFormat.QR_CODE,
                qrCodeSize,
                qrCodeSize
            )
            val bmp = Bitmap.createBitmap(bitMatrix.width, bitMatrix.height, Bitmap.Config.RGB_565)
            for (x in 0 until bitMatrix.width) {
                for (y in 0 until bitMatrix.height) {
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            // Display QR code bitmap
            val imageView = ImageView(requireContext())
            imageView.setImageBitmap(bmp)
            val dialog = Dialog(requireContext())
            dialog.setContentView(imageView)
            dialog.show()
        }
        binding.imageView2.setOnClickListener {
            binding.imageView46.visibility = View.VISIBLE
        }
        collectState()
        binding.diagnosisDetails.setOnTouchListener { _, event ->
            val imageView = binding.imageView46
            val imageRect = Rect()
            imageView.getGlobalVisibleRect(imageRect)

            if (!imageRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                imageView.visibility = View.GONE
            }
            false
        }
        binding.imageView3.setOnClickListener {
            val uri = Uri.parse("https://docs.google.com/document/d/1er8avOFqmEb5thwdLLUBFAVHr7ha0VbknVk42qagOiE/edit?usp=share_link")
            val request = DownloadManager.Request(uri)

            // Set the destination directory and filename for the downloaded file
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "My report.docx")

            // Set the title and description for the notification that appears during the download
            request.setTitle("Downloading Word document")
            request.setDescription("Please wait while your file is being downloaded.")

            // Get a reference to the system's DownloadManager and enqueue the request
            val downloadManager = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.enqueue(request)

            // Show a toast message indicating that the download has started
            Toast.makeText(context, "Download started", Toast.LENGTH_SHORT).show()
        }
            binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        return binding.root
    }
    fun View.contains(x: Int, y: Int): Boolean {
        val location = IntArray(2)
        getLocationOnScreen(location)
        val left = location[0]
        val top = location[1]
        val right = left + width
        val bottom = top + height
        return x >= left && x <= right && y >= top && y <= bottom
    }

    private suspend fun getToken(key: String): String? {
        dataStore = requireContext().dataStore
        val dataStoreKey: Preferences.Key<String> = stringPreferencesKey(key)
        val preference = dataStore.data.first()
        return preference[dataStoreKey]
    }

    fun collectState(){
        lifecycleScope.launch {
            viewModel.getMedicalRecord(
                DoctorInfo1(Filter(_id = args.id)),"Bearer ${getToken(
                    Constants.userToken)}")

            viewModel.loginState.collect{
                binding.textView34.text = it.record?.name
                if (it.record?.date!=null)
                    binding.textView36.text = convertDateFormat(it.record?.date.toString())
                binding.textView43.text = it.record?.doctorName
                if (it.success!=null){
                    if (it.record?.files?.isNotEmpty()!!){
                        Glide.with(requireContext()).load(it.record?.files?.get(0)?.path).into(binding.imageView2)
                    }
                    binding.textView38.text = it.record.chronic.toString()
                    binding.textView40.text = it.record.still.toString()
                }



            }
        }
    }

    private fun convertDateFormat(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d-M-yyyy", Locale.getDefault())
        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date!!)
    }
}