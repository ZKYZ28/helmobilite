package com.example.projettupreferes.presenters

import android.util.Log
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.HomeFragment
import com.example.projettupreferes.models.GameManager

class MainFragmentPresenter(private val homeFragment: HomeFragment, private val mainPresenter : MainActivityPresenter, private val gameManager: GameManager) {
    init {
        homeFragment.presenter = this;
    }

    fun goToNormalGame(desiredFragment: String) {

        //Mettre à jour les statistics
        gameManager.statistics.gamesPlayed++
        TuPreferesRepository.getInstance()?.updateStatics(gameManager.statistics)
    }

    fun goToPersonnal(desiredFragment: String) {
        if(gameManager.categoriesMap.isEmpty()){
            mainPresenter.requestSwitchView("noCategoryFound");
        }else{
            mainPresenter.requestSwitchView(desiredFragment);
        }
    }
}