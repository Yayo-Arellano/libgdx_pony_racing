package com.tiarsoft.ponyrace;

import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.foundation.NSDictionary;
import org.robovm.apple.foundation.NSString;
import org.robovm.apple.foundation.NSURL;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIScreen;
import org.robovm.apple.uikit.UIView;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.bindings.mopub.MPAdView;
import org.robovm.bindings.mopub.MPAdViewDelegate;
import org.robovm.bindings.mopub.MPAdViewDelegateAdapter;
import org.robovm.bindings.mopub.MPConstants;
import org.robovm.bindings.mopub.MPInterstitialAdController;
import org.robovm.bindings.mopub.sample.MPAdViewController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.tiarsoft.handlers.RequestHandler;
import com.tiarsoft.ponyrace.MainPonyRace.Tienda;

public class RobovmLauncher extends IOSApplication.Delegate implements
		RequestHandler {

	private static final String APPID = "776425427";
	private static final String ID_BANNER = "3ef2941c253a40d8b4532a7e1374bf19";
	private static final String ID_INTERSTITIAL = "7b6534ad9a294c869287104172011847";

	protected MainPonyRace game;
	final static Tienda tienda = Tienda.appStore;
	private IOSApplication gdxApp;

	MPAdViewController bannerController;
	MPInterstitialAdController interstitialController;
	private UIViewController adsViewController;

	@Override
	protected IOSApplication createApplication() {
		IOSApplicationConfiguration config = new IOSApplicationConfiguration();
		gdxApp = new IOSApplication(game, config);
		return gdxApp;
	}

	@Override
	public boolean didFinishLaunching(UIApplication application,
			NSDictionary<NSString, ?> launchOptions) {
		boolean result = super.didFinishLaunching(application, launchOptions);

		// Creo el banner
		MPAdView banner = new MPAdView(ID_BANNER, MPConstants.MOPUB_BANNER_SIZE);
		double bannerWidth = UIScreen.getMainScreen().getBounds().size()
				.width();
		double bannerHeight = bannerWidth
				/ MPConstants.MOPUB_BANNER_SIZE.width()
				* MPConstants.MOPUB_BANNER_SIZE.height();
		banner.setFrame(new CGRect(0, 0, bannerWidth, bannerHeight));
		bannerController = new MPAdViewController(banner);

		MPAdViewDelegate bannerDelegate = new MPAdViewDelegateAdapter() {
			@Override
			public UIViewController getViewController() {
				return bannerController;
			}
		};
		banner.setDelegate(bannerDelegate);
		bannerController.getView().addSubview(banner);
		banner.loadAd();

		// Creo el interstitial
		interstitialController = MPInterstitialAdController
				.getAdController(ID_INTERSTITIAL);
		interstitialController.loadAd();

		adsViewController = new UIViewController();
		UIView interstitialView = new UIView(UIScreen.getMainScreen()
				.getBounds());
		interstitialView.setUserInteractionEnabled(false);
		adsViewController.setView(interstitialView);
		application.getKeyWindow().addSubview(adsViewController.getView());
		return result;
	}

	@Override
	public void showAdBanner() {
		if (gdxApp.getUIViewController().getView() == bannerController
				.getView().getSuperview())
			return;// No lo agrego si ya esta
		gdxApp.getUIViewController().getView()
				.addSubview(bannerController.getView());
	}

	@Override
	public void hideAdBanner() {
		bannerController.getView().removeFromSuperview();
	}

	@Override
	public void showInterstitial() {
		interstitialController.show(adsViewController);
		interstitialController.loadAd();

	}

	@Override
	public void showFacebook() {
		Gdx.net.openURI("https://www.facebook.com/Tiarsoft");

	}

	@Override
	public void showMoreGames() {
		Gdx.net.openURI("https://itunes.apple.com/us/artist/gerardo-arellano/id769496540");

	}

	@Override
	public void showRater() {
		UIApplication
				.getSharedApplication()
				.openURL(
						new NSURL(
								"https://userpub.itunes.apple.com/WebObjects/MZUserPublishing.woa/wa/addUserReview?id="
										+ APPID + "&type=Purple+Software"));
	}

	@Override
	public void shareOnFacebook(String mensaje) {
		// TODO Auto-generated method stub

	}

	@Override
	public void shareOnTwitter(String mensaje) {
		// TODO Auto-generated method stub

	}

}
