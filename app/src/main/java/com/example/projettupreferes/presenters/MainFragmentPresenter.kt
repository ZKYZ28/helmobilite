package com.example.projettupreferes.presenters

import com.example.projettupreferes.fragments.HomeFragment
import com.example.projettupreferes.models.GameManager

class MainFragmentPresenter(private val homeFragment: HomeFragment, private val mainPresenter : MainActivityPresenter, private val gameManager: GameManager) {
    init {
        homeFragment.presenter = this;
    }

    fun goToNormalGame(desiredFragment: String) {
        gameManager.statistics.gamesPlayed++
        mainPresenter.requestSwitchView(desiredFragment);
    }

    fun goToPersonnal(desiredFragment: String) {
        if(gameManager.categoriesList.size == 0){
            mainPresenter.requestSwitchView("noCategoryFound");
        }else{
            mainPresenter.requestSwitchView(desiredFragment);
        }
    }
}