package com.bugwars.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.bugwars.game.BugWars;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(1500, 900);
		BugWars core = new BugWars();
		core.setSplashWorker(new DesktopSplashWorker());
		config.setTitle("Bug Wars");
		new Lwjgl3Application(core, config);
	}
}
