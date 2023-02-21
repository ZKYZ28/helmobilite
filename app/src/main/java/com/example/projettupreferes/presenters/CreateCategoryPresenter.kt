package com.example.projettupreferes.presenters

import ImageFileManager
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.CreateCategory
import com.example.projettupreferes.models.Category
import com.example.projettupreferes.models.ImageManager
import com.example.projettupreferes.presenters.viewsInterface.fragments.ICreateCategory

class CreateCategoryPresenter(private val createCategory: CreateCategory, private val mainPresenter : MainActivityPresenter) {
    init {
        createCategory.categoryPresenter = this
    }


    fun validateCreation(categoryName: String, selectedImageUri: Uri?) {
        if (categoryName.isEmpty()) {
            createCategory.showErrorMessage("Le nom de la catégorie ne peut pas être vide")
        } else if (selectedImageUri == null) {
            createCategory.showErrorMessage("Vous devez sélectionner une image")
        } else {
            createCategory(categoryName, selectedImageUri)
        }
    }

    private fun createCategory(categoryName: String, selectedImageUri: Uri) {
        val imagePath = ImageManager.saveImage(createCategory.requireContext(), selectedImageUri)
        if (imagePath == null) {
            createCategory.showErrorMessage("Une erreur s'est produite lors de l'enregistrement de l'image.")
            return
        }
        //Todo : retirer (vérfication du chemin d'enregistrmeent)
        Log.d("IMAGE_SAVED_TO", imagePath.toString())

        val category = Category(categoryName = categoryName, pathImage = imagePath.toString())
        TuPreferesRepository.getInstance()?.insertCategory(category)
        createCategory.close()
    }

    fun temporarySelectedImageUri(context: Context, uri: Uri) {
        createCategory.showSelectedImage(uri)
    }
}
