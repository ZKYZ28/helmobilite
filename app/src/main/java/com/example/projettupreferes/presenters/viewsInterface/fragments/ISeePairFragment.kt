package com.example.projettupreferes.presenters.viewsInterface.fragments

import com.example.projettupreferes.presenters.SeePairPresenter

interface ISeePairFragment {
    fun udpateView()
    fun setSeePairPresenter(seePairPresenter: SeePairPresenter)
    fun changeTitle(titleSeePair : String)
    fun showErrorMessage(s: String)
    fun destroyFragment()


}