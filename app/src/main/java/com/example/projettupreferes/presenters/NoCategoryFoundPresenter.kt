package com.example.projettupreferes.presenters

import com.example.projettupreferes.fragments.NoCategoryFound
import com.example.projettupreferes.fragments.NormalGame

class NoCategoryFoundPresenter(private val noCategoryFound: NoCategoryFound, private val mainPresenter : MainActivityPresenter) {
    init {
        noCategoryFound.presenter = this;
    }

    fun switchToCreateCategory(){
        mainPresenter.requestSwitchView("CreateCategory")
    }
}