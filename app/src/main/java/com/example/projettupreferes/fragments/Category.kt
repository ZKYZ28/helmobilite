package com.example.projettupreferes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projettupreferes.R
import com.example.projettupreferes.presenters.CategoryPresenter
import com.example.projettupreferes.presenters.CreateCategoryPresenter
import java.util.*

private const val PLACEID = "placeId"

class Category : Fragment() {
    private var placeId: String? = null
    lateinit var categoryPresenter: CategoryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            placeId = it.getString(PLACEID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_category, container, false)
        categoryPresenter = CategoryPresenter(this);

        categoryPresenter.loadCategory(UUID.fromString(placeId))


        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(categoryId: String?) =
            Category().apply {
                arguments = Bundle().apply {
                    putString(PLACEID, categoryId)
                }
            }
    }
}