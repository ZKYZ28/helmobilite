package com.example.projettupreferes.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.projettupreferes.R
import com.example.projettupreferes.presenters.CreateCategoryPresenter


class CreateCategory : Fragment() {

    lateinit var presenter: CreateCategoryPresenter;

    private lateinit var confirmCreationButton: Button
    private lateinit var imageCategoryButton: Button
    private lateinit var nameCategory: EditText
    private lateinit var imageSelectedCategory : ImageView
    private lateinit var errorMessage : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_category, container, false);

        confirmCreationButton = view.findViewById(R.id.ConfirmCreation)
        errorMessage = view.findViewById(R.id.ErrorMessage)
        imageSelectedCategory = view.findViewById(R.id.ImageSelectedCategory);
        nameCategory = view.findViewById(R.id.NameCategory)
        imageCategoryButton = view.findViewById(R.id.ImageCategory)


        confirmCreationButton.setOnClickListener {
            presenter.validateCreation(nameCategory.text.toString())
        }

        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                imageSelectedCategory = view.findViewById(R.id.ImageSelectedCategory)
                Log.d("IMAGE", uri.toString())
                imageSelectedCategory.setImageURI(uri)
            }
        }

        imageCategoryButton.setOnClickListener {
            pickImage.launch("image/*")
        }

        return view;
    }

    /**
     * Méthode qui permet d'afficher un message d'erreur
     * indiquant que tous les champs sont obligatoires
     */
    fun displayErrorMessage(){
        errorMessage.visibility = View.VISIBLE;

        errorMessage.postDelayed({
            errorMessage.visibility = View.INVISIBLE
        }, 3000)
    }


    companion object {
        const val PICK_IMAGE_REQUEST = 1
        fun newInstance() =
            CreateCategory().apply {
                arguments = Bundle().apply {
                }
            }
    }
}