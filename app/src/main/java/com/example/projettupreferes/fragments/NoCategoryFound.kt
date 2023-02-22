package com.example.projettupreferes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.projettupreferes.R
import com.example.projettupreferes.presenters.NoCategoryFoundPresenter
import com.example.projettupreferes.presenters.NormalGamePresenter
import com.google.android.material.bottomnavigation.BottomNavigationView

class NoCategoryFound : Fragment() {

    lateinit var presenter: NoCategoryFoundPresenter;
    private lateinit var createCategoryButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_no_category_found, container, false)

        createCategoryButton = view.findViewById(R.id.CreateCategory)
        createCategoryButton.setOnClickListener {
            presenter.switchToCreateCategory()
        }


        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            NoCategoryFound().apply {
                arguments = Bundle().apply {

                }
            }
    }
}