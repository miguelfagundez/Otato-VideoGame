package com.miguelfagundez.otato.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.miguelfagundez.otato.entities.enemies.*;
import com.miguelfagundez.otato.entities.Player;
import com.miguelfagundez.otato.MyGame;
import com.miguelfagundez.otato.utils.B2WorldCreator;
import com.miguelfagundez.otato.utils.Constants;
import com.miguelfagundez.otato.utils.Hud;
import com.miguelfagundez.otato.utils.WorldContactListener;

/**
 * Created by Miguel on 3/12/2019.
 */
public class GameScreen implements Screen{

    // Game Camera and Viewport
    private OrthographicCamera cam;
    private Viewport viewPort;

    // My Game Variable
    private MyGame game;

    // Hud to draw text and time
    private Hud hud;

    //Load the map
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2D Variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //Player
    private Player player;
    private Bird bird;

    //Texture -TEMPORAL-
    private TextureAtlas myTextureAtlas;
    private TextureAtlas myEnemyAtlas;


    public GameScreen(MyGame game){
        System.out.println("Game Screen");
        this.game = game;

        //My Atlas
        myTextureAtlas = new TextureAtlas("sprites/player.pack");
        myEnemyAtlas = new TextureAtlas("sprites/enemies.pack");

        // Create the camera and viewport to follow player throught the tiled map
        cam = new OrthographicCamera();
        viewPort = new FitViewport(Constants.WORLD_WIDTH / Constants.PPM, Constants.WORLD_HEIGHT / Constants.PPM, cam);
        cam.position.set(viewPort.getWorldWidth() / 2, viewPort.getWorldHeight() / 2, 0);

        // Create a new game hud for our name, time, score, level info
        hud = new Hud(this.game.batch);

        //Load the tile map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("world1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Constants.PPM);

        //Create the world
        world = new World(new Vector2(0,-10), true);
        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);

        player = new Player(this);
        bird = new Bird(this, .32f, 2.90f);

        world.setContactListener(new WorldContactListener());
    }

    public TextureAtlas getAtlas(){
        return myTextureAtlas;
    }

    public TextureAtlas getAtlasEnemy(){
        return myEnemyAtlas;
    }

    public World getWorld(){ return world; }
    public TiledMap getMap() {return map; }

    @Override
    public void show() {

    }

    public void handleInput(float delta){

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.body.applyLinearImpulse(new Vector2(0, 4f), player.body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.body.getLinearVelocity().x <= 2)
            player.body.applyLinearImpulse(new Vector2(0.1f, 0), player.body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.body.getLinearVelocity().x >= -2)
            player.body.applyLinearImpulse(new Vector2(-0.1f, 0), player.body.getWorldCenter(), true);

    }

    public void update(float delta){
        handleInput(delta);

        //takes 1 step in a physics simultaion (60 frames per second)
        world.step(1 / 60f, 6, 2);

        // Update the player's position
        player.update(delta);
        //skeleton.update(delta);
        for(MyEnemies enemy : creator.getSkeletons())
            enemy.update(delta);
        for(MyEnemies enemy : creator.getZombies())
            enemy.update(delta);
        bird.update(delta);

        hud.update(delta);

        //Update the cam's X position
        cam.position.x = player.body.getPosition().x;
        //Update our game cam with new coordinates
        cam.update();

        //Rederer only draw that game cam can see
        renderer.setView(cam);

        // Control variable used to show the ship with fire or not
        float aceleracion = Gdx.input.getAccelerometerY();

        if (aceleracion > 1.5f) {
            //myPlayer.setThrust(true);
            player.body.applyLinearImpulse(new Vector2(0.1f, 0), player.body.getWorldCenter(), true);
        } else {
            // If aceleration is negative, then we draw the first frame in ship's image
            //myPlayer.setThrust(false);
            if (aceleracion < -1.5f) {
                player.body.applyLinearImpulse(new Vector2(-0.1f, 0), player.body.getWorldCenter(), true);
                /*myPlayer.setVelocity(0.95f);
                myPlayer.setX(myPlayer.getX() + aceleracion);*/
            }// if aceleracion < -1.5f
        }// if aceleracion > 1.5f
    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render the tile backgound and graphics
        renderer.render();

        //Render the box2d
        b2dr.render(world, cam.combined);

        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        player.draw(game.batch);
        //skeleton.draw(game.batch);
        for(MyEnemies enemy : creator.getSkeletons())
            enemy.draw(game.batch);
        for(MyEnemies enemy : creator.getZombies())
            enemy.draw(game.batch);
        //bird.draw(game.batch);
        game.batch.end();

        // Render our Hud
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        //game.myCam.resize(width,height);
        viewPort.update(width,height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

        renderer.dispose();
        hud.dispose();
        map.dispose();
        world.dispose();
        b2dr.dispose();
        myTextureAtlas.dispose(); //TEMPORAL
        myEnemyAtlas.dispose();

    }

    @Override
    public void dispose() {

    }
}
