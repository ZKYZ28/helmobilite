package com.example.projettupreferes.presenters

import android.net.Uri
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.CreateCategoryFragment
import com.example.projettupreferes.models.Category
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.models.ImageManager
import com.example.projettupreferes.models.Paire

class CreateCategoryPresenter(private val createCategoryFragment: CreateCategoryFragment, private val mainPresenter : MainActivityPresenter, private val gameManager: GameManager) {
    init {
        createCategoryFragment.createCategoryPresenter = this
    }


    fun validateCreation(categoryName: String, selectedImageUri: Uri?) {
        if (categoryName.isEmpty()) {
            createCategoryFragment.showErrorMessage("Le nom de la catégorie ne peut pas être vide")
        } else if (selectedImageUri == null) {
            createCategoryFragment.showErrorMessage("Vous devez sélectionner une image")
        } else {
            val category = createCategory(categoryName, selectedImageUri)
            gameManager.categoriesList.add(category)
            mainPresenter.requestSwitchView("Personnel")
        }
    }

    private fun createCategory(categoryName: String, selectedImageUri: Uri): Category {
        val imagePath = ImageManager.saveImage(createCategoryFragment.requireContext(), selectedImageUri)
        if (imagePath == null) {
            createCategoryFragment.showErrorMessage("Une erreur s'est produite lors de l'enregistrement de l'image.")
        }

        val category = Category(categoryName = categoryName, pathImage = imagePath.toString())

        TuPreferesRepository.getInstance()?.insertCategory(category)
        createCategoryFragment.close()
        return category
    }

    fun onPickImageClicked() {
        createCategoryFragment.showImagePicker()
    }

    fun temporarySelectedImageUri(uri: Uri) {
        createCategoryFragment.showSelectedImage(uri)
    }

}
