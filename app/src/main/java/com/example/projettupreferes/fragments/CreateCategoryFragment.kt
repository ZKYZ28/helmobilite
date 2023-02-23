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
import androidx.fragment.app.Fragment
import com.example.projettupreferes.R
import com.example.projettupreferes.presenters.CreateCategoryPresenter
import com.example.projettupreferes.presenters.viewsInterface.fragments.ICreateCategory
import androidx.activity.result.contract.ActivityResultContracts


class CreateCategoryFragment : Fragment(), ICreateCategory {

    lateinit var createCategoryPresenter: CreateCategoryPresenter
    private lateinit var confirmCreationButton: Button
    private lateinit var imageCategoryButton: Button
    private lateinit var nameCategory: EditText
    private lateinit var imageSelectedCategory: ImageView
    private var selectedImageUri: Uri? = null

    private lateinit var pickImage: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                selectedImageUri = result.data?.data
                if (selectedImageUri != null) {
                    createCategoryPresenter.temporarySelectedImageUri(requireContext(), selectedImageUri!!)
                }
            }
        }
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

        confirmCreationButton.setOnClickListener {
            createCategoryPresenter.validateCreation(nameCategory.text.toString(), selectedImageUri)
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
        fun newInstance() = CreateCategoryFragment()
    }

    /**
     * Méthode qui permet d'afficher un message d'erreur
     * indiquant que tous les champs sont obligatoires
     */
    override fun showErrorMessage(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }



    override fun close() {
        nameCategory.setText("");
        requireActivity().supportFragmentManager.popBackStack()
    }

    override fun showSelectedImage(selectedImageUri: Uri) {
        imageSelectedCategory.setImageURI(selectedImageUri)
    }
}


