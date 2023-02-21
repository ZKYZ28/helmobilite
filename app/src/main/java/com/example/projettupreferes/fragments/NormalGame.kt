package com.example.projettupreferes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projettupreferes.R
import com.example.projettupreferes.presenters.NormalGamePresenter
import com.example.projettupreferes.presenters.PersonnelPresenter

class NormalGame : Fragment() {

    lateinit var presenter: NormalGamePresenter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_normal_game, container, false)
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