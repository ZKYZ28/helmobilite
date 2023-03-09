package com.example.projettupreferes.presenters

import com.example.projettupreferes.activities.MainActivity
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.FragmentsName
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.models.Paire
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import java.util.*

class MainActivityPresenter(private val mainActivity: MainActivity, private val gameManager: GameManager) : IPresenters {
    fun requestSwitchView(desiredFragment: FragmentsName) {
        mainActivity.goTo(desiredFragment)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun loadPair(categoryUUID: UUID?, desiredFragment: FragmentsName?) {
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
                    if (!mainActivity.supportFragmentManager.isStateSaved) {
                        if (desiredFragment != null) {
                            requestSwitchView(desiredFragment)
                        }
                    }
                    gameManager.currentCategoryWithPaires.paires = updatedPaires
                }
        }
    }


}
