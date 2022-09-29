package com.bugwars.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.bugwars.game.BugWars;

// This is where the program starts
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(1216, 896); //divisable by 32

		BugWars core = new BugWars();
		core.setSplashWorker(new DesktopSplashWorker());// Create new instance of 'Bug Wars'
		config.setTitle("Bug Wars");
		new Lwjgl3Application(core, config);
	}
}
