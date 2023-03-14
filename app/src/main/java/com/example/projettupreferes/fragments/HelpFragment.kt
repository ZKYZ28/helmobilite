package com.example.projettupreferes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projettupreferes.R
import com.example.projettupreferes.activities.MainActivity


class HelpFragment : Fragment(), OnFragmentSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Enregistrement de l'instance dans le MainActivity
        (activity as MainActivity).onFragmentSelectedListener = this
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HelpFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onFragmentSelected(fragment: Fragment) {
        if(fragment is HelpFragment) {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }


}