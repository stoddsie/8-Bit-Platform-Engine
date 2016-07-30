package com.gpg.engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gpg.engine.Screens.PlayScreen;

public class Engine extends Game {
	public SpriteBatch batch;
	public static final int V_WIDTH = 512;
	public static final int V_HEIGHT = 272;
	public static final float PPM = 100f;

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
