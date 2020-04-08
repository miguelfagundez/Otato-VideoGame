package com.miguelfagundez.otato.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.miguelfagundez.otato.screens.GameScreen;
import com.miguelfagundez.otato.utils.Constants;


/**
 * Created by Miguel on 3/12/2019.
 */
public class Player extends Sprite{

    public enum State {FALLING, JUMPING, STANDING, RUNNING, DIED };
    public State currentState;
    public State previousState;
    public World world;
    public Body body;
    private TextureRegion playerStand;
    private Animation<TextureRegion> playerRun;
    private Animation<TextureRegion> playerJump;
    private float stateTimer;
    private boolean runningRight;

    public Player(GameScreen screen){
        super(screen.getAtlas().findRegion("2run"));

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 11; i <19; i++)
            frames.add(new TextureRegion(getTexture(), i* 48, 0, 48, 48));
        playerRun = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 0; i <10; i++)
            frames.add(new TextureRegion(getTexture(), i* 48, 0, 48, 48));
        playerJump = new Animation(0.05f, frames);
        frames.clear();

        this.world = screen.getWorld();
        definePlayer();
        playerStand = new TextureRegion(getTexture(),480,0,48,48 );
        setBounds(0, 0, 48 / Constants.PPM, 48 / Constants.PPM);
        setRegion(playerStand);
        System.out.println("getHheight: " + getHeight());
        System.out.println("getWidth: " + getWidth());
    }

    public void update(float delta){
        setPosition(body.getPosition().x - getWidth() / 2f, body.getPosition().y - getHeight() / 4.5f);
        setRegion(getFrame(delta));
    }

    public TextureRegion getFrame(float delta){
        currentState = getState();

        TextureRegion region;
        region = new TextureRegion();

        switch (currentState) {
            case JUMPING:
                region = playerJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = playerStand;
                break;

        }

        if((body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            System.out.println("false");
            runningRight = false;
        }else if ((body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true,false);
            System.out.println("true");
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;
        return region;
    }

    // Calculate which state is drawing at the moment
    public State getState(){
        if (body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if (body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else return State.STANDING;
    }

    public void definePlayer(){
        //Body Definition
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / Constants.PPM, 32 / Constants.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fixture = new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(6 / Constants.PPM);
        fixture.filter.categoryBits = Constants.PLAYER_BIT;
        fixture.filter.maskBits = Constants.DEFAULT_BIT |
                Constants.ENEMY_BIT |
                Constants.OBJECT_BIT |
                Constants.ENEMY_HEAD_BIT;

        fixture.shape = shape;
        body.createFixture(fixture).setUserData(this);

        // new course ---------------------------
        shape.dispose();
        // --------------------------------------

        // Foot line Definition
        EdgeShape feet = new EdgeShape();
        feet.set(new Vector2(-4/Constants.PPM, -6/Constants.PPM), new Vector2(4/Constants.PPM, -6/Constants.PPM));
        fixture.shape = feet;
        fixture.isSensor = true;

        body.createFixture(fixture).setUserData("feet");
    }

    public void lessLife(){
        System.out.println("Player lose a life");
        if(runningRight)
            body.applyLinearImpulse(new Vector2(-1.5f, 0), body.getWorldCenter(), true);
        else
            body.applyLinearImpulse(new Vector2(1.5f, 0), body.getWorldCenter(), true);
        //if((body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){

    }
}
