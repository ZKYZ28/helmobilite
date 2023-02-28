package com.example.projettupreferes.presenters


import android.net.Uri
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.CreatePairFragment
import com.example.projettupreferes.models.*
import com.example.projettupreferes.models.exceptions.SaveImageStorageException
import java.util.*

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
        val idPair = UUID.randomUUID();
        val choiceOne = createChoice(textChoiceOne, selectedImageUriChoiceOne, idPair)
        val choiceTwo = createChoice(textChoiceTwo, selectedImageUriChoiceTwo, idPair)

        if(!choiceOne.textChoice.isEmpty() && !choiceTwo.textChoice.isEmpty()){
            val currentCategoryWithListPairs = gameManager.currentCategoryWithPaires
            //Insertion de la paire
            val pair = Paire(idPair, choiceOneId = choiceOne.idChoice, choiceTwoId = choiceTwo.idChoice, categoryIdFk = currentCategoryWithListPairs.category.idCategory)
            TuPreferesRepository.getInstance()?.insertPaire(pair)

            //Insertion des choix
            TuPreferesRepository.getInstance()?.insertChoice(choiceOne)
            TuPreferesRepository.getInstance()?.insertChoice(choiceTwo)

            //Mettre à jour la liste de paires
            val updatedPaires = currentCategoryWithListPairs.paires + listOf(pair)
            gameManager.currentCategoryWithPaires.paires = updatedPaires

            //Mettre à jour les statistics
            gameManager.statistics.nbrPairs++
            TuPreferesRepository.getInstance()?.updateStatics(gameManager.statistics)

            mainActivityPresenter.requestSwitchView("categoryFragment")
            createPairFragment.close()
        }
    }


    private fun createChoice(textChoice: String, selectedImageUriChoice: Uri?, idPair : UUID): Choice {
        return if(!textChoice.isEmpty()){
            Choice(textChoice = textChoice, isText = true, pairIdFk = idPair)
        }else if(selectedImageUriChoice == null){
            createPairFragment.showErrorMessage("Vous devez choisir un texte ou une image par choix")
            Choice(textChoice = "", isText = true, pairIdFk = idPair)
        }else{
            var imagePath: Uri? = null
            try {
                imagePath = ImageManager.saveImage(createPairFragment.requireContext(), selectedImageUriChoice)
            } catch (e: SaveImageStorageException) {
                createPairFragment.showErrorMessage(e.message!!)
            }

            if (imagePath == null) {
                createPairFragment.showErrorMessage("Une erreur s'est produite lors de l'enregistrement de l'image.")
                //TODO : throw pour éviter imagePath null dans le choix (même chose dans CreateCategoryPresenter et EditCategoryPresenter")
            }
            Choice(textChoice = imagePath.toString(), isText = false, pairIdFk = idPair)
        }
    }

    fun onPickImageClicked(choiceNumber: Int) {
        createPairFragment.showImagePicker(choiceNumber)
    }



    init {
        createPairFragment.presenter = this
    }

}