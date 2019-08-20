package com.example.sjl95.ai_drum.framework;

import android.os.Vibrator;

public interface Game {
    public Audio getAudio();

    public Input getInput();

    public FileIO getFileIO();

    public Graphics getGraphics();

    public Vibrator getVibrator();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getInitScreen();

    public void goToActivity(Class<?> activity);
}
