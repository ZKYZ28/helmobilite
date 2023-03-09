package com.example.projettupreferes.presenters

import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.FragmentsName
import com.example.projettupreferes.fragments.HomeFragment
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.presenters.viewsInterface.fragments.IHomeFragment

class HomeFragmentPresenter(private val homeFragment: IHomeFragment, private val mainPresenter : MainActivityPresenter, private val gameManager: GameManager) {
    init {
        homeFragment.setHomeFragmentPresenter(this);
    }

    fun goToNormalGame(desiredFragment: FragmentsName) {
        //Mettre à jour les statistics
        //Récupération des paires s'il clique instantanément sur le mode normal
        val normalCategory = gameManager.categoriesMap["NORMAL"]
        mainPresenter.loadPair(normalCategory?.idCategory!!, desiredFragment)
        gameManager.statistics.gamesPlayed++
        TuPreferesRepository.getInstance()?.updateStatics(gameManager.statistics)
    }

    fun goToPersonnal(desiredFragment: FragmentsName) {
            mainPresenter.requestSwitchView(desiredFragment);
    }
}