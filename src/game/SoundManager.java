package game;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/*
    Handles all game audio.
    playMusic() loops a background track per level.
    playSound() plays one-shot sound effects without interrupting music.
*/

public class SoundManager{

    private Clip backgroundMusic;
    private String currentMusicFilePath; // Tracks which music file is currently playing

    public void playMusic(String filePath){
        // If the same music is already playing, do nothing to avoid interruption
        if (currentMusicFilePath != null && currentMusicFilePath.equals(filePath) && backgroundMusic != null && backgroundMusic.isRunning()) {
            return;
        }

        stopMusic(); // Stop any *other* music that might be playing

        try {
            AudioInputStream stream= AudioSystem.getAudioInputStream(new File(filePath));
            backgroundMusic= AudioSystem.getClip();
            backgroundMusic.open(stream);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundMusic.start();
            currentMusicFilePath = filePath; // Store the path of the music now playing
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Could not load music: " + filePath + " Error: " + e.getMessage());
        }
    }

    public void stopMusic(){ // stops and closes the current background music if it's playing
        if (backgroundMusic!= null&& backgroundMusic.isRunning()){
            backgroundMusic.stop();
            backgroundMusic.close();
            currentMusicFilePath = null; // Clear the path as no music is playing
        }
    }

    public void playSound(String filePath){ //for playing one-shot sound effects, doesn't interfere with background music
        try {
            AudioInputStream stream= AudioSystem.getAudioInputStream(new File(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
            clip.addLineListener(event ->{
                if (event.getType()== LineEvent.Type.STOP) {
                    clip.close();
                }
            });
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Could not load sound: "+ filePath);
        }
    }
}
