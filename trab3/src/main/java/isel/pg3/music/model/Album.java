package isel.pg3.music.model;

import isel.pg3.music.utils.Mp3Player;

import java.util.*;


/**
 * TODO ...
 */
public class Album extends MusicItem {
    private final String path;    // the name of the music file
    private Collection<Song> songs;
    /** Build a Album with:
     * @param title   - album title
     * @param path    - absolute pathname
     * @param songs   - collection of songs
     * @param artists - set of artists
     * @param genres  - set of artists
     */
    public Album(String title, String path, Collection<Song> songs,
                 Set<String> artists, Set<String> genres)  {
        super(title, artists, genres);
        this.path = path;
        this.songs = songs;
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
        return path;
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