package com.example.projettupreferes.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projettupreferes.R
import com.example.projettupreferes.adaptater.CategoriesAdapter
import com.example.projettupreferes.presenters.PersonnelPresenter
import java.util.*

class PersonnalFragment : Fragment(), PersonnelPresenter.ICategoryListScreen {

    lateinit var callback : ISelectCategory
    lateinit var presenter: PersonnelPresenter

    private lateinit var recycler : RecyclerView
    private lateinit var createCategory: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    interface ISelectCategory {
        fun onSelectedCategory(categoryId: UUID?)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_personnel, container, false)

        createCategory = view.findViewById(R.id.CreateCategory)

        recycler = view.findViewById(R.id.listCategoriesRv)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = CategoriesAdapter(presenter, callback)

        createCategory.setOnClickListener {
            presenter.goToCreateCategory(FragmentsName.CreateCategory)
        }



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadCategories()
    }

    companion object {
        fun newInstance() = PersonnalFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as ISelectCategory
    }

    override fun loadView() {
        if (view != null) {
            recycler.adapter = CategoriesAdapter(presenter, callback)
        }
    }
}
