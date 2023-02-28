package com.example.projettupreferes.models

class GameManager(var statistics: Statistics) {

    private var _currentCategoryWithPaires : CategoryWithPaires = CategoryWithPaires(Category(categoryName = "", pathImage = ""), listOf<Paire>())

    private var _categoriesMap: MutableMap<String, Category> = mutableMapOf()

    var currentCategoryWithPaires: CategoryWithPaires
        get() = _currentCategoryWithPaires
        set(value) {
            _currentCategoryWithPaires = value
        }


    var categoriesMap: MutableMap<String, Category>
        get() = _categoriesMap
        set(value) {
            _categoriesMap = value
        }



}