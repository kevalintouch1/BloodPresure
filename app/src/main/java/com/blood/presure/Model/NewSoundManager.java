package com.blood.presure.Model;

import android.media.SoundPool;

import com.blood.presure.NewBloodApplication;
import com.blood.presure.R;


public class NewSoundManager {
    static NewSoundManager instance;
    SoundPool mSoundPool;
    int resource;
    int sound;


    public static NewSoundManager getInstance() {
        if (instance == null) {
            instance = new NewSoundManager();
        }
        return instance;
    }

    NewSoundManager() {
        load();
    }

    public void load() {
        SoundPool build = new SoundPool.Builder().setMaxStreams(5).build();
        this.mSoundPool = build;
        this.sound = build.load(NewBloodApplication.getInstance(), R.raw.beat, 1);
    }

    public void play() {
        this.mSoundPool.play(this.sound, 1.0f, 1.0f, 0, 0, 1.0f);
    }

    public void destroy() {
        SoundPool soundPool = this.mSoundPool;
        if (soundPool != null) {
            soundPool.release();
        }
        instance = null;
    }
}
