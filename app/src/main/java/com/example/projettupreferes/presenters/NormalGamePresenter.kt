package com.example.projettupreferes.presenters

import android.util.Log
import com.example.projettupreferes.fragments.NoCategoryFound
import com.example.projettupreferes.fragments.NormalGame
import com.example.projettupreferes.models.GameManager

class NormalGamePresenter(private val normalGame: NormalGame, private val mainPresenter : MainActivityPresenter, private val gameManager: GameManager) {
    init {
        normalGame.presenter = this;
    }

    fun onChoiceSelected() {
        gameManager.statistics.nbrSwipes++
        normalGame.changeChoiceText("Serpend", "Mouton")
    }
}