package com.example.projettupreferes.models

class GameManager(val statistics: Statistics) {

    private var _currentCategoryWithPaires : CategoryWithPaires = CategoryWithPaires(Category(categoryName = "", pathImage = ""), listOf<Paire>())

    private var _categoriesList: MutableList<Category> = mutableListOf()

    var categoryWithPaires: CategoryWithPaires
        get() = _currentCategoryWithPaires
        set(value) {
            _currentCategoryWithPaires = value
        }


    var categoriesList: MutableList<Category>
        get() = _categoriesList
        set(value) {
            _categoriesList = value
        }



}