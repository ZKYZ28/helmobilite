package com.example.projettupreferes.presenters

import com.example.projettupreferes.personnel

class PersonnelPresenter(private val personnel: personnel, private val mainPresenter : MainActivityPresenter) {
    init {
        personnel.presenter = this;
    }

    fun goToCreateCategory(desiredFragment: String) {
        mainPresenter.requestSwitchView(desiredFragment);
    }
}