package com.example.projettupreferes.models

import java.util.*

class GameManager() {

    private val defaultStatisticsId = UUID.fromString("b0a51d0e-20b7-4c84-8f84-2cf96eeb9a8a")
    private val defaultStatisticsValue = 0

    var currentCategoryWithPaires : CategoryWithPaires = CategoryWithPaires(Category(categoryName = "", pathImage = ""), emptyList())
    var categoriesMap = mutableMapOf<String, Category>()
    var statistics = Statistics(defaultStatisticsId, defaultStatisticsValue, defaultStatisticsValue, defaultStatisticsValue, defaultStatisticsValue)

}