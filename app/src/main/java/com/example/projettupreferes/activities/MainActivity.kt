package com.example.projettupreferes.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.example.projettupreferes.R
import com.example.projettupreferes.presenters.IMainActivity
import com.example.projettupreferes.presenters.MainActivityPresenter

class MainActivity : AppCompatActivity(), IMainActivity {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val main_presenter = MainActivityPresenter(this);

        //Récupération du clic sur le bouton
        val normalGame = findViewById<LinearLayout>(R.id.normalGame)
        normalGame.setOnClickListener {
            // Code à exécuter lorsque le LinearLayout est cliqué
            main_presenter.requestSwitchView()
        }
    }

}