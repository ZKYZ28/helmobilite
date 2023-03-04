package com.example.projettupreferes.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.example.projettupreferes.R
import com.example.projettupreferes.activities.MainActivity
import com.example.projettupreferes.presenters.CreateCategoryPresenter
import com.example.projettupreferes.presenters.viewsInterface.fragments.ICreateCategory


class CreateCategoryFragment : FragmentWithImagePicker(), ICreateCategory, OnFragmentSelectedListener {

    lateinit var createCategoryPresenter: CreateCategoryPresenter
    private lateinit var confirmCreationButton: Button
    private lateinit var imageCategoryButton: Button
    private lateinit var nameCategory: EditText
    private lateinit var imageSelectedCategory: ImageView
    private var selectedImageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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
                    createCategoryPresenter.temporarySelectedImageUri(uri)
                }
            }

        confirmCreationButton.setOnClickListener {
            createCategoryPresenter.validateCreation(nameCategory.text.toString(), selectedImageUri)
        }

        imageCategoryButton.setOnClickListener {
            createCategoryPresenter.onPickImageClicked()
        }

        /* Bouton retour du téléphone */
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().supportFragmentManager.popBackStack()
        }

        /* Bouton retour application */
        (activity as MainActivity).onFragmentSelectedListener = this


        return view
    }



    fun showImagePicker() {
        super.showImagePickerDialog(0)
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
        nameCategory.setText("");
        requireActivity().supportFragmentManager.popBackStack()
    }

    override fun onFragmentSelected(fragment: Fragment, previousFragment: Fragment?) {
        if(fragment is CreateCategoryFragment) {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

}


