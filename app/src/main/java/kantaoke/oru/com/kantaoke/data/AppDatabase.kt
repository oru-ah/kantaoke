package kantaoke.oru.com.kantaoke.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by Oru on 19/01/2018.
 */
@Database(entities = arrayOf(Song::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun songDao(): SongDao
}