package com.example.projettupreferes.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projettupreferes.R
import com.example.projettupreferes.adaptater.CategoriesAdapter
import com.example.projettupreferes.adaptater.PairsAdapter
import com.example.projettupreferes.presenters.SeePairPresenter
import java.util.*

class SeePairFragment : Fragment(), SeePairPresenter.IPairListScreen {

    lateinit var callback : ISelectPair
    lateinit var presenter: SeePairPresenter

    private lateinit var recycler : RecyclerView

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

        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadpairs()
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
}