package com.example.projettupreferes.presenters

import androidx.appcompat.app.AppCompatActivity
import com.example.projettupreferes.activities.MainActivity

class MainActivityPresenter(private val mainActivity: MainActivity) : IPresenters {
    fun requestSwitchView(currentActivity: String, desireView: Class<out AppCompatActivity>) {
        mainActivity.goTo(currentActivity, desireView)
    }
}
