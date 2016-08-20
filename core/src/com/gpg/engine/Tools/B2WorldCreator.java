package com.gpg.engine.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.gpg.engine.Engine;

/**
 * Created by developer on 01/08/16.
 */
public class B2WorldCreator {

    public B2WorldCreator(World world, TiledMap map) {
        //Box 2d stuff
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //NOTE:Layers are 0 based - 0 = background-graphics.

        //////////////////////////////////////////////////////////////////////////
        //Ground and Walls Layer
        for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)  / Engine.PPM, (rect.getY() + rect.getHeight() / 2) / Engine.PPM);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2 / Engine.PPM, rect.getHeight()/2 / Engine.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Engine.GROUND_BIT;

            body.createFixture(fdef);
        }

        Gdx.app.log("Init","Ground Layer Init");

        //////////////////////////////////////////////////////////////////////////
        //Platform Layers - with category bit PLATFORM_BIT
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)  / Engine.PPM, (rect.getY() + rect.getHeight() / 2) / Engine.PPM);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2 / Engine.PPM, rect.getHeight()/2 / Engine.PPM);
            fdef.shape = shape;

            //Set the category Bits to Platform
            fdef.filter.categoryBits = Engine.PLATFORM_BIT;

            body.createFixture(fdef);
        }
    }

}
