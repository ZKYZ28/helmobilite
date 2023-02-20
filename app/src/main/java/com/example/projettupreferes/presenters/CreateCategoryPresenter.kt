package com.example.projettupreferes.presenters

import com.example.projettupreferes.fragments.CreateCategory

class CreateCategoryPresenter(private val createCategory: CreateCategory, private val mainPresenter : MainActivityPresenter) {
    init {
        createCategory.presenter = this;
    }

    fun validateCreation(categoryName : String){
        if(categoryName.isEmpty()){
            createCategory.displayErrorMessage()
        }else{

        }
    }
}