package com.example.projettupreferes.presenters

import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.presenters.viewsInterface.fragments.IStatisticsFragment

class StatisticsPresenter(private val statisticsFragment: IStatisticsFragment, val gameManager: GameManager) {
    init {
        statisticsFragment.setStatisticsPresenter(this);
    }

    fun updateStatisticsInformation(){
        statisticsFragment.udpateDisplayStatistics(gameManager.statistics.nbrPairs,
                                                    gameManager.statistics.nbrSwipes,
                                                    gameManager.statistics.nbrCategories,
                                                    gameManager.statistics.gamesPlayed)
    }
}