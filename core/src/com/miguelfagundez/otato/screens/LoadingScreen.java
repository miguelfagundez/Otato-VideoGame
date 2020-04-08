package com.miguelfagundez.otato.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.miguelfagundez.otato.MyGame;
import com.miguelfagundez.otato.MyGame;

/**
 * Created by Miguel on 3/12/2019.
 */
public class LoadingScreen implements Screen {

    private MyGame game;


    public LoadingScreen(MyGame game){
        System.out.println("Loading Screen");
        this.game = game;

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        game.batch.end();
        game.setScreen(new GameScreen(game));
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
