package com.tiarsoft.ponyrace;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubView;
import com.startapp.android.publish.StartAppAd;
import com.tiarsoft.handlers.RequestHandler;
import com.tiarsoft.ponyrace.MainPonyRace.Tienda;

public class MainActivity extends AndroidApplication implements RequestHandler {

	String mopubBannerId = "38cb02c442e64c588a98e0636855988d";
	String mopubInterId = "2681a6f09b7c4697a5c36282e4364310";

	String StartppDeveloperId = "104087430";
	String StartappAppId = "212101412";

	String facebookAppID = "573082252762517";

	public Tienda tienda;
	protected MainPonyRace game;

	MoPubInterstitial interMopub;
	MoPubView bannerMopub;
	FrameLayout layout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		StartAppAd.init(this, StartppDeveloperId, StartappAppId);
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useWakelock = true;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		View gameView = initializeForView(game, cfg);

		bannerMopub = new MoPubView(this);
		bannerMopub.setAdUnitId(mopubBannerId); // Enter your Ad Unit ID from www.mopub.com
		bannerMopub.setLayoutParams(new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.TOP));
		bannerMopub.loadAd();

		layout = new FrameLayout(this);
		layout.addView(gameView, new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT));

		setContentView(layout);

		interMopub = new MoPubInterstitial(this, mopubInterId);
		interMopub.load();
	}

	@Override
	protected void onDestroy() {
		bannerMopub.destroy();
		interMopub.destroy();
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {

	}

	public void showInterstitial() {
		runOnUiThread(new Runnable() {
			public void run() {
				if (interMopub.isReady()) {
					interMopub.show();

				}
				interMopub.load();
			}

		});
	}

	@Override
	public void shareOnFacebook(final String mensaje) {

	}

	@Override
	public void showFacebook() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					Intent intent = new Intent(Intent.ACTION_VIEW,
							Uri.parse("fb://profile/216392968461240"));
					startActivity(intent);
				}
				catch (Exception e) {
					Gdx.net.openURI("https://www.facebook.com/Tiarsoft");
				}
			}
		});

	}

	@Override
	public void shareOnTwitter(String mensaje) {
		String tweetUrl = "https://twitter.com/intent/tweet?text="
				+ mensaje
				+ " Download it from &url=http://goo.gl/R9hajS&hashtags=PonyRacing";
		Gdx.net.openURI(tweetUrl);

	}

	@Override
	public void showMoreGames() {
		String linkTienda;
		if (tienda == Tienda.amazon) {
			linkTienda = "amzn://apps/android?s=tiarsoft";
		}
		else if (tienda == Tienda.samsung) {
			linkTienda = "samsungapps://SellerDetail/qotluyngkp";
		}
		else if (tienda == Tienda.slideMe) {
			linkTienda = "sam://search?q=yayo28";
		}
		else
			linkTienda = "http://play.google.com/store/search?q=pub:Tiar";
		Gdx.net.openURI(linkTienda);
	}

	@Override
	public void showRater() {
		runOnUiThread(new Runnable() {
			public void run() {
				String nombrePaquete = MainActivity.this.getPackageName();
				String linkTienda;
				if (tienda == Tienda.amazon) {
					linkTienda = "amzn://apps/android?p=";
				}
				else if (tienda == Tienda.samsung) {
					linkTienda = "samsungapps://ProductDetail/";
				}
				else if (tienda == Tienda.slideMe) {
					linkTienda = "sam://details?id=";
				}
				else {// GooglePlay, Yandex, Otros
					linkTienda = "market://details?id=";
				}

				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(linkTienda + nombrePaquete));
				// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

				Settings.seCalificoApp = true;
				Settings.guardar();
				MainActivity.this.startActivity(intent);

			}
		});
	}

	@Override
	public void showAdBanner() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (bannerMopub.getParent() != layout)
					layout.addView(bannerMopub);
			}
		});

	}

	@Override
	public void hideAdBanner() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				layout.removeView(bannerMopub);
			}
		});

	}

}