package kantaoke.oru.com.kantaoke.fragments

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
import kantaoke.oru.com.kantaoke.KantaokeApplication
import kantaoke.oru.com.kantaoke.R
import kantaoke.oru.com.kantaoke.SongViewModel
import kantaoke.oru.com.kantaoke.adapters.SongAdapter
import kantaoke.oru.com.kantaoke.data.Song

/**
 * Created by Oru on 19/01/2018.
 */

class DrawnSongListFragment : Fragment() {

    lateinit var songs: MutableList<Song>
    lateinit var mAdapter: SongAdapter
    lateinit var fab: FloatingActionButton
    lateinit var songsView: RecyclerView
    lateinit var app: KantaokeApplication
    lateinit var layoutManager: LinearLayoutManager
    lateinit var viewModel: SongViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = inflater!!.inflate(R.layout.drawn_song_list_fragment, container, false)

/*        layoutManager = LinearLayoutManager(this.context)
        songsView = view.findViewById(R.id.drawn_songs_recycler_view)
        songsView.layoutManager = layoutManager

        app = this.activity.application as KantaokeApplication

        task {songs = app.db.songDao().all.filter{it.isAlreadyDrawn}.toMutableList()
        } successUi {
            mAdapter = SongAdapter(this.context, songs)
            songsView.adapter = mAdapter
        }*/

        viewModel = ViewModelProviders.of(this.activity).get(SongViewModel::class.java)

        fab = view.findViewById(R.id.clear_drawn_songs_fab)

        fab.setOnClickListener {
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