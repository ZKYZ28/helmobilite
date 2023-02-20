package com.example.projettupreferes.presenters

import android.util.Log
import com.example.projettupreferes.create_category

class CreateCategoryPresenter(private val createCategory: create_category, private val mainPresenter : MainActivityPresenter) {
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