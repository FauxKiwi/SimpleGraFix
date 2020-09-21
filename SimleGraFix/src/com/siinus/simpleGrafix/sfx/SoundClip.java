package com.siinus.simpleGrafix.sfx;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

public class SoundClip {
    private Clip clip = null;
    private FloatControl gainControl;

    public SoundClip(String path) {
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

    public void stop() {
        if (clip.isRunning()) {
            clip.stop();
        }
    }

    public void close() {
        stop();
        clip.drain();
        clip.close();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        play();
    }

    public void setVolume(float volume) {
        gainControl.setValue(volume);
    }

    public boolean isRunning() {
        return clip.isRunning();
    }
}
