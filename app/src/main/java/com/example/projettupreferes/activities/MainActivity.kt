package com.example.projettupreferes.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.projettupreferes.*
import com.example.projettupreferes.database.TuPreferesDataBase
import com.example.projettupreferes.presenters.CreateCategoryPresenter
import com.example.projettupreferes.presenters.MainActivityPresenter
import com.example.projettupreferes.presenters.MainFragmentPresenter
import com.example.projettupreferes.presenters.PersonnelPresenter
import com.example.projettupreferes.presenters.activitiesInterface.IMainActivity
import com.example.projettupreferes.presenters.activitiesInterface.IViews

class MainActivity : AppCompatActivity(), IMainActivity {

    private val mapFragments = mutableMapOf<String, Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        //Démarrage + initlialisation de la première vue
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Ajout des Fragments
        val fragmentMain = main.newInstance();
        mapFragments["Main"] = fragmentMain;

        val fragmentNormalGame = normal_game.newInstance();
        mapFragments["NormalGame"] = fragmentNormalGame;

        val personnelFragment = personnel.newInstance();
        mapFragments["Personnel"] = personnelFragment;

        val createCategory = create_category.newInstance();
        mapFragments["CreateCategory"] = createCategory;

        //Ajout des presenters
        val mainPresenter = MainActivityPresenter(this)
        val mainFragmentPresenter = MainFragmentPresenter(fragmentMain, mainPresenter)
        val PersonnelPresenter = PersonnelPresenter(personnelFragment, mainPresenter)
        val createCategoryPresenter = CreateCategoryPresenter(createCategory, mainPresenter)

        //Ajout du fragment de base
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, createCategory)
            .commit()

        mainPresenter.addPlace();
    }

    fun goTo(desiredFragment: String) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val fragmentToDisplay = mapFragments[desiredFragment]

        if (fragmentToDisplay != null) {
            transaction.replace(R.id.fragmentContainer, fragmentToDisplay)
        }

        transaction.commit()
    }

    override fun getContext() : Context {
        return this.applicationContext
    }
}
