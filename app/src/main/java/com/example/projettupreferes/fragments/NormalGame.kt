package com.example.projettupreferes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.projettupreferes.R
import com.example.projettupreferes.presenters.NormalGamePresenter
import com.example.projettupreferes.presenters.PersonnelPresenter
import com.google.android.material.bottomnavigation.BottomNavigationView

class NormalGame : Fragment() {

    lateinit var presenter: NormalGamePresenter;

    private lateinit var choiceOne: ConstraintLayout
    private lateinit var choiceTwo: ConstraintLayout
    private lateinit var textChoiceOne: TextView
    private lateinit var textChoiceTwo : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_normal_game, container, false)

        //Init des composants
        choiceOne = view.findViewById(R.id.choiceOne)
        choiceTwo = view.findViewById(R.id.choiceTwo)
        textChoiceOne = view.findViewById(R.id.textChoiceOne)
        textChoiceTwo = view.findViewById(R.id.textChoiceTwo)


        //Ajout des listeners
        choiceOne.setOnClickListener {
            presenter.onChoiceSelected();
        }

        choiceTwo.setOnClickListener {
            presenter.onChoiceSelected();
        }

        return view
    }

    /**
     * Méthode qui permet de changer le text affichée pour les choix présents
     * textChoiceOne qui est le text du premier choix
     * textChoiceTwo qui est le text du deuxième choix
     */
    fun changeChoiceText(textChoiceOne : String, textChoiceTwo: String){
        this.textChoiceOne.text = textChoiceOne
        this.textChoiceTwo.text = textChoiceTwo
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            NormalGame().apply {
                arguments = Bundle().apply {
                }
            }

    }
}