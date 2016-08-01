package com.gpg.engine.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.gpg.engine.Engine;

/**
 * Created by james.stoddern on 29/07/2016.
 */
public class Willy extends Sprite {
    public World world;
    public Body b2body;

    public Willy(World world) {
        this.world = world;
        defineWilly();

    }


    /**
     * Define Will himself
     */
    public void defineWilly() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(150 / Engine.PPM, 80 / Engine.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;


        b2body = world.createBody(bdef);

        b2body.setLinearDamping(1);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / Engine.PPM);

        //stop sliding
        fdef.friction = 100f;

        fdef.shape = shape;
        b2body.createFixture(fdef);

    }

    public void jump(){
        //if ( currentState != State.JUMPING ) {
            this.b2body.applyLinearImpulse(new Vector2(0, 2f), this.b2body.getWorldCenter(), true);
            //currentState = State.JUMPING;

        //}
    }

    public void moveRight() {
        //b2Vec2 vel = this->GetLinearVelocity();

        //this.b2body.applyLinearImpulse(new Vector2(0.05f, 0), this.b2body.getWorldCenter(), true);
        //this.setPosition(this.getX() * 10.0f, this.getY());
        this.b2body.setLinearVelocity(1,0);
    }

    public void moveLeft() {
        //this.b2body.applyLinearImpulse(new Vector2(-0.05f, 0), this.b2body.getWorldCenter(), true);
        //this.setPosition(this.getX() - 10.0f, this.getY());
        this.b2body.setLinearVelocity(-1,0);
    }

}
