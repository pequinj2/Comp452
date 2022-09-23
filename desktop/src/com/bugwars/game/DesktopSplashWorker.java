package com.bugwars.game;

import java.awt.SplashScreen;

public class DesktopSplashWorker implements SplashWorker{
    @Override
    public void closeSplashScreen() {
        SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash != null){
            splash.close();
        }
    }

}
