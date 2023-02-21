package com.example.projettupreferes.presenters.viewsInterface.fragments

import android.media.Image
import android.net.Uri

interface ICreateCategory {

        fun showErrorMessage(message: String)
        fun showImage(imageUri: Uri?)
        fun close()

}