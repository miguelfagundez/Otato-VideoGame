package com.miguelfagundez.otato.utils;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.miguelfagundez.otato.entities.enemies.*;
import com.miguelfagundez.otato.entities.Player;

/**
 * Created by Miguel on 3/21/2019.
 */
public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (fixA.getUserData() == "feet" || fixB.getUserData() == "feet"){
        // if the contact is with the "feet" object
            Fixture feet = fixA.getUserData() == "feet" ? fixA : fixB;
            Fixture object = feet == fixA ? fixB : fixA;

            if(object.getUserData() != null && InteractiveTileObjects.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractiveTileObjects)object.getUserData()).onFeetHit();
            }

        }else{
        //if there are other contact different that "feet" object
            int cDefinition = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

            switch (cDefinition) {
                case Constants.ENEMY_HEAD_BIT | Constants.PLAYER_BIT:
                    if (fixA.getFilterData().categoryBits == Constants.ENEMY_HEAD_BIT)
                        ((MyEnemies) fixA.getUserData()).hitOnHead();
                    else
                        ((MyEnemies) fixB.getUserData()).hitOnHead();
                    break;
                case Constants.ENEMY_BIT | Constants.OBJECT_BIT:
                case Constants.ENEMY_BIT | Constants.DEFAULT_BIT:
                case Constants.ENEMY_BIT | Constants.IMAGINAY_WALL_BIT:
                    if (fixA.getFilterData().categoryBits == Constants.ENEMY_BIT)
                        ((MyEnemies) fixA.getUserData()).reverseVelocity(true, false);
                    else
                        ((MyEnemies) fixB.getUserData()).reverseVelocity(true, false);
                    break;
                case Constants.PLAYER_BIT | Constants.ENEMY_BIT:
                    if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                        ((Player) fixA.getUserData()).lessLife();
                        //System.out.println("Player lose a life");
                        ((MyEnemies) fixB.getUserData()).attack();
                    } else {
                        ((Player) fixB.getUserData()).lessLife();
                        //System.out.println("Player lose a life");
                        ((MyEnemies) fixA.getUserData()).attack();
                    }
                    break;
            /*case Constants.ENEMY_BIT| Constants.DEFAULT_BIT:
                if(fixA.getFilterData().categoryBits == Constants.ENEMY_BIT)
                    ((MyEnemies)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((MyEnemies)fixB.getUserData()).reverseVelocity(true,false);
                break;*/
                case Constants.ENEMY_BIT | Constants.ENEMY_BIT:
                    ((MyEnemies) fixA.getUserData()).reverseVelocity(true, false);
                    ((MyEnemies) fixB.getUserData()).reverseVelocity(true, false);
                    break;
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
