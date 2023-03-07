package com.example.projettupreferes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.example.projettupreferes.R
import com.example.projettupreferes.activities.MainActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HelpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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
        /* Bouton retour du téléphone */
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().supportFragmentManager.popBackStack()
        }

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
                    // Ajoutez ici des arguments si nécessaire
                }
            }
    }

    override fun onFragmentSelected(fragment: Fragment, previousFragment: Fragment?) {
        if(fragment is HelpFragment) {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

}