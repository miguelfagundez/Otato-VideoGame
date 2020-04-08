package com.miguelfagundez.otato.entities.staticItems;

import com.badlogic.gdx.math.Rectangle;
import com.miguelfagundez.otato.screens.GameScreen;
import com.miguelfagundez.otato.utils.Constants;
import com.miguelfagundez.otato.utils.InteractiveTileObjects;


/**
 * Created by Miguel on 3/24/2016.
 */
public class Finish extends InteractiveTileObjects {

    public Finish(GameScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Constants.DEFAULT_BIT);
    }

    @Override
    public void onFeetHit() {

    }
}
