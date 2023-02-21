package com.example.projettupreferes.presenters

import com.example.projettupreferes.fragments.NoCategoryFound
import com.example.projettupreferes.fragments.NormalGame

class NormalGamePresenter(private val normalGame: NormalGame, private val mainPresenter : MainActivityPresenter) {
    init {
        normalGame.presenter = this;
    }
}