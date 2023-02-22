package com.example.projettupreferes.models

class GameManager(val statistics: Statistics,  val categoriesList: MutableList<Category>, var currentCategory : Category) {

    fun setNewCurrentCategory(currentCategoryNew: Category){
        currentCategory = currentCategoryNew
    }
}