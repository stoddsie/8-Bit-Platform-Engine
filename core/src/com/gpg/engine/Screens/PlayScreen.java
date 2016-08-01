package com.gpg.engine.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gpg.engine.Engine;
import com.gpg.engine.Sprites.Willy;

/**
 * Created by james.stoddern on 29/07/2016.
 */
public class PlayScreen implements Screen {

    private Engine game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;

    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2D
    private World world;
    private Box2DDebugRenderer b2dr;

    private Willy willy;


    /**
     * Constructor
     * @param game
     */
    public PlayScreen(Engine game) {
        this.game = game;
        //texture = new Texture("badlogic.jpg");
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(Engine.V_WIDTH / Engine.PPM, Engine.V_HEIGHT / Engine.PPM, gameCam);

        maploader = new TmxMapLoader();
        map = maploader.load("tilemaps/level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Engine.PPM);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0,-4 ), true);
        b2dr = new Box2DDebugRenderer();

        //Box 2d stuff
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //Ground Layer
        for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)  / Engine.PPM, (rect.getY() + rect.getHeight() / 2) / Engine.PPM);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2 / Engine.PPM, rect.getHeight()/2 / Engine.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        willy = new Willy(world);

    }


    /**
     * Handle User Input
     * @param dt
     */
    public void handleInput(float dt) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            willy.jump();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && willy.b2body.getLinearVelocity().x <= 2) {
            willy.moveRight();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && willy.b2body.getLinearVelocity().x >= -2) {
            willy.moveLeft();
        }
    }


    /**
     * Update the world
     * @param dt
     */
    public void update(float dt) {
        handleInput(dt);

        world.step(1/60f, 6, 2);

        gameCam.update();
        renderer.setView(gameCam);

    }



    @Override
    /**
     * Render Method
     */
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world,gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);

        game.batch.begin();
        //game.batch.draw(texture,0,0);
        game.batch.end();
    }


    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
