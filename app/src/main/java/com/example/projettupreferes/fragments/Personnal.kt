package com.example.projettupreferes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.projettupreferes.R
import com.example.projettupreferes.presenters.PersonnelPresenter

class Personnal : Fragment() {

    lateinit var presenter: PersonnelPresenter
    lateinit var recycler : RecyclerView
    lateinit var createCategory: Button
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

        createCategory = view.findViewById(R.id.CreateCategory)
        recycler = view.findViewById(R.id.listCategoriesRv)



        createCategory.setOnClickListener {
            presenter.goToCreateCategory("CreateCategory");
        }

        return view;
    }

    companion object {
        fun newInstance() =
            Personnal().apply {
                arguments = Bundle().apply {
                }
            }
    }
}