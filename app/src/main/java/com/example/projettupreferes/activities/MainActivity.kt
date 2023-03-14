package com.example.projettupreferes.activities

import android.net.Uri
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
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.example.projettupreferes.models.Paire
import kotlinx.coroutines.flow.zip

class MainActivity : AppCompatActivity(), IMainActivity, PersonnalFragment.ISelectCategory, SeePairFragment.ISelectPair {

    private val mapFragments = mutableMapOf<FragmentsName, Fragment>()
    private lateinit var categoryPresenter : CategoryPresenter
    lateinit var onFragmentSelectedListener: OnFragmentSelectedListener
    private lateinit var seePairPresenter : SeePairPresenter
    private lateinit var gameManager : GameManager
    private lateinit var createCategoryPresenter : CreateCategoryPresenter
    private lateinit var createPairPresenter : CreatePairPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
            //Démarrage + initlialisation de la première vue
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)


        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
            onFragmentSelectedListener.onFragmentSelected(currentFragment!!)
            //TODO peut-être mettre ici le supportFragmentManager.popBackStack au lieu de l'appeler dans chaque vue
            if(supportFragmentManager.backStackEntryCount == 1) {
                finish()
            }
        }

            //SETUP image par défaut
            val defaultImageUri = Uri.parse("android.resource://${packageName}/${R.raw.defaut_image}")
            val outputStream = this.openFileOutput("defaut_image.jpg", Context.MODE_PRIVATE)
            val inputStream = this.contentResolver.openInputStream(defaultImageUri)
            inputStream?.copyTo(outputStream)
            outputStream.close()


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

            val createCategoryFragment = CreateCategoryFragment.newInstance();
            mapFragments[FragmentsName.CreateCategory] = createCategoryFragment;

            val editCategoryFragment = EditCategoryFragment.newInstance();
            mapFragments[FragmentsName.EditCategory] = editCategoryFragment;

            val createPairFragment = CreatePairFragment.newInstance()
            mapFragments[FragmentsName.CreatePair] = createPairFragment

            val seePairFragment = SeePairFragment.newInstance()
            mapFragments[FragmentsName.SeePair] = seePairFragment


            //Ajout des presenters
            //Gestionnaire app
            gameManager = GameManager()

            //Ajout du presenter Activity
            val mainPresenter = MainActivityPresenter(this, gameManager)

            val homeFragmentPresenter =
                HomeFragmentPresenter(fragmentHomeFragment, mainPresenter, gameManager)
            val playGamePresenter =
                PlayGamePresenter(fragmentPlayGameFragment, mainPresenter, gameManager)
            val personnelPresenter = PersonnelPresenter(personnelFragment, personnelFragment, mainPresenter, gameManager)
            personnelPresenter.loadCategories()

            val statisticsPresenter = StatisticsPresenter(statisticsFragment, mainPresenter, gameManager)

            createCategoryPresenter = CreateCategoryPresenter(createCategoryFragment, mainPresenter, gameManager)

            categoryPresenter = CategoryPresenter(mainPresenter, gameManager)
            val editCategoryPresenter = EditCategoryPresenter(editCategoryFragment, mainPresenter, gameManager)

            seePairPresenter = SeePairPresenter(seePairFragment, seePairFragment,mainPresenter, gameManager)


            createPairPresenter = CreatePairPresenter(createPairFragment, mainPresenter, gameManager)



             /* Ne pas ajouter le premmier fragment à la stack car
              * il est ajouté dans la méthode onCreate() de l'activité.
              * Cette méthode est appelée lorsque l'activité est créée, avant
              * que la méthode onCreate() du premier fragment ne soit appelée.
              * Par conséquent, le premier fragment est automatiquement ajouté
              * à la stack par la méthode onCreate() de l'activité
              */
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

            /*
             * Synchronisation du menu avec le bouton retour du téléphone
             * et de l'application
             */
            supportFragmentManager.addOnBackStackChangedListener {
                when (supportFragmentManager.findFragmentById(R.id.fragmentContainer)) {
                    is HomeFragment -> bottomNavigationView.menu.findItem(R.id.home).isChecked = true
                    is StatisticsFragment -> bottomNavigationView.menu.findItem(R.id.stats).isChecked = true
                    is HelpFragment -> bottomNavigationView.menu.findItem(R.id.aide).isChecked = true
                    /* Tous les autres cas, c'est le bouton home qui sera sélectionné */
                    else -> bottomNavigationView.menu.findItem(R.id.home).isChecked = true
                }
            }

        }



    override fun goTo(desiredFragment: FragmentsName) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val fragmentToDisplay = mapFragments[desiredFragment]

        if (fragmentToDisplay != null) {
            transaction.replace(R.id.fragmentContainer, fragmentToDisplay)
        }
        transaction.addToBackStack(null)
        transaction.commit()
    }



     override fun giveSupportFragmentManager(): FragmentManager {
        return supportFragmentManager;
    }

    override fun onSelectedCategory(categoryId: UUID?) {
            val newFragment = CategoryFragment.newInstance(categoryId, categoryPresenter)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, newFragment, "categoryFragment")
                .addToBackStack("categoryFragment").commit()
            mapFragments[FragmentsName.CategoryFragment] = newFragment;
    }

    override fun onSelectedPair(pairId: UUID?, position : Int) {
        if (pairId != null) {
            seePairPresenter.updatePairs(pairId, position)
            seePairPresenter.updateRecyclerPairs()
        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 1) {
            createCategoryPresenter.resetCategoryName()
            createPairPresenter.clearChoiceText()
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }
}
