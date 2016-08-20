package com.gpg.engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gpg.engine.Screens.PlayScreen;

public class Engine extends Game {
	public SpriteBatch batch;
	public static final int V_WIDTH = 512;
	public static final int V_HEIGHT = 272;
	public static final float PPM = 100f;

	//Box2D Collision Bits
	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short PLATFORM_BIT = 2;
	public static final short PLAYER_BIT = 4;

	/*
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short ITEM_BIT = 256;
	public static final short MARIO_HEAD_BIT = 512;
	public static final short FIREBALL_BIT = 1024;
	*/

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
