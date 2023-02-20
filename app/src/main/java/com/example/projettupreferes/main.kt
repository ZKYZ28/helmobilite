package com.example.projettupreferes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.projettupreferes.presenters.MainFragmentPresenter


class main : Fragment() {

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
            presenter.goToNormalGame("NormalGame");
        }

        return view
    }

    companion object {
        fun newInstance() =
            main().apply {
                arguments = Bundle().apply {
                }
            }
    }
}