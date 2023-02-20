package com.example.projettupreferes.presenters

import com.example.projettupreferes.main

class MainFragmentPresenter(private val mainFragment: main, private val mainPresenter : MainActivityPresenter) {
    init {
        mainFragment.presenter = this;
    }

    fun goToNormalGame(desiredFragment: String) {
        mainPresenter.requestSwitchView(desiredFragment);
    }
}