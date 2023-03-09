package com.example.projettupreferes.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.projettupreferes.R
import com.example.projettupreferes.activities.MainActivity
import com.example.projettupreferes.presenters.CreatePairPresenter
import com.example.projettupreferes.presenters.viewsInterface.fragments.ICreateCategoryFragment
import com.example.projettupreferes.presenters.viewsInterface.fragments.ICreatePairFragment


class CreatePairFragment : FragmentWithImagePicker(), ICreatePairFragment, OnFragmentSelectedListener {
   lateinit var presenter : CreatePairPresenter
    lateinit var textChoiceOne : EditText
    lateinit var selecteImageChoiceOne : Button
    lateinit var deleteImageChoiceOne : ImageButton


    lateinit var textChoiceTwo : EditText
    lateinit var selecteImageChoiceTwo : Button
    lateinit var deleteImageChoiceTwo : ImageButton

    lateinit var validateButton :Button

    private var selectedImageUriChoiceOne: Uri? = null
    private var selectedImageUriChoiceTwo: Uri? = null

    private val imagePickerContractTwo = ImagePickerContract()



    override fun setCreatePairPresenter(createPairPresenter : CreatePairPresenter){
        this.presenter = createPairPresenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

        imagePickerLauncher = registerForActivityResult(imagePickerContract) { uri: Uri? ->
            if (imagePickerSource == 1) {
                selectedImageUriChoiceOne = uri
                if (selectedImageUriChoiceOne != null) {
                    deleteImageChoiceOne.isVisible = true
                    deleteImageChoiceOne.isEnabled = true

                    textChoiceOne.isVisible = false
                    textChoiceOne.isEnabled = false
                }
            } else {
                selectedImageUriChoiceTwo = uri
                if (selectedImageUriChoiceTwo != null) {
                    deleteImageChoiceTwo.isVisible = true
                    deleteImageChoiceTwo.isEnabled = true

                    textChoiceTwo.isVisible = false
                    textChoiceTwo.isEnabled = false
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_create_pair, container, false)

        textChoiceOne = view.findViewById(R.id.texteChoiceOne)
        textChoiceTwo = view.findViewById(R.id.texteChoiceTwo)
        selecteImageChoiceOne = view.findViewById(R.id.selectImageChoiceOne)
        selecteImageChoiceTwo = view.findViewById(R.id.selectImageChoiceTwo)
        deleteImageChoiceOne = view.findViewById(R.id.deleteImageChoiceOne)
        deleteImageChoiceTwo = view.findViewById(R.id.deleteImageChoiceTwo)
        validateButton = view.findViewById(R.id.validateCreation)


        deleteImageChoiceOne.isEnabled = false
        deleteImageChoiceTwo.isEnabled = false


        selecteImageChoiceOne.setOnClickListener {
           // imagePickerSource = 1
            presenter.onPickImageClicked(1)
        }

        selecteImageChoiceTwo.setOnClickListener {
          //  imagePickerSource = 2
            presenter.onPickImageClicked(2)
        }


        textChoiceOne.setOnKeyListener { view, keyCode, keyEvent ->
            presenter.manageDisplayImageChoiceOne(textChoiceOne.length())
            false
        }

        textChoiceTwo.setOnKeyListener { view, keyCode, keyEvent ->
            presenter.manageDisplayImageChoiceTwo(textChoiceTwo.length())
            false
        }

        deleteImageChoiceOne.setOnClickListener{
            presenter.deleteImageChoiceOneTraitment()
        }

        deleteImageChoiceTwo.setOnClickListener{
            presenter.deleteImageChoiceTwoTraitment()
        }

        validateButton.setOnClickListener {
            presenter.validateCreation(textChoiceOne.text.toString(), textChoiceTwo.text.toString(), selectedImageUriChoiceOne, selectedImageUriChoiceTwo, requireContext())
        }

        textChoiceOne.setText("")
        textChoiceTwo.setText("")

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            //requireActivity().supportFragmentManager.popBackStack()
        //    presenter.goToCategoryFragment()
            requireActivity().supportFragmentManager.popBackStack()
            //Forcer la destruction de la vue //TODO : demander si besoin de détruire vu que onDestroyView est automatiquement appelé
            //requireActivity().supportFragmentManager.beginTransaction().remove(this@EditCategoryFragment).commit()
        }

        // Enregistrement de l'instance dans le MainActivity
        (activity as MainActivity).onFragmentSelectedListener = this

        return view
    }

    override fun deactivateSelecteImageChoiceOne(){
        selecteImageChoiceOne.isEnabled = false
        selecteImageChoiceOne.isVisible = false
    }

    override fun activateSelecteImageChoiceOne(){
        selecteImageChoiceOne.isEnabled = true
        selecteImageChoiceOne.isVisible = true
    }

    override fun deactivateSelecteImageChoiceTwo(){
        selecteImageChoiceTwo.isEnabled = false
        selecteImageChoiceTwo.isVisible = false
    }

    override fun activateSelecteImageChoiceTwo(){
        selecteImageChoiceTwo.isEnabled = true
        selecteImageChoiceTwo.isVisible = true
    }

    override fun onDeleteImageChoiceOne(){
        selectedImageUriChoiceOne = null
        deleteImageChoiceOne.isVisible = false
        deleteImageChoiceOne.isEnabled = true

        textChoiceOne.isVisible = true
        textChoiceOne.isEnabled = true
    }

    override fun onDeleteImageChoiceTwo(){
        selectedImageUriChoiceTwo = null
        deleteImageChoiceTwo.isVisible = false
        deleteImageChoiceTwo.isEnabled = true

        textChoiceTwo.isVisible = true
        textChoiceTwo.isEnabled = true
    }

    /**
     * Méthode qui permet d'afficher un message d'erreur
     * indiquant que tous les champs sont obligatoires
     */
    override fun showErrorMessage(errorMessage: String) {
        super.displayErrorMessage(errorMessage)
    }

    override fun close() {
        //requireActivity().supportFragmentManager.popBackStack()
        //TODO : vérifier remove
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
    }

    override fun showImagePicker(choiceNumber: Int) {
        super.showImagePickerDialog(choiceNumber)
    }

    override fun onResume(){
        //TODO CHANGER POUR NE PAS LE FAIRE QUAND ON CHANGE LA PHOTO
        super.onResume()
        textChoiceOne.setText("")
        textChoiceTwo.setText("")
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CreatePairFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onFragmentSelected(fragment: Fragment, previousFragment: Fragment?) {
        if(fragment is CreatePairFragment) {
            //presenter.goToCategoryFragment()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}