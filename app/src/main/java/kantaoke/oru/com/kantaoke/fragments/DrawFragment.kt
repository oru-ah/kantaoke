package kantaoke.oru.com.kantaoke.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kantaoke.oru.com.kantaoke.KantaokeApplication
import kantaoke.oru.com.kantaoke.R
import nl.komponents.kovenant.task
import nl.komponents.kovenant.ui.failUi
import nl.komponents.kovenant.ui.successUi
import java.util.*

/**
 * Created by Oru on 19/01/2018.
 */

class DrawFragment : Fragment() {

    lateinit var drawButton: Button
    lateinit var drawnSongTitleView: TextView
    lateinit var drawnSongArtistView: TextView
    lateinit var app: KantaokeApplication

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.draw_fragment, container, false)

        drawButton = view.findViewById(R.id.draw_button)
        drawnSongTitleView = view.findViewById(R.id.drawn_song_title)
        drawnSongArtistView = view.findViewById(R.id.drawn_song_artist)

        app = this.activity.application as KantaokeApplication

        drawButton.setOnClickListener {
            drawButton.isClickable = false
            task {
                val songs = app.db.songDao().all.filter { !it.isAlreadyDrawn }
                val drawnSong = songs.getRandomElement()
                drawnSong.isAlreadyDrawn = true
                app.db.songDao().updateSong(drawnSong)
                return@task drawnSong
            } successUi {
                drawnSongTitleView.text = it.title
                drawnSongArtistView.text = it.artist
            } failUi {
                drawnSongTitleView.text = "Hai estratto tutte le canzoni!"
                drawnSongArtistView.text = ""
            } always {
                drawButton.isClickable = true
            }
        }

        return view
    }

    fun <E> List<E>.getRandomElement() = this[Random().nextInt(this.size)]

}