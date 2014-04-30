package com.tiarsoft.ponyrace;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSDictionary;
import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.foundation.NSString;
import org.robovm.apple.foundation.NSURL;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.bindings.gpgs.GPGToastPlacement;
import org.robovm.bindings.gpp.GPPURLHandler;
import org.robovm.bindings.playservices.PlayServicesManager;
import org.robovm.bindings.playservices.PlayServicesManager.LoginCallback;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.tiarsoft.handlers.GoogleGameServicesHandler;

public class MainGPGS extends AppPurchases implements GoogleGameServicesHandler {

	private final String GOOGLE_GAME_SERVICES_ID = "809133709631.apps.googleusercontent.com";
	private PlayServicesManager manager;
	private UIViewController gameServicesViewController;

	public static void main(String[] argv) {
		try (NSAutoreleasePool pool = new NSAutoreleasePool()) {
			UIApplication.main(argv, null, MainGPGS.class);
			pool.close();
		}
	}

	@Override
	protected IOSApplication createApplication() {
		game = new MainPonyRace(tienda, this, this);
		return super.createApplication();
	}

	private LoginCallback loginCallback = new LoginCallback() {

		@Override
		public void success() {

		}

		@Override
		public void error(org.robovm.apple.foundation.NSError error) {
			// TODO Auto-generated method stub

		}

	};

	@Override
	public boolean didFinishLaunching(UIApplication application,
			NSDictionary<NSString, ?> launchOptions) {
		boolean result = super.didFinishLaunching(application, launchOptions);

		gameServicesViewController = new UIViewController();
		gameServicesViewController.getView().setUserInteractionEnabled(false);
		application.getKeyWindow().addSubview(
				gameServicesViewController.getView());

		manager = new PlayServicesManager();
		manager.setClientId(GOOGLE_GAME_SERVICES_ID);
		manager.setViewController(gameServicesViewController);
		manager.setToastLocation(PlayServicesManager.TOAST_BOTH,
				GPGToastPlacement.GPGToastPlacementTop);
		manager.setUserDataToRetrieve(true, false);
		manager.setLoginCallback(loginCallback);
		manager.didFinishLaunching();

		return result;
	}

	@Override
	public boolean openURL(UIApplication application, NSURL url,
			String sourceApplication, NSObject annotation) {
		return GPPURLHandler.handleURL(url, sourceApplication, annotation);
	}

	@Override
	public void unlockAchievement(String achievementId) {
		manager.unlockAchievement(achievementId);

	}

	@Override
	public void getLeaderboard() {
		manager.showLeaderboardsPicker();

	}

	@Override
	public void getAchievements() {
		manager.showAchievements();

	}

	@Override
	public boolean isSignedIn() {
		return manager.isLoggedIn();
	}

	@Override
	public void signIn() {
		manager.login();

	}

	@Override
	public void signOut() {
		manager.logout();

	}

	@Override
	public void submitScore(float tiempoLap, String leaderBoard) {
		if (!isSignedIn())
			return;
		long tiempoLong = (long) (tiempoLap * 100.0f);// Para recorrer el punto 2 numeros a la izq, y poder enviar un lingo
		manager.postScore(leaderBoard, tiempoLong);

	}

	@Override
	public void unlockIncrementalAchievement(String achievementId,
			int pasosDados) {
		// TODO Auto-generated method stub

	}

}
