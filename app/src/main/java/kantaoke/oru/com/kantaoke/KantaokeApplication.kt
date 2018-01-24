package kantaoke.oru.com.kantaoke

import android.app.Application
import android.arch.persistence.room.Room
import kantaoke.oru.com.kantaoke.data.AppDatabase
import nl.komponents.kovenant.android.startKovenant
import nl.komponents.kovenant.android.stopKovenant

/**
 * Created by Oru on 19/01/2018.
 */
class KantaokeApplication : Application() {

    lateinit var db : AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "kantaokedb")
                .fallbackToDestructiveMigration().build()
        startKovenant()
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKovenant()
    }
}