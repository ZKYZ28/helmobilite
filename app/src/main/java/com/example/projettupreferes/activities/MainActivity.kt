package com.example.projettupreferes.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.projettupreferes.R
import com.example.projettupreferes.database.TuPreferesDataBase
import com.example.projettupreferes.presenters.MainActivityPresenter
import com.example.projettupreferes.presenters.activitiesInterface.IMainActivity
import com.example.projettupreferes.presenters.activitiesInterface.IViews

class MainActivity : AppCompatActivity(), IMainActivity {

    val mapViews = mutableMapOf<String, IViews>()

    override fun onCreate(savedInstanceState: Bundle?) {
        //Démarrage + initlialisation de la première vue
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Ajout des vues
        val normalGameActivity = NormalGameActivity()
        mapViews["NormalGame"] = normalGameActivity
        mapViews["Main"] = this

        //Ajout des presenters
        val mainPresenter = MainActivityPresenter(this)

        //Récupération du clic sur le bouton
        val normalGame = findViewById<LinearLayout>(R.id.normalGame)
        normalGame.setOnClickListener {
            // Code à exécuter lorsque le LinearLayout est cliqué
            val desireView = Class.forName("com.example.projettupreferes.activities.NormalGameActivity")
            mainPresenter.requestSwitchView("Main", desireView as Class<out AppCompatActivity>)
        }

        mainPresenter.addPlace();
    }

    fun goTo(currentActivity: String, desireView : Class<out AppCompatActivity>) {
        val intent = Intent(mapViews[currentActivity]!!.getContext(), desireView)
        startActivity(intent)
    }

    override fun getContext() : Context {
        return this.applicationContext
    }
}
