package com.example.projettupreferes.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.projettupreferes.R
import com.example.projettupreferes.presenters.EditCategoryPresenter

class EditCategoryFragment : FragmentWithImagePicker() {

    lateinit var presenter : EditCategoryPresenter
    private lateinit var confirmModification: Button
    private lateinit var imageCategoryEdit: Button
    private lateinit var nameCategoryEdit: EditText
    private lateinit var imageSelectedCategoryEdit: ImageView
    private var selectedImageUri: Uri? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_category, container, false)

        confirmModification = view.findViewById(R.id.ConfirmModification)
        imageCategoryEdit = view.findViewById(R.id.ImageCategoryEdit)
        nameCategoryEdit = view.findViewById(R.id.NameCategoryEdit)
        imageSelectedCategoryEdit = view.findViewById(R.id.ImageSelectedCategoryEdit)

        //Traitement de l'image si utilisateur a pris galerie ou photo
        imagePickerLauncher = registerForActivityResult(imagePickerContract) { uri ->
            if (uri != null) {
                selectedImageUri = uri
                presenter.temporarySelectedImageUri(uri)
            }
        }


        confirmModification.setOnClickListener {
            presenter.validateModification(nameCategoryEdit.text.toString(), selectedImageUri)
        }

        imageCategoryEdit.setOnClickListener{
            presenter.onPickImageClicked()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getCurrentCategory()
    }

    fun displayInformationInFields(categoryName: String, imagePath: Uri) {
        nameCategoryEdit.setText(categoryName)
        imageSelectedCategoryEdit.setImageURI(imagePath)
        selectedImageUri = imagePath;
    }

    /**
     * Méthode qui permet d'afficher un message d'erreur
     * indiquant que tous les champs sont obligatoires
     */
    fun showErrorMessage(errorMessage: String) {
        super.displayErrorMessage(errorMessage)
    }

    /**
     * Affichage de la boite de dialogue comprenant
     * la galerie et l'appareil photo
     */
    fun showImagePicker() {
        super.showImagePickerDialog(0)
    }

    fun close() {
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        //TODO : tendre vers cette ligne à l'avenir
       // requireActivity().supportFragmentManager.popBackStack("categoryFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }


    fun showSelectedImage(selectedImageUri: Uri) {
        imageSelectedCategoryEdit.setImageURI(selectedImageUri)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            EditCategoryFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}