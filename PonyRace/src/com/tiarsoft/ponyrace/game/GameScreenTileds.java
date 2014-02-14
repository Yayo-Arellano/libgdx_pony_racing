package com.tiarsoft.ponyrace.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.esotericsoftware.spine.Animation;
import com.tiarsoft.handlers.AmazonGameServicesHandler;
import com.tiarsoft.ponyrace.Assets;
import com.tiarsoft.ponyrace.MainPonyRace;
import com.tiarsoft.ponyrace.MainPonyRace.Tienda;
import com.tiarsoft.ponyrace.Settings;
import com.tiarsoft.ponyrace.game.objetos.Pony;
import com.tiarsoft.ponyrace.menuobjetos.BotonNube;
import com.tiarsoft.ponyrace.screens.LoadingScreen;
import com.tiarsoft.ponyrace.screens.Screens;
import com.tiarsoft.ponyrace.screens.WorldMapTiledScreen;

public class GameScreenTileds extends Screens {

	public enum State {
		ready,
		running,
		paused,
		timeUp,
		nextLevel,
		tryAgain;

	}

	// Variables para descontar las monedas 1 por 1 cuando terminas la carrera y te dan monedas
	public final float GET_COIN_FOR_TIME_LEFT = .065f;
	public float time_left_coin = 0;
	public final int MULTIPLICADOR_MONEDAS_TIME_LEFT;
	// fin

	WorldTiled oWorld;
	WorldTiledRenderer renderer;

	Vector3 touchPoint;
	State state;
	boolean jump, fireBomb, fireWood;
	int nivelTiled;

	Button btIzq, btDer, btJump;
	TextButton btFireBomb, btFireWood;

	Button btPausa;

	// int monedasRecolectadas;
	StringBuilder stringMonedasRecolectadas;

	StringBuilder stringTiempoLeft;

	StringBuilder lapTime;

	String stringMundoActual;

	float accelX;

	boolean drawStatsEndRace;

	public GameScreenTileds(MainPonyRace game, int nivelTiled) {
		super(game);
		Settings.statTimesPlayed++;

		oWorld = new WorldTiled(game, nivelTiled);
		state = State.ready;
		this.nivelTiled = nivelTiled;
		renderer = new WorldTiledRenderer(batcher, oWorld);
		touchPoint = new Vector3();

		fireBomb = jump = fireWood = false;

		btDer = new Button(oAssets.padDer);
		btIzq = new Button(oAssets.padIzq);
		btJump = new Button(oAssets.btJumpUp, oAssets.btJumpDown);

		btPausa = new Button(oAssets.btPauseUp);

		TextButtonStyle txButtonStyleFireBombs = new TextButtonStyle(oAssets.btBombaUp, oAssets.btBombaDown, null, oAssets.fontChco);
		TextButtonStyle txButtonStyleFireWoods = new TextButtonStyle(oAssets.btTroncoUp, oAssets.btTroncoDown, null, oAssets.fontChco);

		btFireBomb = new TextButton(Settings.numeroBombas + "", txButtonStyleFireBombs);
		btFireWood = new TextButton(Settings.numeroWoods + "", txButtonStyleFireWoods);

		setBotonesInterfaz();
		inicializarBotonesMenusInGame();

		lapTime = new StringBuilder();

		stringMonedasRecolectadas = new StringBuilder();
		stringMonedasRecolectadas.append("0");

		stringTiempoLeft = new StringBuilder();
		stringTiempoLeft.append((int) oWorld.tiempoLeft);

		if (nivelTiled == 1000) {
			stringMundoActual = "Secret world";
		}
		else {
			stringMundoActual = "World " + nivelTiled;
		}
		drawStatsEndRace = false;

		switch (Settings.nivelTime) {
			default:
			case 0:
				MULTIPLICADOR_MONEDAS_TIME_LEFT = 0;
				break;
			case 1:
				MULTIPLICADOR_MONEDAS_TIME_LEFT = 1;
				break;
			case 2:
				MULTIPLICADOR_MONEDAS_TIME_LEFT = 2;
				break;
			case 3:
				MULTIPLICADOR_MONEDAS_TIME_LEFT = 3;
				break;
			case 4:
				MULTIPLICADOR_MONEDAS_TIME_LEFT = 4;
				break;
			case 5:
				MULTIPLICADOR_MONEDAS_TIME_LEFT = 5;
				break;
		}

	}

	int tamanoBoton = 105;

	public void setBotonesInterfaz() {
		stage.clear();

		btJump.setPosition(692, 10);
		btJump.setSize(tamanoBoton, tamanoBoton);

		btFireBomb.setPosition(584, 10);
		btFireBomb.setSize(tamanoBoton, tamanoBoton);

		btFireWood.setPosition(692, 123);
		btFireWood.setSize(tamanoBoton, tamanoBoton);

		btIzq.setPosition(5, 5);
		btIzq.setSize(120, 120);

		btDer.setPosition(130, 5);
		btDer.setSize(120, 120);

		btPausa.setSize(45, 45);
		btPausa.setPosition(750, 430);

		btJump.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				jump = true;
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		btFireBomb.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				fireBomb = true;
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		btFireWood.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				fireWood = true;
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		btDer.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
				accelX = 1;
			};

			public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
				accelX = 0;
			};
		});
		btIzq.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
				accelX = -1;
			};

			public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
				accelX = 0;
			};
		});

		btPausa.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setPause();
				super.clicked(event, x, y);
			}
		});

		stage.addActor(btDer);
		stage.addActor(btIzq);
		stage.addActor(btJump);
		stage.addActor(btPausa);

		if (Settings.numeroBombas > 0)
			stage.addActor(btFireBomb);
		if (Settings.numeroWoods > 0)
			stage.addActor(btFireWood);

		if (Settings.seCalificoApp == false && Settings.statTimesPlayed % 5 == 0) {
			game.signin.showDialogRate();
		}
	}

	@Override
	public void update(float delta) {

		if (oWorld == null)
			return;

		switch (state) {
			default:
			case ready:
				updateReady(delta);
				break;
			case running:
				updateRunning(delta);
				break;
			case timeUp:
				updateTimeUp(delta);
				break;
			case paused:
				updatePaused(delta);
				break;
			case nextLevel:
				updateNextLevel(delta);
				break;
			case tryAgain:
				updateTryAgain(delta);
				break;
		}

	}

	private void updateReady(float delta) {
		if (Gdx.input.isTouched() && !game.signin.isDialogRateShown())
			state = State.running;
	}

	private void updateRunning(float delta) {
		if (Gdx.input.isKeyPressed(Keys.A))
			accelX = -1;
		else if (Gdx.input.isKeyPressed(Keys.D))
			accelX = 1;

		oWorld.update(delta, accelX, jump, fireBomb, fireWood, renderer);

		if (oWorld.state == WorldTiled.State.timeUp) {
			setTimeUp();

		}
		else if (oWorld.state == WorldTiled.State.nextLevel) {// Solo se pone cuando ganas en primer lugar
			setNextLevel();

			if (nivelTiled == Settings.mundosDesbloqueados)
				Settings.mundosDesbloqueados++;

			if (Settings.mundosDesbloqueados > Assets.mundoMaximo)
				Settings.mundosDesbloqueados = Assets.mundoMaximo;

		}
		else if (oWorld.state == WorldTiled.State.tryAgain) {
			setTryAgain();
		}

		setPuntuacionesGoogleGameServices();

		stringMonedasRecolectadas.delete(0, stringMonedasRecolectadas.length());
		stringMonedasRecolectadas.append(oWorld.oPony.monedasRecolectadas);

		stringTiempoLeft.delete(0, stringTiempoLeft.length());
		stringTiempoLeft.append((int) oWorld.tiempoLeft);

		lapTime.delete(0, lapTime.length());
		lapTime.append("Lap ").append(String.format("%.2f", oWorld.tiempoLap));

		if (fireBomb)
			btFireBomb.setText(Settings.numeroBombas + "");

		if (fireWood)
			btFireWood.setText(Settings.numeroWoods + "");
		fireBomb = jump = fireWood = false;

		if (oWorld.state == WorldTiled.State.nextLevel || oWorld.state == WorldTiled.State.tryAgain) {
			if (((int) oWorld.tiempoLeft) % 2 == 0 && oWorld.oPony.monedasRecolectadas % 2 == 0 && ((int) oWorld.tiempoLap % 2) == 0)
				Settings.isEnabledSecretWorld = true;
		}

	}

	private void setPuntuacionesGoogleGameServices() {

		String idWorld1 = "CgkI55iksNMTEAIQAA";
		String idWorld2 = "CgkI55iksNMTEAIQAw";
		String idWorld3 = "CgkI55iksNMTEAIQBA";
		String idWorld4 = "CgkI55iksNMTEAIQBQ";
		String idWorld5 = "CgkI55iksNMTEAIQBg";
		String idWorld6 = "CgkI55iksNMTEAIQMQ";
		String idWorld7 = "CgkI55iksNMTEAIQMg";
		String idWorld8 = "CgkI55iksNMTEAIQMw";
		String idWorld9 = "CgkI55iksNMTEAIQNA";
		String idWorld10 = "CgkI55iksNMTEAIQNQ";
		String idWorld11 = "CgkI55iksNMTEAIQNg";
		String idWorld12 = "CgkI55iksNMTEAIQNw";

		if (game.gameServiceHandler instanceof AmazonGameServicesHandler) {
			idWorld1 = "world1";
			idWorld2 = "world2";
			idWorld3 = "world3";
			idWorld4 = "world4";
			idWorld5 = "world5";
			idWorld6 = "world6";
			idWorld7 = "world7";
			idWorld8 = "world8";
			idWorld9 = "world9";
			idWorld10 = "world10";
			idWorld11 = "world11";
			idWorld12 = "world12";
		}

		if (state != State.running && state != State.paused && state != State.timeUp) // Si esta corriendo, o pausado, o timeup ps no se sube el tiempo a GPGS

			switch (nivelTiled) {
				case 1:
					game.gameServiceHandler.submitScore(oWorld.tiempoLap, idWorld1);
					break;
				case 2:
					game.gameServiceHandler.submitScore(oWorld.tiempoLap, idWorld2);
					break;
				case 3:
					game.gameServiceHandler.submitScore(oWorld.tiempoLap, idWorld3);
					break;
				case 4:
					game.gameServiceHandler.submitScore(oWorld.tiempoLap, idWorld4);
					break;
				case 5:
					game.gameServiceHandler.submitScore(oWorld.tiempoLap, idWorld5);
					break;
				case 6:
					game.gameServiceHandler.submitScore(oWorld.tiempoLap, idWorld6);
					break;
				case 7:
					game.gameServiceHandler.submitScore(oWorld.tiempoLap, idWorld7);
					break;
				case 8:
					game.gameServiceHandler.submitScore(oWorld.tiempoLap, idWorld8);
					break;
				case 9:
					game.gameServiceHandler.submitScore(oWorld.tiempoLap, idWorld9);
					break;
				case 10:
					game.gameServiceHandler.submitScore(oWorld.tiempoLap, idWorld10);
					break;
				case 11:
					game.gameServiceHandler.submitScore(oWorld.tiempoLap, idWorld11);
					break;
				case 12:
					game.gameServiceHandler.submitScore(oWorld.tiempoLap, idWorld12);
					break;

			}

	}

	private void updateTimeUp(float delta) {
		oWorld.update(delta, renderer);

	}

	private void updatePaused(float delta) {
	}

	private void updateNextLevel(float delta) {
		oWorld.update(delta, renderer);

		if (ScreenStateTime >= oAssets.finCarreraAnimacion1Lugar.getDuration()) {
			drawStatsEndRace = true;
			giveCoinsAfterfinish(delta);
		}

	}

	private void updateTryAgain(float delta) {
		oWorld.update(delta, renderer);

		if (ScreenStateTime >= oAssets.finCarreraAnimacion2Lugar.getDuration()) {
			drawStatsEndRace = true;
			giveCoinsAfterfinish(delta);
		}
	}

	private void giveCoinsAfterfinish(float delta) {
		time_left_coin += delta;
		if (Settings.nivelTime > 0 && Settings.dificultadActual >= Settings.DIFICULTAD_NORMAL && oWorld.tiempoLeft > 0 && time_left_coin >= GET_COIN_FOR_TIME_LEFT) {
			time_left_coin -= GET_COIN_FOR_TIME_LEFT;
			oWorld.tiempoLeft--;
			oWorld.oPony.monedasRecolectadas += MULTIPLICADOR_MONEDAS_TIME_LEFT;
			stringMonedasRecolectadas.delete(0, stringMonedasRecolectadas.length());
			stringMonedasRecolectadas.append(oWorld.oPony.monedasRecolectadas);
			Settings.sumarMonedas(MULTIPLICADOR_MONEDAS_TIME_LEFT);
			game.oAssets.playSound(game.oAssets.pickCoin);
		}
	}

	@Override
	public void draw(float delta) {
		GLCommon gl = Gdx.gl;
		gl.glClearColor(.38f, .77f, .87f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (state == State.paused)
			delta = 0;

		if (renderer == null)
			return;

		renderer.render(delta, ScreenlastStatetime, ScreenStateTime);
		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);

		batcher.enableBlending();
		batcher.begin();
		switch (state) {
			default:
			case ready:
				presentReady(delta);
				break;
			case running:
				presentRunning(delta);
				break;
			case timeUp:
				presentTimeUp(delta);
				break;
			case paused:
				presentPaused(delta);
				break;
			case nextLevel:
				presentNextLevel(delta);
				break;
			case tryAgain:
				presentTryAgain(delta);
				break;

		}

		batcher.end();

		stage.act();
		stage.draw();
		// Table.drawDebug(stage);

	}

	private void presentReady(float delta) {
		bounds = oAssets.fontGde.getBounds("Touch the screen to start");
		oAssets.fontGde.draw(batcher, "Touch the screen to start", SCREEN_WIDTH / 2f - bounds.width / 2f, SCREEN_HEIGHT / 2f - bounds.height / 2f);

	}

	private void presentRunning(float delta) {

		int alturaIndicador = 440;
		batcher.draw(oAssets.indicador, 150f, alturaIndicador, 500, 15);
		batcher.draw(oAssets.lugaresMarco, 0, 250, 75, 164);// Barra del lado izq que muestra los primeros 3 lugares

		// Para dibujar los lugares donde estan los ponyes en la barra superior
		for (int i = 0; i < oWorld.arrPosiciones.size; i++) {
			Pony oPony = oWorld.arrPosiciones.get(i);

			AtlasRegion textura;
			AtlasRegion perfil;

			if (oPony.nombreSkin.equals("Cloud")) {
				textura = oAssets.indicadorCloud;
				perfil = oAssets.perfilRegionCloud;
			}
			else if (oPony.nombreSkin.equals("Natylol")) {
				textura = oAssets.indicadorNatylol;
				perfil = oAssets.perfilRegionNatylol;
			}
			else if (oPony.nombreSkin.equals("Ignis")) {
				textura = oAssets.indicadorIgnis;
				perfil = oAssets.perfilRegionIgnis;
			}
			else if (oPony.nombreSkin.equals("cientifico")) {
				textura = oAssets.indicadorCientifico;
				perfil = oAssets.perfilRegionCientifico;
			}
			else if (oPony.nombreSkin.equals("LAlba")) {
				textura = oAssets.indicadorLighthingAlba;
				perfil = oAssets.perfilRegionLAlba;
			}
			else {
				textura = oAssets.indicadorMinion;
				perfil = oAssets.perfilRegionEnemigo;
			}

			float posocion = 500 / oWorld.tamanoMapaX * oPony.position.x + 140;
			batcher.draw(textura, posocion, alturaIndicador, 25, 25);

			if (i == 0) {
				batcher.draw(perfil, 26, 368, 45, 45);
			}
			if (i == 1) {
				batcher.draw(perfil, 26, 310, 45, 45);
			}
			if (i == 2) {
				batcher.draw(perfil, 26, 252, 45, 45);
			}
		}
		// Fin

		// Dibujar Monedas
		batcher.draw(oAssets.moneda, 5, 445, 30, 30);
		oAssets.fontChco.draw(batcher, stringMonedasRecolectadas, 38, 472);

		// El mundo actual
		bounds = oAssets.fontChco.getBounds(stringMundoActual);
		oAssets.fontChco.draw(batcher, stringMundoActual, SCREEN_WIDTH / 2 - bounds.width / 2, alturaIndicador - 5);

		// El tiempo que queda
		bounds = oAssets.fontChco.getBounds(stringTiempoLeft);
		oAssets.fontChco.draw(batcher, stringTiempoLeft, SCREEN_WIDTH / 2 - bounds.width / 2, alturaIndicador - 32);

		// fin

		oAssets.fontChco.draw(batcher, "FPS=" + Gdx.graphics.getFramesPerSecond(), 0, 225);
		// oAssets.fontChco.draw(batcher, "Posicion " + oWorld.oPony.lugarEnLaCarrera, 0, 220);
		oAssets.fontChco.draw(batcher, lapTime, 0, 190);
	}

	AtlasRegion pixelNegro;
	float duration = 1.5f;
	float curFade = 0;

	private void presentTimeUp(float delta) {

		oAssets.finCarreraAnimacionTimesUp.apply(oAssets.finCarreraSkeleton, ScreenlastStatetime, ScreenStateTime, false, null);
		oAssets.finCarreraSkeleton.setX(SCREEN_WIDTH / 2f);
		oAssets.finCarreraSkeleton.setY(SCREEN_HEIGHT / 2f);
		oAssets.finCarreraSkeleton.updateWorldTransform();
		oAssets.finCarreraSkeleton.update(delta);
		skelrender.draw(batcher, oAssets.finCarreraSkeleton);

	}

	private void presentPaused(float delta) {

	}

	private void presentNextLevel(float delta) {

		oAssets.finCarreraAnimacion1Lugar.apply(oAssets.finCarreraSkeleton, ScreenlastStatetime, ScreenStateTime, false, null);
		oAssets.finCarreraSkeleton.setX(SCREEN_WIDTH / 2f);
		oAssets.finCarreraSkeleton.setY(SCREEN_HEIGHT / 2f);
		oAssets.finCarreraSkeleton.updateWorldTransform();
		oAssets.finCarreraSkeleton.update(delta);
		skelrender.draw(batcher, oAssets.finCarreraSkeleton);
		if (drawStatsEndRace)
			dibujarStatsEndGame();

	}

	private void presentTryAgain(float delta) {
		Animation anim;
		if (oWorld.oPony.lugarEnLaCarrera == 2)
			anim = oAssets.finCarreraAnimacion2Lugar;
		else if (oWorld.oPony.lugarEnLaCarrera == 3)
			anim = oAssets.finCarreraAnimacion3Lugar;
		else
			anim = oAssets.finCarreraAnimacionGameOver;
		anim.apply(oAssets.finCarreraSkeleton, ScreenlastStatetime, ScreenStateTime, false, null);
		oAssets.finCarreraSkeleton.setX(SCREEN_WIDTH / 2f);
		oAssets.finCarreraSkeleton.setY(SCREEN_HEIGHT / 2f);
		oAssets.finCarreraSkeleton.updateWorldTransform();
		oAssets.finCarreraSkeleton.update(delta);
		skelrender.draw(batcher, oAssets.finCarreraSkeleton);
		if (drawStatsEndRace)
			dibujarStatsEndGame();

	}

	private void dibujarStatsEndGame() {
		oAssets.fontChco.draw(batcher, "LapTime", 270, 160);
		oAssets.fontChco.draw(batcher, lapTime, 420, 160);
		oAssets.fontChco.draw(batcher, "Time Left", 270, 130);
		oAssets.fontChco.draw(batcher, (int) oWorld.tiempoLeft + "", 420, 130);

		// Dibujar Monedas
		batcher.draw(oAssets.moneda, 360, 70, 30, 30);
		oAssets.fontChco.draw(batcher, stringMonedasRecolectadas, 420, 97);
		// fin
	}

	private void setTimeUp() {
		state = State.timeUp;
		ScreenStateTime = 0;
		stage.clear();

		btTryAgain.setPosition(5, 5);
		btMainMenu.setPosition(645, 5);

		stage.addActor(btMainMenu);

		if (nivelTiled != 1000)// Si es el mundo secreto no agrego el try again
			stage.addActor(btTryAgain);
		game.reqHandler.showAdBanner();
	}

	public void setNextLevel() {
		state = State.nextLevel;
		ScreenStateTime = 0;
		stage.clear();

		btTryAgain.setPosition(5, 5);
		btNextLevel.setPosition(645, 5);

		stage.addActor(btNextLevel);
		stage.addActor(btTryAgain);

		if (game.tiendaActual != Tienda.appStore)
			game.signin.showDialogShareOnFacebook("");
		game.reqHandler.showAdBanner();
	}

	public void setTryAgain() {
		state = State.tryAgain;
		ScreenStateTime = 0;
		stage.clear();

		btTryAgain.setPosition(5, 5);
		btMainMenu.setPosition(645, 5);

		stage.addActor(btMainMenu);
		stage.addActor(btTryAgain);
		game.reqHandler.showAdBanner();
	}

	public void setPause() {
		state = State.paused;
		stage.clear();
		WindowStyle windowStyle = new WindowStyle(oAssets.fontGde, Color.WHITE, oAssets.fondoVentanaPause);

		Dialog dialogPause = new Dialog("", windowStyle);
		dialogPause.setTouchable(Touchable.childrenOnly);
		Table content = dialogPause.getContentTable();

		btSonido.setPosition(285, 25);
		btMusica.setPosition(432, 25);

		content.defaults().padLeft(50).padRight(50);
		content.add(btContinue).expandX().padTop(125).colspan(2);
		content.row();
		if (nivelTiled != 1000)// si es secreto no agrego el try again
			content.add(btTryAgain).padBottom(45).padTop(10);
		content.add(btMainMenu).padBottom(45).padTop(10);

		// content.debug();
		// dialogPause.debug();
		dialogPause.show(stage);

		stage.addActor(btSonido);
		stage.addActor(btMusica);
		game.reqHandler.showAdBanner();

	}

	BotonNube btMainMenu;
	BotonNube btContinue;
	BotonNube btTryAgain;
	BotonNube btNextLevel;
	ImageButton btSonido;
	ImageButton btMusica;

	private void inicializarBotonesMenusInGame() {
		btMainMenu = new BotonNube(oAssets.nube, "Menu", oAssets.fontChco);
		btMainMenu.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				btMainMenu.wasSelected = true;
				btMainMenu.addAction(Actions.sequence(Actions.delay(.2f), btMainMenu.accionInicial, Actions.run(new Runnable() {
					@Override
					public void run() {
						GameScreenTileds.this.game.setScreen(new LoadingScreen(game, WorldMapTiledScreen.class));
						dispose();

					}
				})));
			};
		});

		btContinue = new BotonNube(oAssets.nube, "Continue", oAssets.fontChco);
		btContinue.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				btContinue.wasSelected = true;
				btContinue.addAction(Actions.sequence(Actions.delay(.2f), btContinue.accionInicial, Actions.run(new Runnable() {
					@Override
					public void run() {
						game.reqHandler.hideAdBanner();
						setBotonesInterfaz();
						state = State.running;

					}
				})));
			};
		});

		btTryAgain = new BotonNube(oAssets.nube, "Try again", oAssets.fontChco);
		btTryAgain.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				btTryAgain.wasSelected = true;
				btTryAgain.addAction(Actions.sequence(Actions.delay(.2f), btTryAgain.accionInicial, Actions.run(new Runnable() {
					@Override
					public void run() {
						GameScreenTileds.this.game.setScreen(new LoadingScreen(game, GameScreenTileds.class, nivelTiled));
						if (Settings.dificultadActual == Settings.DIFICULTAD_SUPERHARD & state == State.nextLevel)
							Settings.sumarMonedas((int) (MULTIPLICADOR_MONEDAS_TIME_LEFT * oWorld.tiempoLeft));

					}
				})));
			};
		});

		btNextLevel = new BotonNube(oAssets.nube, "Next", oAssets.fontChco);
		btNextLevel.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				btNextLevel.wasSelected = true;
				btNextLevel.addAction(Actions.sequence(Actions.delay(.2f), btNextLevel.accionInicial, Actions.run(new Runnable() {
					@Override
					public void run() {
						GameScreenTileds.this.game.setScreen(new LoadingScreen(game, WorldMapTiledScreen.class));
						if (Settings.dificultadActual == Settings.DIFICULTAD_SUPERHARD)
							Settings.sumarMonedas((int) (MULTIPLICADOR_MONEDAS_TIME_LEFT * oWorld.tiempoLeft));

						dispose();

					}
				})));
			};
		});

		btSonido = new ImageButton(oAssets.btSonidoOff, null, oAssets.btSonidoON);
		btSonido.setChecked(Settings.isSonidoON);
		btSonido.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Settings.isSonidoON = !Settings.isSonidoON;
				super.clicked(event, x, y);
			}
		});

		btMusica = new ImageButton(oAssets.btMusicaOff, null, oAssets.btMusicaON);
		btMusica.setChecked(Settings.isMusicaON);
		btMusica.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Settings.isMusicaON = !Settings.isMusicaON;
				oAssets.platMusicInGame();
				super.clicked(event, x, y);
			}
		});

		btTryAgain.setSize(150, 100);
		btMainMenu.setSize(150, 100);
		btContinue.setSize(150, 100);
		btNextLevel.setSize(150, 100);
	}

	@Override
	public void show() {

	}

	@Override
	public void dispose() {
		super.dispose();

		// Pongo esta condicion porque algunas veces el usuario presiona 2 veces el boton y se llama 2 veces este metodo;
		if (oWorld == null || renderer == null)
			return;
		// Checo el achivmente de las monedas
		game.achievements.checkMonedasAchivments(oWorld.oPony.monedasRecolectadas);
		game.achievements.checkEatChiliAchievements(oWorld.oPony.chilesRecolectados);
		game.achievements.checkEatChocolateAchievements(oWorld.oPony.dulcesRecolectados);
		game.achievements.checkGetBallonsAchievements(oWorld.oPony.globosRecolectados);

		oWorld.oWorldBox.dispose();
		renderer.renderBox.dispose();
		oAssets.tiledMap.dispose();
		renderer.tiledRender.dispose();
		renderer = null;
		oWorld = null;
		game.oAssets.unloadGameScreenTiled();

	}

	@Override
	public void hide() {
		super.hide();

		// El Ad se mostrara cada 5 veces
		int tiempoAds = 4;
		if (game.tiendaActual == Tienda.appStore)
			tiempoAds = 3;

		if (Settings.statTimesPlayed % tiempoAds == 0) {
			game.reqHandler.showInterstitial();
		}

	}

	@Override
	public boolean keyDown(int keycode) {
		if (Keys.DPAD_DOWN == keycode)
			renderer.OrthoCam.position.y -= .1;
		else if (Keys.DPAD_UP == keycode) {
			renderer.OrthoCam.position.y += .1;
			jump = true;
		}
		else if (Keys.DPAD_LEFT == keycode)
			renderer.OrthoCam.position.x -= 3;

		else if (Keys.DPAD_RIGHT == keycode)
			renderer.OrthoCam.position.x += 3;
		else if (Keys.K == keycode)
			renderer.OrthoCam.position.x -= .1f;
		else if (Keys.L == keycode)
			renderer.OrthoCam.position.x += .1f;

		else if (Keys.SPACE == keycode)
			jump = true;
		else if (Keys.B == keycode)
			fireBomb = true;
		else if (Keys.N == keycode)
			fireWood = true;
		else if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
			if (state == State.running)
				setPause();
			else {
				game.setScreen(new LoadingScreen(game, WorldMapTiledScreen.class));
				dispose();
			}
			return true;
		}

		return super.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.A || keycode == Keys.D)
			accelX = 0;
		return super.keyUp(keycode);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		this.guiCam.unproject(touchPoint.set(screenX, screenY, 0));
		Gdx.app.log("X", touchPoint.x + "");
		Gdx.app.log("Y", touchPoint.y + "");

		if (oWorld == null)
			return false;

		return false;
	}

	@Override
	public void pause() {
		setPause();
		super.pause();
	}

	@Override
	public void resume() {
		// Cuando pasa a nextLevel se muestra el video, por lo que cuando regresa del video se debe llamar este metodo
		// if (state == State.nextLevel)
		// game.setScreen(new LoadingScreen(game, WorldMapScreen.class));
		// super.resume();
	}

}
