package com.example.projettupreferes.presenters

import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.HomeFragment
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.presenters.viewsInterface.fragments.IHomeFragment

class HomeFragmentPresenter(private val homeFragment: IHomeFragment, private val mainPresenter : MainActivityPresenter, private val gameManager: GameManager) {
    init {
        homeFragment.setHomeFragmentPresenter(this);
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