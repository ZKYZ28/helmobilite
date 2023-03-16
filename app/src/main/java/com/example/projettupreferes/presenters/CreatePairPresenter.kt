package com.example.projettupreferes.presenters


import android.content.Context
import android.net.Uri
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.models.*
import com.example.projettupreferes.models.exceptions.SaveImageStorageException
import com.example.projettupreferes.presenters.viewsInterface.fragments.ICreatePairFragment
import java.util.*

class CreatePairPresenter(private val createPairFragment: ICreatePairFragment, private val gameManager: GameManager) {
    init {
        createPairFragment.setCreatePairPresenter(this)
    }

    fun clearChoiceText(){
        createPairFragment.clearTextChoice()
    }

    fun deleteImageChoiceOneTraitement() {
        createPairFragment.onDeleteImageChoiceOne()
    }

    fun deleteImageChoiceTwoTraitement() {
        createPairFragment.onDeleteImageChoiceTwo()
    }

    fun manageDisplayImageChoiceOne(textChoiceOneLength : Int){
        if(textChoiceOneLength > 0){
            createPairFragment.deactivateSelecteImageChoiceOne()
        }else{
            createPairFragment.activateSelecteImageChoiceOne()
        }
    }

    fun manageDisplayImageChoiceTwo(textChoiceTwoLength : Int) {
        if(textChoiceTwoLength > 0){
            createPairFragment.deactivateSelecteImageChoiceTwo()
        }else{
            createPairFragment.activateSelecteImageChoiceTwo()
        }
    }

    fun validateCreation(textChoiceOne: String, textChoiceTwo: String, selectedImageUriChoiceOne: Uri?, selectedImageUriChoiceTwo: Uri?, context : Context) {
        val idPair = UUID.randomUUID();
        val choiceOne = createChoice(textChoiceOne, selectedImageUriChoiceOne, idPair, context)
        val choiceTwo = createChoice(textChoiceTwo, selectedImageUriChoiceTwo, idPair, context)

        if(choiceOne != null && choiceTwo != null){
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
            updateNbrPairs()

            clearChoiceText()
            createPairFragment.close()
        }

    }

    private fun updateNbrPairs(){
        gameManager.statistics.nbrPairs++
        TuPreferesRepository.getInstance()?.updateStatics(gameManager.statistics)
    }

    private fun createChoice(textChoice: String, selectedImageUriChoice: Uri?, idPair : UUID, context : Context): Choice? {
        return if(textChoice.isNotEmpty()){
            Choice(textChoice = textChoice.uppercase(), isText = true, pairIdFk = idPair)
        }else if(selectedImageUriChoice == null){
            createPairFragment.showErrorMessage("Vous devez choisir un texte ou une image par choix")
            null
        }else{
            var imagePath: Uri?
            try {
                imagePath = ImageManager.saveImage(context, selectedImageUriChoice)
            } catch (e: SaveImageStorageException) {
                createPairFragment.showErrorMessage(e.message!!)
                return null
            }
            Choice(textChoice = imagePath.toString(), isText = false, pairIdFk = idPair)
        }
    }

    fun onPickImageClicked(choiceNumber: Int) {
        createPairFragment.showImagePicker(choiceNumber)
    }

}