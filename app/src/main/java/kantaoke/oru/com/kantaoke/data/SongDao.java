package kantaoke.oru.com.kantaoke.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Oru on 19/01/2018.
 */
@Dao
public interface SongDao {

    @Query("SELECT * FROM `song`")
    LiveData<List<Song>> getAll();

    @Update
    void updateSong(Song song);

    @Update
    void updateAllSongs(List<Song> songs);

    @Insert
    void insertSong(Song song);

    @Delete
    void deleteSong(Song song);

}
