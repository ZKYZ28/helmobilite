package com.example.projettupreferes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projettupreferes.presenters.MainActivityPresenter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val main_presenter = MainActivityPresenter(this);
    }
}