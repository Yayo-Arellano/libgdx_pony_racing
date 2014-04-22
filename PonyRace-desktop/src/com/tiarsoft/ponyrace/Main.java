package com.tiarsoft.ponyrace;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
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

		// TexturePacker2.Settings settings = new TexturePacker2.Settings();
		// settings.combineSubdirectories = true;
		// settings.flattenPaths = true;
		// settings.alias = false;
		//
		// TexturePacker2.process(settings, "/Users/Yayo/Pictures/Games/PonyRace/comun", "/Users/Yayo/Pictures/Games/PonyRace", "atlasComun.txt");

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

		new LwjglApplication(new MainPonyRace(Tienda.none, handler,
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
	};

	static GameServicesHandler gameServicesHandler = new GameServicesHandler() {

		@Override
		public void unlockIncrementalAchievementGPGS(String achievementId,
				int pasosDados) {
			// TODO Auto-generated method stub

		}

		@Override
		public void unlockAchievement(String achievementId) {
			// TODO Auto-generated method stub

		}

		@Override
		public void submitScore(float tiempoLap, String leaderBoard) {
			// TODO Auto-generated method stub

		}

		@Override
		public void signOutGPGS() {
			// TODO Auto-generated method stub

		}

		@Override
		public void signInGPGS() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean isSignedInGPGS() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void getLeaderboard() {
			// TODO Auto-generated method stub

		}

		@Override
		public void getAchievements() {
			// TODO Auto-generated method stub

		}
	};
}
