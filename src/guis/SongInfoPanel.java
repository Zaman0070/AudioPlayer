package guis;

import utils.ImageLoader;
import utils.MyColors;
import repo.Song;

import javax.swing.*;
import java.awt.*;

public class SongInfoPanel extends JPanel {
    private JLabel songTitle;
    private JLabel songArtist;

    public SongInfoPanel() {
        setLayout(null);
        setBackground(null);

        JLabel songImage = new JLabel(ImageLoader.loadImage("assets/record.png"));
        songImage.setBounds(0, 0, 400, 225);
        songImage.setHorizontalAlignment(SwingConstants.CENTER);
        add(songImage);

        songTitle = new JLabel("Song Title");
        songTitle.setBounds(10, 230, 380, 30);
        songTitle.setFont(new Font("Dialog", Font.ITALIC, 20));
        songTitle.setHorizontalAlignment(SwingConstants.CENTER);
        songTitle.setForeground(MyColors.TEXT_COLOR);
        add(songTitle);

        songArtist = new JLabel("Song Artist");
        songArtist.setBounds(10, 260, 380, 30);
        songArtist.setFont(new Font("Dialog", Font.PLAIN, 16));
        songArtist.setHorizontalAlignment(SwingConstants.CENTER);
        songArtist.setForeground(MyColors.TEXT_COLOR);
        add(songArtist);
    }

    public void updateSongInfo(Song song) {
        songTitle.setText(song.getSongTitle());
        songArtist.setText(song.getSongArtist());
    }
}
