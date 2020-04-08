package com.miguelfagundez.otato;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.miguelfagundez.otato.screens.LoadingScreen;


/**
 * Created by Miguel on 3/14/2019.
 */
public class MyGame extends Game {
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new LoadingScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

		super.dispose();
		batch.dispose();
	}
}
