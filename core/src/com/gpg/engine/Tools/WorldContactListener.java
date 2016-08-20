package com.gpg.engine.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.gpg.engine.Engine;
import com.gpg.engine.Sprites.Player;

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

        //THIS SHOULD ALLOW PLAYER TO PASS THROUGH PLATFORMS AND COLLIDE ON THE WAY DOWN
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        float platform_y;
        float player_y;

        platform_y = fixtureA.getBody().getPosition().y;
        player_y = fixtureB.getBody().getPosition().y;

        if(fixtureA.getFilterData().categoryBits == Engine.PLAYER_BIT
                && fixtureB.getFilterData().categoryBits == Engine.PLATFORM_BIT ||
                fixtureA.getFilterData().categoryBits == Engine.PLATFORM_BIT
                        && fixtureB.getFilterData().categoryBits == Engine.PLAYER_BIT ) {

            //Gdx.app.log("Player Y ", "" + player_y);

            if (fixtureA.getBody().getUserData() == "platform") {
                platform_y = fixtureA.getBody().getPosition().y;
                player_y = fixtureB.getBody().getPosition().y;
            } else if(fixtureA.getBody().getUserData() == "player") {
                player_y = fixtureA.getBody().getPosition().y;
                platform_y = fixtureB.getBody().getPosition().y;
            }

            if(player_y < platform_y + 0.22f) {             //the player below
                contact.setEnabled(false);
            } else {
                contact.setEnabled(true);
            }
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
