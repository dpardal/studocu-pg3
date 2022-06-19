package isel.pg3.music.model;

import isel.pg3.music.utils.Mp3Player;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * TODO ...
 * The playlist is very similar to album, so it seems
 * reasonable to have an intermediary abstract class
 * to represent albums and play lists.
 */
public class Playlist extends MusicItem{
    private ArrayList<Song> songs;

    public Playlist(String name) {
        super( name );
        songs = new ArrayList<>();
    }

    public void addSong( Song song ) {
        songs.add(song);
        this.addArtists( song.getArtists() );
        this.addGenres( song.getGenres() );
    }

    @Override
    public long getDuration() {
        long duration = 0;
        for ( Song song : songs ){
            duration += song.getDuration();
        }
        return duration;
    }

    @Override
    public String getPath() {
        // não existe path para uma playlist (acho eu)
        // a playlist é so uma coleção de referencias para as musicas
        return null;
    }

    @Override
    public void playOn(Mp3Player player) {
        ArrayList<String> paths = new ArrayList<>();
        for ( Song song : songs ){
            paths.add( song.getPath() );
        }
        player.play( paths );
    }

    @Override
    public Iterator<Song> iterator() {
        return songs.iterator();
    }
}
