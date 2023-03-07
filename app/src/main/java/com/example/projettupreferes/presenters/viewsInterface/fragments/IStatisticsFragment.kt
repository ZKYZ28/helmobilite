package com.example.projettupreferes.presenters.viewsInterface.fragments

import com.example.projettupreferes.presenters.StatisticsPresenter

interface IStatisticsFragment {
    fun setStatisticsPresenter(statisticsPresenter : StatisticsPresenter)
    fun udpateDisplayStatistics(nbrPairs : Int, nbrSwipes : Int, nbrCategories : Int, nbrGamePlayed : Int)
}