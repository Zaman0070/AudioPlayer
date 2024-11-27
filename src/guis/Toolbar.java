package guis;

import repo.MusicPlayer;
import repo.Song;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class Toolbar extends JToolBar {
    private JFileChooser fileChooser;

    public Toolbar(MusicPlayerGuis gui, MusicPlayer musicPlayer) {
        setFloatable(false);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createEmptyBorder());
        add(menuBar);

        JMenu songMenu = new JMenu("Song");
        menuBar.add(songMenu);

        fileChooser = new JFileChooser("assets");

        JMenuItem loadSong = new JMenuItem("Load Song");
        songMenu.add(loadSong);
        loadSong.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(gui);
            File file = fileChooser.getSelectedFile();
            if (result == JFileChooser.APPROVE_OPTION && file != null) {
                Song song = new Song(file.getPath());
                musicPlayer.loadSong(song);

                // Update UI
                gui.getSongInfoPanel().updateSongInfo(song);
                gui.getPlaybackPanel().updatePlaybackSlider(song);
            }
        });

        JMenu playListMenu = new JMenu("PlayList");
        menuBar.add(playListMenu);

        JMenuItem createPlayList = new JMenuItem("Create PlayList");
        createPlayList.addActionListener(e -> {
            new PlayListDialog(gui).setVisible(true);
        });
        playListMenu.add(createPlayList);

        JMenuItem loadPlayList = new JMenuItem("Load PlayList");
        loadPlayList.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileFilter(new FileNameExtensionFilter("Playlist", "txt"));
            jFileChooser.setCurrentDirectory(new File("assets"));
            int result = jFileChooser.showOpenDialog(gui);
            File selectedFile = jFileChooser.getSelectedFile();

            if(result == JFileChooser.APPROVE_OPTION && selectedFile != null) {
                musicPlayer.stopSong();
                musicPlayer.loadPlayList(selectedFile);
            }
        });
        playListMenu.add(loadPlayList);
    }
}
