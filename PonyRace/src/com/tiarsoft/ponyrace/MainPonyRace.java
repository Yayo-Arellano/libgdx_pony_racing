package com.tiarsoft.ponyrace;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tiarsoft.handlers.GameServicesHandler;
import com.tiarsoft.handlers.RequestHandler;
import com.tiarsoft.ponyrace.menuobjetos.DialogSingInGGS;
import com.tiarsoft.ponyrace.screens.LoadingScreen;
import com.tiarsoft.ponyrace.screens.MainMenuScreen;
import com.tiarsoft.ponyrace.screens.Screens;

public class MainPonyRace extends Game {
	public enum Tienda {
		googlePlay,
		amazon,
		slideMe,
		none,
		samsung,
		appStore
	}

	final public RequestHandler reqHandler;
	final public GameServicesHandler gameServiceHandler;
	final public Tienda tiendaActual;

	public MainPonyRace(Tienda tienda, RequestHandler handler, GameServicesHandler gameServiceHandler) {
		this.gameServiceHandler = gameServiceHandler;
		this.reqHandler = handler;
		tiendaActual = tienda;
	}

	public Assets oAssets;
	public Stage stage;
	public SpriteBatch batcher;
	public Achievements achievements;
	public DialogSingInGGS signin;

	@Override
	public void create() {
		Settings.cargar();
		oAssets = new Assets();
		achievements = new Achievements(this);
		stage = new Stage(Screens.SCREEN_WIDTH, Screens.SCREEN_HEIGHT, false);
		batcher = new SpriteBatch();
		signin = new DialogSingInGGS(this, stage);
		this.setScreen(new LoadingScreen(this, MainMenuScreen.class, 1));

	}

	String status;

	@Override
	public void dispose() {
		getScreen().dispose();
		stage.dispose();
		batcher.dispose();
		oAssets.fontChco.dispose();
		oAssets.fontGde.dispose();
		oAssets.dispose();
		super.dispose();
	}

}
