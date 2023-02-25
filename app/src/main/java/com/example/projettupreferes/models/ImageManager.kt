package com.example.projettupreferes.models

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

class ImageManager {

    companion object {
        fun saveImage(context: Context, uri: Uri): Uri {
            val file = File(context.filesDir, "category_images")
            if (!file.exists()) {
                file.mkdir()
            }

            val newFile = File(file, "category_image_${System.currentTimeMillis()}.jpg")
            val inputStream = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(newFile)

            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            return Uri.fromFile(newFile)
        }
    }
}