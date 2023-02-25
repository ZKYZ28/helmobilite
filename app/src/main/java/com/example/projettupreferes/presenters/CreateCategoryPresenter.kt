package com.example.projettupreferes.presenters

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.CreateCategoryFragment
import com.example.projettupreferes.models.Category
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.models.ImageManager
import com.example.projettupreferes.models.Paire
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
            gameManager.categoriesList.add(category)
            mainPresenter.requestSwitchView("Personnel")
        }
    }

    fun checkIfCategoryAlreadyExist(categoryName : String) : Boolean{
        //TODO CHECK SI LE NOM DE LA CATEGORIE N'EST PAS DEJA UTILISE
        //TuPreferesRepository.getInstance()?.checkIfCategoryAlreadyExiste(categoryName)
        return false
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

    fun temporarySelectedImageUri(context: Context, uri: Uri) {
        createCategoryFragment.showSelectedImage(uri)
    }
}
