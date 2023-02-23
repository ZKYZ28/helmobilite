package com.example.projettupreferes.presenters


import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.view.isVisible
import com.example.projettupreferes.fragments.CreatePairFragment
import com.example.projettupreferes.models.Choice
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.models.ImageManager

class CreatePairPresenter(private val createPairFragment: CreatePairFragment, private val  mainActivityPresenter: MainActivityPresenter, private val gameManager: GameManager) {
    fun deleteImageChoiceOneTraitment() {
        createPairFragment.onDeleteImageChoiceOne()
    }

    fun deleteImageChoiceTwoTraitment() {
        createPairFragment.onDeleteImageChoiceTwo()
    }

    fun manageDisplayImageChoiceTwo(textChoiceTwoLenght : Int) {
        if(textChoiceTwoLenght != 0){
            createPairFragment.deactivateSelecteImageChoiceTwo()
        }else{
            createPairFragment.activateSelecteImageChoiceTwo()
        }
    }

    fun manageDisplayImageChiceOne(textChoiceOneLenght : Int){
        if(textChoiceOneLenght != 0){
            createPairFragment.deactivateSelecteImageChoiceOne()
        }else{
            createPairFragment.activateSelecteImageChoiceOne()
        }
    }

    fun validateCreation(textChoiceOne: String, textChoiceTwo: String, selectedImageUriChoiceOne: Uri?, selectedImageUriChoiceTwo: Uri?) {
       val choiceOne = createChoice(textChoiceOne, selectedImageUriChoiceOne)
        val choiceTwo = createChoice(textChoiceTwo, selectedImageUriChoiceTwo)

        if(!choiceOne.textChoice.isEmpty() && !choiceTwo.textChoice.isEmpty()){
            val pair = Pair(choiceOne, choiceTwo)
        }
    }

    private fun createChoice(textChoice: String, selectedImageUriChoice: Uri?): Choice {
        return if(!textChoice.isEmpty()){
            Choice(textChoice, true)
        }else if(selectedImageUriChoice == null){
            createPairFragment.showErrorMessage("Vous devez choisir un texte ou une image par choix")
            Choice("", true)
        }else{
            val imagePath = ImageManager.saveImage(createPairFragment.requireContext(), selectedImageUriChoice)
            Choice(imagePath.toString(), false)
        }
    }


    init {
        createPairFragment.presenter = this
    }

}