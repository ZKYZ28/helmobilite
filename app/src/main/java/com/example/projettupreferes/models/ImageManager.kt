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

            try {
                if (!file.exists()) {
                    file.mkdir()
                }
                //Création d'un nouveau fichier avec un nom unique basé sur la date et l'heure
                val newFile = File(file, "category_image_${System.currentTimeMillis()}.jpg")
                //On ouvre un flux d'entrée pour lire l'image à partir de l'URI
                val inputStream = context.contentResolver.openInputStream(uri)
                //On ouvre un flux de sortie pour écrire l'image dans le nouveau fichier
                val outputStream = FileOutputStream(newFile)

                //On utilise un bloc try-with-ressources pour s'assurer de la fermeture des ressources
                inputStream?.use { input ->
                    outputStream.use { output ->
                        val bitmap = BitmapFactory.decodeStream(input)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, output)
                    }
                }

                //On retourne l'URI du nouveau fichier créé
                return Uri.fromFile(newFile)
            } catch (e: FileNotFoundException) {
                throw SaveImageStorageException("Fichier non trouvé lors de l'enregistrement de l'image")
            } catch (e : IllegalArgumentException) {
                throw SaveImageStorageException ("Impossible de sauvegarder l'image, veuillez réessayer")
            } catch (e: IOException) {
                throw SaveImageStorageException("Erreur survenue lors de l'enregistrement de l'image")
            }
        }
    }

}