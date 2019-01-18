package org.minhvu.operationrebound.essentials;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Sound {
    public Clip FIRE;
    public Clip BITE;
    public Clip DEATH;
    public Clip HELL;
    public Clip START_RELOAD;
    public Clip FINISH_RELOAD;

    private InputStream inputstream;
    private AudioInputStream audioinputstream;

    public Sound() {
        FIRE = getAudioClip("/sounds/fire.wav");
        BITE = getAudioClip("/sounds/bite.wav");
        DEATH = getAudioClip("/sounds/death.wav");
        HELL = getAudioClip("/sounds/hell.wav");
        START_RELOAD = getAudioClip("/sounds/sreload.wav");
        FINISH_RELOAD = getAudioClip("/sounds/freload.wav");
    }

    private Clip getAudioClip(String path) {
        Clip clip = null;

        inputstream = getClass().getResourceAsStream(path);

        try {
            audioinputstream = AudioSystem.getAudioInputStream(new BufferedInputStream(inputstream));

            try {
                clip = AudioSystem.getClip();
                clip.open(audioinputstream);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

        return clip;
    }
}