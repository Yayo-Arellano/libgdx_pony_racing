package com.tiarsoft.ponyrace;

import java.util.Random;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.ads.Ad;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.ads.InterstitialAd;
import com.google.ads.AdRequest.ErrorCode;
import com.sromku.simple.fb.Permissions;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebook.OnLoginListener;
import com.sromku.simple.fb.SimpleFacebook.OnPublishListener;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.entities.Feed;
import com.startapp.android.publish.AdDisplayListener;
import com.startapp.android.publish.StartAppAd;
import com.tiarsoft.handlers.RequestHandler;
import com.tiarsoft.ponyrace.MainPonyRace.Tienda;
import com.tiarsoft.ponyrace.game.GameScreenTileds;

public class MainActivity extends AndroidApplication implements RequestHandler {
	String admobBannerId = "1c54c7ef4c6b4a0e";
	String admobInterstitialId = "7cf10305e521450e";

	String StartppDeveloperId = "104087430";
	String StartappAppId = "212101412";

	InterstitialAd interAdmob;
	AdRequest adRequest;
	StartAppAd interStartApp;

	String testDevices[] = { //

	"1854CA4BA5218E72358728EB28DC2CED" // GalaxyS4 mini//
	};

	protected Tienda tienda;
	protected Random oRan;
	protected MainPonyRace game;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		StartAppAd.init(this, StartppDeveloperId, StartappAppId);
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = true;

		View gameView = initializeForView(game, cfg);
		AdView adView = new AdView(this, AdSize.SMART_BANNER, admobBannerId);// Network mediation no sierve porque otras redes no soportan smartBanner

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setGravity(Gravity.CENTER);

		layout.addView(adView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		layout.addView(gameView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));

		// RelativeLayout layout = new RelativeLayout(this);
		// RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		// RelativeLayout.LayoutParams gameParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		//
		// adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);// pone el add en la parte de arriba
		// adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);// centra el add de a la ancho
		// gameParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);// pone el surfaceview enla parte de abajo

		// layout.addView(gameView, gameParam);
		// layout.addView(adView, adParams);

		setContentView(layout);
		adRequest = new AdRequest();
		for (int i = 0; i < testDevices.length; i++) {
			adRequest.addTestDevice(testDevices[i]);
		}

		adView.loadAd(adRequest);

		interStartApp = new StartAppAd(this);

		interAdmob = new InterstitialAd(this, admobInterstitialId);
		interAdmob.loadAd(adRequest);

		// initialize(new MainPonyRace(Tienda.none, this), cfg);
	}

	@Override
	public void onBackPressed() {

	}

	@Override
	protected void onResume() {
		Permissions[] permissions = new Permissions[] { Permissions.PUBLISH_ACTION };
		SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder().setAppId("573082252762517")
		// .setNamespace("")
		.setPermissions(permissions).build();
		SimpleFacebook.setConfiguration(configuration);
		oFacebook = SimpleFacebook.getInstance(this);

		// Esto es para checar las instalaciones
		com.facebook.AppEventsLogger.activateApp(this, "573082252762517");
		super.onResume();
		interStartApp.onResume();
		interStartApp.loadAd();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		oFacebook.onActivityResult(this, requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
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

	protected SimpleFacebook oFacebook;

	@Override
	public void shareOnFacebook(final String mensaje) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (oFacebook.isLogin()) {

					// build feed
					Feed feed = new Feed.Builder().setMessage("Pony Racing")// I Made 232.43 seconds in a race, can u beat my score
					.setName("Pony Racing ")// Pony racing
					.setCaption("Get it now!")//
					.setDescription("Everyone loves to play racing games! so spend unforgettable time and experience a new kind of racing journey to become the FASTEST PONY RACER ever!")//
					.setPicture("https://dl.dropboxusercontent.com/u/78073642/PonyRace/150x150.PNG")//
					.addProperty("Also available for iOS", "Download", "https://itunes.apple.com/us/app/pony-racing/id776425427?ls=1&mt=8").setLink("https://play.google.com/store/apps/details?id=com.tiarsoft.ponyrace")//
					.addAction("Tiarsoft", "https://www.facebook.com/Tiarsoft")//
					.build();//

					// publish the feed
					oFacebook.publish(feed, new OnPublishListener() {

						@Override
						public void onFail(String reason) {
							Log.e("Facebook", reason);

						}

						@Override
						public void onException(Throwable throwable) {
							Log.e("Facebook", throwable.toString());

						}

						@Override
						public void onThinking() {
							// TODO Auto-generated method stub

						}

						@Override
						public void onComplete(String id) {
							Log.e("Facebook", id);
							Settings.sumarMonedas(Settings.MONEDAS_REGALO_SHARE_FACEBOOK);
							Settings.guardar();

						}
					});

				}
				else {
					oFacebook.login(new OnLoginListener() {
						@Override
						public void onFail(String reason) {
							Log.e("Facebook", reason);
						}

						@Override
						public void onException(Throwable throwable) {
							Log.e("Facebook", throwable.toString());
						}

						@Override
						public void onThinking() {
						}

						@Override
						public void onNotAcceptingPermissions() {
							Log.w("Facebook", "User didn't accept read permissions");
						}

						@Override
						public void onLogin() {
							shareOnFacebook(mensaje);
						}
					});

				}

			}
		});

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
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/216392968461240"));
					startActivity(intent);
				}
				catch (Exception e) {
					Gdx.net.openURI("https://www.facebook.com/Tiarsoft");
				}
			}
		});

	}

	@Override
	public void showInterstitial() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				showStartAppInterstitial();

			}

		});
	}

	private void showStartAppInterstitial() {
		if (interStartApp.isReady()) {
			interStartApp.showAd(new AdDisplayListener() {

				@Override
				public void adHidden(com.startapp.android.publish.Ad arg0) {

				}

				@Override
				public void adDisplayed(com.startapp.android.publish.Ad arg0) {
					if (game.getScreen() instanceof GameScreenTileds) {
						GameScreenTileds screen = (GameScreenTileds) game.getScreen();
						screen.setPause();
					}
				}
			});

		}
		else {

			showGoogleInterstitial();
		}
		Gdx.app.log("Startapp", "LOAD();");
		interStartApp.loadAd();

	}

	private void showGoogleInterstitial() {
		com.google.ads.AdListener adListener = new com.google.ads.AdListener() {

			@Override
			public void onReceiveAd(Ad arg0) {
			}

			@Override
			public void onPresentScreen(Ad arg0) {
				if (game.getScreen() instanceof GameScreenTileds) {
					GameScreenTileds screen = (GameScreenTileds) game.getScreen();
					screen.setPause();
				}
			}

			@Override
			public void onLeaveApplication(Ad arg0) {

			}

			@Override
			public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
			}

			@Override
			public void onDismissScreen(Ad arg0) {

			}
		};
		interAdmob.setAdListener(adListener);
		interAdmob.show();
		interAdmob.loadAd(adRequest);

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