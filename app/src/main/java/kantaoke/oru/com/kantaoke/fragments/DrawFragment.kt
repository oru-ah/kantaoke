package kantaoke.oru.com.kantaoke.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kantaoke.oru.com.kantaoke.KantaokeApplication
import kantaoke.oru.com.kantaoke.R
import kantaoke.oru.com.kantaoke.SongViewModel
import kantaoke.oru.com.kantaoke.data.Song
import java.util.*

/**
 * Created by Oru on 19/01/2018.
 */

class DrawFragment : Fragment() {

    lateinit var drawButton: Button
    lateinit var drawnSongTitleView: TextView
    lateinit var drawnSongArtistView: TextView
    lateinit var app: KantaokeApplication
    lateinit var viewModel: SongViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.draw_fragment, container, false)

        drawButton = view.findViewById(R.id.draw_button)
        drawnSongTitleView = view.findViewById(R.id.drawn_song_title)
        drawnSongArtistView = view.findViewById(R.id.drawn_song_artist)

        app = this.activity.application as KantaokeApplication

        viewModel = ViewModelProviders.of(this.activity).get(SongViewModel::class.java)

        viewModel.songs.observe(this.activity, android.arch.lifecycle.Observer<MutableList<Song>> {
            val drawnSong = it!!.filter { it.isAlreadyDrawn }.maxBy { it.dateDrawn }
            drawnSongTitleView.text = drawnSong?.title
            drawnSongArtistView.text = drawnSong?.artist
        })

        drawButton.setOnClickListener {
            val drawnSongs = viewModel.getItems().value!!.filter { !it.isAlreadyDrawn }
            if (drawnSongs.isNotEmpty()){
                val drawnSong = drawnSongs.getRandomElement()
                drawnSong.isAlreadyDrawn = true
                drawnSong.dateDrawn = Date()
                viewModel.updateItem(drawnSong)
            } else {
                drawnSongTitleView.text = "Hai estratto tutte le canzoni!"
                drawnSongArtistView.text = ""
            }
        }

        return view
    }

    fun <E> List<E>.getRandomElement() = this[Random().nextInt(this.size)]

}