package repo;
import guis.MusicPlayerGuis;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.*;
import java.util.ArrayList;

public class MusicPlayer extends PlaybackListener {

    private static final  Object playSignal = new Object();
    private MusicPlayerGuis gui;
    private Song song;
    public Song getSong() {
        return song;
    }
    private ArrayList<Song> playList;
    private int playListIndex;
    private AdvancedPlayer player;
    private boolean isPaused;
    private boolean songEnded;
    private boolean pressedNext, pressedPrevious;
    private int currentFrame;
    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }
    private int currentTimeMillis;

    public void setCurrentTimeMillis(int currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
    }

    public MusicPlayer(MusicPlayerGuis gui){
        this.gui = gui;
    }

    public void loadSong(Song loadSong){
        song = loadSong;
        playList = null;
        if (!songEnded)
            stopSong();
        if (song!=null){
            gui.getPlaybackPanel().togglePlayPause(true);
            gui.getSongInfoPanel().updateSongInfo(song);
            gui.getPlaybackPanel().updatePlaybackSlider(song);
            currentTimeMillis = 0;
            currentFrame = 0;
            playCurrentSong();

        }

    }

    public void loadPlayList(File playListFile){
       playList = new ArrayList<>();
       // store the path of the songs in the playlist
        try {
            FileReader fileReader = new FileReader(playListFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String songPath;
            while ((songPath = bufferedReader.readLine())!=null){
                playList.add(new Song(songPath));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(playList.size()>0){
            gui.getSongInfoPanel().updateSongInfo(playList.get(0));
            currentTimeMillis = 0;
            song = playList.get(0);
            currentFrame = 0;
            // update title and artist
            // update playpause button
            gui.getPlaybackPanel().togglePlayPause(true);
            gui.getSongInfoPanel().updateSongInfo(song);
            // update playback slider
            gui.getPlaybackPanel().updatePlaybackSlider(song);
            playCurrentSong();
        }
    }



    public void playCurrentSong(){
        if(song==null)return;
        try {
            FileInputStream fileInputStream = new FileInputStream(song.getSongPath());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            player = new AdvancedPlayer(bufferedInputStream);
            player.setPlayBackListener(this);

            startMusicThread();
            startPlaybackSliderThread();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void pauseSong(){
        if(player !=null){
            isPaused = true;
            stopSong();
        }
    }

    public void stopSong(){
        if (player!=null){
            player.stop();
            player.close();
            player =null;
        }
    }

    public void nextSong(){
        if (playList ==null) return;
        if (playListIndex+1 > playList.size()-1)return;
        pressedNext = true;
        if(!songEnded)
            stopSong();
        if (playListIndex < playList.size()-1) {
            playListIndex++;
            song = playList.get(playListIndex);
            currentTimeMillis = 0;
            currentFrame = 0;
            gui.getSongInfoPanel().updateSongInfo(song);
            gui.getPlaybackPanel().updatePlaybackSlider(song);
            gui.getPlaybackPanel().togglePlayPause(true);

            playCurrentSong();
        }
    }

    public void previousSong(){
        if (playList ==null) return;
        if (playListIndex-1 < 0)return;
        pressedPrevious = true;
        if(!songEnded)
            stopSong();
        if (playListIndex > 0) {
            playListIndex--;
            song = playList.get(playListIndex);
            currentTimeMillis = 0;
            currentFrame = 0;
            gui.getSongInfoPanel().updateSongInfo(song);
            gui.getPlaybackPanel().updatePlaybackSlider(song);
            gui.getPlaybackPanel().togglePlayPause(true);

            playCurrentSong();
        }
    }

    private void startMusicThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(isPaused){
                        synchronized (playSignal){
                            isPaused = false;
                            playSignal.notify();
                        }

                        player.play(currentFrame,Integer.MAX_VALUE);
                    }
                    else {
                        player.play();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void startPlaybackSliderThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(isPaused){
                    try {
                        synchronized (playSignal){
                            playSignal.wait();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                while (!isPaused && !songEnded && !pressedNext && !pressedPrevious){
                   try {
                       currentTimeMillis++;
                       int calculatedFrame = (int) ((double) currentTimeMillis * 2.08 *song.getFrame());
                       gui.getPlaybackPanel().setPlaybackSliderValue(calculatedFrame);
                          Thread.sleep(1);
                   }catch (Exception e){
                       e.printStackTrace();
                   }

                }
            }
        }).start();

    }

    @Override
    public void playbackStarted(PlaybackEvent evt) {
        System.out.println("Playback Start");
        songEnded = false;
        pressedNext = false;
        pressedPrevious = false;
    }

    @Override
    public void playbackFinished(PlaybackEvent evt) {
        System.out.println("Playback Finish");
        System.out.println("Frame: "+evt.getFrame());
        if (isPaused){
            currentFrame +=(int) ((double) evt.getFrame() * song.getFrame());
        }else {
            // if next or previous button is pressed we don't want to play the next song
            if (pressedNext || pressedPrevious) return;
            songEnded = true;
            if (playList==null){
                gui.getPlaybackPanel().togglePlayPause(false);
            }else {
                if (playListIndex == playList.size() - 1) {
                    gui.getPlaybackPanel().togglePlayPause(false);
                }else {
                    nextSong();
                }
            }
        }
    }
}
