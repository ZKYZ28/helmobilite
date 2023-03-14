package com.example.projettupreferes.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.projettupreferes.R
import com.example.projettupreferes.activities.MainActivity
import com.example.projettupreferes.presenters.CreateCategoryPresenter
import com.example.projettupreferes.presenters.viewsInterface.fragments.ICreateCategoryFragment


class CreateCategoryFragment : FragmentWithImagePicker(), ICreateCategoryFragment, OnFragmentSelectedListener {

    lateinit var presenter: CreateCategoryPresenter
    private lateinit var confirmCreationButton: Button
    private lateinit var imageCategoryButton: Button
    private  var nameCategory: EditText? = null
    private lateinit var imageSelectedCategory: ImageView
    private var selectedImageUri: Uri? = null


    override fun setCreateCategoryPresenter(createCategoryPresenter: CreateCategoryPresenter){
        this.presenter = createCategoryPresenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create_category, container, false)


        confirmCreationButton = view.findViewById(R.id.ConfirmCreation)
        imageSelectedCategory = view.findViewById(R.id.ImageSelectedCategory)
        nameCategory = view.findViewById(R.id.NameCategory)
        imageCategoryButton = view.findViewById(R.id.ImageCategory)


        //Traitement de l'image si utilisateur a pris galerie ou photo
        imagePickerLauncher = registerForActivityResult(imagePickerContract) { uri ->
            if (uri != null) {
                    selectedImageUri = uri
                presenter.temporarySelectedImageUri(uri)
                }
            }

        confirmCreationButton.setOnClickListener {
            presenter.validateCreation(nameCategory?.text.toString(), selectedImageUri, requireContext())
        }

        imageCategoryButton.setOnClickListener {
            presenter.onPickImageClicked()
        }

        /* Bouton retour application */
        (activity as MainActivity).onFragmentSelectedListener = this


        return view
    }

    override fun showImagePicker() {
        super.showImagePickerDialog(0)
    }

    override fun resetCategoryName() {
        nameCategory?.setText("")
    }

    override fun showSelectedImage(selectedImageUri: Uri) {
        imageSelectedCategory.setImageURI(selectedImageUri)
    }

    companion object {
        fun newInstance() = CreateCategoryFragment()
    }

    /**
     * Méthode qui permet d'afficher un message d'erreur
     * indiquant que tous les champs sont obligatoires
     */
    override fun showErrorMessage(errorMessage: String) {
        super.displayErrorMessage(errorMessage)
    }

    override fun close() {
        selectedImageUri = null
        requireActivity().supportFragmentManager.popBackStack()
    }

    /**
     * Méthode appelée lorsqu'on utilise le bouton retour de l'app
     */
    override fun onFragmentSelected(fragment: Fragment) {
        nameCategory?.setText("");
        if(fragment is CreateCategoryFragment) {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

}


