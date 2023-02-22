package com.example.projettupreferes.presenters

import com.example.projettupreferes.fragments.PersonnalFragment
import com.example.projettupreferes.models.Category
import com.example.projettupreferes.models.GameManager
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