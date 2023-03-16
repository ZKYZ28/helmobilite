package com.example.projettupreferes.presenters

import android.net.Uri
import com.example.projettupreferes.adaptater.PairsAdapter
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.models.Choice
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.models.Paire
import com.example.projettupreferes.presenters.viewsInterface.fragments.ISeePairFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.*

class SeePairPresenter(private var seePairFragment: ISeePairFragment, private val PairListScreen: IPairListScreen, private val gameManager: GameManager) {

    init {
        seePairFragment.setSeePairPresenter(this)
    }


    interface IPairItemScreen {
        fun showPair(idPair : UUID?, position: Int)
    }

    interface IPairListScreen {
        fun loadView()
    }

    fun updateRecyclerPairs(){
        seePairFragment.udpateView()
    }

    fun getItemCount(): Int {
        val currentPairs = gameManager.currentCategoryWithPaires.paires;
        if(currentPairs.isEmpty()){
            return 0
        }
        return currentPairs.size
    }

    fun showPairOn(holder: PairsAdapter.ViewHolder, position: Int) {
        val p: Paire = gameManager.currentCategoryWithPaires.paires[position]
        val result = TuPreferesRepository.getInstance()?.getChoice(p.choiceOneId)
        val result2 = TuPreferesRepository.getInstance()?.getChoice(p.choiceTwoId)

        GlobalScope.launch {
            val choiceOne = result?.firstOrNull()
            val choiceTwo = result2?.firstOrNull()
            if (choiceOne != null && choiceTwo != null) {
                holder.showPair(p.idPaire, position)
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
        val currentCat = gameManager.currentCategoryWithPaires.category;
        seePairFragment.changeTitle(currentCat.categoryName)
    }

    fun updatePairs(pairId : UUID?, position : Int){
        TuPreferesRepository.getInstance()?.deletePaire(pairId!!)
        updateStats()

        val pairesList = gameManager.currentCategoryWithPaires.paires.toMutableList()
        pairesList.removeAt(position);
        gameManager.currentCategoryWithPaires.paires = pairesList.toList()

        if(gameManager.currentCategoryWithPaires.paires.isEmpty()){
            seePairFragment.destroyFragment()
        }
    }

    private fun updateStats() {
        gameManager.statistics.nbrPairs--
        TuPreferesRepository.getInstance()?.updateStatics(gameManager.statistics)
    }
}