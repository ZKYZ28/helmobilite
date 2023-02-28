package com.example.projettupreferes.presenters

import android.util.Log
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.HomeFragment
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.models.Paire
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class MainFragmentPresenter(private val homeFragment: HomeFragment, private val mainPresenter : MainActivityPresenter, private val gameManager: GameManager) {
    init {
        homeFragment.presenter = this;
    }

    fun goToNormalGame(desiredFragment: String) {
        val normalCategory = gameManager.categoriesMap["Normal"]
        Log.d("VALEUR ID", normalCategory?.idCategory.toString())
        GlobalScope.launch(Dispatchers.Main) {
            TuPreferesRepository.getInstance()?.getPairesByCategoryId(normalCategory?.idCategory)
                ?.collect { paires ->
                    val updatedPaires = mutableListOf<Paire>()
                    Log.d("Taille paire", paires.size.toString())
                    paires.forEach { paire ->
                        val choiceOneFlow = TuPreferesRepository.getInstance()?.getChoice(paire.choiceOneId)
                        val choiceTwoFlow = TuPreferesRepository.getInstance()?.getChoice(paire.choiceTwoId)

                        if (choiceOneFlow != null && choiceTwoFlow != null) {
                            choiceOneFlow.zip(choiceTwoFlow) { choiceOne, choiceTwo ->
                                Paire(paire.idPaire, choiceOneId = choiceOne?.idChoice!!, choiceTwoId = choiceTwo?.idChoice!!, categoryIdFk = normalCategory?.idCategory!!)
                            }?.collect { paireWithChoices ->
                                updatedPaires.add(paireWithChoices)
                            }
                        }
                    }
                    Log.d("TAILLE LUE", updatedPaires.size.toString())
                    gameManager.currentCategoryWithPaires.paires = updatedPaires
                }
        }
        Log.d("TAILLE ", gameManager.currentCategoryWithPaires.paires.size.toString())
        //Mettre à jour les statistics
        gameManager.statistics.gamesPlayed++
        TuPreferesRepository.getInstance()?.updateStatics(gameManager.statistics)
        mainPresenter.requestSwitchView("NormalGame")
    }

    fun goToPersonnal(desiredFragment: String) {
        if(gameManager.categoriesMap.isEmpty()){
            mainPresenter.requestSwitchView("noCategoryFound");
        }else{
            mainPresenter.requestSwitchView(desiredFragment);
        }
    }
}