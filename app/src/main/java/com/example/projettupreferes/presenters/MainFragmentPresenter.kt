package com.example.projettupreferes.presenters

import com.example.projettupreferes.fragments.Home

class MainFragmentPresenter(private val homeFragment: Home, private val mainPresenter : MainActivityPresenter) {
    init {
        homeFragment.presenter = this;
    }

    fun goToNormalGame(desiredFragment: String) {
        mainPresenter.requestSwitchView(desiredFragment);
    }
}