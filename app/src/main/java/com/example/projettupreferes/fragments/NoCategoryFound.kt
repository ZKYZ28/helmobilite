package com.example.projettupreferes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projettupreferes.R
import com.example.projettupreferes.presenters.NoCategoryFoundPresenter
import com.example.projettupreferes.presenters.NormalGamePresenter

class NoCategoryFound : Fragment() {

    lateinit var presenter: NoCategoryFoundPresenter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_no_category_found, container, false)
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