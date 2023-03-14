package com.example.projettupreferes.adaptater

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projettupreferes.R
import com.example.projettupreferes.fragments.PersonnalFragment
import com.example.projettupreferes.presenters.PersonnelPresenter
import java.util.*

class CategoriesAdapter(
    private val personnelPresenter: PersonnelPresenter,
    var callBacks: PersonnalFragment.ISelectCategory) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>(){

    override fun getItemCount(): Int {
        return personnelPresenter.getItemCount()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.category_item_list, parent, false)
        return ViewHolder(view, callBacks)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        personnelPresenter.showCategoryOn(holder, position)
    }

    class ViewHolder(view: View, callBacks: PersonnalFragment.ISelectCategory) :
        RecyclerView.ViewHolder(view), View.OnClickListener,
        PersonnelPresenter.ICategoryItemScreen {

        private val callBacks: PersonnalFragment.ISelectCategory
        private val categoryNameTextView: TextView
        private val categoryImageView: ImageView
        private var id: UUID? = null

        init {
            this.callBacks = callBacks
            view.setOnClickListener(this)
            categoryNameTextView = view.findViewById(R.id.category_name)
            categoryImageView = view.findViewById(R.id.category_image)
        }

        override fun toString(): String {
            return super.toString() + " '" + id + "'"
        }

        override fun onClick(view: View) {
            callBacks.onSelectedCategory(id)
        }

        override fun showCategory(id: UUID?, name: String?, imagePath: String?) {
            this.id = id
            categoryNameTextView.text = name
            val uri = Uri.parse(imagePath)
            categoryImageView.setImageURI(uri)
        }
    }
}


/*Quand le recyclerView veut s'afficher la première fois il va demander getItemCournt(nbrItem dans la liste)
* Lorsqu'il va être créé la première fois il va appelé autant de fois onCreateViewHolder qu'il n'y a d'éléments
* Ensuite il va appeler onBindViewHolder autant de fois qu'il y a d'éléments*/