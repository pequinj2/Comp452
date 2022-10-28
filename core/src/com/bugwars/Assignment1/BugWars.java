package com.bugwars.Assignment1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BugWars extends Game {
	SpriteBatch batch;
	Texture img;
	public BitmapFont font;
	private SplashWorker splashWorker;
	
	@Override
	public void create () {
		splashWorker.closeSplashScreen();
		batch = new SpriteBatch();
		font = new BitmapFont(); // use libGDX's default Arial font
		this.setScreen(new MainMenuScreen((this)));
	}

	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}


	/**
	 *  Getter for Splash screen
	 *  Called in "DesktopSplashWorker"
	 * @return
	 */
	public SplashWorker getSplashWorker() {
		return splashWorker;
	}

	/**
	 * Setter for Splash screen
	 * Called in "DesktopLauncher"
	 * @param splashWorker
	 */
	public void setSplashWorker (SplashWorker splashWorker){
		this.splashWorker = splashWorker;
	}
}
