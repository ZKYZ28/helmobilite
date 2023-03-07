package com.example.projettupreferes.presenters.viewsInterface.fragments

import android.net.Uri
import com.example.projettupreferes.presenters.EditCategoryPresenter

interface IEditCategoryFragment {
    fun setEditCategoryPresenter(presenter : EditCategoryPresenter)
    fun displayInformationInFields(categoryName: String, imagePath: Uri)
    fun showErrorMessage(errorMessage: String)
    fun showImagePicker()
    fun close()
    fun showSelectedImage(selectedImageUri: Uri)
}