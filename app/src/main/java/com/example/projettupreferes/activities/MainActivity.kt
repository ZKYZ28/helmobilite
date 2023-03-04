package com.example.projettupreferes.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
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

    private val mapFragments = mutableMapOf<FragmentsName, Fragment>()
    private lateinit var categoryPresenter : CategoryPresenter
    lateinit var onFragmentSelectedListener: OnFragmentSelectedListener
    private var previousFragment : Fragment? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        //Démarrage + initlialisation de la première vue
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.addOnBackStackChangedListener {
            previousFragment = supportFragmentManager.fragments.lastOrNull()
        }

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
            onFragmentSelectedListener.onFragmentSelected(currentFragment!!, previousFragment)
        }




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

            //Ajout du presenter Activity
            val mainPresenter = MainActivityPresenter(this, gameManager)



            //Ajout des Fragments
            val fragmentHomeFragment = HomeFragment.newInstance();
            mapFragments[FragmentsName.Main] = fragmentHomeFragment;

            val statisticsFragment = StatisticsFragment.newInstance();
            mapFragments[FragmentsName.Statistics] = statisticsFragment;

            val helpFragment = HelpFragment.newInstance();
            mapFragments[FragmentsName.Help] = helpFragment;

            val fragmentPlayGameFragment = PlayGameFragment.newInstance();
            mapFragments[FragmentsName.NormalGame] = fragmentPlayGameFragment;

            val personnelFragment = PersonnalFragment.newInstance();
            mapFragments[FragmentsName.Personnal] = personnelFragment;

            val notCategoryFound = NoCategoryFoundFragment.newInstance();
            mapFragments[FragmentsName.NoCategoryFound] = notCategoryFound;

            val createCategoryFragment = CreateCategoryFragment.newInstance();
            mapFragments[FragmentsName.CreateCategory] = createCategoryFragment;

            val editCategoryFragment = EditCategoryFragment.newInstance();
            mapFragments[FragmentsName.EditCategory] = editCategoryFragment;

            val createPairFragment = CreatePairFragment.newInstance()
            mapFragments[FragmentsName.CreatePair] = createPairFragment

            val seePairFragment = SeePairFragment.newInstance()
            mapFragments[FragmentsName.SeePair] = seePairFragment


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
                .addToBackStack(null)
                .commit()

            //Réagir au clic sur le menu
            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

            bottomNavigationView.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.home -> {
                        // Appeler la méthode "goTo" de votre présentateur avec le nom du fragment "Main"
                        mainPresenter.requestSwitchView(FragmentsName.Main)
                        true
                    }
                    R.id.stats -> {
                        // Appeler la méthode "goTo" de votre présentateur avec le nom du fragment "NormalGame"
                        mainPresenter.requestSwitchView(FragmentsName.Statistics)
                        true
                    }
                    R.id.aide -> {
                        mainPresenter.requestSwitchView(FragmentsName.Help)
                        true
                    }
                    else -> false
                }
            }

        }

    fun goTo(desiredFragment: FragmentsName) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val fragmentToDisplay = mapFragments[desiredFragment]

        if (fragmentToDisplay != null) {
            transaction.replace(R.id.fragmentContainer, fragmentToDisplay)
        }
        transaction.addToBackStack(null)
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
            mapFragments[FragmentsName.CategoryFragment] = newFragment;
    }

    override fun onSelectedPair(pairId: UUID?) {
        //TODO UPDATE LA VUE COMMENT ?
        if (pairId != null) {
            Log.d("ICI", "DELETE")
            TuPreferesRepository.getInstance()?.deletePaire(pairId)
        }
    }

}
