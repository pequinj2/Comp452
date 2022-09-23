package com.bugwars.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class BugWars extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private SplashWorker splashWorker;
	
	@Override
	public void create () {
		splashWorker.closeSplashScreen();
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
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
