package com.example.projettupreferes.presenters

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.example.projettupreferes.R
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.FragmentsName
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.models.Paire
import com.example.projettupreferes.presenters.viewsInterface.activity.IMainActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*

class MainActivityPresenter(private val mainActivity: IMainActivity, private val gameManager: GameManager) {
    init {
        loadStatistics()
    }

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
        val defaultImageUri = Uri.parse("android.resource://${context.packageName}/${R.raw.defaut_image}")
        try {
            val outputStream = context.openFileOutput("defaut_image.jpg", Context.MODE_PRIVATE)
            val inputStream = context.contentResolver.openInputStream("abc".toUri())
            if (inputStream == null) {
                ("Le fichier d'image par défaut n'a pas été trouvé")
                mainActivity.showErrorMessage("coucou")
                return
            }
            inputStream.copyTo(outputStream)
            outputStream.close()
        } catch (e: FileNotFoundException) {
            Log.d("EXCEPTION LANCCE", "AZFFZFZF")
        } catch (e: SecurityException) {

        } catch (e: IOException) {
        }

    }


}
