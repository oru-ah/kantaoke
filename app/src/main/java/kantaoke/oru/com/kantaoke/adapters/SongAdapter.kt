package kantaoke.oru.com.kantaoke.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kantaoke.oru.com.kantaoke.R
import kantaoke.oru.com.kantaoke.data.Song

/**
 * Created by Oru on 19/01/2018.
 */
class SongAdapter
internal constructor(context: Context, songs: MutableList<Song>) : RecyclerView.Adapter<SongAdapter.ViewHolder>() {

    private var mSongs: MutableList<Song>? = null
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null
    private val mContext: Context = context

    init {
        this.mSongs = songs
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val view = mInflater.inflate(R.layout.song_list_item, parent, false)
            return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.titleView.text = mSongs!![position].title
        holder.artistView.text = mSongs!![position].artist
        if (mSongs!![position].isAlreadyDrawn) {
            holder.itemView.setBackgroundColor(mContext.resources.getColor(R.color.alreadyDrawnSongBackground))
        } else {
            holder.itemView.setBackgroundColor(mContext.resources.getColor(R.color.white))
        }
        holder.itemView.tag = mSongs!![position].id
    }

    override fun getItemCount(): Int {
        return mSongs!!.size
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var titleView: TextView = itemView.findViewById(R.id.song_title)
        internal var artistView: TextView = itemView.findViewById(R.id.song_artist)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }
    }

    fun refreshSongs(songs: MutableList<Song>) {
        this.mSongs!!.clear()
        this.mSongs!!.addAll(songs)
        notifyDataSetChanged()
    }

    internal fun setClickListener(itemClickListener: ItemClickListener) {
        this.mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}