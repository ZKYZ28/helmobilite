package com.example.projettupreferes.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract

class ImagePickerContract :  ActivityResultContract<Int, Uri?>(){
    override fun createIntent(context: Context, input: Int): Intent {
        val intent = Intent()
        when (input) {
            REQUEST_IMAGE_CAPTURE -> {
                intent.action = MediaStore.ACTION_IMAGE_CAPTURE
                return intent
            }
            REQUEST_PICK_IMAGE -> {
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"
                return intent
            }
            else -> throw IllegalArgumentException("Invalid input")
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return if (resultCode == Activity.RESULT_OK) intent?.data else null
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 0
        const val REQUEST_PICK_IMAGE = 1
    }

}