package com.example.projettupreferes.presenters

import android.util.Log
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.CreateCategory
import com.example.projettupreferes.models.Category
import java.util.UUID.randomUUID

class CreateCategoryPresenter(private val createCategory: CreateCategory, private val mainPresenter : MainActivityPresenter) {
    init {
        createCategory.presenter = this;
    }


    fun validateCreation(categoryName : String){
        if(categoryName.isEmpty()){
            createCategory.displayErrorMessage()
        }else{
            val category = Category( randomUUID(), categoryName);
            TuPreferesRepository.getInstance()?.insertCategory(category)
            Log.d("BD OK ", "INSERTION DANS LA BD")
            displayALlCat()
        }
    }

    fun displayALlCat(){
        var categories = TuPreferesRepository.getInstance()?.getCategories();
        var test = arrayOf(categories)
        Log.d("SIZEORIGIN", test.size.toString())

        var cateList = categories?.value ?: emptyList()
        Log.d("SIZE", cateList.size.toString())
        for (cate in cateList){
            Log.d("CATE", cate.categoryName)
        }
    }
}