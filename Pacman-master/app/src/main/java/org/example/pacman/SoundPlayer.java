package org.example.pacman;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {
    private static SoundPool soundPool;
    private static int fruitHit;
    private static int dead;
    private static int pacSound;
    private boolean isPlaying = false;

    public SoundPlayer (Context context){
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        fruitHit = soundPool.load(context,R.raw.pacman_eatfruit,1);
        dead = soundPool.load(context, R.raw.pacman_death,1);
        pacSound = soundPool.load(context, R.raw.movement,1);
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public void playFruitSound(){
        soundPool.play(fruitHit, 1.0f, 1.0f,1,0,1.0f);
    }
    public void playDeadSound(){
        soundPool.play(dead, 1.0f, 1.0f,1,0,1.0f);
    }
    public void playPacMoveSound(){
        if (isPlaying==false){
            setPlaying(true);
        soundPool.play(pacSound, 1.0f, 1.0f,1,-1,1.0f);

        }
    }
}
