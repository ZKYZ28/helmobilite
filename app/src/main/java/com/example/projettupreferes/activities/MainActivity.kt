package com.example.projettupreferes.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.projettupreferes.*
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.*
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.models.Statistics
import com.example.projettupreferes.presenters.*
import com.example.projettupreferes.presenters.viewsInterface.activity.IMainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity(), IMainActivity, PersonnalFragment.ISelectCategory, SeePairFragment.ISelectPair {

    private val mapFragments = mutableMapOf<String, Fragment>()
    private lateinit var categoryPresenter : CategoryPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        //Démarrage + initlialisation de la première vue
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Ajout du presenter Activity
        val mainPresenter = MainActivityPresenter(this)

        //Gestionnaire d'objets
        val uudiStat = UUID.randomUUID();
        val statistics = Statistics(uudiStat, 0, 0, 0, 0) // BOUGER CA
        var gameManager = GameManager(statistics)

        GlobalScope.launch(Dispatchers.Main) {
            TuPreferesRepository.getInstance()?.getStatistics(UUID.fromString("b0a51d0e-20b7-4c84-8f84-2cf96eeb9a8a"))
                ?.collect { statisticsLoad ->
                    gameManager.statistics = statisticsLoad!!
                }
        }


            //Ajout des Fragments
            val fragmentHomeFragment = HomeFragment.newInstance();
            mapFragments["Main"] = fragmentHomeFragment;

            val statisticsFragment = StatisticsFragment.newInstance();
            mapFragments["Statistics"] = statisticsFragment;

            val helpFragment = HelpFragment.newInstance();
            mapFragments["Help"] = helpFragment;

            val fragmentPlayGameFragment = PlayGameFragment.newInstance();
            mapFragments["NormalGame"] = fragmentPlayGameFragment;

            val personnelFragment = PersonnalFragment.newInstance();
            mapFragments["Personnel"] = personnelFragment;

            val notCategoryFound = NoCategoryFoundFragment.newInstance();
            mapFragments["noCategoryFound"] = notCategoryFound;

            val createCategoryFragment = CreateCategoryFragment.newInstance();
            mapFragments["CreateCategory"] = createCategoryFragment;

            val editCategoryFragment = EditCategoryFragment.newInstance();
            mapFragments["EditCategory"] = editCategoryFragment;

            val createPairFragment = CreatePairFragment.newInstance()
            mapFragments["CreatePair"] = createPairFragment

            val seePairFragment = SeePairFragment.newInstance()
            mapFragments["SeePair"] = seePairFragment


        //Ajout des presenters Fragments
            val mainFragmentPresenter =
                MainFragmentPresenter(fragmentHomeFragment, mainPresenter, gameManager)
            val playGamePresenter =
                PlayGamePresenter(fragmentPlayGameFragment, mainPresenter, gameManager)
            val personnelPresenter = PersonnelPresenter(personnelFragment, mainPresenter, gameManager)
            personnelPresenter.loadCategories()

            val statisticsPresenter = StatisticsPresenter(statisticsFragment, mainPresenter, gameManager)

            val createCategoryPresenter = CreateCategoryPresenter(createCategoryFragment, mainPresenter, gameManager)
            val noCategoryFound = NoCategoryFoundPresenter(notCategoryFound, mainPresenter)
            categoryPresenter = CategoryPresenter(mainPresenter, gameManager)
            val editCategoryPresenter = EditCategoryPresenter(editCategoryFragment, mainPresenter, gameManager)
            val seePairPresenter = SeePairPresenter(seePairFragment, mainPresenter, gameManager)


            val createPairPresenter = CreatePairPresenter(createPairFragment, mainPresenter, gameManager)

            //Ajout du fragment de base
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragmentHomeFragment)
                .commit()

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
                        mainPresenter.requestSwitchView("Statistics")
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

    override fun onSelectedCategory(categoryId: UUID?) {
            val newFragment = CategoryFragment.newInstance(categoryId, categoryPresenter)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, newFragment, "categoryFragment")
                .addToBackStack("categoryFragment").commit()
            mapFragments["categoryFragment"] = newFragment;
    }

    override fun onSelectedPair(pairId: UUID?) {
        //TODO UPDATE LA VUE COMMENT ?
        if (pairId != null) {
            Log.d("ICI", "DELETE")
            TuPreferesRepository.getInstance()?.deletePaire(pairId)
        }
    }
}
