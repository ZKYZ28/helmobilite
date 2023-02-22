package com.example.projettupreferes.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.projettupreferes.R
import com.example.projettupreferes.presenters.CategoryPresenter
import java.util.*

private const val CATEGORYID = "categoryId"

class CategoryFragment : Fragment() {
    private var categoryId: String? = null
    lateinit var categoryPresenter: CategoryPresenter

    lateinit var imageCategoryIv : ImageView
    lateinit var edtiCategoryButton : Button
    lateinit var addPaireButton : Button
    lateinit var seePairesButton : Button
    lateinit var deleteCategoryButton : Button
    lateinit var categoryNameEt : TextView

    lateinit var playGameCl : ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryId = it.getString(CATEGORYID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_category, container, false)
        categoryPresenter.setCategoryFragment(this);

        imageCategoryIv = view.findViewById(R.id.imageCategory)
        edtiCategoryButton = view.findViewById(R.id.edtiCategory)
        addPaireButton = view.findViewById(R.id.addPaire)
        seePairesButton = view.findViewById(R.id.seePaires)
        deleteCategoryButton = view.findViewById(R.id.deleteCategory)
        categoryNameEt = view.findViewById(R.id.categoryName)
        playGameCl = view.findViewById(R.id.playGame)

        edtiCategoryButton.setOnClickListener {
            categoryPresenter.editCategory()
        }

        addPaireButton.setOnClickListener {

        }

        seePairesButton.setOnClickListener {

        }

        deleteCategoryButton.setOnClickListener {
            categoryPresenter.deleteCategory(UUID.fromString(categoryId))
        }

        playGameCl.setOnClickListener {

        }

        categoryPresenter.loadCategory(UUID.fromString(categoryId))

        return view
    }

    fun displayCategoryInformation(categoryName : String, categoryImagePath : String){
        imageCategoryIv.setImageURI(Uri.parse(categoryImagePath))
        categoryNameEt.text = categoryName
    }

    companion object {
        @JvmStatic
        fun newInstance(categoryId: String?, categoryPresenterInit: CategoryPresenter) =
            CategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(CATEGORYID, categoryId)
                    categoryPresenter = categoryPresenterInit;
                }
            }
    }
}