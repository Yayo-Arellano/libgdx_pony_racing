package com.tiarsoft.ponyrace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tiarsoft.handlers.GoogleGameServicesHandler;
import com.tiarsoft.ponyrace.MainPonyRace;
import com.tiarsoft.ponyrace.menuobjetos.BotonNube;

public class LeaderboardChooseScreen extends Screens {

	BotonNube btLeaderBoard, btAchievements, btBack;
	TextButton btSignOut;

	public LeaderboardChooseScreen(MainPonyRace game) {
		super(game);

		cargarBotones();

		Table contenedor = new Table();
		contenedor.setPosition(SCREEN_WIDTH / 2f, SCREEN_HEIGHT / 2f);
		contenedor.add(btLeaderBoard).fillX();
		contenedor.row().pad(50, 0, 0, 0);
		contenedor.add(btAchievements).fillX();

		stage.addActor(contenedor);
	}

	private void cargarBotones() {
		btLeaderBoard = new BotonNube(oAssets.nube, "LeaderBoards", oAssets.fontChco);
		btLeaderBoard.setSize(290, 140);
		btLeaderBoard.addListener(new ClickListener() {

			public void clicked(InputEvent event, float x, float y) {
				btLeaderBoard.wasSelected = true;
				btLeaderBoard.addAction(Actions.sequence(Actions.delay(.2f), btLeaderBoard.accionInicial, Actions.run(new Runnable() {
					@Override
					public void run() {

						game.gameServiceHandler.getLeaderboard();

					}
				})));
			};
		});

		btAchievements = new BotonNube(oAssets.nube, "Achievements", oAssets.fontChco);
		btAchievements.setSize(290, 140);
		btAchievements.addListener(new ClickListener() {

			public void clicked(InputEvent event, float x, float y) {
				btAchievements.wasSelected = true;
				btAchievements.addAction(Actions.sequence(Actions.delay(.2f), btAchievements.accionInicial, Actions.run(new Runnable() {
					@Override
					public void run() {

						game.gameServiceHandler.getAchievements();

					}
				})));
			};
		});

		btBack = new BotonNube(oAssets.nube, "Back", oAssets.fontGde);
		btBack.setSize(150, 100);
		btBack.setPosition(645, 5);
		btBack.addListener(new ClickListener() {

			public void clicked(InputEvent event, float x, float y) {
				btBack.wasSelected = true;
				btBack.addAction(Actions.sequence(Actions.delay(.2f), btBack.accionInicial, Actions.run(new Runnable() {
					@Override
					public void run() {
						LeaderboardChooseScreen.this.game.setScreen(new LoadingScreen(LeaderboardChooseScreen.this.game, MainMenuScreen.class));
					}
				})));
			};
		});

		TextButtonStyle stilo = new TextButtonStyle(oAssets.btSignInUp, oAssets.btSignInDown, null, oAssets.skin.getFont("default-font"));
		btSignOut = new TextButton("Sign out", stilo);
		btSignOut.setPosition(5, 5);
		btSignOut.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.gameServiceHandler.signInGPGS();
				LeaderboardChooseScreen.this.game.setScreen(new LoadingScreen(LeaderboardChooseScreen.this.game, MainMenuScreen.class));
			}

		});

		if (game.gameServiceHandler instanceof GoogleGameServicesHandler)
			stage.addActor(btSignOut);
		stage.addActor(btBack);

	}

	@Override
	public void update(float delta) {
		GLCommon gl = Gdx.gl;
		gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);

		batcher.disableBlending();
		batcher.begin();
		batcher.draw(oAssets.fondoMainMenu, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		batcher.end();

		stage.act(delta);
		stage.draw();

	}

	@Override
	public void draw(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
			this.game.setScreen(new LoadingScreen(game, MainMenuScreen.class));

			return true;
		}
		return false;
	}

}
