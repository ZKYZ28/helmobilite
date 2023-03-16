package com.example.projettupreferes.presenters

import android.content.Context
import android.net.Uri
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.FragmentsName
import com.example.projettupreferes.models.Category
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.models.ImageManager
import com.example.projettupreferes.models.exceptions.SaveImageStorageException
import com.example.projettupreferes.presenters.viewsInterface.fragments.ICreateCategoryFragment

class CreateCategoryPresenter(private val createCategoryFragment: ICreateCategoryFragment, private val mainPresenter : MainActivityPresenter, private val gameManager: GameManager) {

    companion object {
        private const val DEFAULT_IMAGE_URI = "file:///data/user/0/com.example.projettupreferes/files/defaut_image.jpg"
    }

    init {
        createCategoryFragment.setCreateCategoryPresenter(this)
    }

    fun resetCategoryName(){
        createCategoryFragment.resetCategoryName()
    }

    fun validateCreation(categoryName: String, selectedImageUri: Uri?, context: Context) {
        if (categoryName.isEmpty()) {
            createCategoryFragment.showErrorMessage("Le nom de la catégorie ne peut pas être vide")
        } else if(!checkIfCategoryAlreadyExist(categoryName)){
            var uri = selectedImageUri
            if (uri == null){
                uri =  Uri.parse(DEFAULT_IMAGE_URI)
            }

            val category = createCategory(categoryName.uppercase(), uri!!, context) ?: return
            gameManager.categoriesMap[categoryName] = category

            addCategory()

            createCategoryFragment.resetCategoryName()
            mainPresenter.requestSwitchView(FragmentsName.Personnal)
        }
    }

    private fun addCategory(){
        gameManager.statistics.nbrCategories++
        TuPreferesRepository.getInstance()?.updateStatics(gameManager.statistics)
    }

    private fun checkIfCategoryAlreadyExist(categoryName : String) : Boolean{
        if (gameManager.categoriesMap.containsKey(categoryName)){
            createCategoryFragment.showErrorMessage("Une catégorie avec ce nom existe déjà")
            return true
        }
        return false
    }

    private fun createCategory(categoryName: String, selectedImageUri: Uri, context : Context): Category? {
        var imagePath: Uri
        try {
            imagePath = ImageManager.saveImage(context, selectedImageUri)
        } catch (e: SaveImageStorageException) {
            createCategoryFragment.showErrorMessage(e.message!!)
            return null
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
