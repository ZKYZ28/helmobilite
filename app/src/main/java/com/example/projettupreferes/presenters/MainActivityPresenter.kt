package com.example.projettupreferes.presenters

import androidx.appcompat.app.AppCompatActivity
import com.example.projettupreferes.activities.MainActivity
import com.example.projettupreferes.database.repository.TuPreferesRepository
import com.example.projettupreferes.models.Place
import java.util.*

class MainActivityPresenter(private val mainActivity: MainActivity) : IPresenters {
    fun requestSwitchView(currentActivity: String, desireView: Class<out AppCompatActivity>) {
        mainActivity.goTo(currentActivity, desireView)
    }

    fun addPlace(): UUID? {
        val place = Place()
        TuPreferesRepository().getTuPreferesRepositoryInstance()!!.insertPlace(place)
        return place.getId()
    }
}
