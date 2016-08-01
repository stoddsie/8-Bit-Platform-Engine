package com.gpg.engine.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gpg.engine.Engine;
import com.gpg.engine.Screens.PlayScreen;

/**
 * Created by james.stoddern on 29/07/2016.
 */
public class Willy extends Sprite {
    public World world;
    public Body b2body;

    public enum State { FALLING, JUMPING, STANDING, WALKING, DEAD };
    public State currentState;
    public State previousState;

    private TextureRegion willyStand;
    private TextureRegion willyJump;
    //private TextureRegion willyWalk;
    private Animation willyWalk;

    private float stateTimer;
    private boolean runningRight;

    private boolean willyIsDead;

    public Willy(World world, PlayScreen screen) {
        super(screen.getAtlas().findRegion("willy_right"));

        this.world = world;
        //this.screen = screen;
        //this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();


        for(int i = 1; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("willy_right"), i * 20, 0, 20, 32));
        willyWalk = new Animation(0.1f, frames);
        frames.clear();

        willyJump = new TextureRegion(screen.getAtlas().findRegion("willy_right"), 60, 0, 20, 32);

        defineWilly();


        willyStand = new TextureRegion(getTexture(), 0,0,20,32);
        setBounds(0,0,20 / Engine.PPM, 32 / Engine.PPM);
        setRegion(willyStand);

    }


    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() /2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
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
        //CircleShape shape = new CircleShape();
        PolygonShape shape = new PolygonShape();
        //shape.setRadius(10 / Engine.PPM);



        shape.setAsBox(9 / Engine.PPM, 14 / Engine.PPM);

        //stop sliding
        fdef.friction = 100f;
        fdef.shape = shape;
        b2body.createFixture(fdef);

    }


    public TextureRegion getFrame(float dt){
        //get marios current state. ie. jumping, running, standing...
        currentState = getState();

        TextureRegion region;

        //depending on the state, get corresponding animation keyFrame.
        switch(currentState){
            //case DEAD:
                //region = willyDead;
            //    break;
            case JUMPING:
                region = willyJump;
                break;
            case WALKING:
                region = willyWalk.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = willyStand;
                break;
        }

        //if mario is running left and the texture isnt facing left... flip it.
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        //if mario is running right and the texture isnt facing right... flip it.
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;

    }


    public float getStateTimer(){
        return stateTimer;
    }


    public State getState(){
        //Test to Box2D for velocity on the X and Y-Axis
        //if mario is going positive in Y-Axis he is jumping... or if he just jumped and is falling remain in jump state
        if(willyIsDead)
            return State.DEAD;
        else if((b2body.getLinearVelocity().y > 0 && currentState == State.JUMPING) || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
            //if negative in Y-Axis mario is falling
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
            //if mario is positive or negative in the X axis he is running
        else if(b2body.getLinearVelocity().x != 0)
            return State.WALKING;
            //if none of these return then he must be standing
        else
            return State.STANDING;
    }



    /**
     * Jump
     */
    public void jump(){
        //if ( currentState != State.JUMPING ) {
            this.b2body.applyLinearImpulse(new Vector2(0, 3f), this.b2body.getWorldCenter(), true);
            currentState = State.JUMPING;

        //}
    }


    /**
     * Left
     */
    public void moveLeft() {
        //this.b2body.applyLinearImpulse(new Vector2(-0.05f, 0), this.b2body.getWorldCenter(), true);
        //this.setPosition(this.getX() - 10.0f, this.getY());
        //if ( currentState != State.JUMPING ) {
            this.b2body.setLinearVelocity(-1, 0);
        //}
    }


    /**
     * Right
     */
    public void moveRight() {
        //b2Vec2 vel = this->GetLinearVelocity();

        //this.b2body.applyLinearImpulse(new Vector2(0.05f, 0), this.b2body.getWorldCenter(), true);
        //this.setPosition(this.getX() * 10.0f, this.getY());
        //if ( currentState != State.JUMPING ) {
            this.b2body.setLinearVelocity(1, 0);
        //}
    }




}
