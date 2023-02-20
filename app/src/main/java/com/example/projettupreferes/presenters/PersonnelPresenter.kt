package com.example.projettupreferes.presenters

import com.example.projettupreferes.fragments.Personnal

class PersonnelPresenter(private val personnel: Personnal, private val mainPresenter : MainActivityPresenter) {
    init {
        personnel.presenter = this;
    }

    fun goToCreateCategory(desiredFragment: String) {
        mainPresenter.requestSwitchView(desiredFragment);
    }
}