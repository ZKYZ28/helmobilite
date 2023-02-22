package com.example.projettupreferes.presenters

import com.example.projettupreferes.fragments.CategoryFragment
import com.example.projettupreferes.models.GameManager
import java.util.UUID

class CategoryPresenter(private val mainPresenter : MainActivityPresenter, private val gameManager: GameManager) {

    private var categoryFragment : CategoryFragment? = null

    fun setCategoryFragment(categoyFragmentNew : CategoryFragment){
        this.categoryFragment = categoyFragmentNew
    }

    fun loadCategory(categoryUUID : UUID){
        //TODO LOAD LA CAT DEPUIS LA BD

        //TODO CHANGER LE CAT COURANTE DANS GAMEMANAGER
        //gameManager.setCurrentCategory(category)

        categoryFragment?.displayCategoryInformation("FRANCOIS CAT", "file:///data/user/0/com.example.projettupreferes/files/category_images/category_image_1677004614898.jpeg")
    }

    fun deleteCategory(categoryUUID : UUID){
        //SUPPRIMER LA CAT DEPUIS LA BD
        //TuPreferesRepository.getInstance()?.deleteCategory(categoryUUID)

        mainPresenter.requestSwitchView("Personnel")
    }

    fun editCategory(){
        mainPresenter.requestSwitchView("EditCategory")
    }


}
