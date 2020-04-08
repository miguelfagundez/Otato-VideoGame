package com.miguelfagundez.otato.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
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
 * Created by Miguel on 3/12/2019.
 */
public class Bird extends MyEnemies {

    private float stateTimer;
    private Animation<TextureRegion> flyAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;

    public Bird(GameScreen screen, float x, float y){
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i=0; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlasEnemy().findRegion("bird"), i*32, 0, 32, 24));
        flyAnimation = new Animation<TextureRegion>(0.15f, frames);
        stateTimer = 0;
        setBounds(getX(), getY(), 32 / Constants.PPM, 24 / Constants.PPM);
        setToDestroy = false;
        destroyed = false;
        isFlip = false;
    }

    public void update(float delta){
        stateTimer += delta;
        if(!destroyed) {
            body.setLinearVelocity(velocity);
            body.applyLinearImpulse(new Vector2(0, .1667f), body.getWorldCenter(), true);
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 4.5f);
            setRegion(flyAnimation.getKeyFrame(stateTimer, true));
            if (isFlip)
                setFlip(true, false);

        }
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
        fixture.filter.categoryBits = Constants.BIRD_BIT;
        fixture.filter.maskBits = Constants.PLAYER_BIT;

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
    public void hitOnHead() {
        setToDestroy = true;
    }

    @Override
    public void attack(){}

}
