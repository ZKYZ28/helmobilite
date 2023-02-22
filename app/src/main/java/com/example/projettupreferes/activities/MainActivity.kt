package com.example.projettupreferes.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.projettupreferes.*
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.fragments.*
import com.example.projettupreferes.models.Category
import com.example.projettupreferes.models.GameManager
import com.example.projettupreferes.presenters.*
import com.example.projettupreferes.presenters.viewsInterface.activity.IMainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity(), IMainActivity, PersonnalFragment.ISelectCategory {

    private val mapFragments = mutableMapOf<String, Fragment>()
    private var categoriesList = mutableListOf<Category>()
    private lateinit var categoryPresenter : CategoryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        //Démarrage + initlialisation de la première vue
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Ajout du presenter Activity
        val mainPresenter = MainActivityPresenter(this)

        // Récupération de la liste de catégories
        lifecycleScope.launch {
            TuPreferesRepository.getInstance()?.getCategoriesList()?.collect { categories ->
                categoriesList = categories as MutableList<Category>
                //Traitement de la liste de catégories
                Log.d("CATEGORIESSIZE", categoriesList.size.toString())
                categoriesList.forEach {
                    Log.d("CATEGORY", "Nom de catégorie : ${it.categoryName}, pathImage : ${it.pathImage}")
                }
            }
        }

//

        //Gestionnaire d'objets
        val statistics =  com.example.projettupreferes.models.Statistics(0, 0, 0, 0) // CHANGER L IMPORT
        // val categories : HashMap<String, Category> = HashMap(TuPreferesRepository.getInstance()?.getCategories())

         val categories = mutableListOf<Category>()
         categories.add(Category(UUID.fromString("2493123b-5db9-4117-83c3-c3b8a2eaec7a"), "SALUT MONSIEUR DEVLEG", "file:///data/user/0/com.example.projettupreferes/files/category_images/category_image_1677004614898.jpeg"))
        val gameManager = GameManager(statistics, categories, Category(UUID.randomUUID(), "UNKNOW", ""))


            //Ajout des Fragments
            val fragmentHomeFragment = HomeFragment.newInstance();
            mapFragments["Main"] = fragmentHomeFragment;

            val statisticsFragment = StatisticsFragment.newInstance();
            mapFragments["Statistics"] = statisticsFragment;

            val helpFragment = HelpFragment.newInstance();
            mapFragments["Help"] = helpFragment;

            val fragmentNormalGameFragment = NormalGameFragment.newInstance();
            mapFragments["NormalGame"] = fragmentNormalGameFragment;

            val personnelFragment = PersonnalFragment.newInstance();
            mapFragments["Personnel"] = personnelFragment;

            val notCategoryFound = NoCategoryFoundFragment.newInstance();
            mapFragments["noCategoryFound"] = notCategoryFound;

            val createCategoryFragment = CreateCategoryFragment.newInstance();
            mapFragments["CreateCategory"] = createCategoryFragment;

            val editCategoryFragment = EditCategoryFragment.newInstance();
            mapFragments["EditCategory"] = editCategoryFragment;




        //Ajout des presenters Fragments
            val mainFragmentPresenter =
                MainFragmentPresenter(fragmentHomeFragment, mainPresenter, gameManager)
            val normalGamePresenter =
                NormalGamePresenter(fragmentNormalGameFragment, mainPresenter, gameManager)
            val personnelPresenter = PersonnelPresenter(personnelFragment, mainPresenter, gameManager)

            val createCategoryPresenter = CreateCategoryPresenter(createCategoryFragment, mainPresenter, gameManager)
            val noCategoryFound = NoCategoryFoundPresenter(notCategoryFound, mainPresenter)
            categoryPresenter = CategoryPresenter(mainPresenter, gameManager)
            val editCategoryPresenter = EditCategoryPresenter(editCategoryFragment, mainPresenter, gameManager)

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
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, com.example.projettupreferes.fragments.CategoryFragment.newInstance(categoryId.toString(), categoryPresenter))
            .addToBackStack(null).commit()
    }
}
