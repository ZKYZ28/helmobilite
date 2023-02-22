package com.example.projettupreferes.presenters

import com.example.projettupreferes.fragments.NormalGameFragment
import com.example.projettupreferes.models.GameManager

class NormalGamePresenter(private val normalGameFragment: NormalGameFragment, private val mainPresenter : MainActivityPresenter, private val gameManager: GameManager) {
    init {
        normalGameFragment.presenter = this;
    }

    fun onChoiceSelected() {
        gameManager.statistics.nbrSwipes++
        normalGameFragment.changeChoiceText("Serpend", "Mouton")
    }
}