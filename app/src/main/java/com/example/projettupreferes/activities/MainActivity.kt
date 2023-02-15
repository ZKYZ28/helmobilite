package com.example.projettupreferes.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.example.projettupreferes.R
import com.example.projettupreferes.presenters.activitiesInterface.IMainActivity
import com.example.projettupreferes.presenters.MainActivityPresenter
import com.example.projettupreferes.presenters.activitiesInterface.IViews

class MainActivity : AppCompatActivity(), IMainActivity {

    val mapViews = mutableMapOf<String, IViews>();

    override fun onCreate(savedInstanceState: Bundle?) {
        //Démarrage + initlialisation de la première vue
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Ajout des vues
        val normalGameActivity = NormalGameActivity()
        mapViews["NormalGame"] = normalGameActivity;
        mapViews["Main"] = this;

        //Ajout des presenters
        val mainPresenter = MainActivityPresenter(this);


        //Récupération du clic sur le bouton
        val normalGame = findViewById<LinearLayout>(R.id.normalGame)
        normalGame.setOnClickListener {
            // Code à exécuter lorsque le LinearLayout est cliqué
            mainPresenter.requestSwitchView("Main", "NormalGame")
        }
    }

    fun goTo(current_activity: String, desire_view : String){
        val intent = Intent(this, NormalGameActivity::class.java);
        startActivity(intent);
    }
}