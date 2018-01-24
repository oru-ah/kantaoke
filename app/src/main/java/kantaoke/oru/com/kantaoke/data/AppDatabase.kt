package kantaoke.oru.com.kantaoke.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import kantaoke.oru.com.kantaoke.Converters

/**
 * Created by Oru on 19/01/2018.
 */
@Database(entities = arrayOf(Song::class), version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun songDao(): SongDao
}