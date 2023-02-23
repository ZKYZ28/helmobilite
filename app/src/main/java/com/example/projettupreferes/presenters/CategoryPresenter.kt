package com.example.projettupreferes.presenters

import android.util.Log
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.CategoryFragment
import com.example.projettupreferes.models.GameManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.UUID

class CategoryPresenter(private val mainPresenter : MainActivityPresenter, private val gameManager: GameManager) {

    private var categoryFragment : CategoryFragment? = null

    fun setCategoryFragment(categoyFragmentNew : CategoryFragment){
        this.categoryFragment = categoyFragmentNew
    }

    fun loadCategory(categoryUUID: UUID?) {
        if (categoryFragment != null) {
            GlobalScope.launch(Dispatchers.Main) {
                TuPreferesRepository.getInstance()?.getCategory(categoryUUID)
                    ?.collect { category ->
                        if (category != null) {
                            this@CategoryPresenter.gameManager.currentCategory = category
                            categoryFragment?.displayCategoryInformation(
                                category.categoryName,
                                category.pathImage
                            )
                        }
                    }
            }
        }
    }


    fun deleteCategory(){
        //SUPPRIMER LA CAT DEPUIS LA BD
        TuPreferesRepository.getInstance()?.deleteCategory(gameManager.currentCategory)

        categoryFragment?.close()
    }

    fun editCategory(){
        mainPresenter.requestSwitchView("EditCategory")
    }

    fun goToPair() {
        mainPresenter.requestSwitchView("CreatePair")
    }


}
