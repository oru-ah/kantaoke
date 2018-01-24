package kantaoke.oru.com.kantaoke.fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import kantaoke.oru.com.kantaoke.KantaokeApplication
import kantaoke.oru.com.kantaoke.R
import kantaoke.oru.com.kantaoke.adapters.SongAdapter
import kantaoke.oru.com.kantaoke.data.Song
import nl.komponents.kovenant.task
import nl.komponents.kovenant.ui.successUi

/**
 * Created by Oru on 19/01/2018.
 */

class SongListFragment : Fragment() {

    lateinit var songs: MutableList<Song>
    lateinit var mAdapter: SongAdapter
    lateinit var fab: FloatingActionButton
    lateinit var songsView: RecyclerView
    lateinit var app: KantaokeApplication
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = inflater!!.inflate(R.layout.song_list_fragment, container, false)

        layoutManager = LinearLayoutManager(this.context)
        songsView = view.findViewById(R.id.songs_recycler_view)
        songsView.layoutManager = layoutManager

        app = this.activity.application as KantaokeApplication

        task {
            songs = app.db.songDao().all.toMutableList()
        } successUi {
            mAdapter = SongAdapter(this.context, songs)
            songsView.adapter = mAdapter
        }

        fab = view.findViewById(R.id.add_song_fab)

        fab.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this.context)
            val dialogView = inflater.inflate(R.layout.add_new_song_dialog, null)
            dialogBuilder.setView(dialogView)
            val titleInput = dialogView.findViewById<EditText>(R.id.title_input)
            val artistInput = dialogView.findViewById<EditText>(R.id.artist_input)
            dialogBuilder.setTitle("Aggiungi nuova canzone")
            dialogBuilder.setPositiveButton("Salva") { dialog, whichButton ->
                val newSong = Song()
                newSong.title = titleInput.text.toString()
                newSong.artist = artistInput.text.toString()
                task {
                    app.db.songDao().insertSong(newSong)
                    songs = app.db.songDao().all.toMutableList()
                } successUi {
                    mAdapter.refreshSongs(songs)
                }
            }
            dialogBuilder.setNegativeButton("Annulla") { dialog, i -> }
            dialogBuilder.show()
        }

        return view
    }

}