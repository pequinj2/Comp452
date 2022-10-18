package com.bugwars.Assignment1;

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
