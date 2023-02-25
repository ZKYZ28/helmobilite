package com.example.projettupreferes.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.projettupreferes.R

abstract class FragmentWithImagePicker : Fragment() {

    protected lateinit var imagePickerLauncher: ActivityResultLauncher<Int>
    protected val imagePickerContract = ImagePickerContract()
    protected var imagePickerSource = 0

    private lateinit var cameraPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var galleryPermissionLauncher: ActivityResultLauncher<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Enregistrer le cameraPermissionLauncher
        cameraPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                launchImagePicker(ImagePickerContract.REQUEST_IMAGE_CAPTURE)
            } else {
                displayErrorMessage(getString(R.string.permission_camera_denied))
            }
        }

        // Enregistrer le galleryPermissionLauncher
        galleryPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                launchImagePicker(ImagePickerContract.REQUEST_PICK_IMAGE)
            } else {
                displayErrorMessage(getString(R.string.permission_read_external_storage_denied))
            }
        }
    }

    private fun launchImagePicker(mode: Int) {
        imagePickerLauncher.launch(mode)
    }

    protected fun showImagePickerDialog(source : Int) {
        imagePickerSource = source
        val items = arrayOf(getString(R.string.take_photo), getString(R.string.choose_from_gallery))
        val builder = AlertDialog.Builder(requireContext())
        builder.setItems(items) { dialog, which ->
            when (which) {
                0 -> {
                    cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                }
                1 -> {
                    galleryPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }
        builder.show()
    }

    protected fun displayErrorMessage(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }
}

