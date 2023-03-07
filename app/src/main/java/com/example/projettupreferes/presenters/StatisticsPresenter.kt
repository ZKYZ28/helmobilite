package com.example.projettupreferes.presenters

import com.example.projettupreferes.fragments.StatisticsFragment
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.presenters.viewsInterface.fragments.IStatisticsFragment

class StatisticsPresenter(val statisticsFragment: IStatisticsFragment, val mainActivityPresenter: MainActivityPresenter, val gameManager: GameManager) {
    init {
        statisticsFragment.setStatisticsPresenter(this);
    }

    fun udpateStatisticsInformation(){
        statisticsFragment.udpateDisplayStatistics(gameManager.statistics.nbrPairs,
                                                    gameManager.statistics.nbrSwipes,
                                                    gameManager.statistics.nbrCategories,
                                                    gameManager.statistics.gamesPlayed)
    }
}