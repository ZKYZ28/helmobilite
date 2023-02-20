package com.example.projettupreferes.presenters

import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.CreateCategory
import com.example.projettupreferes.models.Category

class CreateCategoryPresenter(private val createCategory: CreateCategory, private val mainPresenter : MainActivityPresenter) {
    init {
        createCategory.presenter = this;
    }


    fun validateCreation(categoryName : String){
        if(categoryName.isEmpty()){
            createCategory.displayErrorMessage()
        }else{
            val category = Category(categoryName);
            TuPreferesRepository.getInstance()
        }
    }
}