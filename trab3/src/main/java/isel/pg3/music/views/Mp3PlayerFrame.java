package isel.pg3.music.views;


import isel.pg3.music.model.*;
import isel.pg3.music.utils.Mp3Player;
import isel.pg3.music.utils.Utils;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;


import static isel.pg3.music.model.MusicItemFinder.*;
import static isel.pg3.music.utils.Utils.*;
import static isel.pg3.music.views.ItemsViewer.*;

public class Mp3PlayerFrame extends JFrame {
    private static final String DEFAULT_FOLDER_MUSICS = "C:/Users/admin/Desktop/music";

    // states for the music player
    private enum State { Playing, Paused, Stopped }

    // current state
    private State state = State.Stopped;

    // Components for the south panel
    private Icon playIcon =  Utils.getIconFromResource("play.png");
    private Icon pauseIcon = Utils.getIconFromResource("pause.png");
    private Icon stopIcon = Utils.getIconFromResource("stop.png");
    private JButton playPauseBut = getButton(playIcon, this::onPlayPause);
    private JTextField albumName = new JTextField(32);
    private JTextField songName  = new JTextField(32);

    // Content panel for viewer
    private ItemsViewer musicItemsViewer = new ItemsViewer();

    // Components for the playList dialog creation - is incompleted
    private JTextField playlistName = new JTextField(20);
    private JTextField playlistDuration = new JTextField("3600", 6);


    // Model

    // This list is used just for this sample
    // In your aplication you must replace this with a MusicDB as shown in the next commented line
    //private List<Song> songs = new ArrayList<>();
    //private List<Album> albums = new ArrayList<>();
    //private List<Playlist> folders = new ArrayList<>();


    private MusicDB musicDB = new MusicDB();
    private Mp3Player player = new Mp3Player();



    private void showOn(String title, Iterable<?> values,
                        SelectedItemListener selected, ZoomItemListener zoom) {
        musicItemsViewer.setItems(title, values, selected, zoom);
    }

    private  JComponent[] inputsPlayList = {
        new JLabel("Name"), playlistName,
        new JLabel("Duration"), playlistDuration
        // TODO .. is incompleted
    };

    private void initInputsPlayList() {
        playlistName.setText( "" );
        playlistDuration.setText( "3600");
    }

    private int getInputsPlaylist(String title ) {
        initInputsPlayList();
        return JOptionPane.showConfirmDialog(this, inputsPlayList,
            title, JOptionPane.PLAIN_MESSAGE);
    }

    // listeners for viewer
    private void onSelectedPlaylistItem( Object obj ) {
        Playlist pl = (Playlist) obj;
        songName.setText( "" );
        albumName.setText( pl.getTitle() );
    }
    private void onZoomPlaylistItem( Object obj ) {
        Playlist pl = (Playlist) obj;
        showOn( pl.getTitle(), musicDB.getPlaylist( pl.getTitle() ), this::onSelectedSongItem, null);
    }

    private void onSelectedAlbumItem( Object obj ) {
        Album a = (Album) obj;
        songName.setText( "" );
        albumName.setText( a.getTitle() );
    }
    private void onZoomAlbumItem( Object obj ) {
        Album a = (Album) obj;
        showOn( a.getTitle(), musicDB.getAlbum( a.getTitle() ), this::onSelectedSongItem, null);
    }
    private void onSelectedSongItem(Object obj) {
        Song s = (Song) obj;
        songName.setText( s.getTitle() );
        albumName.setText(s.getAlbum());
    }

    private void onZoomArtistsItem( Object obj) {  }
    private void onZoomGenresItem( Object obj) {  }


    // listeners for Player
    private void onCompletedList( ) {
        state = State.Stopped;
        player.stop();
        playPauseBut.setIcon(playIcon);
        songName.setText( "" );
        albumName.setText( "" );
    }

    private void onMusicStartPlay( String  title ) {
        songName.setText(title);
        MusicItem mi = musicItemsViewer.getSelectedItem();
        if( mi instanceof Song ){ albumName.setText( ((Song) mi).getAlbum() ); }
        if( mi instanceof Album ){ albumName.setText( mi.getTitle() ); }
        if( mi instanceof Playlist ){ albumName.setText( mi.getTitle() ); }
    }

    private void onError( Exception ex ) {
        // TODO
    }

    /**
     * listeners for buttons
     * Incomplete methods to exemplify the use of MP3Player
     */
    private void onStop(ActionEvent evt) {
         if (state != State.Stopped) {
             state = State.Stopped;
             player.stop();
             playPauseBut.setIcon(playIcon);
             songName.setText( "" );
             albumName.setText( "" );
        }
    }

    private void onPlayPause(ActionEvent evt) {
        MusicItem selectedItem = musicItemsViewer.getSelectedItem();
        switch(state) {
            case Playing:
                if (selectedItem != null ) {
                    state = State.Paused;
                    player.pause();
                    playPauseBut.setIcon(playIcon);
                }
                break;
            case Stopped:
                if (selectedItem != null ) {
                    state = State.Playing;
                    selectedItem.playOn(player);
                    playPauseBut.setIcon(pauseIcon);
                }
                break;
            case Paused:
                if (selectedItem != null ) {
                    state = State.Playing;
                    player.resume();
                    playPauseBut.setIcon(pauseIcon);
                }
                break;
        }
    }

    /**
     * Incomplete methods to exemplify addition and visualization
     * (use song as example)
     */
    private void addSongItem(ActionEvent e) {
        try {
            File  f = chooseFile(DEFAULT_FOLDER_MUSICS);
            if (f != null) {
                Song s = getSong(f);
//                songs.add( s );
                musicDB.addSong( s );
                showOn("Songs", musicDB.getSongs(), this::onSelectedSongItem, null);
            }
        }
        catch (Exception ex ) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addAlbumItem(ActionEvent e) {
        try {
            File  f = chooseFile(DEFAULT_FOLDER_MUSICS);
            if (f != null) {
                Album a = getAlbum( f );
//                albums.add( a );
                musicDB.addAlbum( a );
                showOn("Albums", musicDB.getAlbums(), this::onSelectedAlbumItem, this::onZoomAlbumItem);
            }
        }
        catch (Exception ex ) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addPlaylistItem(ActionEvent e) {
        try {
            //Note that you must define DEFAULT_FOLDER_MUSICS according to your file system
            File  f = chooseFile(DEFAULT_FOLDER_MUSICS);
            if (f != null) {
                System.out.println( e.getActionCommand() );
                List<MusicItem> folder_content = getMusics( f );
                Playlist pl = new Playlist( f.getName() + " (" + e.getActionCommand() + ")" );
                folder_content.forEach( mi -> {
                    if ( mi instanceof Song ){ pl.addSong( (Song) mi ); }
                    if ( mi instanceof Album ){
                        mi.iterator().forEachRemaining( pl::addSong );
                    }
                } );
//                folders.add( pl );
                musicDB.addPlaylist( pl );
                showOn( e.getActionCommand() +"s", musicDB.getPlaylists(), this::onSelectedPlaylistItem, this::onZoomPlaylistItem);
            }
        }
        catch (Exception ex ) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showSongItem(ActionEvent e) {
        albumName.setText( "" );
        songName.setText( "" );
        showOn("Songs", musicDB.getSongs(), this::onSelectedSongItem, null);
    }

    private void showAlbumItem(ActionEvent e) {
        albumName.setText( "" );
        songName.setText( "" );
        showOn("Albums", musicDB.getAlbums(), this::onSelectedAlbumItem, this::onZoomAlbumItem);
    }

    private void showPlaylistItem(ActionEvent e) {
        albumName.setText( "" );
        songName.setText( "" );
        showOn("Playlists", musicDB.getPlaylists(), this::onSelectedPlaylistItem, this::onZoomPlaylistItem);
    }

    private void showGenresItem(ActionEvent e) {
        albumName.setText( "" );
        songName.setText( "" );
        showOn("Genres", musicDB.getGenres(), null, null);
    }

    private void showArtistsItem(ActionEvent e) {
        albumName.setText( "" );
        songName.setText( "" );
        showOn("Genres", musicDB.getArtists(), null, null);
    }

    private void showSongItemByGenre(ActionEvent e) {
        albumName.setText( "" );
        songName.setText( "" );
        // criar um array de strings com os géneros selecionados, e usar o getSongs(String ... genres)
//        String[] selectedItems =((MenuItemCheckList) ((JFrame) getRootPane().getContentPane()).getJMenuBar().getMenu(0)).getSelected();
        String[] selectedItems = ( (MenuItemCheckList) getJMenuBar().getMenu(0)).getSelected();
        showOn("Songs by genre", musicDB.getSongs( selectedItems ), this::onSelectedSongItem, null);
    }

    private void showSongItemByArtist(ActionEvent e) {
        albumName.setText( "" );
        songName.setText( "" );
        String[] chosenArtist = { e.getActionCommand() };
        showOn("Songs by artist", musicDB.getSongs( chosenArtist ), this::onSelectedSongItem, null);
    }

    private void buildMenus() {
        // Only for test
        JMenuBar menuBar = new JMenuBar();

        MenuItemCheckList genres = new MenuItemCheckList("Genres");
        menuBar.add(genres);
        String[] genreStrings = {"Jazz", "Folk", "Rock", "Pop", "Metal", "R&B", "Soul", "Blues"};
        // depois, de alguma forma, tenho de percorrer o que está em musicDB.getGenres()
        // PS: se mostrar o que está em musicDB.getGenres() é muito pesado, tenho de limitar o que está em musicDB.getGenres()
        for (String str : genreStrings ){
            JCheckBoxMenuItem mi = new JCheckBoxMenuItem( str );
            genres.add( mi );
            mi.addActionListener( this::showSongItemByGenre );
        }

        JMenu artists = new JMenu("Artists");
        menuBar.add(artists);
        String[] artistStrings = {"Al Jarreau", "Al Di Meola", "Eric Clapton", "Bee Gees"};

        // depois, de alguma forma, tenho de percorrer o que está em musicDB.getArtists()
        // Iterable<String> artistStrings = musicDB.getArtists();
        for (String str : artistStrings ){
            JMenuItem mi = new JMenuItem( str );
            artists.add( mi );
            mi.addActionListener( this::showSongItemByArtist );
        }

        JMenu add = new JMenu("Add");
        menuBar.add(add);
        JMenuItem addAlbum = new JMenuItem("Album");
        JMenuItem addSong = new JMenuItem("Song");
        JMenuItem addFolder = new JMenuItem("Folder");
        JMenuItem addPlaylist = new JMenuItem("Playlist");
        add.add(addAlbum);
        add.add(addSong);
        add.add(addFolder);
        add.add(addPlaylist);

        JMenu show = new JMenu("Show");
        menuBar.add(show);
        JMenuItem showAlbum = new JMenuItem("Albums");
        JMenuItem showSong = new JMenuItem("Songs");
        JMenuItem showPlaylist = new JMenuItem("Playlist");
        JMenuItem showGenres = new JMenuItem("Genres");
        JMenuItem showArtists = new JMenuItem("Artists");
        show.add(showAlbum);
        show.add(showSong);
        show.add(showPlaylist);
        show.add(showGenres);
        show.add(showArtists);


        //Action Events

        //-------- Genres ------------
        //ActionListener adicionado no ciclo For

        //-------- Add ----------------
        addSong.addActionListener(this::addSongItem);
        addAlbum.addActionListener(this::addAlbumItem);
        addPlaylist.addActionListener(this::addPlaylistItem);
        addFolder.addActionListener(this::addPlaylistItem);


        //-------Show--------------
        showSong.addActionListener(this::showSongItem);
        showAlbum.addActionListener(this::showAlbumItem);
        showPlaylist.addActionListener(this::showPlaylistItem);
        showGenres.addActionListener(this::showGenresItem);
        showArtists.addActionListener(this::showArtistsItem);


        setJMenuBar( menuBar );


    }

    private void initComponents() {
        JPanel musicPanel = new JPanel(new GridLayout(2,1));
        musicPanel.add(getBox("Album", albumName, playPauseBut));
        musicPanel.add(getBox("Song", songName, getButton(stopIcon, this::onStop)));

        Container pane = getContentPane();
        pane.add(musicItemsViewer);
        pane.add(musicPanel, BorderLayout.SOUTH);
    }

    private void initModel() {
        player.setCompletedListListener (this::onCompletedList);
        player.setStartMusicListener(this::onMusicStartPlay);
    }

    public Mp3PlayerFrame() {
        initModel();
        initComponents();
        buildMenus();
        setSize(750, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
