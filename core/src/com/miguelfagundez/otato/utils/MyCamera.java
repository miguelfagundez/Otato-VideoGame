package com.miguelfagundez.otato.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Miguel on 3/12/2019.
 */
public class MyCamera {
    private OrthographicCamera camera;
    private Viewport gamePort;

    /**
     * Method: MenuScreen
     * Definition: Constructor
     * Parameters:
     *      int myWidth:     size in X's axis
     *      int myHeight:    size in Y's axis
     */
    public MyCamera(int myWidth, int myHeight){
        camera = new OrthographicCamera();
        gamePort = new FitViewport(myWidth, myHeight, camera);
    }

    /**
     * Method: resize
     * Definition: it is used when the window size is changed (desktop or html projects)
     * Parameters:
     *      int width:     new size in X's axis
     *      int height:    new size in Y's axis
     */
    public void resize(int width, int height){
        gamePort.update(width,height);
    }

    /**
     * Method: getCombined
     * Definition: it is used when the Projection matrix is setted.
     * Parameters:
     *          None
     */
    public Matrix4 getCombined(){
        return camera.combined;
    }
}
