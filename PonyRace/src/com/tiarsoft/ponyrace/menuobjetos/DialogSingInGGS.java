package com.tiarsoft.ponyrace.menuobjetos;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tiarsoft.handlers.GoogleGameServicesHandler;
import com.tiarsoft.ponyrace.MainPonyRace;
import com.tiarsoft.ponyrace.Settings;

public class DialogSingInGGS {
	Stage stage;
	final MainPonyRace game;

	Dialog dialogSignIn, dialogRate, dialogShareFacebook;

	public DialogSingInGGS(MainPonyRace game, Stage stage) {
		this.stage = stage;
		this.game = game;
	}

	public void showDialogSignIn() {
		dialogSignIn = new Dialog("Log in", game.oAssets.skin);
		Label lblContenido = new Label("Sign in with Google to share your scores and achievements with your friends", game.oAssets.skin);
		lblContenido.setWrap(true);

		dialogSignIn.getContentTable().add(lblContenido).width(450).height(150);

		TextButtonStyle stilo = new TextButtonStyle(game.oAssets.btSignInUp, game.oAssets.btSignInDown, null, game.oAssets.skin.getFont("default-font"));
		TextButton btSignIn = new TextButton("Log in", stilo);
		btSignIn.getLabel().setWrap(true);
		TextButton btNotNow = new TextButton("Not now", game.oAssets.skin);

		btNotNow.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dialogSignIn.hide();
				game.reqHandler.hideAdBanner();

			}
		});

		btSignIn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((GoogleGameServicesHandler) game.gameServiceHandler).signInGPGS();
				dialogSignIn.hide();
				game.reqHandler.hideAdBanner();

			}
		});

		dialogSignIn.getButtonTable().add(btSignIn).minWidth(140).fill();
		dialogSignIn.getButtonTable().add(btNotNow).minWidth(140).fill();
		dialogSignIn.show(stage);
		game.reqHandler.showAdBanner();
	}

	public void showDialogRate() {
		dialogRate = new Dialog("Support this game", game.oAssets.skin);
		Label lblContenido = new Label("Hello, thank you for playing Pony racing.\nHelp us to support this game. Just rate us at the app store.", game.oAssets.skin);
		lblContenido.setWrap(true);

		dialogRate.getContentTable().add(lblContenido).width(450).height(170);

		TextButton rate = new TextButton("Rate", game.oAssets.skin);
		rate.getLabel().setWrap(true);
		TextButton btNotNow = new TextButton("Not now", game.oAssets.skin);

		btNotNow.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				dialogRate.hide();
				game.reqHandler.hideAdBanner();

			}
		});

		rate.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.reqHandler.showRater();
				dialogRate.hide();
				game.reqHandler.hideAdBanner();

			}
		});

		dialogRate.getButtonTable().add(rate).minWidth(140).fill();
		dialogRate.getButtonTable().add(btNotNow).minWidth(140).fill();
		dialogRate.show(stage);
		game.reqHandler.showAdBanner();
	}

	public void showDialogShareOnFacebook(final String mensaje) {
		dialogShareFacebook = new Dialog("Share on Facebook", game.oAssets.skin);
		Label lblContenido = new Label("Share your lap time in Facebook and get " + Settings.MONEDAS_REGALO_SHARE_FACEBOOK + " coins", game.oAssets.skin);
		lblContenido.setWrap(true);

		dialogShareFacebook.getContentTable().add(lblContenido).width(450).height(170);

		TextButtonStyle stilo = new TextButtonStyle(game.oAssets.btShareFacebookUp, game.oAssets.btShareFacebookDown, null, game.oAssets.skin.getFont("default-font"));
		TextButton rate = new TextButton("Share", stilo);
		rate.getLabel().setWrap(true);

		TextButton btNotNow = new TextButton("Not now", game.oAssets.skin);

		btNotNow.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dialogShareFacebook.hide();

			}
		});

		rate.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.reqHandler.shareOnFacebook(mensaje);
				dialogShareFacebook.hide();

			}
		});

		dialogShareFacebook.getButtonTable().add(rate).minWidth(140).fill();
		dialogShareFacebook.getButtonTable().add(btNotNow).minWidth(140).fill();
		dialogShareFacebook.show(stage);
	}

	public boolean isDialogRateShown() {
		return stage.getActors().contains(dialogRate, true);
	}
}
