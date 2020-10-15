package com.siinus.simpleGrafix.sfx;

import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Represents a sound clip that can be played in the program.
 *
 * @author Simon
 * @since 1.0
 */
public class SoundClip {
    private Clip clip = null;
    private FloatControl gainControl;

    /**
     * Creates a new sound clip.
     *
     * @param path The path to the audio file.
     */
    public SoundClip(@NotNull String path) {
        AudioInputStream audioInputStream = null;
        try {
            if (path.startsWith("http://") || path.startsWith("https://")) {
                audioInputStream = AudioSystem.getAudioInputStream(new URL(path));
            } else {
                audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(SoundClip.class.getResourceAsStream(path)));
            }

            AudioFormat baseFormat = audioInputStream.getFormat();
            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            AudioInputStream decodedAudioInputStream = AudioSystem.getAudioInputStream(decodeFormat, audioInputStream);

            clip = AudioSystem.getClip();
            clip.open(decodedAudioInputStream);

            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts playing the sound clip.
     */
    public void play() {
        if (clip==null) {
            return;
        }
        stop();
        clip.setFramePosition(0);
        while (!clip.isRunning()) {
            clip.start();
        }
    }

    /**
     * Stops playing the sound clip.
     */
    public void stop() {
        if (clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * Closes the sound clip.
     */
    public void close() {
        stop();
        clip.drain();
        clip.close();
    }

    /**
     * Starts looping the sound clip.
     */
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        play();
    }

    /**
     * Sets the volume of the sound clip.
     *
     * @param volume The new volume
     */
    public void setVolume(float volume) {
        gainControl.setValue(volume);
    }

    /**
     * Returns the volume of the sound clip.
     *
     * @return The volume
     */
    public boolean isRunning() {
        return clip.isRunning();
    }
}
