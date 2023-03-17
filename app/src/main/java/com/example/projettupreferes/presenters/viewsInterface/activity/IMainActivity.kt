package com.example.projettupreferes.presenters.viewsInterface.activity

import androidx.fragment.app.FragmentManager
import com.example.projettupreferes.fragments.FragmentsName

interface IMainActivity {

   fun giveSupportFragmentManager(): FragmentManager
   fun goTo(desiredFragment: FragmentsName)


}