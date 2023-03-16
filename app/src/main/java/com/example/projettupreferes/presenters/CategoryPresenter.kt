package com.example.projettupreferes.presenters

import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.FragmentsName
import com.example.projettupreferes.models.CategoryWithPaires
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.models.Paire
import com.example.projettupreferes.presenters.viewsInterface.fragments.ICategoryFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.UUID

class CategoryPresenter(
    private val mainPresenter: MainActivityPresenter,
    private val gameManager: GameManager
) {

    private var categoryFragment: ICategoryFragment? = null
    private var categoryWithPaires: CategoryWithPaires? = null;

    fun setCategoryFragment(categoryFragmentNew: ICategoryFragment) {
        this.categoryFragment = categoryFragmentNew
        categoryWithPaires = gameManager.currentCategoryWithPaires;
    }

    fun loadCategory(categoryUUID: UUID?) {
        if (categoryFragment != null) {
            GlobalScope.launch(Dispatchers.Main) {
                TuPreferesRepository.getInstance()?.getCategory(categoryUUID)
                    ?.collect { category ->
                        if (category != null) {
                            categoryWithPaires?.category = category
                            categoryFragment?.displayCategoryInformation(
                                category.categoryName,
                                category.pathImage
                            )
                        }
                    }
            }
        }
    }


    fun deleteCategory() {
        removeCategory()

        TuPreferesRepository.getInstance()?.updateStatics(gameManager.statistics)
        TuPreferesRepository.getInstance()?.deleteCategory(categoryWithPaires!!.category)
        categoryFragment?.close()
    }

    private fun removeCategory(){
        gameManager.categoriesMap.remove(categoryWithPaires!!.category.categoryName)
        gameManager.statistics.nbrCategories--
    }

    fun requestToDeleteCategory(){
        if(categoryWithPaires!!.category.categoryName == "NORMAL"){
            categoryFragment?.showErrorMessage("Vous ne pouvez pas supprimer la catégorie Normal")
        }else{
            categoryFragment?.displayPopUpConfirmation()
        }
    }

    fun editCategory() {
        if(categoryWithPaires!!.category.categoryName == "NORMAL"){
            categoryFragment?.showErrorMessage("Vous ne pouvez pas éditer la catégoire Normal")
        }else{
            mainPresenter.requestSwitchView(FragmentsName.EditCategory)
        }
    }

    fun loadPair(categoryUUID: UUID?) {
        mainPresenter.loadPair(categoryUUID, null)
    }

    fun switchToPlayGame() {
        if (checkIfPairIsEmpty(categoryWithPaires!!.paires)) return

        mainPresenter.requestSwitchView(FragmentsName.NormalGame)
        gameManager.statistics.gamesPlayed++
        TuPreferesRepository.getInstance()?.updateStatics(gameManager.statistics)
    }

    fun switchToSeePairs() {
        if (checkIfPairIsEmpty(categoryWithPaires!!.paires)) return
        mainPresenter.requestSwitchView(FragmentsName.SeePair)
    }

    private fun checkIfPairIsEmpty (currentListPair : List<Paire>): Boolean {
        if (currentListPair.isEmpty()) {
            categoryFragment?.showErrorMessage("Vous n'avez aucune paire liée à cette catégorie")
            return true
        }
        return false
    }

    fun switchToAddPair() {
        mainPresenter.requestSwitchView(FragmentsName.CreatePair)
    }
}


