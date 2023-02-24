package com.example.projettupreferes.presenters


import android.net.Uri
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.CreatePairFragment
import com.example.projettupreferes.models.*

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
            val currentCategoryWithListPairs = gameManager.categoryWithPaires
            //Insertion des choix
            TuPreferesRepository.getInstance()?.insertChoice(choiceOne)
            TuPreferesRepository.getInstance()?.insertChoice(choiceTwo)

            //Insertion de la paire
            val pair = Paire(choiceOneId = choiceOne.idChoice, choiceTwoId = choiceTwo.idChoice, categoryId = currentCategoryWithListPairs.category.idCategory)
            TuPreferesRepository.getInstance()?.insertPaire(pair)

            //Mettre à jour la liste de paires
            val updatedPaires = currentCategoryWithListPairs.paires + pair
            gameManager.categoryWithPaires.paires = updatedPaires

            mainActivityPresenter.requestSwitchView("categoryFragment")
            createPairFragment.close()
        }
    }

   /* fun validateCreation(textChoiceOne: String, textChoiceTwo: String, selectedImageUriChoiceOne: Uri?, selectedImageUriChoiceTwo: Uri?) {
        val choiceOne = createChoice(textChoiceOne, selectedImageUriChoiceOne)
        val choiceTwo = createChoice(textChoiceTwo, selectedImageUriChoiceTwo)

        if(!choiceOne.textChoice.isEmpty() && !choiceTwo.textChoice.isEmpty()){
            val currentCategoryWithListPairs = gameManager.categoryWithPaires
            //Insertion des choix
            TuPreferesRepository.getInstance()?.insertChoice(choiceOne)
            TuPreferesRepository.getInstance()?.insertChoice(choiceTwo)

            //Insertion de la paire
            val pair = Paire(choiceOneId = choiceOne.idChoice, choiceTwoId = choiceTwo.idChoice, categoryId = currentCategoryWithListPairs.category.idCategory)
            val pairWithChoices = PaireWithChoices(pair, choiceOne, choiceTwo)
            TuPreferesRepository.getInstance()?.insertPaire(pair)

            //Mettre à jour la liste de paires
            val updatedPaires = currentCategoryWithListPairs.paires + pairWithChoices
            gameManager.categoryWithPaires.paires = updatedPaires
            mainActivityPresenter.requestSwitchView("categoryFragment")
            createPairFragment.close()
        }
    }*/

    private fun createChoice(textChoice: String, selectedImageUriChoice: Uri?): Choice {
        return if(!textChoice.isEmpty()){
            Choice(textChoice = textChoice, isText = true)
        }else if(selectedImageUriChoice == null){
            createPairFragment.showErrorMessage("Vous devez choisir un texte ou une image par choix")
            Choice(textChoice = "", isText = true)
        }else{
            val imagePath = ImageManager.saveImage(createPairFragment.requireContext(), selectedImageUriChoice)
            Choice(textChoice = imagePath.toString(), isText = false)
        }
    }


    init {
        createPairFragment.presenter = this
    }

}