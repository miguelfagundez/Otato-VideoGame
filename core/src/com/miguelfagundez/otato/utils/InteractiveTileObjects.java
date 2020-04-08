package com.miguelfagundez.otato.utils;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.miguelfagundez.otato.screens.GameScreen;

/**
 * Created by Miguel on 3/18/2019.
 */
public abstract class InteractiveTileObjects {

    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    public InteractiveTileObjects(GameScreen screen, Rectangle bounds){
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = bounds;

        BodyDef bddef = new BodyDef();
        FixtureDef fixture = new FixtureDef();
        PolygonShape polygon = new PolygonShape();

        bddef.type = BodyDef.BodyType.StaticBody;
        bddef.position.set((bounds.getX() + bounds.getWidth()/2) / Constants.PPM, (bounds.getY() + bounds.getHeight()/2) / Constants.PPM);
        body = world.createBody(bddef);

        polygon.setAsBox((bounds.getWidth()/2) / Constants.PPM, (bounds.getHeight()/2) / Constants.PPM);
        fixture.shape = polygon;
        fixture.filter.categoryBits = Constants.OBJECT_BIT;

        this.fixture = body.createFixture(fixture);

    }

    public abstract void onFeetHit();

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        /*Array<TiledMapTileLayer.Cell> array;
        array = new Array<TiledMapTileLayer.Cell>(4);

        array.add(layer.getCell((int)(body.getPosition().x * Constants.PPM / 16), (int)(body.getPosition().y * Constants.PPM / 16)));
        array.add(layer.getCell((int) ((body.getPosition().x - 1) * Constants.PPM / 16), (int) (body.getPosition().y * Constants.PPM / 16)));
        array.add(layer.getCell((int)(body.getPosition().x * Constants.PPM / 16), (int)((body.getPosition().y - 1) * Constants.PPM / 16)));
        array.add(layer.getCell((int)((body.getPosition().x - 1) * Constants.PPM / 16), (int)((body.getPosition().y - 1) * Constants.PPM / 16)));*/

        return layer.getCell((int)(body.getPosition().x * Constants.PPM / 16), (int)(body.getPosition().y * Constants.PPM / 16));
    }
}
