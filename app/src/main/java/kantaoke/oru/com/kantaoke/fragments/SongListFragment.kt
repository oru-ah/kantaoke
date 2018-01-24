package kantaoke.oru.com.kantaoke.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
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
import android.widget.TextView
import kantaoke.oru.com.kantaoke.KantaokeApplication
import kantaoke.oru.com.kantaoke.R
import kantaoke.oru.com.kantaoke.SongViewModel
import kantaoke.oru.com.kantaoke.adapters.SongAdapter
import kantaoke.oru.com.kantaoke.data.Song

/**
 * Created by Oru on 19/01/2018.
 */

class SongListFragment : Fragment() {

    lateinit var mAdapter: SongAdapter
    lateinit var fab: FloatingActionButton
    lateinit var songsView: RecyclerView
    lateinit var app: KantaokeApplication
    lateinit var layoutManager: LinearLayoutManager
    lateinit var viewModel: SongViewModel
    lateinit var resetDrawsButton: TextView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = inflater!!.inflate(R.layout.song_list_fragment, container, false)

        layoutManager = LinearLayoutManager(this.context)
        songsView = view.findViewById(R.id.songs_recycler_view)
        songsView.layoutManager = layoutManager
        resetDrawsButton = view.findViewById(R.id.draw_reset)

        app = this.activity.application as KantaokeApplication

        mAdapter = SongAdapter(this.context, mutableListOf<Song>())
        songsView.adapter = mAdapter

        viewModel = ViewModelProviders.of(this.activity).get(SongViewModel::class.java)

        viewModel.songs.observe(this.activity, Observer<MutableList<Song>> {
            mAdapter.refreshSongs(it!!)
            songsView.adapter = mAdapter
        })

        /* task {
             songs = app.db.songDao().all.toMutableList()
         } successUi {
             mAdapter = SongAdapter(this.context, songs)
             songsView.adapter = mAdapter
         }*/

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
                viewModel.addItem(newSong)
            }
            dialogBuilder.setNegativeButton("Annulla") { dialog, i -> }
            dialogBuilder.show()
        }

        resetDrawsButton.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this.context)
            dialogBuilder.setTitle("Vuoi resettare tutte le canzoni estratte?")
            dialogBuilder.setPositiveButton("Sì") { dialog, whichButton ->
                val songs = viewModel.getItems().value!!.filter { it.isAlreadyDrawn }.toMutableList()
                if (songs.isNotEmpty()){
                    songs.forEach { it.isAlreadyDrawn = false }
                    viewModel.updateAllItems(songs)
                }
/*                task {
                    songs.forEach { it.isAlreadyDrawn = false }
                    app.db.songDao().updateAllSongs(songs)
                    return@task app.db.songDao().all.filter { it.isAlreadyDrawn }.toMutableList()
                } successUi {
                    mAdapter.refreshSongs(it) }*/
            }
            dialogBuilder.setNegativeButton("No") { dialog, i -> }
            dialogBuilder.show()
        }

        return view
    }

}