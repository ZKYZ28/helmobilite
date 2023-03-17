package com.example.projettupreferes.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.projettupreferes.R
import com.example.projettupreferes.activities.MainActivity
import com.example.projettupreferes.presenters.CategoryPresenter
import com.example.projettupreferes.presenters.viewsInterface.fragments.ICategoryFragment
import java.util.*


class CategoryFragment : Fragment(), ICategoryFragment, OnFragmentSelectedListener {
    private var categoryId: UUID? = null
    lateinit var categoryPresenter: CategoryPresenter

    lateinit var imageCategoryIv: ImageView
    lateinit var edtiCategoryButton: Button
    lateinit var addPaireButton: Button
    lateinit var seePairesButton: Button
    lateinit var deleteCategoryButton: Button
    lateinit var categoryNameEt: TextView

    lateinit var playGameCl: ConstraintLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        categoryPresenter.setCategoryFragment(this);

        imageCategoryIv = view.findViewById(R.id.imageCategory)
        edtiCategoryButton = view.findViewById(R.id.edtiCategory)
        addPaireButton = view.findViewById(R.id.addPaire)
        seePairesButton = view.findViewById(R.id.seePaires)
        deleteCategoryButton = view.findViewById(R.id.deleteCategory)
        categoryNameEt = view.findViewById(R.id.categoryName)
        playGameCl = view.findViewById(R.id.playGame)

        edtiCategoryButton.setOnClickListener {
            categoryPresenter.editCategory()
        }

        addPaireButton.setOnClickListener {
            categoryPresenter.switchToAddPair()
        }

        seePairesButton.setOnClickListener {
            categoryPresenter.switchToSeePairs()
        }

        deleteCategoryButton.setOnClickListener {
            categoryPresenter.requestToDeleteCategory()
        }

        playGameCl.setOnClickListener {
            categoryPresenter.switchToPlayGame()
        }

        categoryPresenter.loadCategory(categoryId)

        /* Bouton retour application */
        (activity as MainActivity).onFragmentSelectedListener = this


        return view
    }

    override fun displayPopUpConfirmation(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirmation")
        builder.setMessage("Voulez-vous vraiment supprimer la catégorie ?")
        builder.setPositiveButton("OK") { _, _ ->
            categoryPresenter.deleteCategory()
        }
        builder.setNegativeButton("Annuler", null)
        val dialog = builder.create()
        dialog.show()
    }

    override fun displayCategoryInformation(categoryName: String, categoryImagePath: String) {
        imageCategoryIv.setImageURI(Uri.parse(categoryImagePath))
        categoryNameEt.text = categoryName
    }

    override fun close() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    /**
     * Méthode qui permet d'afficher un message d'erreur
     * indiquant que tous les champs sont obligatoires
     */
    override fun showErrorMessage(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun newInstance(categoryUUID: UUID?, categoryPresenterInit: CategoryPresenter) =
            CategoryFragment().apply {
                arguments = Bundle().apply {
                    categoryPresenter = categoryPresenterInit;
                    categoryId = categoryUUID
                    if (categoryId != null) {
                        categoryPresenter.loadCategory(categoryUUID)
                        categoryPresenter.loadPair(categoryUUID)
                    }
                }
            }
    }

    override fun onFragmentSelected(fragment: Fragment) {
        if (fragment is CategoryFragment) {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}