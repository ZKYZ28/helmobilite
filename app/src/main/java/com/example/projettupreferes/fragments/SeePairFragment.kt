package com.example.projettupreferes.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projettupreferes.R
import com.example.projettupreferes.activities.MainActivity
import com.example.projettupreferes.adaptater.PairsAdapter
import com.example.projettupreferes.presenters.SeePairPresenter
import com.example.projettupreferes.presenters.viewsInterface.fragments.ISeePairFragment
import java.util.*

class SeePairFragment : Fragment(), SeePairPresenter.IPairListScreen, ISeePairFragment, OnFragmentSelectedListener {

    lateinit var callback : ISelectPair
    lateinit var presenter: SeePairPresenter

    private lateinit var recycler : RecyclerView
    private var titleSeePair: TextView? = null

    override fun setSeePairPresenter(seePairPresenter: SeePairPresenter){
        this.presenter = seePairPresenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    interface ISelectPair {
        fun onSelectedPair(pairId: UUID?, position : Int)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_see_pair, container, false)

        recycler = view.findViewById(R.id.listPairsRv)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = PairsAdapter(presenter, callback)

        titleSeePair = view.findViewById(R.id.TitleSeePair)

        // Enregistrement de l'instance dans le MainActivity
        (activity as MainActivity).onFragmentSelectedListener = this

        return view;

    }




    /**
     * Méthode appelée par la MainActivity lorsque
     * le bouton retour présent dans le header
     * est pressé alors que le fragment actif est "SeePairFragment
     */
    override fun onFragmentSelected(fragment: Fragment) {
        if(fragment is SeePairFragment) {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override fun changeTitle(titleSeePair : String){
        this.titleSeePair?.text = titleSeePair
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadpairs()
    }

    override fun udpateView(){
        presenter.loadpairs()
        recycler.adapter = PairsAdapter(presenter, callback)
    }

    override fun onResume() {
        super.onResume()
        presenter.displayTitle()
    }

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

    override fun showErrorMessage(errorMessage : String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun destroyFragment() {
        if(isAdded){
            requireActivity().supportFragmentManager.popBackStack()
        }
    }





}