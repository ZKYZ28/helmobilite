package com.example.projettupreferes.presenters

import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.FragmentsName
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.presenters.viewsInterface.fragments.IHomeFragment

class HomeFragmentPresenter(homeFragment: IHomeFragment, private val mainPresenter : MainActivityPresenter, private val gameManager: GameManager) {
    init {
        homeFragment.setHomeFragmentPresenter(this);
    }

    fun goToNormalGame(desiredFragment: FragmentsName) {
        val normalCategory = gameManager.categoriesMap["NORMAL"]
        mainPresenter.loadPair(normalCategory?.idCategory!!, desiredFragment)
        updatePlayedGame()
    }

    private fun updatePlayedGame(){
        gameManager.statistics.gamesPlayed++
        TuPreferesRepository.getInstance()?.updateStatics(gameManager.statistics)
    }

    fun goToPersonnal(desiredFragment: FragmentsName) {
            mainPresenter.requestSwitchView(desiredFragment);
    }
}