package com.example.projettupreferes.activities


import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.projettupreferes.*
import com.example.projettupreferes.fragments.*
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.presenters.*
import com.example.projettupreferes.presenters.viewsInterface.activity.IMainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import android.content.pm.ActivityInfo
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity(), IMainActivity, PersonnalFragment.ISelectCategory,
    SeePairFragment.ISelectPair {

    private val mapFragments = mutableMapOf<FragmentsName, Fragment>()
    private lateinit var categoryPresenter: CategoryPresenter
    lateinit var onFragmentSelectedListener: OnFragmentSelectedListener
    private lateinit var seePairPresenter: SeePairPresenter
    private lateinit var gameManager: GameManager
    private lateinit var createCategoryPresenter: CreateCategoryPresenter
    private lateinit var createPairPresenter: CreatePairPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        //Démarrage + initlialisation de la première vue
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bloquer la rotation de l'écran en mode portrait
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //Ajout des presenters
        //Gestionnaire app
        gameManager = GameManager()

        //Ajout du presenter Activity
        val mainPresenter = MainActivityPresenter(this, gameManager)
        mainPresenter.loadDefaultImage(this)


        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
            onFragmentSelectedListener.onFragmentSelected(currentFragment!!)
            if (supportFragmentManager.backStackEntryCount == 1) {
                finish()
            }
        }




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


        val homeFragmentPresenter =
            HomeFragmentPresenter(fragmentHomeFragment, mainPresenter, gameManager)
        val playGamePresenter =
            PlayGamePresenter(fragmentPlayGameFragment, gameManager)
        val personnelPresenter =
            PersonnelPresenter(personnelFragment, personnelFragment, mainPresenter, gameManager)
        personnelPresenter.loadCategories()

        val statisticsPresenter = StatisticsPresenter(statisticsFragment, gameManager)

        createCategoryPresenter =
            CreateCategoryPresenter(createCategoryFragment, mainPresenter, gameManager)

        categoryPresenter = CategoryPresenter(mainPresenter, gameManager)
        val editCategoryPresenter = EditCategoryPresenter(editCategoryFragment, gameManager)

        seePairPresenter =
            SeePairPresenter(seePairFragment, seePairFragment, gameManager)


        createPairPresenter = CreatePairPresenter(createPairFragment, gameManager)


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
                is StatisticsFragment -> bottomNavigationView.menu.findItem(R.id.stats).isChecked =
                    true
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

    override fun onSelectedPair(pairId: UUID?, position: Int) {
        if (pairId != null) {
            seePairPresenter.updatePairs(pairId, position)
            seePairPresenter.updateRecyclerPairs()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            createCategoryPresenter.resetCategoryName()
            createPairPresenter.clearChoiceText()
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }
}
