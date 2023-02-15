package com.example.projettupreferes.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.projettupreferes.R
import com.example.projettupreferes.presenters.activitiesInterface.INormalGameActivity

class NormalGameActivity : AppCompatActivity(), INormalGameActivity{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal_game)
        Log.d("NormalGameActivity", "NormalGameActivity");
    }
}