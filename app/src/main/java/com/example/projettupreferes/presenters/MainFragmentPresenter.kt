package com.example.projettupreferes.presenters

import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.FragmentsName
import com.example.projettupreferes.fragments.HomeFragment
import com.example.projettupreferes.models.GameManager

class MainFragmentPresenter(private val homeFragment: HomeFragment, private val mainPresenter : MainActivityPresenter, private val gameManager: GameManager) {
    init {
        homeFragment.presenter = this;
    }

    fun goToNormalGame(desiredFragment: FragmentsName) {
        //Mettre à jour les statistics
        //Récupération des paires s'il clique instantanément sur le mode normal
        val normalCategory = gameManager.categoriesMap["Normal"]
        mainPresenter.loadPair(normalCategory?.idCategory!!, desiredFragment)
        gameManager.statistics.gamesPlayed++
        TuPreferesRepository.getInstance()?.updateStatics(gameManager.statistics)
    }

    fun goToPersonnal(desiredFragment: FragmentsName) {
        if(gameManager.categoriesMap.isEmpty()){
            mainPresenter.requestSwitchView(FragmentsName.NoCategoryFound);
        }else{
            mainPresenter.requestSwitchView(desiredFragment);
        }
    }
}