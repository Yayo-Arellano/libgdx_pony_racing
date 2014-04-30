package com.tiarsoft.ponyrace;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.tiarsoft.handlers.GameServicesHandler;
import com.tiarsoft.handlers.RequestHandler;
import com.tiarsoft.ponyrace.MainPonyRace.Tienda;

public class Main {

	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "PonyRace";
		cfg.width = 800;
		cfg.height = 480;

		// cfg.fullscreen = true;

		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.combineSubdirectories = true;
		settings.flattenPaths = true;
		settings.alias = false;
		settings.pot = false;
		settings.square = false;

		// TexturePacker
		// .process(
		// settings,
		// "/Users/Yayo/Dropbox/Tiarsoft/Pony Games Racing/Imagenes/comun",
		// "/Users/Yayo/Dropbox/Tiarsoft/Pony Games Racing/Imagenes",
		// "atlasComun.txt");

		// Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
		//
		// @Override
		// public void uncaughtException(Thread arg0, Throwable ex) {
		// System.err.println("Critical Failure" + ex.getLocalizedMessage());
		// Sys.alert("Critical Failure", ex.toString());
		//
		//
		// }
		// });

		new LwjglApplication(new MainPonyRace(Tienda.otros, handler,
				gameServicesHandler), cfg);

	}

	static RequestHandler handler = new RequestHandler() {

		@Override
		public void showRater() {
			// TODO Auto-generated method stub

		}

		@Override
		public void showMoreGames() {
			// TODO Auto-generated method stub

		}

		@Override
		public void showInterstitial() {
			// TODO Auto-generated method stub

		}

		@Override
		public void showFacebook() {
			// TODO Auto-generated method stub

		}

		@Override
		public void showAdBanner() {
			// TODO Auto-generated method stub

		}

		@Override
		public void shareOnFacebook(String mensaje) {
			// TODO Auto-generated method stub

		}

		@Override
		public void hideAdBanner() {
			// TODO Auto-generated method stub

		}

		@Override
		public void shareOnTwitter(String mensaje) {
			// TODO Auto-generated method stub

		}
	};

	static GameServicesHandler gameServicesHandler = new GameServicesHandler() {

		@Override
		public void unlockAchievement(String achievementId) {
			// TODO Auto-generated method stub

		}

		@Override
		public void submitScore(float tiempoLap, String leaderBoard) {
			// TODO Auto-generated method stub

		}

		@Override
		public void getLeaderboard() {
			// TODO Auto-generated method stub

		}

		@Override
		public void getAchievements() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean isSignedIn() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void signIn() {
			// TODO Auto-generated method stub

		}

		@Override
		public void signOut() {
			// TODO Auto-generated method stub

		}

		@Override
		public void unlockIncrementalAchievement(String achievementId,
				int pasosDados) {
			// TODO Auto-generated method stub

		}
	};
}
