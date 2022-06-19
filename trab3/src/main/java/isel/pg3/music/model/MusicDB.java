package isel.pg3.music.model;

import isel.pg3.music.views.MenuItemCheckList;

import java.util.*;

/**
 * TODO ...
 */
@SuppressWarnings("unused")
public class MusicDB {
    // Date
    private HashMap<String, MusicItem> table;

    public MusicDB() {
        table = new HashMap<>();
    }

    /**
     * Methods to add songs, songs, albums and playlists
     */
    public Song addSong(Song song) {
        // Returns the previous value associated with key, or null if there was no mapping for key.
        return (Song) table.put( song.getTitle(), song );
    }

    public Album addAlbum(Album album) {
        // Returns the previous value associated with key, or null if there was no mapping for key.
        return (Album) table.put( album.getTitle(), album );
    }

    public Playlist addPlaylist(Playlist playlist) {
        // Returns the previous value associated with key, or null if there was no mapping for key.
        return (Playlist) table.put( playlist.getTitle(), playlist );
    }

    public List<MusicItem> addMusics( List<MusicItem> items ) {
        for ( MusicItem item : items ){
            if ( item instanceof Album ){ addAlbum( (Album) item ); }
            if ( item instanceof Song ){ addSong( (Song) item ); }
            if ( item instanceof Playlist ){ addPlaylist( (Playlist) item ); }
        }
        return items;
    }

    public Playlist addRandomPlayList(String name, String[] genres,
                                      int totalSongs, long maxDuration) {

        Playlist pl = new Playlist(name);
        Random rndm = new Random();
        int random;
        // até n tempo ou n musicas ou n géneros
        for ( int i = 0; i < totalSongs && pl.getDuration() < maxDuration && i < genres.length; ++i ){
            if ( pl.getGenres().containsAll( Arrays.asList( genres ) ) ){    // se ainda nao tiver todos os generos
                TreeSet<Song> it = ( TreeSet<Song> ) this.getSongs( genres[i] );
                random = rndm.nextInt( it.size() );
                Song[] array = it.toArray( it.toArray( new Song[ it.size() ] ) );
                pl.addSong( array[ random ] );
            }
            else{
                // se ainda nao tiver todos, mas já tem este
                if ( pl.getGenres().contains( genres[i] ) ){ break; }
                // se ainda nao tiver todos, mas ainda nao tem este
                else{
                    TreeSet<Song> it = ( TreeSet<Song> ) this.getSongs( genres[i] );
                    random = rndm.nextInt( it.size() );
                    Song[] array = it.toArray( it.toArray( new Song[ it.size() ] ) );
                    pl.addSong( array[ random ] );
                }
            }
        }
        return pl;
    }

    /**
     * Methods to get the song, album or playlist with given title
     */
    public Song getSong(String t) {
        Object obj = table.get( t );
        if ( obj instanceof Song ) return (Song) obj;
        return null;
    }
    public Album getAlbum(String t) {
        Object obj = table.get( t );
        if ( obj instanceof Album ) return (Album) obj;
        return null;
    }
    public Playlist getPlaylist(String t) {
        Object obj = table.get( t );
        if ( obj instanceof Playlist ) return (Playlist) obj;
        return null;
    }

    /**
     * Methods to get all songs, albums, playlists, artists and genres.
     */
    public Iterable<Song> getSongs() {
        ArrayList<Song> songs = new ArrayList<>();
        table.forEach( (key, value) -> {
            if (value instanceof Song) songs.add( (Song) value );
        } );
        return songs;
    }
    public Iterable<Album> getAlbums() {
        ArrayList<Album> albums = new ArrayList<>();
        table.forEach( (key, value) -> {
            if (value instanceof Album) albums.add( (Album) value );
        } );
        return albums;
    }
    public Iterable<Playlist> getPlaylists() {
        ArrayList<Playlist> playlists = new ArrayList<>();
        table.forEach( (key, value) -> {
            if (value instanceof Playlist) playlists.add( (Playlist) value );
        } );
        return playlists;
    }
    public Iterable<String> getArtists() {
        Set<String> artists = new TreeSet<>();
        table.forEach( (key, value) -> {
            for( String artist : value.getArtists() ){
                artists.add( artist );
            }
        } );
        return artists;
    }
    public Iterable<String> getGenres() {
        Set<String> genres = new TreeSet<>();
        table.forEach( (key, value) -> {
            for( String genre : value.getGenres() ){
                genres.add( genre );
            }
        } );
        return genres;
    }

    /**
     * Method to get the songs of the specified artist.
     */
    public Iterable<Song> getSongs(String artist) {
        Set<Song> songs = new TreeSet<>();
        table.forEach( (key, value) -> {
            if ( value.getArtists().contains( artist ) ) songs.add((Song) value);
        });
        return songs;
    }

    /**
     * Method for getting songs that have at least one of the specified genres.
     */
    public Iterable<Song> getSongs(String ... genres) {
        Set<Song> songs = new TreeSet<>();
        for ( String genre : genres ) {
            table.forEach((key, value) -> {
                if (value.getGenres().contains( genre )) songs.add((Song) value);
            });
        }
        return songs;
    }

}
