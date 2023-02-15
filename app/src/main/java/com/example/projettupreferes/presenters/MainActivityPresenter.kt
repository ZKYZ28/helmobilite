package com.example.projettupreferes.presenters

import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.startActivity
import com.example.projettupreferes.activities.MainActivity
import com.example.projettupreferes.presenters.activitiesInterface.IViews

class MainActivityPresenter(main_activity: MainActivity) : IPresenters{
    val mainActivity = main_activity;

    fun requestSwitchView(current_activity: String, desire_view : String) {
        Log.d("MainActivityPresenter", "MainActivityPresenter");
        mainActivity.goTo(current_activity, desire_view);
    }
}