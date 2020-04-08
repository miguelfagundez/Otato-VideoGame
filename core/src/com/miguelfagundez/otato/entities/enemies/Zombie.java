package com.miguelfagundez.otato.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.miguelfagundez.otato.screens.GameScreen;
import com.miguelfagundez.otato.utils.Constants;

/**
 * Created by Miguel on 3/25/2019.
 */
public class Zombie extends MyEnemies {

    private float stateTimer;
    private float attackTimer;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> initAnimation;
    private Animation<TextureRegion> attackAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private boolean init;
    private boolean attackActive;
    private Vector2 oldposition;


    public Zombie(GameScreen screen, float x, float y) {
        super(screen, x, y);

        frames = new Array<TextureRegion>();
        for(int i=0; i < 5; i++) {
            for (int j = 0; j < 2; j++)
                frames.add(new TextureRegion(screen.getAtlasEnemy().findRegion("zombiewalk"), j * 31, i * 48, 31, 48));
        }
        walkAnimation = new Animation(0.15f, frames);

        frames.clear();

        for(int i=0; i < 10; i++)
            frames.add(new TextureRegion(screen.getAtlasEnemy().findRegion("zombieappear"), i*37, 0, 37, 48));
        initAnimation = new Animation(0.30f, frames);

        frames.clear();

        for(int i=0; i < 7; i++)
            frames.add(new TextureRegion(screen.getAtlasEnemy().findRegion("zombieattack"), i*55, 0, 55, 48));
        attackAnimation = new Animation(0.05f, frames);

        attackActive = false;
        attackTimer = 0;

        stateTimer = 0;
        setBounds(getX(), getY(), 31 / Constants.PPM, 48 / Constants.PPM);
        setToDestroy = false;
        destroyed = false;
    }

    @Override
    protected void defineEnemy() {
        //Body Definition
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bdef);
        FixtureDef fixture = new FixtureDef();

        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Constants.PPM);
        fixture.filter.categoryBits = Constants.ENEMY_BIT;
        fixture.filter.maskBits = Constants.DEFAULT_BIT |
                Constants.PLAYER_BIT |
                Constants.OBJECT_BIT |
                Constants.IMAGINAY_WALL_BIT;

        fixture.shape = shape;
        body.createFixture(fixture).setUserData(this);

        //Create the head fixture here
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-4,8).scl(1 / Constants.PPM);
        vertice[1] = new Vector2(4,8).scl(1 / Constants.PPM);
        vertice[2] = new Vector2(-3,3).scl(1 / Constants.PPM);
        vertice[3] = new Vector2(3,3).scl(1 / Constants.PPM);
        head.set(vertice);

        fixture.shape = head;
        fixture.restitution = 1f; //Player jump when he's on the enemy's head
        fixture.filter.categoryBits = Constants.ENEMY_HEAD_BIT;
        body.createFixture(fixture).setUserData(this);
    }

    @Override
    public void update(float delta) {

        if(!attackActive)
            stateTimer += delta;
        else
            attackTimer += delta;
        //stateTimer += delta;

        if(stateTimer > 1) {
            if (init) {
                if (!initAnimation.isAnimationFinished(stateTimer)) {
                    setPosition(oldposition.x - getWidth() / 2, oldposition.y - getHeight() / 4.5f);
                    setBounds(getX(), getY(), 37 / Constants.PPM, 48 / Constants.PPM);
                    setRegion(initAnimation.getKeyFrame(stateTimer, true));
                } else {
                    init = false;
                    setToDestroy = false;
                    destroyed = false;
                    body.setActive(true);
                    setPosition(oldposition.x - getWidth() / 2, oldposition.y - getHeight() / 4.5f);
                    setBounds(getX(), getY(), 31 / Constants.PPM, 48 / Constants.PPM);
                    setRegion(walkAnimation.getKeyFrame(stateTimer, true));
                }
            } else if (setToDestroy && !destroyed) {
                destroyed = true;
                oldposition = body.getPosition();
                //world.destroyBody(body);
                body.setActive(false);
                setRegion(new TextureRegion(screen.getAtlasEnemy().findRegion("zombiedie"), 219, 0, 73, 48));
                attackActive = false;
                stateTimer = 0;
                init = true;
            } else if (!destroyed) {
                if(attackActive) {
                    setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 4.5f);
                    setBounds(getX(), getY(), 55 / Constants.PPM, 48 / Constants.PPM);
                    setRegion(attackAnimation.getKeyFrame(attackTimer, true));
                    if(attackAnimation.isAnimationFinished(attackTimer)) {
                        attackActive = false;
                        reverseVelocity(true, false);
                        attackTimer = 0;
                    }
                }else {
                    body.setLinearVelocity(velocity);
                    setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 4.5f);
                    setBounds(getX(), getY(), 31 / Constants.PPM, 48 / Constants.PPM);
                    setRegion(walkAnimation.getKeyFrame(stateTimer, true));
                }
                if (isFlip)
                    setFlip(true, false);
            }
        }else
            setRegion(new TextureRegion(screen.getAtlasEnemy().findRegion("zombiedie"), 219, 0, 73, 48));
    }

    public void draw(Batch batch){
        //if(!destroyed || stateTimer < 1)
        if(init || (!init && !destroyed) || stateTimer < 1)
            super.draw(batch);
    }

    @Override
    public void hitOnHead() {
        setToDestroy = true;
    }
    @Override
    public void attack(){
        System.out.println("Zombie Attack");
        attackTimer = 0;
        attackActive = true;
    }

}
