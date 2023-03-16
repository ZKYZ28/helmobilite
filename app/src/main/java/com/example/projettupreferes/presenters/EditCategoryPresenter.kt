package com.example.projettupreferes.presenters

import android.content.Context
import android.net.Uri
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.models.ImageManager
import com.example.projettupreferes.models.exceptions.SaveImageStorageException
import com.example.projettupreferes.presenters.viewsInterface.fragments.IEditCategoryFragment

class EditCategoryPresenter(private val editCategoryFragment: IEditCategoryFragment, val gameManager: GameManager) {
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
        var imagePath: Uri
        try {
           imagePath = ImageManager.saveImage(context, selectedImageUri)
        } catch (e: SaveImageStorageException) {
            editCategoryFragment.showErrorMessage(e.message!!)
            return
        }

        val currentCat = gameManager.currentCategoryWithPaires.category;
        currentCat.categoryName = categoryName
        currentCat.pathImage = imagePath.toString()
        TuPreferesRepository.getInstance()?.updateCategory(currentCat)

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