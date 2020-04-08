package com.miguelfagundez.otato.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.miguelfagundez.otato.screens.GameScreen;


/**
 * Created by Miguel on 3/12/2019.
 */
public abstract class MyEnemies extends Sprite{

    public enum State {WALKING, ATTACKING, FLYING, DIED};

    protected World world;
    protected GameScreen screen;
    public Body body;
    public Vector2 velocity;
    public boolean isFlip;

    public MyEnemies(GameScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(0.5f, 0f);
        isFlip = true;
    }

    protected abstract void defineEnemy();
    public abstract void update(float delta);
    public abstract void hitOnHead();
    public abstract void attack();

    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;

        isFlip = !isFlip;
    }

    public void dispose(){
        world.dispose();
    }
}
