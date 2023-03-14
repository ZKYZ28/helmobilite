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
import com.example.projettupreferes.activities.MainActivity
import com.example.projettupreferes.adaptater.CategoriesAdapter
import com.example.projettupreferes.presenters.PersonnelPresenter
import com.example.projettupreferes.presenters.viewsInterface.fragments.IPersonnalFragment
import java.util.*

class PersonnalFragment : Fragment(), PersonnelPresenter.ICategoryListScreen, IPersonnalFragment, OnFragmentSelectedListener {

    lateinit var callback : ISelectCategory
    lateinit var presenter: PersonnelPresenter

    private lateinit var recycler : RecyclerView
    private lateinit var createCategory: Button

    override fun setPersonnelPresenter(personnelPresenter: PersonnelPresenter){
        this.presenter = personnelPresenter
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


        createCategory.setOnClickListener {
            presenter.goToCreateCategory(FragmentsName.CreateCategory)
        }

        // Enregistrement de l'instance dans le MainActivity
        (activity as MainActivity).onFragmentSelectedListener = this


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

    override fun onFragmentSelected(fragment: Fragment) {
        if(fragment is PersonnalFragment) {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}
