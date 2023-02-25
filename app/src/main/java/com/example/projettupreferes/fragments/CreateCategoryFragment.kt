package com.example.projettupreferes.fragments

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.example.projettupreferes.R
import com.example.projettupreferes.presenters.CreateCategoryPresenter
import com.example.projettupreferes.presenters.viewsInterface.fragments.ICreateCategory


class CreateCategoryFragment : Fragment(), ICreateCategory {

    lateinit var createCategoryPresenter: CreateCategoryPresenter
    private lateinit var confirmCreationButton: Button
    private lateinit var imageCategoryButton: Button
    private lateinit var nameCategory: EditText
    private lateinit var imageSelectedCategory: ImageView
    private var selectedImageUri: Uri? = null

   // private lateinit var pickImage: ActivityResultLauncher<Intent>
    //private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Int>
    private lateinit var cameraXLauncher: ActivityResultLauncher<Uri>
    private val imagePickerContract = ImagePickerContract()
    private val cameraXContract = CameraXContract()
    private val REQUEST_IMAGE_CAPTURE = 0
    private val REQUEST_PICK_IMAGE = 1


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

        imagePickerLauncher = registerForActivityResult(imagePickerContract) { uri ->
            uri?.let {
                selectedImageUri = uri
                Log.d("URI LU ENNVOYE AU PRESENTER", uri.toString())
                createCategoryPresenter.temporarySelectedImageUri(it) }
            Log.d("URI LU EN DEHORS DU LET", uri.toString())
        }

        cameraXLauncher = registerForActivityResult(cameraXContract) { uri ->
            uri?.let {
                selectedImageUri = uri
                imageSelectedCategory.setImageURI(uri)
            }
        }

        confirmCreationButton.setOnClickListener {
            createCategoryPresenter.validateCreation(nameCategory.text.toString(), selectedImageUri)
        }

        imageCategoryButton.setOnClickListener {
            createCategoryPresenter.onPickImageClicked()
        }


        return view
    }

    fun launchImagePicker(mode: Int) {
        imagePickerLauncher.launch(mode)
    }


     fun showImagePickerDialog() {
        val items = arrayOf(getString(R.string.take_photo), getString(R.string.choose_from_gallery))
        val builder = AlertDialog.Builder(requireContext())
        builder.setItems(items) { dialog, which ->
            when (which) {
                0 -> launchImagePicker(REQUEST_IMAGE_CAPTURE)
                1 -> launchImagePicker(REQUEST_PICK_IMAGE)
            }
        }
        builder.show()
    }


    fun onImagePickError() {
        // Traiter les erreurs de sélection d'image
        Toast.makeText(requireContext(), "Error picking image", Toast.LENGTH_SHORT).show()
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


