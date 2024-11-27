//package guis;
//import repo.MusicPlayer;
//import repo.Song;
//import utils.MyColors;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import javax.swing.filechooser.FileNameExtensionFilter;
//import javax.swing.plaf.basic.BasicSliderUI;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.util.Hashtable;
//
//public class MusicPlayerGuis extends JFrame {
//
//    private MusicPlayer musicPlayer;
//    private JFileChooser fileChooser;
//    private JLabel songTitle,songArtist;
//    private JPanel playbackPanel;
//    private JSlider playbackSlider;
//
//    public MusicPlayerGuis(){
//        super("Music Player");
//        setSize(400, 600);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        setResizable(false);
//        setLayout(null);
//        getContentPane().setBackground(MyColors.FRAME_COLOR);
//
//        musicPlayer = new MusicPlayer();
//        fileChooser = new JFileChooser();
//        fileChooser.setCurrentDirectory(new File("assets"));
//        fileChooser.setFileFilter(new FileNameExtensionFilter( "MP3", "mp3"));
//
//        addGuiComponents();
//    }
//    private void addGuiComponents(){
//       // add toolbar
//        addToolbar();
//
//        JLabel songImage = new JLabel(loadImage("assets/record.png"));
//        songImage.setBounds(10, 50, getWidth()-20, 225);
//        songImage.setHorizontalAlignment(SwingConstants.CENTER);
//        add(songImage);
//
//        // song title
//        songTitle = new JLabel("Song Title");
//        songTitle.setBounds(10, 280, getWidth()-20, 30);
//        songTitle.setFont(new Font("Dialog", Font.ITALIC, 20));
//        songTitle.setHorizontalAlignment(SwingConstants.CENTER);
//        songTitle.setForeground(MyColors.TEXT_COLOR);
//        add(songTitle);
//
//        // song artist
//        songArtist = new JLabel("Song Artist");
//        songArtist.setBounds(10, 310, getWidth()-20, 30);
//        songArtist.setFont(new Font("Dialog", Font.PLAIN, 16));
//        songArtist.setHorizontalAlignment(SwingConstants.CENTER);
//        songArtist.setForeground(MyColors.TEXT_COLOR);
//        add(songArtist);
//
//        // playback slider
//        playbackSlider = new JSlider(JSlider.HORIZONTAL,0, 100, 0);
//        playbackSlider.setBounds(10, 350, getWidth()-20, 30);
//        playbackSlider.setBackground(null);
//        playbackSlider.setUI(new BasicSliderUI(playbackSlider){
//            @Override
//            public void paintThumb(Graphics g) {
//                Graphics2D g2 = (Graphics2D) g;
//                g2.setColor(Color.RED); // Customize the thumb color
//                g2.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
//            }
//
//            @Override
//            public void paintTrack(Graphics g) {
//                Graphics2D g2 = (Graphics2D) g;
//                g2.setColor(Color.BLUE); // Customize the track color
//                g2.fillRect(trackRect.x, trackRect.y + (trackRect.height / 2 - 2), trackRect.width, 4);
//            }
//        });
//        add(playbackSlider);
//
//        // playback buttons
//       addPlayBackButton();
//    }
//
//    private void addPlayBackButton(){
//        playbackPanel = new JPanel();
//        playbackPanel.setBounds(10, 400, getWidth()-20, 100);
//        playbackPanel.setBackground(null);
//
//        // previous button
//        JButton previousButton = new JButton(loadImage("assets/previous.png"));
//        previousButton.setBorderPainted(false);
//        previousButton.setBackground(null);
//        playbackPanel.add(previousButton);
//
//        // play button
//        JButton playButton = new JButton(loadImage("assets/play.png"));
//        playButton.setBorderPainted(false);
//        playButton.setBackground(Color.BLUE);
//        playButton.addActionListener(e -> {
//            enablePauseDisablePlayButton();
//           musicPlayer.playCurrentSong();
//        });
//        playbackPanel.add(playButton);
//
//
//        // pause button
//        JButton pauseButton = new JButton(loadImage("assets/pause.png"));
//        pauseButton.setBorderPainted(false);
//        pauseButton.setBackground(null);
//        pauseButton.setVisible(false);
//        pauseButton.addActionListener(e -> {
//            enablePlayDisablePauseButton();
//            musicPlayer.pauseSong();
//        });
//        playbackPanel.add(pauseButton);
//
//        // next button
//        JButton nextButton = new JButton(loadImage("assets/next.png"));
//        nextButton.setBorderPainted(false);
//        nextButton.setBackground(null);
//        playbackPanel.add(nextButton);
//
//
//        add(playbackPanel);
//    }
//
//    private void addToolbar(){
//        JToolBar toolbar = new JToolBar();
//        toolbar.setBounds(0, 0, getWidth(), 20);
//        toolbar.setFloatable(false);
//
//        JMenuBar menuBar = new JMenuBar();
//        menuBar.setBorder(BorderFactory.createEmptyBorder());
//        toolbar.add(menuBar);
//
//        JMenu songMenu = new JMenu("Song");
//        menuBar.add(songMenu);
//
//        JMenuItem loadSong = new JMenuItem("Load Song");
//        songMenu.add(loadSong);
//        loadSong.addActionListener(e -> {
//         int result =  fileChooser.showOpenDialog(this);
//           File file = fileChooser.getSelectedFile();
//           if (result == JFileChooser.APPROVE_OPTION && file != null){
//               Song song = new Song(file.getPath());
//               // load song
//               musicPlayer.loadSong(song);
//               // update song title and artist
//                updateSongTitleAndArtist(song);
//                updatePlayBackSlider(song);
//                enablePauseDisablePlayButton();
//           }
//        });
//
//        JMenu playListMenu = new JMenu("PlayList");
//        menuBar.add(playListMenu);
//
//        JMenuItem createPlayList = new JMenuItem("Create PlayList");
//        playListMenu.add(createPlayList);
//
//        JMenuItem loadPlayList = new JMenuItem("Load PlayList");
//        playListMenu.add(loadPlayList);
//
//        add(toolbar);
//    }
//
//    private void enablePauseDisablePlayButton(){
//
//            JButton playButton = (JButton) playbackPanel.getComponent(1);
//            JButton pauseButton = (JButton) playbackPanel.getComponent(2);
//
//            playButton.setVisible(false);
//            playButton.setEnabled(false);
//
//            pauseButton.setVisible(true);
//            pauseButton.setEnabled(true);
//
//    }
//
//    private void enablePlayDisablePauseButton(){
//
//        JButton playButton = (JButton) playbackPanel.getComponent(1);
//        JButton pauseButton = (JButton) playbackPanel.getComponent(2);
//
//        playButton.setVisible(true);
//        playButton.setEnabled(true);
//
//        pauseButton.setVisible(false);
//        pauseButton.setEnabled(false);
//
//    }
//
//    private void updateSongTitleAndArtist(Song song){
//        songTitle.setText(song.getSongTitle());
//        songArtist.setText(song.getSongArtist());
//    }
//    private void updatePlayBackSlider(Song song){
//        playbackSlider.setMaximum(song.getMp3File().getFrameCount());
//        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
//
//        JLabel startLabel = new JLabel("00:00");
//        startLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
//        startLabel.setForeground(MyColors.BUTTON_COLOR);
//
//        JLabel endLabel = new JLabel(song.getSongLength());
//        endLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
//        endLabel.setForeground(MyColors.BUTTON_COLOR);
//
//        labelTable.put(0, startLabel);
//        labelTable.put(song.getMp3File().getFrameCount(), endLabel);
//
//        playbackSlider.setLabelTable(labelTable);
//        playbackSlider.setPaintLabels(true);
//
//    }
//
//    private ImageIcon loadImage(String path){
//       try {
//           BufferedImage img = ImageIO.read(new File(path));
//           return new ImageIcon(img);
//       }catch (Exception e) {
//           e.printStackTrace();
//       }
//         return null;
//    }
//}


package guis;
import repo.MusicPlayer;
import utils.MyColors;
import javax.swing.*;

public class MusicPlayerGuis extends JFrame {
    private MusicPlayer musicPlayer;
    private SongInfoPanel songInfoPanel;
    private PlaybackPanel playbackPanel;
    private Toolbar toolbar;

    public MusicPlayerGuis() {
        super("Music Player");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(MyColors.FRAME_COLOR);

        musicPlayer = new MusicPlayer(this);

        // Initialize components
        songInfoPanel = new SongInfoPanel();
        playbackPanel = new PlaybackPanel(musicPlayer, this);
        toolbar = new Toolbar(this, musicPlayer);

        // Add components to frame
        add(toolbar);
        add(songInfoPanel);
        add(playbackPanel);

        // Update bounds of components
        toolbar.setBounds(0, 0, getWidth(), 20);
        songInfoPanel.setBounds(10, 50, getWidth() - 20, 300);
        playbackPanel.setBounds(10, 350, getWidth() - 20, 200);
    }

    public SongInfoPanel getSongInfoPanel() {
        return songInfoPanel;
    }

    public PlaybackPanel getPlaybackPanel() {
        return playbackPanel;
    }
}
