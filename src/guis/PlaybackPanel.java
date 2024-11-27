package guis;

import repo.MusicPlayer;
import repo.Song;
import utils.ImageLoader;
import utils.MyColors;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;

public class PlaybackPanel extends JPanel {
    private JSlider playbackSlider;
    private JButton playButton;
    private JButton pauseButton;

    public PlaybackPanel(MusicPlayer musicPlayer, MusicPlayerGuis gui) {
        setLayout(null);
        setBackground(null);

        playbackSlider = new JSlider(0, 100, 0);
        playbackSlider.setBounds(0, 0, 380, 30);
        playbackSlider.setBackground(null);
        playbackSlider.setUI(new CustomSliderUI(playbackSlider));
        playbackSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
              musicPlayer.pauseSong();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                JSlider source = (JSlider) e.getSource();
                int value = source.getValue();
                musicPlayer.setCurrentFrame(value);
                // update current time to in milliseconds
                musicPlayer.setCurrentTimeMillis((int)  (value/ (2.08 * musicPlayer.getSong().getFrame())) );
                musicPlayer.playCurrentSong();
                togglePlayPause(true);



            }
        });
        add(playbackSlider);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 40, 380, 100);
        buttonPanel.setBackground(null);

        JButton previousButton = new JButton(ImageLoader.loadImage("assets/previous.png"));
        previousButton.addActionListener(e -> {
            musicPlayer.previousSong();
        });
        buttonPanel.add(previousButton);

        playButton = new JButton(ImageLoader.loadImage("assets/play.png"));
        playButton.setBackground(MyColors.BUTTON_COLOR); // Background color of the button
        playButton.setForeground(Color.WHITE); // Text or icon foreground color
        playButton.setBorderPainted(false); // Removes border for a cleaner look
        playButton.setFocusPainted(false); // Removes focus highlight
        playButton.addActionListener(e -> {
            togglePlayPause(true);
            musicPlayer.playCurrentSong();
        });
        buttonPanel.add(playButton);

        pauseButton = new JButton(ImageLoader.loadImage("assets/pause.png"));
        pauseButton.setBackground(MyColors.BUTTON_COLOR); // Background color of the button
        pauseButton.setForeground(Color.WHITE); // Text or icon foreground color
        pauseButton.setBorderPainted(false); // Removes border for a cleaner look
        pauseButton.setFocusPainted(false); // Removes focus highlight
        pauseButton.setVisible(false);
        pauseButton.addActionListener(e -> {
            togglePlayPause(false);
            musicPlayer.pauseSong();
        });
        buttonPanel.add(pauseButton);


        JButton nextButton = new JButton(ImageLoader.loadImage("assets/next.png"));
        nextButton.addActionListener(e -> {
            musicPlayer.nextSong();
        });
        buttonPanel.add(nextButton);

        add(buttonPanel);
    }

    public void setPlaybackSliderValue(int value) {
        playbackSlider.setValue(value);
    }

    public void updatePlaybackSlider(Song song) {
        playbackSlider.setMaximum(song.getMp3File().getFrameCount());
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("00:00"));
        labelTable.put(song.getMp3File().getFrameCount(), new JLabel(song.getSongLength()));
        playbackSlider.setLabelTable(labelTable);
        playbackSlider.setPaintLabels(true);
    }

    public void togglePlayPause(boolean isPlaying) {
        playButton.setVisible(!isPlaying);
        pauseButton.setVisible(isPlaying);
    }

    private static class CustomSliderUI extends BasicSliderUI {
        public CustomSliderUI(JSlider slider) {
            super(slider);
        }

        @Override
        public void paintThumb(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.RED);
            g2.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
        }

        @Override
        public void paintTrack(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.BLUE);
            g2.fillRect(trackRect.x, trackRect.y + trackRect.height / 2 - 2, trackRect.width, 4);
        }
    }
}
