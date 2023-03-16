package com.example.projettupreferes.presenters


import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.presenters.viewsInterface.fragments.IPlayGameFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class PlayGamePresenter(private val playGameFragment: IPlayGameFragment, private val gameManager: GameManager) {
    init {
        playGameFragment.setPlayGamePresenter(this);
    }

    fun onChoiceSelected() {
        val currentPairs = gameManager.currentCategoryWithPaires.paires;
        val currentPair = currentPairs[generateRandomNumber(currentPairs.size -1)]

        loadChoice(currentPair.choiceOneId, true)
        loadChoice(currentPair.choiceTwoId, false)
    }

    private fun loadChoice(uuidChoice : UUID?, isFirst : Boolean){
        GlobalScope.launch(Dispatchers.Main) {
          TuPreferesRepository.getInstance()?.getChoice(uuidChoice)
              ?.collect(){ choice ->
                  if(isFirst){
                      if (choice != null) {
                          playGameFragment.displayChoiceOne(choice.textChoice, choice.isText)
                      }
                  }else{
                      if (choice != null) {
                          playGameFragment.displayChoiceTwo(choice.textChoice, choice.isText)
                      }
                  }
              }
        }
    }


    private fun generateRandomNumber(max : Int) : Int{
        return Random().nextInt(max - 0 + 1) + 0
    }

    fun updateStatistics() {
        gameManager.statistics.nbrSwipes++
        TuPreferesRepository.getInstance()?.updateStatics(gameManager.statistics)
    }
}