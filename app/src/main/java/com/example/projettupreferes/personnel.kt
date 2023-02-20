package com.example.projettupreferes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.projettupreferes.presenters.MainFragmentPresenter
import com.example.projettupreferes.presenters.PersonnelPresenter

class personnel : Fragment() {

    lateinit var presenter: PersonnelPresenter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_personnel, container, false);

        val createCategoryButton = view.findViewById<Button>(R.id.CreateCategory)
        createCategoryButton.setOnClickListener {
            presenter.goToCreateCategory("CreateCategory");
        }

        return view;
    }

    companion object {
        fun newInstance() =
            personnel().apply {
                arguments = Bundle().apply {
                }
            }
    }
}