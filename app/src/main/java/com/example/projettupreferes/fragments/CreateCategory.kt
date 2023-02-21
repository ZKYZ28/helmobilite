package com.example.projettupreferes.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.projettupreferes.R
import com.example.projettupreferes.presenters.CreateCategoryPresenter
import com.example.projettupreferes.presenters.viewsInterface.fragments.ICreateCategory

class CreateCategory : Fragment(), ICreateCategory {

        lateinit var categoryPresenter: CreateCategoryPresenter
        private lateinit var confirmCreationButton: Button
        private lateinit var imageCategoryButton: Button
        private lateinit var nameCategory: EditText
        private lateinit var imageSelectedCategory: ImageView
        private lateinit var errorMessage: TextView

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            imageCategoryButton.setOnClickListener {
                categoryPresenter.pickImage()
            }
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater.inflate(R.layout.fragment_create_category, container, false)

            confirmCreationButton = view.findViewById(R.id.ConfirmCreation)
            errorMessage = view.findViewById(R.id.ErrorMessage)
            imageSelectedCategory = view.findViewById(R.id.ImageSelectedCategory)
            nameCategory = view.findViewById(R.id.NameCategory)
            imageCategoryButton = view.findViewById(R.id.ImageCategory)

            confirmCreationButton.setOnClickListener {
                categoryPresenter.validateCreation(nameCategory.text.toString(), imageSelectedCategory.tag as? Uri)
            }

            return view
        }

        override fun showErrorMessage(errorMessage: String) {
            displayErrorMessage(errorMessage)
        }

        override fun showImage(imageUri: Uri?) {
            imageUri?.let {
                imageSelectedCategory.setImageURI(it)
                imageSelectedCategory.tag = it
            }
        }

        override fun close() {
            requireActivity().supportFragmentManager.popBackStack()
        }


        companion object {
            @JvmStatic
            fun newInstance() =
                CreateCategory().apply {
                    arguments = Bundle().apply {

                    }
                }
        }


    /**
     * Méthode qui permet d'afficher un message d'erreur
     * indiquant que tous les champs sont obligatoires
     */
    fun displayErrorMessage(errorMsg: String){
        errorMessage.text = errorMsg
        errorMessage.visibility = View.VISIBLE;

        errorMessage.postDelayed({
            errorMessage.visibility = View.INVISIBLE
        }, 3000)
    }

}


