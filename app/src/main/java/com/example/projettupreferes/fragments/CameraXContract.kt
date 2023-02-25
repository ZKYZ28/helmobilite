package com.example.projettupreferes.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CameraXContract : ActivityResultContract<Uri, Uri?>(){

    private lateinit var context: Context
    private lateinit var currentPhotoUri: Uri

    override fun createIntent(context: Context, input: Uri): Intent {
        this.context = context
        val photoFile = createImageFile(context)
        val photoURI = FileProvider.getUriForFile(context, "com.example.myapp.fileprovider", photoFile)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        if (resultCode != Activity.RESULT_OK) {
            return null
        }
        return currentPhotoUri
    }

    private fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoUri = Uri.fromFile(this)
        }
    }
}