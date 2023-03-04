package com.example.projettupreferes.presenters

import android.net.Uri
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.CreateCategoryFragment
import com.example.projettupreferes.fragments.FragmentsName
import com.example.projettupreferes.models.Category
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.models.ImageManager
import com.example.projettupreferes.models.Paire
import com.example.projettupreferes.models.exceptions.SaveImageStorageException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateCategoryPresenter(private val createCategoryFragment: CreateCategoryFragment, private val mainPresenter : MainActivityPresenter, private val gameManager: GameManager) {
    init {
        createCategoryFragment.createCategoryPresenter = this
    }


    fun validateCreation(categoryName: String, selectedImageUri: Uri?) {
        if (categoryName.isEmpty()) {
            createCategoryFragment.showErrorMessage("Le nom de la catégorie ne peut pas être vide")
        } else if (selectedImageUri == null) {
            createCategoryFragment.showErrorMessage("Vous devez sélectionner une image")
        } else if(!checkIfCategoryAlreadyExist(categoryName)){
            val category = createCategory(categoryName, selectedImageUri)
            gameManager.categoriesMap[categoryName] = category

            //Mettre à jour les statistics
            gameManager.statistics.nbrCategories++
            TuPreferesRepository.getInstance()?.updateStatics(gameManager.statistics)

            mainPresenter.requestSwitchView(FragmentsName.Personnal)
        }
    }

    fun checkIfCategoryAlreadyExist(categoryName : String) : Boolean{
        if (gameManager.categoriesMap.containsKey(categoryName)){
            createCategoryFragment.showErrorMessage("Une catégorie avec ce nom existe déjà")
            return true
        }
        return false
    }

    private fun createCategory(categoryName: String, selectedImageUri: Uri): Category {
        var imagePath: Uri? = null
        try {
            imagePath = ImageManager.saveImage(createCategoryFragment.requireContext(), selectedImageUri)
        } catch (e: SaveImageStorageException) {
            createCategoryFragment.showErrorMessage(e.message!!)
        }

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
