package com.example.projettupreferes.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.projettupreferes.models.exceptions.SaveImageStorageException
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class ImageManager {


    companion object {
        @Throws(SaveImageStorageException::class)
        fun saveImage(context: Context, uri: Uri): Uri {
            val file = File(context.filesDir, "category_images")
            if (!file.exists()) {
                file.mkdir()
            }

            val newFile = File(file, "category_image_${System.currentTimeMillis()}.jpg")
            val inputStream = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(newFile)

            try {
                //inputStream et outpuStream implémente Closeable, de cette manière, on s'assure
                //que la ressource sera fermée
                inputStream?.use { input ->
                    outputStream.use { output ->
                        val bitmap = BitmapFactory.decodeStream(input)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, output)
                    }
                }
            } catch (e: FileNotFoundException) {
                throw SaveImageStorageException("Fichier non trouvé lors de l'enregistrement de l'image")
            } catch (e : IllegalArgumentException) {
                throw SaveImageStorageException ("Impossible de sauvergarder l'image, veuillez réessayer")
            } catch (e: IOException) {
                throw SaveImageStorageException("Erreur survenue lors de l'enregistrement de l'image")
            }

            return Uri.fromFile(newFile)
        }
    }
}