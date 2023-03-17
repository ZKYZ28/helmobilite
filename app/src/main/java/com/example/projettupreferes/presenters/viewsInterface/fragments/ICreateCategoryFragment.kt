package com.example.projettupreferes.presenters.viewsInterface.fragments

import android.net.Uri
import com.example.projettupreferes.presenters.CreateCategoryPresenter


interface ICreateCategoryFragment {
        fun resetCategoryName()
        fun showErrorMessage(message: String)
        fun close()
        fun showSelectedImage(selectedImageUri: Uri)
        fun showImagePicker()
        fun setCreateCategoryPresenter(createCategoryPresenter: CreateCategoryPresenter)
}