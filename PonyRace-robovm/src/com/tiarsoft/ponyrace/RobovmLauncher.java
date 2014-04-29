package com.tiarsoft.ponyrace;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.tiarsoft.handlers.GoogleGameServicesHandler;
import com.tiarsoft.handlers.RequestHandler;
import com.tiarsoft.ponyrace.MainPonyRace.Tienda;

public class RobovmLauncher extends IOSApplication.Delegate implements
		RequestHandler, GoogleGameServicesHandler {

	private IOSApplication gdxApp;
	private MainPonyRace game;
	Tienda tienda = Tienda.appStore;

	@Override
	protected IOSApplication createApplication() {
		IOSApplicationConfiguration config = new IOSApplicationConfiguration();
		game = new MainPonyRace(tienda, this, this);
		gdxApp = new IOSApplication(game, config);
		return gdxApp;
	}

	public static void main(String[] argv) {
		try (NSAutoreleasePool pool = new NSAutoreleasePool()) {
			UIApplication.main(argv, null, RobovmLauncher.class);
			pool.close();
		}
	}

	@Override
	public void submitScore(float tiempoLap, String leaderBoard) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unlockAchievement(String achievementId) {
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
	public void showRater() {
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
	public void showMoreGames() {
		// TODO Auto-generated method stub

	}

	@Override
	public void shareOnFacebook(String mensaje) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showAdBanner() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hideAdBanner() {
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

	@Override
	public void shareOnTwitter(String mensaje) {
		// TODO Auto-generated method stub

	}

}
