package com.example.projettupreferes.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.example.projettupreferes.R
import com.example.projettupreferes.activities.MainActivity
import com.example.projettupreferes.presenters.EditCategoryPresenter
import com.example.projettupreferes.presenters.viewsInterface.fragments.IEditCategoryFragment

class EditCategoryFragment : FragmentWithImagePicker(), IEditCategoryFragment, OnFragmentSelectedListener {

    lateinit var presenter : EditCategoryPresenter
    private lateinit var confirmModification: Button
    private lateinit var imageCategoryEdit: Button
    private lateinit var nameCategoryEdit: EditText
    private lateinit var imageSelectedCategoryEdit: ImageView
    private var selectedImageUri: Uri? = null


    override fun setEditCategoryPresenter(editCategoryPresenter : EditCategoryPresenter){
        this.presenter = editCategoryPresenter
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
            presenter.validateModification(nameCategoryEdit.text.toString(), selectedImageUri, requireContext())
        }

        imageCategoryEdit.setOnClickListener{
            presenter.onPickImageClicked()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().supportFragmentManager.popBackStack()
          //  presenter.goToCategoryFragment()
            //Forcer la destruction de la vue //TODO : demander si besoin de détruire vu que onDestroyView est automatiquement appelé
            //requireActivity().supportFragmentManager.beginTransaction().remove(this@EditCategoryFragment).commit()
        }

        // Enregistrement de l'instance dans le MainActivity
        (activity as MainActivity).onFragmentSelectedListener = this


//        val callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                // Gérer le retour arrière ici
//                //requireActivity().supportFragmentManager.popBackStack()
//                requireActivity().supportFragmentManager.beginTransaction().remove(this@EditCategoryFragment).commit()
//                presenter.goToCategoryFragment()
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun displayInformationInFields(categoryName: String, imagePath: Uri) {
        nameCategoryEdit.setText(categoryName)
        imageSelectedCategoryEdit.setImageURI(imagePath)
        selectedImageUri = imagePath;
    }

    /**
     * Méthode qui permet d'afficher un message d'erreur
     * indiquant que tous les champs sont obligatoires
     */
    override fun showErrorMessage(errorMessage: String) {
        //TODO : regarder si on ne peut pas appeler super Android
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    /**
     * Affichage de la boite de dialogue comprenant
     * la galerie et l'appareil photo
     */
    override fun showImagePicker() {
        super.showImagePickerDialog(0)
    }

    override fun close() {
        //requireActivity().supportFragmentManager.popBackStack()
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
       // requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
       // requireActivity().supportFragmentManager.popBackStack("categoryFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        //TODO : plus besoin ceci ?
        //presenter.goToCategoryFragment()
    }

    /**
     * Cette méthode permet de rafraichir les informations à chaque
     * fois que le fragment "EditCategoryFragment" est recréé. De cette façon,
     * chaque fois que le fragment est visible à l'utilisateur, la méthode "getCurrentCategory"
     * sera appelée et les données sront mises à jour.
     */
    override fun onResume() {
        super.onResume()
        presenter.getCurrentCategory()
    }

//    override fun onStop() {
//        super.onStop()
//        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
//        presenter.goToCategoryFragment()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
//            // L'utilisateur a appuyé sur le bouton retour
//            // Supprime le fragment actuel et revient au fragment précédent
//           requireActivity().supportFragmentManager.popBackStack("categoryFragment", 0)
//            presenter.goToCategoryFragment()
//        }
//    }

//    override fun onStop() {
//        super.onStop()
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
//            requireActivity().supportFragmentManager.popBackStack()
//            presenter.goToCategoryFragment()
//        }
//    }





//    fun onBackPressed() {
//        // Retour au fragment parent
//        parentFragmentManager.popBackStack()
//
//        // Suppression du fragment actuel
//        parentFragmentManager.beginTransaction().remove(this).commit()
//    }


    override fun showSelectedImage(selectedImageUri: Uri) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("EditCategoryFragment", "onDestroyView()")
    }

    override fun onFragmentSelected(fragment: Fragment, previousFragment: Fragment?) {
        if(fragment is EditCategoryFragment) {
            /* Ancienne version */
//            presenter.goToCategoryFragment()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

}