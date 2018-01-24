package kantaoke.oru.com.kantaoke

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import kantaoke.oru.com.kantaoke.data.AppDatabase
import kantaoke.oru.com.kantaoke.data.Song
import nl.komponents.kovenant.task

/**
 * Created by Oru on 24/01/2018.
 */

class SongViewModel(application: Application) : AndroidViewModel(application) {

    val songs: LiveData<MutableList<Song>>

    private val appDatabase: AppDatabase

    init {

        var app = application as KantaokeApplication

        appDatabase = app.db

        songs = appDatabase.songDao().all
    }

    fun getItems(): LiveData<MutableList<Song>> {
        return songs
    }

    fun updateAllItems(songs: MutableList<Song>){
        task { appDatabase.songDao().updateAllSongs(songs) }
    }

    fun deleteItem(song: Song) {
        task { appDatabase.songDao().deleteSong(song) }
    }

    fun addItem(song: Song) {
        task { appDatabase.songDao().insertSong(song) }
    }

    fun updateItem(song: Song) {
        task { appDatabase.songDao().updateSong(song)}
    }
}
