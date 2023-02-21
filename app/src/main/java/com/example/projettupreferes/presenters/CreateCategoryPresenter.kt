package com.example.projettupreferes.presenters

import ImageFileManager
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.CreateCategory
import com.example.projettupreferes.models.Category

//TODO mettre l'interface ICreateCategory
class CreateCategoryPresenter(private val createCategory: CreateCategory, private val mainPresenter: MainActivityPresenter) {
    init {
        createCategory.categoryPresenter = this;
    }


    private val imageFileManager = createCategory.context?.let { ImageFileManager(it) }

    fun validateCreation(categoryName: String, uri: Uri?) {
        if (categoryName.isEmpty()) {
            createCategory.showErrorMessage("Le nom de la catégorie ne peut pas être vide")
        } else {
            val pickImage =
                createCategory.registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                    if (uri != null) {
                        createCategory.showImage(uri)
                        createCategory(categoryName, uri)
                    } else {
                        createCategory.showErrorMessage("Vous devez sélectionner une image")
                    }
                }
            pickImage.launch("image/*")
        }
    }


    fun createCategory(name: String, uri: Uri?) {
        if (name.isEmpty()) {
            createCategory.showErrorMessage("Le nom d'une catégorie ne peut être vide !")
            return
        }

        if (uri == null) {
            createCategory.showErrorMessage("Une image est requise !")
            return
        }

        // Enregistrer l'image dans le dossier privé
        val imageFileManager = createCategory.context?.let { ImageFileManager(it) }
        val imagePath = imageFileManager?.saveImageToInternalStorage(uri)

        if (imagePath == null) {
            createCategory.showErrorMessage("Une erreur s'est produite lors de l'enregistrement de l'image.")
            return
        }

        //Création de l'objet catégorie
        val category = Category(categoryName = name, pathImage = imagePath);
        //Insertion de la catégorie en bd
        TuPreferesRepository.getInstance()?.insertCategory(category)
        createCategory.close()
    }

    fun pickImage() {
        imageFileManager?.pickImage(createCategory) { uri ->
            if (uri != null) {
                createCategory.showImage(uri)
            }
        }
    }

}