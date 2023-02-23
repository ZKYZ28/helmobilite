package com.example.projettupreferes.presenters

import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.PersonnalFragment
import com.example.projettupreferes.models.Category
import com.example.projettupreferes.models.GameManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class PersonnelPresenter(private val personnel: PersonnalFragment, private val mainPresenter : MainActivityPresenter, private val gameManager: GameManager) {
    init {
        personnel.presenter = this;
    }

    interface ICategoryItemScreen {
        fun showCategory(id: UUID?, name: String?, imagePath: String?)
    }

    interface ICategoryListScreen {
        fun loadView()
    }

    //**************************************\\

    fun goToCreateCategory(desiredFragment: String) {
        mainPresenter.requestSwitchView(desiredFragment);
    }


    fun loadCategories() {
        GlobalScope.launch(Main) {
            TuPreferesRepository.getInstance()?.getCategoriesWithPairesList()
                ?.collect { categoriesWithPaires ->
                    val categories = categoriesWithPaires.map { it.category }
                    this@PersonnelPresenter.gameManager.categoriesList = categories.toMutableList()
                    personnel.loadView()
                }
        }
    }





    fun getItemCount(): Int {
        if(gameManager.categoriesList == null){
            return 0
        }
        return gameManager.categoriesList.size
    }

    fun showCategoryOn(
        holder: ICategoryItemScreen,
        position: Int
    ) {
        val p: Category = gameManager.categoriesList[position]
        holder.showCategory(p.id, p.categoryName, p.pathImage)
    }
}