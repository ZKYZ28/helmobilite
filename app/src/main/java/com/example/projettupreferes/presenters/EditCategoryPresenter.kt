package com.example.projettupreferes.presenters

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.EditCategoryFragment
import com.example.projettupreferes.fragments.FragmentsName
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.models.ImageManager
import com.example.projettupreferes.models.exceptions.SaveImageStorageException
import com.example.projettupreferes.presenters.viewsInterface.fragments.IEditCategoryFragment
import java.io.File

class EditCategoryPresenter(private val editCategoryFragment: IEditCategoryFragment, val mainPresenter : MainActivityPresenter, val gameManager: GameManager) {
    init {
        editCategoryFragment.setEditCategoryPresenter(this)
    }


    fun validateModification(categoryName : String, selectedImageUri: Uri?, context: Context){
        if (categoryName.isEmpty()) {
            editCategoryFragment.showErrorMessage("Le nom de la catégorie ne peut pas être vide")
        } else if (selectedImageUri == null) {
            editCategoryFragment.showErrorMessage("Veuillez sélectionner une image")
        } else {
            editCategory(categoryName, selectedImageUri, context)
        }
    }

    private fun editCategory(categoryName: String, selectedImageUri: Uri, context: Context) {
        Log.d("URI DEPUIS L'APPAREIL PHOTO", selectedImageUri.toString())
        var imagePath: Uri? = null
        try {
           imagePath = ImageManager.saveImage(context, selectedImageUri)
            Log.d("URI ENREGISTRE", imagePath.toString())
        } catch (e: SaveImageStorageException) {
            editCategoryFragment.showErrorMessage(e.message!!)
        }

        if (imagePath == null) {
            editCategoryFragment.showErrorMessage("Une erreur s'est produite lors de l'enregistrement de l'image.")
        }

        //TODO : refactor appels
        gameManager.currentCategoryWithPaires.category.categoryName = categoryName
        gameManager.currentCategoryWithPaires.category.pathImage = imagePath.toString()
        TuPreferesRepository.getInstance()?.updateCategory(gameManager.currentCategoryWithPaires.category)

       // goToCategoryFragment()
        editCategoryFragment.close()
    }

    fun temporarySelectedImageUri(uri: Uri) {
        editCategoryFragment.showSelectedImage(uri)
    }

    fun getCurrentCategory() {
        val currentCategory = gameManager.currentCategoryWithPaires.category;
        editCategoryFragment.displayInformationInFields(currentCategory.categoryName, Uri.parse(currentCategory.pathImage))
    }

    fun onPickImageClicked() {
        editCategoryFragment.showImagePicker()
    }

}