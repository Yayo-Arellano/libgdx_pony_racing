package com.tiarsoft.ponyrace;

import org.robovm.bindings.gpgs.GPGToastPlacement;
import org.robovm.bindings.gpp.GPPURLHandler;
import org.robovm.bindings.mopub.MPAdView;
import org.robovm.bindings.mopub.MPAdViewDelegate;
import org.robovm.bindings.mopub.MPConstants;
import org.robovm.bindings.mopub.MPInterstitialAdController;
import org.robovm.bindings.mopub.sample.MPAdViewController;
import org.robovm.bindings.playservices.PlayServicesManager;
import org.robovm.bindings.playservices.PlayServicesManager.LoginCallback;
import org.robovm.cocoatouch.coregraphics.CGRect;
import org.robovm.cocoatouch.foundation.NSAutoreleasePool;
import org.robovm.cocoatouch.foundation.NSDictionary;
import org.robovm.cocoatouch.foundation.NSError;
import org.robovm.cocoatouch.foundation.NSObject;
import org.robovm.cocoatouch.foundation.NSURL;
import org.robovm.cocoatouch.uikit.UIApplication;
import org.robovm.cocoatouch.uikit.UIScreen;
import org.robovm.cocoatouch.uikit.UIView;
import org.robovm.cocoatouch.uikit.UIViewController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.tiarsoft.handlers.GoogleGameServicesHandler;
import com.tiarsoft.handlers.RequestHandler;
import com.tiarsoft.ponyrace.MainPonyRace.Tienda;

public class RobovmLauncher extends IOSApplication.Delegate implements RequestHandler, GoogleGameServicesHandler {

	private IOSApplication gdxApp;
	private PlayServicesManager manager;
	private MainPonyRace game;
	Tienda tienda = Tienda.appStore;

	private final String clientID = "675216428135-bti78t6tt6ihnh3fbuaq8l4r8kkpm7fd.apps.googleusercontent.com";
	private static final String INTERSTITIAL_ID = "7b6534ad9a294c869287104172011847";
	private static final String BANNER_ID = "3ef2941c253a40d8b4532a7e1374bf19";

	MPAdViewController bannerController;
	MPInterstitialAdController interstitialController;
	private UIViewController rootViewController;
	MPAdView asd;

	@Override
	protected IOSApplication createApplication() {
		IOSApplicationConfiguration config = new IOSApplicationConfiguration();
		game = new MainPonyRace(tienda, this, this);
		gdxApp = new IOSApplication(game, config);
		return gdxApp;
	}

	public static void main(String[] argv) {
		NSAutoreleasePool pool = new NSAutoreleasePool();
		UIApplication.main(argv, null, RobovmLauncher.class);
		pool.drain();
	}

	private LoginCallback loginCallback = new LoginCallback() {

		@Override
		public void success() {
			// TODO Auto-generated method stub

		}

		@Override
		public void error(NSError error) {
			// TODO Auto-generated method stub

		}

	};

	@SuppressWarnings("rawtypes")
	@Override
	public boolean didFinishLaunching(UIApplication application, NSDictionary launchOptions) {
		final boolean result = super.didFinishLaunching(application, launchOptions);

		manager = new PlayServicesManager();
		manager.setClientId(clientID);
		manager.setViewController(gdxApp.getUIViewController());
		manager.setToastLocation(PlayServicesManager.TOAST_BOTH, GPGToastPlacement.GPGToastPlacementTop);
		manager.setUserDataToRetrieve(true, false);
		manager.setLoginCallback(loginCallback);
		manager.didFinishLaunching();

		MPAdView banner = new MPAdView(BANNER_ID, MPConstants.MOPUB_BANNER_SIZE);
		float bannerWidth = UIScreen.getMainScreen().getApplicationFrame().size().width();
		float bannerHeight = bannerWidth / MPConstants.MOPUB_BANNER_SIZE.width() * MPConstants.MOPUB_BANNER_SIZE.height();
		banner.setFrame(new CGRect(0, 0, bannerWidth, bannerHeight));
		// banner.setBackgroundColor(new UIColor(1, 0, 0, 1)); // Remove this after testing.
		bannerController = new MPAdViewController(banner);
		MPAdViewDelegate bannerDelegate = new MPAdViewDelegate.Adapter() {
			@Override
			public UIViewController getViewController() {
				return bannerController;
			}
		};
		banner.setDelegate(bannerDelegate);
		bannerController.getView().addSubview(banner);
		banner.loadAd();
		asd = banner;
		interstitialController = MPInterstitialAdController.getAdController(INTERSTITIAL_ID);
		interstitialController.loadAd();

		rootViewController = new UIViewController();
		UIView interstitialView = new UIView(UIScreen.getMainScreen().getBounds());
		interstitialView.setUserInteractionEnabled(false); // This is important, otherwise you want get any touch input in your game.
		rootViewController.setView(interstitialView);
		application.getKeyWindow().addSubview(rootViewController.getView());

		// --Banner Ads
		// mainViewController.addChildViewController(adViewController);
		// mainViewController.getView().addSubview(adViewController.getView());
		// banner.loadAd();

		// --Fin Banner Ads

		// fin ads
		return result;
	}

	boolean isAdShown = false;// Previene que se ponga mchas veces el add y eso bugea el worlmap

	@Override
	public void showAdBanner() {
		if (!isAdShown) {
			gdxApp.getUIViewController().getView().addSubview(asd);
			isAdShown = true;
		}

	}

	@Override
	public void hideAdBanner() {
		if (isAdShown) {
			asd.removeFromSuperview();
			isAdShown = false;
		}

	}

	@Override
	public boolean openURL(UIApplication application, NSURL url, String sourceApplication, NSObject annotation) {
		return GPPURLHandler.handleURL(url, sourceApplication, annotation);
	}

	public IOSApplication getApplication() {
		return gdxApp;
	}

	@Override
	public void signOutGPGS() {
		manager.logout();

	}

	@Override
	public void showMoreGames() {
		Gdx.net.openURI("http://ad.leadboltads.net/show_app_wall?section_id=941506167");

	}

	@Override
	public void showInterstitial() {
		interstitialController.show(rootViewController);
		interstitialController.loadAd();
	}

	@Override
	public void showFacebook() {
		Gdx.net.openURI("https://www.facebook.com/Tiarsoft");

	}

	@Override
	public void showRater() {
		Settings.seCalificoApp = true;
		Settings.guardar();
		Gdx.net.openURI("https://itunes.apple.com/us/app/pony-racing/id776425427?ls=1&mt=8");

	}

	@Override
	public void submitScore(float tiempoLap, String leaderBoard) {
		if (!isSignedInGPGS())// por si no esta logeado
			return;
		long tiempoLong = (long) (tiempoLap * 100.0f);// Para recorrer el punto 2 numeros a la izq, y poder enviar un lingo

		manager.postScore(leaderBoard, tiempoLong);

	}

	@Override
	public void unlockAchievement(String achievementId) {
		if (!isSignedInGPGS())// por si no esta logeado
			return;
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
	public boolean isSignedInGPGS() {
		return manager.isLoggedIn();
	}

	@Override
	public void signInGPGS() {
		manager.login();
	}

	@Override
	public void unlockIncrementalAchievementGPGS(String achievementId, int pasosDados) {
		if (!isSignedInGPGS())// por si no esta logeado
			return;
		manager.incrementAchievement(achievementId, pasosDados);

	}

	@Override
	public void shareOnFacebook(String mensaje) {
		// TODO Auto-generated method stub

	}

}
