package com.tiarsoft.ponyrace;

import java.util.EnumSet;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.amazon.ags.api.AmazonGamesCallback;
import com.amazon.ags.api.AmazonGamesClient;
import com.amazon.ags.api.AmazonGamesFeature;
import com.amazon.ags.api.AmazonGamesStatus;
import com.tiarsoft.handlers.AmazonGameServicesHandler;

public class PonyRaceAGC extends MainActivityFacebook implements
		AmazonGameServicesHandler {

	AmazonGamesClient amazonClient;

	ProgressDialog dialogWait;
	final String pleaseWait = "Please Wait...";

	AmazonGamesCallback callback = new AmazonGamesCallback() {
		@Override
		public void onServiceNotReady(AmazonGamesStatus status) {
			if (dialogWait != null)
				dialogWait.dismiss();
		}

		@Override
		public void onServiceReady(AmazonGamesClient amazonGamesClient) {
			amazonClient = amazonGamesClient;
			if (dialogWait != null)
				dialogWait.dismiss();
			// ready to use GameCircle
		}
	};

	EnumSet<AmazonGamesFeature> myGameFeatures = EnumSet.of(
			AmazonGamesFeature.Achievements, AmazonGamesFeature.Leaderboards);

	@Override
	public void onResume() {
		super.onResume();
		AmazonGamesClient.initialize(this, callback, myGameFeatures);
	}

	@Override
	public void onPause() {
		super.onPause();
		if (amazonClient != null) {
			AmazonGamesClient.release();
		}
	}

	@Override
	public void onBackPressed() {
		if (dialogWait != null) {
			dialogWait.dismiss();
		}
		super.onBackPressed();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		game = new MainPonyRace(tienda, this, this);
		super.onCreate(savedInstanceState);

	}

	@Override
	public void submitScore(float tiempoLap, String leaderBoard) {
		if (amazonClient == null)
			return;
		int tiempoLong = (int) tiempoLap;
		amazonClient.getLeaderboardsClient().submitScore(leaderBoard,
				tiempoLong);

	}

	@Override
	public void unlockAchievement(String achievementId) {
		if (amazonClient == null)
			return;
		amazonClient.getAchievementsClient().updateProgress(achievementId,
				100.0f);

	}

	@Override
	public void getLeaderboard() {
		if (amazonClient == null)
			AmazonGamesClient.initialize(this, callback, myGameFeatures);
		else
			amazonClient.getLeaderboardsClient().showLeaderboardsOverlay();

	}

	@Override
	public void getAchievements() {
		if (amazonClient == null)
			AmazonGamesClient.initialize(this, callback, myGameFeatures);
		else
			amazonClient.getAchievementsClient().showAchievementsOverlay();

	}

	@Override
	public boolean isSignedInGPGS() {
		if (amazonClient == null)
			return false;
		else
			return true;
	}

	@Override
	public void signInGPGS() {
		if (amazonClient == null) {
			AmazonGamesClient.shutdown();
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					dialogWait = ProgressDialog.show(PonyRaceAGC.this, "",
							pleaseWait, true, true);
				}
			});
			AmazonGamesClient.initialize(this, callback, myGameFeatures);
		}

	}

	@Override
	public void signOutGPGS() {
		if (amazonClient != null) {
			AmazonGamesClient.shutdown();
		}

	}

	@Override
	public void unlockIncrementalAchievementGPGS(String achievementId,
			int pasosDados) {
		// TODO Auto-generated method stub

	}

}
