package com.gpg.engine.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.gpg.engine.Engine;

/**
 * Created by developer on 01/08/16.
 */
public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            /*
            case Engine.WILLY_HEAD_BIT | Engine.BRICK_BIT:
            case Engine.WILLY_HEAD_BIT | Engine.COIN_BIT:
                if(fixA.getFilterData().categoryBits == Engine.MARIO_HEAD_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Mario) fixA.getUserData());
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Mario) fixB.getUserData());
                break;
            case Engine.ENEMY_HEAD_BIT | Engine.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == Engine.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead((Mario) fixB.getUserData());
                else
                    ((Enemy)fixB.getUserData()).hitOnHead((Mario) fixA.getUserData());
                break;
            case Engine.ENEMY_BIT | Engine.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Engine.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Engine.MARIO_BIT | Engine.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == Engine.MARIO_BIT)
                    ((Mario) fixA.getUserData()).hit((Enemy)fixB.getUserData());
                else
                    ((Mario) fixB.getUserData()).hit((Enemy)fixA.getUserData());
                break;
            case Engine.ENEMY_BIT | Engine.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).hitByEnemy((Enemy)fixB.getUserData());
                ((Enemy)fixB.getUserData()).hitByEnemy((Enemy)fixA.getUserData());
                break;
            case Engine.ITEM_BIT | Engine.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Engine.ITEM_BIT)
                    ((Item)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Item)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Engine.ITEM_BIT | Engine.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == Engine.ITEM_BIT)
                    ((Item)fixA.getUserData()).use((Mario) fixB.getUserData());
                else
                    ((Item)fixB.getUserData()).use((Mario) fixA.getUserData());
                break;
            case Engine.FIREBALL_BIT | Engine.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Engine.FIREBALL_BIT)
                    ((FireBall)fixA.getUserData()).setToDestroy();
                else
                    ((FireBall)fixB.getUserData()).setToDestroy();
                break;
                */

        }
        contact.setEnabled(false);
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        /*
        fixtureA = contact.getFixtureA();
        fixtureB = contact.getFixtureB();
        if(fixtureA.getBody().getUserData() == "platform" && fixtureB.getBody().getUserData() == "ball"|| fixtureA.getBody().getUserData() == "ball" && fixtureB.getBody().getUserData() == "platform"){
            if (fixtureA.getBody().getUserData() == "platform") {
                platform_y = fixtureA.getBody().getPosition().y;
                ball_y = fixtureB.getBody().getPosition().y;
            } else if(fixtureA.getBody().getUserData() == "ball") {
                ball_y = fixtureA.getBody().getPosition().y;
                platform_y = fixtureB.getBody().getPosition().y;
            }
            if(ball_y < platform_y + 1.5F) {             //the ball is below
                contact.setEnabled(false);
            }
        }
        */
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
