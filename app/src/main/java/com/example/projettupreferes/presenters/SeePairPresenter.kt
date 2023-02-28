package com.example.projettupreferes.presenters

import android.net.Uri
import android.util.Log
import com.example.projettupreferes.adaptater.PairsAdapter
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.SeePairFragment
import com.example.projettupreferes.models.Choice
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.models.Paire
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.*
import java.net.URI

class SeePairPresenter(private val seePairFragment: SeePairFragment, private val mainActivityPresenter: MainActivityPresenter, private val gameManager: GameManager) {

    init {
        seePairFragment.presenter = this
    }

    interface IPairItemScreen {
        fun showPair(idPair: UUID?)
    }

    interface IPairListScreen {
        fun loadView()
    }

    fun getItemCount(): Int {
        if(gameManager.currentCategoryWithPaires.paires.isEmpty()){
            return 0
        }
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
        seePairFragment.loadView()
    }

    fun displayTitle(){
        seePairFragment.changeTitle(gameManager.currentCategoryWithPaires.category.categoryName)
    }

}