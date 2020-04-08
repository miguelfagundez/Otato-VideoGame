package com.miguelfagundez.otato.utils;

import com.badlogic.gdx.Gdx;

/**
 * Created by Miguel on 3/12/2019.
 */
public class Constants {
    // Width & Height of the Screen
    public static final int WIDTH = Gdx.graphics.getWidth();
    public static final int HEIGHT = Gdx.graphics.getHeight();

    // WORLD SIZES
    public static final int WORLD_WIDTH = 600;
    public static final int WORLD_HEIGHT = 312;

    //PPI - Pixel per Meters (for Box2D)
    public static final float PPM = 100;

    //Collision Operations - Filters
    public static final short DEFAULT_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short OBJECT_BIT = 4;
    public static final short ENEMY_BIT = 16;
    public static final short DESTROYED_BIT = 32;
    public static final short ENEMY_HEAD_BIT = 64;
    public static final short IMAGINAY_WALL_BIT = 128;
    public static final short BIRD_BIT = 256;

    // Game's Name
    public static final String GAME_NAME = "Otato";
}
