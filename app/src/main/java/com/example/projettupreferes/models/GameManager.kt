package com.example.projettupreferes.models

import java.util.*

class GameManager() {

    private var _currentCategoryWithPaires : CategoryWithPaires = CategoryWithPaires(Category(categoryName = "", pathImage = ""), listOf<Paire>())

    private var _categoriesMap: MutableMap<String, Category> = mutableMapOf()

    var _statistics : Statistics = Statistics(UUID.fromString("b0a51d0e-20b7-4c84-8f84-2cf96eeb9a8a"), 0, 0, 0, 0)

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

        var statistics : Statistics
            get() = _statistics
        set(value) {
            _statistics = value
        }

}