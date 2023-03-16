package com.example.projettupreferes.presenters

import android.content.Context
import android.net.Uri
import com.example.projettupreferes.R
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.FragmentsName
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.models.Paire
import com.example.projettupreferes.presenters.viewsInterface.activity.IMainActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.zip
import java.util.*

class MainActivityPresenter(private val mainActivity: IMainActivity, private val gameManager: GameManager) {
    init {
        loadStatistics()
    }

    private var loadPairJob: Job? = null

    fun requestSwitchView(desiredFragment: FragmentsName) {
        mainActivity.goTo(desiredFragment)
    }

    private fun loadStatistics(){
        GlobalScope.launch(Dispatchers.Main) {
            TuPreferesRepository.getInstance()?.getStatistics(UUID.fromString("b0a51d0e-20b7-4c84-8f84-2cf96eeb9a8a"))
                ?.collect { statisticsLoad ->
                    gameManager.statistics = statisticsLoad!!
                }
        }
    }

    fun loadPair(categoryUUID: UUID?, desiredFragment: FragmentsName?) {
        loadPairJob?.cancel()                                                           // annule la tâche précédente si elle est en cours
        loadPairJob = GlobalScope.launch(Dispatchers.Main) {
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

                    if (!mainActivity.giveSupportFragmentManager().isStateSaved) {
                        if (desiredFragment != null) {
                            requestSwitchView(desiredFragment)
                        }
                    }

                    gameManager.currentCategoryWithPaires.paires = updatedPaires
                }
        }
    }

    fun loadDefaultImage(context : Context){
        //SETUP image par défaut
        val defaultImageUri = Uri.parse("android.resource://${context.packageName}/${R.raw.defaut_image}")
        val outputStream = context.openFileOutput("defaut_image.jpg", Context.MODE_PRIVATE)
        val inputStream = context.contentResolver.openInputStream(defaultImageUri)
        inputStream?.copyTo(outputStream)
        outputStream.close()
    }


}
