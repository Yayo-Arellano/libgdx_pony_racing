package com.tiarsoft.ponyrace;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.startapp.android.publish.Ad;
import com.startapp.android.publish.AdDisplayListener;
import com.startapp.android.publish.StartAppAd;
import com.tiarsoft.handlers.RequestHandler;
import com.tiarsoft.ponyrace.MainPonyRace.Tienda;
import com.tiarsoft.ponyrace.game.GameScreenTileds;

public class MainActivity extends AndroidApplication implements RequestHandler {
	String admobIdBanner = "1c54c7ef4c6b4a0e";
	String admobIdInterstitial = "7cf10305e521450e";

	String StartppDeveloperId = "104087430";
	String StartappAppId = "212101412";

	String facebookAppID = "573082252762517";

	public Tienda tienda;
	protected MainPonyRace game;

	InterstitialAd interAdmob;
	AdView bannerAdmob;
	AdRequest adRequest;
	StartAppAd interStartApp;
	LinearLayout layout;

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
		bannerAdmob = new AdView(this);
		bannerAdmob.setAdSize(AdSize.SMART_BANNER);
		bannerAdmob.setAdUnitId(admobIdBanner);

		layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setGravity(Gravity.CENTER);

		layout.addView(bannerAdmob, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		layout.addView(gameView, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));

		setContentView(layout);

		adRequest = new AdRequest.Builder()//
				// .addTestDevice("1854CA4BA5218E72358728EB28DC2CED")//
				.build();

		bannerAdmob.loadAd(adRequest);
		interAdmob = new InterstitialAd(this);
		interAdmob.setAdUnitId(admobIdInterstitial);
		interAdmob.loadAd(adRequest);

		interStartApp = new StartAppAd(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		interStartApp.onResume();
		interStartApp.loadAd();
		bannerAdmob.resume();
	}

	@Override
	protected void onPause() {
		bannerAdmob.pause();
		super.onPause();

	}

	@Override
	protected void onDestroy() {
		bannerAdmob.destroy();
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {

	}

	public void showInterstitial() {
		runOnUiThread(new Runnable() {
			public void run() {
				showStartAppInterstitial();
			}

		});
	}

	private void showGoogleInterstitial() {
		if (interAdmob.isLoaded()) {
			interAdmob.setAdListener(new AdListener() {
				@Override
				public void onAdOpened() {
					if (game.getScreen() instanceof GameScreenTileds) {
						GameScreenTileds screen = (GameScreenTileds) game
								.getScreen();
						screen.setPause();
					}
				}
			});
			interAdmob.show();
		}
		interAdmob.loadAd(adRequest);

	}

	private void showStartAppInterstitial() {
		if (interStartApp.isReady()) {
			interStartApp.showAd(new AdDisplayListener() {

				@Override
				public void adHidden(Ad arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void adDisplayed(Ad arg0) {
					if (game.getScreen() instanceof GameScreenTileds) {
						GameScreenTileds screen = (GameScreenTileds) game
								.getScreen();
						screen.setPause();

					}
				}
			});
		}
		else {

			showGoogleInterstitial();
		}
		interStartApp.loadAd();

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
				else if (tienda == Tienda.googlePlay) {
					linkTienda = "market://details?id=";
				}
				else if (tienda == Tienda.samsung) {
					linkTienda = "samsungapps://ProductDetail/";
				}
				else {
					linkTienda = "sam://details?id=";
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
	public void shareOnFacebook(final String mensaje) {

	}

	@Override
	public void showMoreGames() {
		String linkTienda;
		if (tienda == Tienda.amazon) {
			linkTienda = "amzn://apps/android?s=tiarsoft";
		}
		else if (tienda == Tienda.googlePlay) {
			linkTienda = "https://play.google.com/store/apps/developer?id=TiarSoft";
		}
		else if (tienda == Tienda.samsung) {
			linkTienda = "samsungapps://SellerDetail/qotluyngkp";
		}
		else {
			linkTienda = "sam://search?q=yayo28";
		}
		Gdx.net.openURI(linkTienda);
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
	public void showAdBanner() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hideAdBanner() {
		// TODO Auto-generated method stub

	}
}