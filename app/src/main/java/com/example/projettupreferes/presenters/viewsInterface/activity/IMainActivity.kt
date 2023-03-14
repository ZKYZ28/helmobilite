package com.example.projettupreferes.presenters.viewsInterface.activity

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.example.projettupreferes.fragments.FragmentsName

interface IMainActivity {

   fun giveSupportFragmentManager(): FragmentManager
   fun goTo(desiredFragment: FragmentsName)

   fun showErrorMessage(errorMessage : String)

}