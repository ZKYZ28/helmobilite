package com.example.projettupreferes.models

class GameManager(val statistics: Statistics) {

    private lateinit var _currentCategory: Category

    private var _categoriesList: MutableList<Category> = mutableListOf()

    var currentCategory: Category
        get() = _currentCategory
        set(value) {
            _currentCategory = value
        }

    var categoriesList: MutableList<Category>
        get() = _categoriesList
        set(value) {
            _categoriesList = value
        }


}