package guis;

import utils.MyColors;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class PlayListDialog extends JDialog {
    private MusicPlayerGuis musicPlayerGuis;
    private ArrayList<String> songPath;
    public PlayListDialog(MusicPlayerGuis musicPlayerGuis) {
        this.musicPlayerGuis = musicPlayerGuis;
        songPath = new ArrayList<>();
        setTitle("Create PlayList");
        setSize(400, 400);
        setLocationRelativeTo(musicPlayerGuis);
        setModal(true);
        setResizable(false);
        getContentPane().setBackground(MyColors.FRAME_COLOR);
        addDialogComponents();
    }
    private void addDialogComponents() {
        JPanel songContainer = new JPanel();
        songContainer.setLayout(new BoxLayout(songContainer, BoxLayout.Y_AXIS));
        songContainer.setBounds((int)(getWidth() * 0.030), 10, (int)(getWidth() * 0.94), (int)(getHeight() * 0.70));
        add(songContainer);

        JButton addSongButton = new JButton("Add Song");
        addSongButton.setBounds(45, (int)(getHeight() * 0.80), 150, 30);
        addSongButton.setFont(new Font("Dialog", Font.PLAIN, 16));
        addSongButton.setBackground(Color.BLACK);
        addSongButton.addActionListener((e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileFilter(new FileNameExtensionFilter("MP3", "mp3"));
            jFileChooser.setCurrentDirectory(new File("assets"));
            int result = jFileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = jFileChooser.getSelectedFile();
                JLabel songLabel = new JLabel(file.getPath());
                songLabel.setFont(new Font("Dialog", Font.PLAIN, 13));
                songLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                songPath.add(songLabel.getText());

                songContainer.add(songLabel);
                songContainer.revalidate();
            }
        }));
        add(addSongButton);

        JButton savePlayListButton = new JButton("Save PlayList");
        savePlayListButton.setBounds(210, (int)(getHeight() * 0.80), 150, 30); // Adjusted position
        savePlayListButton.setFont(new Font("Dialog", Font.PLAIN, 16));
        savePlayListButton.setBackground(Color.BLACK);
        savePlayListButton.addActionListener((e -> {
          try {
              JFileChooser jFileChooser = new JFileChooser();
              jFileChooser.setCurrentDirectory(new File("assets"));
              int result = jFileChooser.showSaveDialog(this);
              if(result == JFileChooser.APPROVE_OPTION) {
                  File file = jFileChooser.getSelectedFile();

                  if(!file.getName().substring(file.getName().length()-4).equalsIgnoreCase(".txt")) {
                      file = new File(file.getAbsoluteFile() + ".txt");
                  }
                  file.createNewFile();

                    // Write the song paths to the file
                    FileWriter fileWriter = new FileWriter(file);
                  BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                  IntStream.range(0, songPath.size())
                          .forEach(i -> {
                              try {
                                  bufferedWriter.write(songPath.get(i) + "\n");
                              } catch (IOException ex) {
                                  ex.printStackTrace();
                              }
                          });
                  bufferedWriter.close();
                  // success dialog
                  JOptionPane.showMessageDialog(this, "PlayList saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();

              }

          }catch (Exception ex){
              ex.printStackTrace();
          }


        }));
        add(savePlayListButton);

        JButton loadPlayListButton = new JButton("Load PlayList");
        loadPlayListButton.setBounds(320, (int)(getHeight() * 0.80), 150, 30); // Adjusted position
        loadPlayListButton.setFont(new Font("Dialog", Font.PLAIN, 16));
        loadPlayListButton.setBackground(Color.BLACK);
        add(loadPlayListButton);
    }

}
