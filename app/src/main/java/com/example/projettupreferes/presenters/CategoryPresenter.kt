package com.example.projettupreferes.presenters

import android.util.Log
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.CategoryFragment
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.models.Paire
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
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
    fun loadPair(categoryUUID: UUID?) {
        GlobalScope.launch(Dispatchers.Main) {
            TuPreferesRepository.getInstance()?.getPairesByCategoryId(categoryUUID)
                ?.collect { paires ->
                    val updatedPaires = mutableListOf<Paire>()

                    paires.forEach { paire ->
                        val choiceOneFlow = TuPreferesRepository.getInstance()?.getChoice(paire.choiceOneId)
                        val choiceTwoFlow = TuPreferesRepository.getInstance()?.getChoice(paire.choiceTwoId)

                        if (choiceOneFlow != null && choiceTwoFlow != null) {
                            choiceOneFlow.zip(choiceTwoFlow) { choiceOne, choiceTwo ->
                                Paire(paire.idPaire, choiceOneId = choiceOne?.idChoice!!, choiceTwoId = choiceTwo?.idChoice!!, categoryIdFk = categoryUUID!!)
                            }?.collect { paireWithChoices ->
                                updatedPaires.add(paireWithChoices)
                            }
                        }
                    }

                    gameManager.categoryWithPaires.paires = updatedPaires
                }
        }
    }

    fun switchToPlayGame(){
        if(gameManager.categoryWithPaires.paires.isEmpty()){
            categoryFragment?.showErrorMessage("Vous n'avez aucune paire liée à cette catégorie")
        }else{
            mainPresenter.requestSwitchView("NormalGame")

            //Mettre à jour les statistics
            Log.d("STATSPRINTFL", gameManager.statistics.idStatistics.toString())
            gameManager.statistics.gamesPlayed++
            TuPreferesRepository.getInstance()?.updateStatics(gameManager.statistics)
        }
    }

    fun switchToSeePairs() {
        if(gameManager.categoryWithPaires.paires.isEmpty()){
            categoryFragment?.showErrorMessage("Vous n'avez aucune paire liée à cette catégorie")
        }else{
            mainPresenter.requestSwitchView("SeePair")
        }
    }

    fun switchToAddPair() {
        mainPresenter.requestSwitchView("CreatePair")
    }
}


