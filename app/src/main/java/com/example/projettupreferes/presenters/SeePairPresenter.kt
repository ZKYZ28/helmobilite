package com.example.projettupreferes.presenters

import android.net.Uri
import android.util.Log
import com.example.projettupreferes.adaptater.PairsAdapter
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.FragmentsName
import com.example.projettupreferes.fragments.SeePairFragment
import com.example.projettupreferes.models.Choice
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.models.Paire
import com.example.projettupreferes.presenters.viewsInterface.fragments.ISeePairFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import java.util.*
import java.net.URI

class SeePairPresenter(private var seePairFragment: ISeePairFragment, private val PairListScreen: IPairListScreen, private val mainActivityPresenter: MainActivityPresenter, private val gameManager: GameManager) {

    init {
        seePairFragment.setSeePairPresenter(this)
    }

    interface IPairItemScreen {
        fun showPair(idPair: UUID?)
    }

    interface IPairListScreen {
        fun loadView()
    }

    fun setFragment(seePairFragment: ISeePairFragment){
        this.seePairFragment = seePairFragment
        this.seePairFragment.setSeePairPresenter(this)
    }

    fun getItemCount(): Int {
        if(gameManager.currentCategoryWithPaires.paires.isEmpty()){
            Log.d("SIZEPAIRES", gameManager.currentCategoryWithPaires.paires.size.toString())
            return 0
        }
        Log.d("SIZEPAIRES", gameManager.currentCategoryWithPaires.paires.size.toString())
        return gameManager.currentCategoryWithPaires.paires.size
    }

    fun showPairOn(holder: PairsAdapter.ViewHolder, position: Int) {
        val p: Paire = gameManager.currentCategoryWithPaires.paires[position]
        val result = TuPreferesRepository.getInstance()?.getChoice(p.choiceOneId)
        val result2 = TuPreferesRepository.getInstance()?.getChoice(p.choiceTwoId)

        GlobalScope.launch {
            val choiceOne = result?.firstOrNull()
            val choiceTwo = result2?.firstOrNull()
            if (choiceOne != null && choiceTwo != null) {
                holder.showPair(p.idPaire)
                displayChoice(choiceOne, choiceTwo, holder)
            }
        }
    }

    private fun displayChoice(choiceOne : Choice, choiceTwo : Choice, holder: PairsAdapter.ViewHolder){
        if(choiceOne.isText){
            holder.displayChoiceOneText(choiceOne.textChoice)
        }else{
            holder.displayButtonChoiceOne(Uri.parse(choiceOne.textChoice))
        }

        if(choiceTwo.isText){
            holder.displayChoiceTwoText(choiceTwo.textChoice)
        }else{
            holder.displayButtonChoiceTwo(Uri.parse(choiceTwo.textChoice))
        }
    }

    fun loadpairs() {
        PairListScreen.loadView()
    }

    fun displayTitle(){
        seePairFragment.changeTitle(gameManager.currentCategoryWithPaires.category.categoryName)
    }

    fun updatePairs(onUpdateComplete: () -> Unit){
        val categoryUUID = gameManager.currentCategoryWithPaires.category.idCategory
        // RECHARGE LES CHOIX PUIS CREE LE FRAGMENT POUR REAFFICHER TOUT CORRECTEMENT. PEUT ETRE JUSTE MODIFIER LA LISTE EN INTERNE POUR NE PAS DEVOIR RELOAD DEPUIS LA BD. COMMENT FAIRE ? FOREACH UUID EQUALS OU METTRE UNE MAP ?
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

                    gameManager.currentCategoryWithPaires.paires = updatedPaires

                    // Appelle le callback pour signaler la fin de l'exécution
                    onUpdateComplete()
                }
        }
    }

    fun goToCategoryFragment() {
        mainActivityPresenter.requestSwitchView(FragmentsName.CategoryFragment)
    }

    fun switchWhenListIsEmpty() {
        if(gameManager.currentCategoryWithPaires.paires.isEmpty()) {
            seePairFragment.showErrorMessage("Vous n'avez plus aucune paire ! Il est temps d'en céer...")
            goToCategoryFragment()
        }
    }

}