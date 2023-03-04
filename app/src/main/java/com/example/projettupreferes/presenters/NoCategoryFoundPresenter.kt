package com.example.projettupreferes.presenters

import com.example.projettupreferes.fragments.FragmentsName
import com.example.projettupreferes.fragments.NoCategoryFoundFragment

class NoCategoryFoundPresenter(private val noCategoryFoundFragment: NoCategoryFoundFragment, private val mainPresenter : MainActivityPresenter) {
    init {
        noCategoryFoundFragment.presenter = this;
    }

    fun switchToCreateCategory(){
        mainPresenter.requestSwitchView(FragmentsName.CategoryFragment)
    }
}