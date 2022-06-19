package isel.pg3.music.model;

import isel.pg3.music.utils.Mp3Player;

import java.util.*;
/**
 * TODO - the Song is incompleted
 */
public class Song extends MusicItem  {
    private final String path;    // the name of the music file
    private final long duration;
    private final String album;
    // TODO ...
   /**
     * Build a Song with:
     * @param title    - song title
     * @param path     - absolute pathname
     * @param artist   - artist
     * @param genre    - genre of music
     * @param duration - song duration
     * @param album    - name of the album
     */
    public Song(String title, String path, String genre, String artist, long duration, String album) {
        super( title, artist, genre );
        this.path = path;
        this.duration = duration;
        this.album = album;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Iterator<Song> iterator() { return Collections.singleton(this).iterator(); }

    @Override
    public void playOn(Mp3Player player ) {
        player.play(getPath());
    }

    @Override
    public long getDuration() {
        return duration;
    }

    @Override
    public String toString() {
       return super.toString() + ", from album " + getAlbum();
    }

    public String getAlbum() {
        return album;
    }
}

