package com.example.projettupreferes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.projettupreferes.R
import com.example.projettupreferes.presenters.StatisticsPresenter

class StatisticsFragment : Fragment() {

    lateinit var presenter : StatisticsPresenter

    lateinit var nbrPairs : TextView
    lateinit var nbrSwipes : TextView
    lateinit var nbrCategories : TextView
    lateinit var nbrGamePlayed : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_statistiques, container, false)

        nbrPairs = view.findViewById(R.id.nbrPairs)
        nbrSwipes = view.findViewById(R.id.nbrSwipes)
        nbrCategories = view.findViewById(R.id.nbrCategories)
        nbrGamePlayed = view.findViewById(R.id.nbrGamePlayed)

        presenter.udpateStatisticsInformation()

        return view;
    }

    fun udpateDisplayStatistics(nbrPairs : Int, nbrSwipes : Int, nbrCategories : Int, nbrGamePlayed : Int){
        this.nbrPairs.text = nbrPairs.toString()
        this.nbrSwipes.text = nbrSwipes.toString()
        this.nbrCategories.text = nbrCategories.toString()
        this.nbrGamePlayed.text = nbrGamePlayed.toString()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            StatisticsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}