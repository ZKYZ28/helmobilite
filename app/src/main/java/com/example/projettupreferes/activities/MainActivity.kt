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
import android.widget.Toast
import com.example.projettupreferes.models.Paire
import kotlinx.coroutines.flow.zip

class MainActivity : AppCompatActivity(), IMainActivity, PersonnalFragment.ISelectCategory, SeePairFragment.ISelectPair {

    private val mapFragments = mutableMapOf<FragmentsName, Fragment>()
    private lateinit var categoryPresenter : CategoryPresenter
    lateinit var onFragmentSelectedListener: OnFragmentSelectedListener
    private var previousFragment : Fragment? = null
    private lateinit var seePairPresenter : SeePairPresenter
    private lateinit var gameManager : GameManager

    override fun onCreate(savedInstanceState: Bundle?) {
        //SETUP IMAGE
        val defaultImageUri = Uri.parse("android.resource://${packageName}/${R.raw.defaut_image}")
        val outputStream = this.openFileOutput("defaut_image.jpg", Context.MODE_PRIVATE)
        val inputStream = this.contentResolver.openInputStream(defaultImageUri)
        inputStream?.copyTo(outputStream)
        outputStream.close()


        //Démarrage + initlialisation de la première vue
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        supportFragmentManager.addOnBackStackChangedListener {
//            previousFragment = supportFragmentManager.fragments.lastOrNull()
//        }

        Log.d("Nombre de backStack", supportFragmentManager.backStackEntryCount.toString())

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
            onFragmentSelectedListener.onFragmentSelected(currentFragment!!, previousFragment)
            //TODO peut-être mettre ici le supportFragmentManager.popBackStack au lieu de l'appeler dans chaque vue
            Log.d("Nombre de backStack dans le bouton", supportFragmentManager.backStackEntryCount.toString())
            if(supportFragmentManager.backStackEntryCount == 1) {
                finish()
            }
        }




        //Gestionnaire d'objets
        val uudiStat = UUID.randomUUID();
        val statistics = Statistics(uudiStat, 0, 0, 0, 0) // BOUGER CA
        gameManager = GameManager(statistics)

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

            val createCategoryFragment = CreateCategoryFragment.newInstance();
            mapFragments[FragmentsName.CreateCategory] = createCategoryFragment;

            val editCategoryFragment = EditCategoryFragment.newInstance();
            mapFragments[FragmentsName.EditCategory] = editCategoryFragment;

            val createPairFragment = CreatePairFragment.newInstance()
            mapFragments[FragmentsName.CreatePair] = createPairFragment

            val seePairFragment = SeePairFragment.newInstance()
            mapFragments[FragmentsName.SeePair] = seePairFragment


        //Ajout des presenters Fragments
            val homeFragmentPresenter =
                HomeFragmentPresenter(fragmentHomeFragment, mainPresenter, gameManager)
            val playGamePresenter =
                PlayGamePresenter(fragmentPlayGameFragment, mainPresenter, gameManager)
            val personnelPresenter = PersonnelPresenter(personnelFragment, personnelFragment, mainPresenter, gameManager)
            personnelPresenter.loadCategories()

            val statisticsPresenter = StatisticsPresenter(statisticsFragment, mainPresenter, gameManager)

            val createCategoryPresenter = CreateCategoryPresenter(createCategoryFragment, mainPresenter, gameManager)

            categoryPresenter = CategoryPresenter(mainPresenter, gameManager)
            val editCategoryPresenter = EditCategoryPresenter(editCategoryFragment, mainPresenter, gameManager)

            seePairPresenter = SeePairPresenter(seePairFragment, seePairFragment,mainPresenter, gameManager)


            val createPairPresenter = CreatePairPresenter(createPairFragment, mainPresenter, gameManager)



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


            val categoryUUID = gameManager.currentCategoryWithPaires.category.idCategory
            // RECHARGE LES CHOIX PUIS CREE LE FRAGMENT POUR REAFFICHER TOUT CORRECTEMENT. PEUT ETRE JUSTE MODIFIER LA LISTE EN INTERNE POUR NE PAS DEVOIR RELOAD DEPUIS LA BD. COMMENT FAIRE ? FOREACH UUID EQUALS OU METTRE UNE MAP ?
            GlobalScope.launch(Dispatchers.Main) {
                TuPreferesRepository.getInstance()?.getPairesByCategoryId(categoryUUID)
                    ?.collect { paires ->
                        val updatedPaires = mutableListOf<Paire>()

                        paires.forEach { paire ->
                            val choiceOneFlow = TuPreferesRepository.getInstance()?.getChoice(paire.choiceOneId)
                            val choiceTwoFlow = TuPreferesRepository.getInstance()?.getChoice(paire.choiceTwoId)

                            if (choiceOneFlow != null && choiceTwoFlow != null) {
                                choiceOneFlow.zip(choiceTwoFlow) { choiceOne, choiceTwo ->
                                    Paire(paire.idPaire, choiceOneId = choiceOne?.idChoice!!, choiceTwoId = choiceTwo?.idChoice!!, categoryIdFk = categoryUUID!!)
                                }?.collect { paireWithChoices ->
                                    updatedPaires.add(paireWithChoices)
                                }
                            }
                        }

                        gameManager.currentCategoryWithPaires.paires = updatedPaires
                        val newFragment = SeePairFragment.newInstance()
                        mapFragments[FragmentsName.SeePair] = newFragment;
                        seePairPresenter.setFragment(newFragment)

                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, newFragment, "SeePair")
                            .addToBackStack("SeePair").commit()
                    }
            }
        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }



}
