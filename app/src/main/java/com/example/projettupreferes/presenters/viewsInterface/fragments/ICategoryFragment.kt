package com.example.projettupreferes.presenters.viewsInterface.fragments

interface ICategoryFragment {
    fun displayPopUpConfirmation()
    fun displayCategoryInformation(categoryName : String, categoryImagePath : String)
    fun close()
    fun showErrorMessage(errorMessage: String)
}