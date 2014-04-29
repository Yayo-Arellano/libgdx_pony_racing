package com.tiarsoft.ponyrace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.tiarsoft.ponyrace.Assets;
import com.tiarsoft.ponyrace.MainPonyRace;
import com.tiarsoft.ponyrace.Settings;
import com.tiarsoft.ponyrace.game.GameScreenTileds;

public abstract class Screens extends InputAdapter implements Screen {
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 480;

	public static final int WORLD_SCREEN_WIDTH = 80;
	public static final int WORLD_SCREEN_HEIGHT = 48;
	public static final int WORLD_SCREEN_PROFUNDIDAD = 10;

	public MainPonyRace game;
	public Stage stage;
	public SpriteBatch batcher;

	public OrthographicCamera guiCam;

	public SkeletonRenderer skelrender;
	protected TextBounds bounds;

	protected Assets oAssets;
	protected float ScreenlastStatetime;
	protected float ScreenStateTime;

	public Screens(MainPonyRace game) {
		oAssets = game.oAssets;
		stage = game.stage;
		stage.clear();
		batcher = game.batcher;
		InputMultiplexer input = new InputMultiplexer(stage, this);
		Gdx.input.setInputProcessor(input);
		this.game = game;
		guiCam = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
		guiCam.position.set(SCREEN_WIDTH / 2f, SCREEN_HEIGHT / 2f, 0);

		skelrender = new SkeletonRenderer();

		ScreenlastStatetime = ScreenStateTime = 0;
		if (this instanceof MainMenuScreen) {
			oAssets.fontGde.setScale(1f);
			oAssets.fontChco.setScale(.65f);
		}
		else if (this instanceof GameScreenTileds) {
			oAssets.fontGde.setScale(.625f);
			oAssets.fontChco.setScale(.55f);
		}
		else if (this instanceof WorldMapTiledScreen) {
			oAssets.fontGde.setScale(.8f);
			oAssets.fontChco.setScale(.6f);
		}
		else if (this instanceof LeaderboardChooseScreen) {
			oAssets.fontGde.setScale(.8f);
			oAssets.fontChco.setScale(.65f);
		}
		else if (this instanceof ShopScreen) {
			oAssets.fontGde.setScale(.68f);
			oAssets.fontChco.setScale(.45f);
		}

		// windowReady.debug();
	}

	@Override
	public void render(float delta) {
		ScreenlastStatetime = ScreenStateTime;
		ScreenStateTime += delta;

		update(delta);

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		draw(delta);

		// if(Gdx.input.justTouched())
		// if (Settings.musicEnabled && !Assets.music.isPlaying())
		// Assets.music.play();
		// loger.log();

	}

	public abstract void update(float delta);

	public abstract void draw(float delta);

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public abstract void show();

	@Override
	public void hide() {
		Settings.guardar();
	}

	@Override
	public void pause() {
		// Assets.music.pause();
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

}
