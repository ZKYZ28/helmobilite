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
import com.google.android.material.bottomnavigation.BottomNavigationView

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

        val helpFragment = Help.newInstance();
        mapFragments["Help"] = helpFragment;

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

        //Réagir au clic sur le menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    // Appeler la méthode "goTo" de votre présentateur avec le nom du fragment "Main"
                    mainPresenter.requestSwitchView("Main")
                    true
                }
                R.id.stats -> {
                    // Appeler la méthode "goTo" de votre présentateur avec le nom du fragment "NormalGame"
                    mainPresenter.requestSwitchView("NormalGame")
                    true
                }
                R.id.aide -> {
                    mainPresenter.requestSwitchView("Help")
                    true
                }
                else -> false
            }
        }


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
