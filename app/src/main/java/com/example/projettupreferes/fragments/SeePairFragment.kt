package com.example.projettupreferes.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projettupreferes.R
import com.example.projettupreferes.activities.MainActivity
import com.example.projettupreferes.adaptater.PairsAdapter
import com.example.projettupreferes.presenters.SeePairPresenter
import java.util.*

class SeePairFragment : Fragment(), SeePairPresenter.IPairListScreen, OnFragmentSelectedListener {

    lateinit var callback : ISelectPair
    lateinit var presenter: SeePairPresenter

    private lateinit var recycler : RecyclerView
    lateinit var titleSeePair: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    interface ISelectPair {
        fun onSelectedPair(pairId: UUID?)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_see_pair, container, false)

        recycler = view.findViewById(R.id.listPairsRv)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = PairsAdapter(presenter, callback)

        titleSeePair = view.findViewById(R.id.TitleSeePair)

        // Enregistrement de l'instance dans le MainActivity
        (activity as MainActivity).onFragmentSelectedListener = this


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().supportFragmentManager.popBackStack()
          //  presenter.goToCategoryFragment()
        }

        return view;

    }

    /**
     * Méthode appelée par la MainActivity lorsque
     * le bouton retour présent dans le header
     * est pressé alors que le fragment actif est "SeePairFragment
     */
    override fun onFragmentSelected(fragment: Fragment, previousFragment: Fragment?) {
        if(fragment is SeePairFragment) {
           // presenter.goToCategoryFragment()
            requireActivity().supportFragmentManager.popBackStack()
            //Todo : destroy ?
        }
    }

    fun changeTitle(titleSeePair : String){
        this.titleSeePair.text = titleSeePair
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadpairs()
    }

    override fun onResume() {
        super.onResume()
        presenter.displayTitle()
        presenter.switchWhenListIsEmpty()
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//
//        if(context is MainActivity) {
//            context.onFragmentSelectedListener = this
//        }
//    }


    companion object {
        @JvmStatic
        fun newInstance() = SeePairFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as ISelectPair
    }

    override fun loadView() {
        if (view != null) {
            recycler.adapter = PairsAdapter(presenter, callback)
        }
    }

    fun showErrorMessage(errorMessage : String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }


}