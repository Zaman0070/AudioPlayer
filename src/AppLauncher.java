import guis.MusicPlayerGuis;
import repo.Song;

import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Song song = new Song("assets/Wind Riders - Asher Fulero.mp3");
               new MusicPlayerGuis().setVisible(true);
            }
        });
    }
}
