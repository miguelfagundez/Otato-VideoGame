package com.miguelfagundez.otato.entities.staticItems;

import com.badlogic.gdx.math.Rectangle;
import com.miguelfagundez.otato.screens.GameScreen;
import com.miguelfagundez.otato.utils.Constants;
import com.miguelfagundez.otato.utils.InteractiveTileObjects;


/**
 * Created by Miguel on 3/24/2016.
 */
public class Ground extends InteractiveTileObjects {

    public Ground(GameScreen screen, Rectangle bounds){
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Constants.OBJECT_BIT);
    }
    @Override
    public void onFeetHit() {

    }
}
