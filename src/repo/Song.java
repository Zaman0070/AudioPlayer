package repo;

import com.mpatric.mp3agic.Mp3File;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import java.io.File;

public class Song {
    private String songTitle;
    private String songArtist;
    private String songLength;
    private String songPath;
    private Mp3File mp3File;
    private double frame;

    public Song(String path){
        this.songPath = path;
        try {
            mp3File = new Mp3File(path);
            frame = (double) mp3File.getFrameCount() / mp3File.getLengthInMilliseconds();
            songLength = covertToSongLengthFormat();

            AudioFile audioFile = AudioFileIO.read(new File(path));
            Tag tag = audioFile.getTag();
            if (tag != null){
                this.songTitle = tag.getFirst(FieldKey.TITLE);
                this.songArtist = tag.getFirst(FieldKey.ARTIST);
            }else {
                this.songTitle = "Unknown";
                this.songArtist = "Unknown";
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String covertToSongLengthFormat(){
        long mint = mp3File.getLengthInSeconds() / 60;
        long sec = mp3File.getLengthInSeconds() % 60;
        return String.format("%02d:%02d",mint,sec);
    }


    public String getSongTitle() {
        return songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public String getSongLength() {
        return songLength;
    }

    public String getSongPath() {
        return songPath;
    }

    public Mp3File getMp3File() {
        return mp3File;
    }

    public double getFrame() {
        return frame;
    }
}
