package com.example.projettupreferes.presenters

import android.util.Log
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.CategoryFragment
import com.example.projettupreferes.models.GameManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import java.util.UUID

class CategoryPresenter(
    private val mainPresenter: MainActivityPresenter,
    private val gameManager: GameManager
) {

    private var categoryFragment: CategoryFragment? = null

    fun setCategoryFragment(categoyFragmentNew: CategoryFragment) {
        this.categoryFragment = categoyFragmentNew
    }

    fun loadCategory(categoryUUID: UUID?) {
        if (categoryFragment != null) {
            GlobalScope.launch(Dispatchers.Main) {
                TuPreferesRepository.getInstance()?.getCategory(categoryUUID)
                    ?.collect { category ->
                        if (category != null) {
                            //TODO changer les 3 appels
                            this@CategoryPresenter.gameManager.categoryWithPaires.category =
                                category
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
        //SUPPRIMER LA CAT DEPUIS LA BD
        TuPreferesRepository.getInstance()?.deleteCategory(gameManager.categoryWithPaires.category)

        categoryFragment?.close()
    }

    fun editCategory() {
        mainPresenter.requestSwitchView("EditCategory")
    }

    //TODO : retirer uuid
    fun goToPair(categoryUUID: UUID?) {
        mainPresenter.requestSwitchView("CreatePair")
        GlobalScope.launch(Dispatchers.Main) {
            TuPreferesRepository.getInstance()?.getPairesByCategoryId(categoryUUID)
                ?.collect { paires ->
                    this@CategoryPresenter.gameManager.categoryWithPaires.paires = paires

                    gameManager.categoryWithPaires.paires.forEach { paire ->
                        val choiceOneFlow = TuPreferesRepository.getInstance()?.getChoice(paire.choiceOneId)
                        val choiceTwoFlow = TuPreferesRepository.getInstance()?.getChoice(paire.choiceTwoId)

                        if(choiceOneFlow != null && choiceTwoFlow != null) {
                            choiceOneFlow?.zip(choiceTwoFlow) { choiceOne, choiceTwo ->
                                Log.d("Choice", "${choiceOne?.textChoice} ${choiceTwo?.textChoice}")
                            }?.collect {
                            }
                        }

                    }
                }
        }
    }



}


