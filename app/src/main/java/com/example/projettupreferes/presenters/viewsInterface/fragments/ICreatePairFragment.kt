package com.example.projettupreferes.presenters.viewsInterface.fragments

import com.example.projettupreferes.presenters.CreatePairPresenter

interface ICreatePairFragment {

    fun clearTextChoice()
     fun setCreatePairPresenter(presenter : CreatePairPresenter)
    fun deactivateSelecteImageChoiceOne()
    fun activateSelecteImageChoiceOne()
    fun deactivateSelecteImageChoiceTwo()
    fun activateSelecteImageChoiceTwo()
    fun onDeleteImageChoiceOne()
    fun onDeleteImageChoiceTwo()
    fun showErrorMessage(errorMessage: String)
    fun close()
    fun showImagePicker(choiceNumber: Int)
}