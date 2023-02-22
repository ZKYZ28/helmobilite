package com.example.projettupreferes.presenters

import android.util.Log
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.Personnal
import com.example.projettupreferes.models.Category
import com.example.projettupreferes.models.GameManager
import java.util.*

class PersonnelPresenter(private val personnel: Personnal, private val mainPresenter : MainActivityPresenter, private val gameManager: GameManager) {
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