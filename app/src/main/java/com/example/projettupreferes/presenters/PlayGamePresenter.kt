package com.example.projettupreferes.presenters


import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.PlayGameFragment
import com.example.projettupreferes.models.GameManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class PlayGamePresenter(private val playGameFragment: PlayGameFragment, private val mainPresenter : MainActivityPresenter, private val gameManager: GameManager) {
    init {
        playGameFragment.presenter = this;
    }

    fun onChoiceSelected() {
        //TODO AJOUTER LES STATS

        val max = gameManager.categoryWithPaires.paires.size -1
        val currentPair = gameManager.categoryWithPaires.paires[generateRandomNumber(max)]

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
}