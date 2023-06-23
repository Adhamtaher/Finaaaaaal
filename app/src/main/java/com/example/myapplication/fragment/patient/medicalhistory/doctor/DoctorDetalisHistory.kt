package com.example.myapplication.fragment.patient.medicalhistory.doctor

import android.app.Dialog
import android.app.DownloadManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDoctorDetalisHistoryBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter

class DoctorDetalisHistory : Fragment() {
    private lateinit var binding: FragmentDoctorDetalisHistoryBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDoctorDetalisHistoryBinding.inflate(inflater, container, false)
        binding.generateQR.setOnClickListener {
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
        binding.Download.setOnClickListener {
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
}
