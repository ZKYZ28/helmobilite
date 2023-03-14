package com.example.projettupreferes.database

import android.app.Application

class TuPreferesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        TuPreferesDataBase.initDatabase(baseContext)
    }

    override fun onTerminate() {
        super.onTerminate()
        TuPreferesDataBase.disconnectDatabase()
    }
}