package com.example.projettupreferes.presenters

import com.example.projettupreferes.fragments.Home
import com.example.projettupreferes.models.GameManager

class MainFragmentPresenter(private val homeFragment: Home, private val mainPresenter : MainActivityPresenter, private val gameManager: GameManager) {
    init {
        homeFragment.presenter = this;
    }

    fun goToNormalGame(desiredFragment: String) {
        gameManager.statistics.gamesPlayed++
        mainPresenter.requestSwitchView(desiredFragment);
    }

    fun goToPersonnal(desiredFragment: String) {
        if(gameManager.categoriesMap.size == 0){
            mainPresenter.requestSwitchView("noCategoryFound");
        }else{
            mainPresenter.requestSwitchView(desiredFragment);
        }
    }
}