package com.tiarsoft.ponyrace;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;

import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.Permission.Type;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.entities.Feed;
import com.sromku.simple.fb.entities.Privacy;
import com.sromku.simple.fb.entities.Privacy.PrivacySettings;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnPublishListener;

public class MainActivityFacebook extends MainActivity {
	protected SimpleFacebook oFacebook;
	ProgressDialog progress;
	Privacy privacy;

	@Override
	protected void onResume() {
		Permission[] permissions = new Permission[] { Permission.PUBLISH_ACTION };
		SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
				.setAppId(facebookAppID)
				// .setNamespace("")
				.setPermissions(permissions).build();

		privacy = new Privacy.Builder().setPrivacySettings(
				PrivacySettings.EVERYONE).build();

		SimpleFacebook.setConfiguration(configuration);
		oFacebook = SimpleFacebook.getInstance(this);
		com.facebook.AppEventsLogger.activateApp(this, facebookAppID);
		super.onResume();
	}

	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		oFacebook.onActivityResult(this, request, response, data);
	}

	@Override
	public void shareOnFacebook(final String mensaje) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (oFacebook.isLogin()) {
					Feed feed = new Feed.Builder()
							.setMessage(mensaje)
							.setName("Pony Racing")
							.setCaption("Get it now!")
							.setDescription(
									"Everyone loves to play racing games! so spend unforgettable time and experience a new kind of racing journey to become the FASTEST PONY RACER ever!")
							.setPicture(
									"https://dl.dropboxusercontent.com/u/78073642/IconosWeb/ponyRace.png")
							.addProperty("Also available for iOS", "Download",
									"https://itunes.apple.com/us/app/pony-racing/id776425427?ls=1&mt=8")
							.setLink(
									"https://play.google.com/store/apps/details?id=com.tiarsoft.ponyracing")
							.addAction("Tiarsoft",
									"https://www.facebook.com/Tiarsoft")
							.setPrivacy(privacy)//
							.build();

					oFacebook.publish(feed, publishListener);
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
						public void onLogin() {
							shareOnFacebook(mensaje);
						}

						@Override
						public void onNotAcceptingPermissions(Type type) {
							Log.w("Facebook",
									"User didn't accept read permissions");

						}
					});

				}

			}
		});

	}

	OnPublishListener publishListener = new OnPublishListener() {
		@Override
		public void onFail(String reason) {
			Log.w("Facebook", reason);
			progress.hide();

		}

		@Override
		public void onException(Throwable throwable) {
			progress.hide();
		}

		@Override
		public void onThinking() {
			progress = ProgressDialog.show(MainActivityFacebook.this, "Wait..",
					"Sharing Score");

		}

		@Override
		public void onComplete(String id) {
			progress.hide();
			Log.e("Facebook", id);
			Settings.sumarMonedas(Settings.MONEDAS_REGALO_SHARE_FACEBOOK);
			Settings.guardar();

		}
	};

}
