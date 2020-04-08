package com.miguelfagundez.otato.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.miguelfagundez.otato.entities.enemies.*;
import com.miguelfagundez.otato.entities.staticItems.*;
import com.miguelfagundez.otato.screens.GameScreen;


/**
 * Created by Miguel on 3/18/2019.
 */
public class B2WorldCreator {

    private Array<Skeleton> skeletons;
    private Array<Zombie> zombies;

    public B2WorldCreator(GameScreen screen){

        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        //Create Bodies and Fixtures
        BodyDef bddef = new BodyDef();
        PolygonShape polygon = new PolygonShape();
        FixtureDef fixture = new FixtureDef();
        Body body;

        //Box
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            new Box(screen, rect);
        }

        //Ground
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            new Ground(screen, rect);
        }

        //Stone
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            new Stone(screen, rect);
        }

        /* Skeletons */
        skeletons = new Array<Skeleton>();
        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            skeletons.add(new Skeleton(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM));
        }

        /* Zombies */
        zombies = new Array<Zombie>();
        for(MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            zombies.add(new Zombie(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM));
        }

        //Water
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            bddef.type = BodyDef.BodyType.StaticBody;
            bddef.position.set((rect.getX() + rect.getWidth()/2) / Constants.PPM, (rect.getY() + rect.getHeight()/2) / Constants.PPM);
            body = world.createBody(bddef);

            polygon.setAsBox((rect.getWidth()/2) / Constants.PPM, (rect.getHeight()/2) / Constants.PPM);
            fixture.shape = polygon;

            body.createFixture(fixture);
        }

        //Imaginay Wall
        for(MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            new ImaginaryWall(screen, rect);
        }

        //Tree
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            bddef.type = BodyDef.BodyType.StaticBody;
            bddef.position.set((rect.getX() + rect.getWidth()/2) / Constants.PPM, (rect.getY() + rect.getHeight()/2) / Constants.PPM);
            body = world.createBody(bddef);

            polygon.setAsBox((rect.getWidth()/2) / Constants.PPM, (rect.getHeight()/2) / Constants.PPM);
            fixture.shape = polygon;

            body.createFixture(fixture);
        }

        //Finish
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            new Finish(screen, rect);
        }
    }

    public Array<Skeleton> getSkeletons() {
        return skeletons;
    }

    public Array<Zombie> getZombies() {
        return zombies;
    }
}
