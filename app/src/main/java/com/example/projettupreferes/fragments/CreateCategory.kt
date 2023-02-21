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
import androidx.fragment.app.Fragment
import com.example.projettupreferes.R
import com.example.projettupreferes.presenters.CreateCategoryPresenter
import com.example.projettupreferes.presenters.viewsInterface.fragments.ICreateCategory
import androidx.activity.result.contract.ActivityResultContracts


class CreateCategory : Fragment(), ICreateCategory {

    lateinit var categoryPresenter: CreateCategoryPresenter
    private lateinit var confirmCreationButton: Button
    private lateinit var imageCategoryButton: Button
    private lateinit var nameCategory: EditText
    private lateinit var imageSelectedCategory: ImageView
    private lateinit var errorMessage: TextView
    private var selectedImageUri: Uri? = null

    private val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            selectedImageUri = result.data?.data
            if (selectedImageUri != null) {
                categoryPresenter.temporarySelectedImageUri(requireContext(), selectedImageUri!!)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create_category, container, false)


        confirmCreationButton = view.findViewById(R.id.ConfirmCreation)
        errorMessage = view.findViewById(R.id.ErrorMessage)
        imageSelectedCategory = view.findViewById(R.id.ImageSelectedCategory)
        nameCategory = view.findViewById(R.id.NameCategory)
        imageCategoryButton = view.findViewById(R.id.ImageCategory)

        confirmCreationButton.setOnClickListener {
            categoryPresenter.validateCreation(nameCategory.text.toString(), selectedImageUri)
        }

        imageCategoryButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            pickImage.launch(intent)
        }


        return view
    }


    companion object {
        private val PICK_IMAGE_REQUEST = 1
        fun newInstance() = CreateCategory()
    }

    /**
     * Méthode qui permet d'afficher un message d'erreur
     * indiquant que tous les champs sont obligatoires
     */
    override fun showErrorMessage(errorMessage: String) {
        //displayErrorMessage(errorMessage)
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }



    override fun close() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    override fun showSelectedImage(selectedImageUri: Uri) {
        imageSelectedCategory.setImageURI(selectedImageUri)
    }
    /**
     * Méthode qui permet d'afficher un message d'erreur
     * indiquant que tous les champs sont obligatoires
     */
    fun displayErrorMessage(errorMsg: String) {
        errorMessage.text = errorMsg
        errorMessage.visibility = View.VISIBLE;

        errorMessage.postDelayed({
            errorMessage.visibility = View.INVISIBLE
        }, 3000)
    }
}


