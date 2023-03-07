package com.example.projettupreferes.presenters.viewsInterface.fragments

import com.example.projettupreferes.presenters.PlayGamePresenter

interface IPlayGameFragment {
    fun setPlayGamePresenter(playGamePresenter: PlayGamePresenter)
    fun displayChoiceOne(choiceInformation : String, isText : Boolean)
    fun displayChoiceTwo(choiceInformation : String, isText : Boolean)
}