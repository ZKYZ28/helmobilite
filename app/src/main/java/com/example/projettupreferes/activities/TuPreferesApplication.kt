package com.example.projettupreferes.activities

import android.app.Application
import android.util.Log
import com.example.projettupreferes.database.TuPreferesDataBase

class TuPreferesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("BD", "onCreate pour la BD")
        TuPreferesDataBase.initDatabase(baseContext)
    }

    override fun onTerminate() {
        super.onTerminate()
        TuPreferesDataBase.disconnectDatabase()
    }
}