package com.example.projettupreferes.database.repository

import androidx.lifecycle.LiveData
import com.example.projettupreferes.database.TuPreferesDataBase
import com.example.projettupreferes.database.dao.TuPreferesDao
import com.example.projettupreferes.models.Place
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TuPreferesRepository {
    var instance: TuPreferesRepository? = null
    val tuPreferesDao: TuPreferesDao? = TuPreferesDataBase.getInstance()?.tuPreferesDao()
    val executor: ExecutorService = Executors.newSingleThreadExecutor()


    fun getPlaces(): LiveData<List<Place>> {
        return tuPreferesDao!!.getPlaces()
    }

    fun getPlace(uuid: UUID?): LiveData<Place> {
        return tuPreferesDao!!.getPlace(uuid)
    }

    fun insertPlace(place: Place?) {
        executor.execute(Runnable { tuPreferesDao!!.insert(place!!) })
    }

    fun updatePlace(place: Place?) {
        executor.execute(Runnable { tuPreferesDao!!.update(place!!) })
    }

    fun getTuPreferesRepositoryInstance(): TuPreferesRepository? {
        if (instance == null) instance = TuPreferesRepository()
        return instance
    }

}
