package com.example.projettupreferes.presenters

import com.example.projettupreferes.fragments.StatisticsFragment
import com.example.projettupreferes.models.GameManager

class StatisticsPresenter(val statisticsFragment: StatisticsFragment, val mainActivityPresenter: MainActivityPresenter, val gameManager: GameManager) {
    init {
        statisticsFragment.presenter = this;
    }

    fun udpateStatisticsInformation(){
        statisticsFragment.udpateDisplayStatistics(gameManager.statistics.nbrPairs,
                                                    gameManager.statistics.nbrSwipes,
                                                    gameManager.statistics.nbrCategories,
                                                    gameManager.statistics.gamesPlayed)
    }
}