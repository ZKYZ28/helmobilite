package com.example.projettupreferes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.projettupreferes.R
import com.example.projettupreferes.presenters.MainFragmentPresenter


class HomeFragment : Fragment() {

    lateinit var presenter: MainFragmentPresenter;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Créer la vue du fragment en inflatant un layout XML
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        // Trouver le bouton dans la vue
        val normalGameButton = view.findViewById<ConstraintLayout>(R.id.normalGame)

        // Ajouter un écouteur de clic au bouton
        normalGameButton.setOnClickListener {
            presenter.goToNormalGame(FragmentsName.NormalGame);
        }

        // Trouver le bouton dans la vue
        val personnelButton = view.findViewById<ConstraintLayout>(R.id.personnel)

        // Ajouter un écouteur de clic au bouton
        personnelButton.setOnClickListener {
            presenter.goToPersonnal(FragmentsName.Personnal);
        }

        return view
    }

    companion object {
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}