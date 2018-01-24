package kantaoke.oru.com.kantaoke.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Oru on 19/01/2018.
 */

@Entity
public class Song {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "song_title")
    public String title;

    @ColumnInfo(name = "song_artist")
    public String artist;

    public boolean isAlreadyDrawn;
}
