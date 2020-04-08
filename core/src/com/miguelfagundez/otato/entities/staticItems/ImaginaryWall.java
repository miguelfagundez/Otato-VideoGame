package com.miguelfagundez.otato.entities.staticItems;

import com.badlogic.gdx.math.Rectangle;
import com.miguelfagundez.otato.screens.GameScreen;
import com.miguelfagundez.otato.utils.Constants;
import com.miguelfagundez.otato.utils.InteractiveTileObjects;


/**
 * Created by Miguel on 3/25/2016.
 */
public class ImaginaryWall extends InteractiveTileObjects {
    public ImaginaryWall(GameScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Constants.IMAGINAY_WALL_BIT);
    }

    @Override
    public void onFeetHit() {

    }
}
