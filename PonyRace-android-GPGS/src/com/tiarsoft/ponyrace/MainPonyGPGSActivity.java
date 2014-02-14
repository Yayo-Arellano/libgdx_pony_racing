package com.tiarsoft.ponyrace;

import android.content.Intent;
import android.os.Bundle;

import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.tiarsoft.handlers.GoogleGameServicesHandler;

public class MainPonyGPGSActivity extends MainActivity implements GameHelperListener, GoogleGameServicesHandler {

	private GameHelper gameHelper;

	public MainPonyGPGSActivity() {
		gameHelper = new GameHelper(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		game = new MainPonyRace(tienda, this, this);
		super.onCreate(savedInstanceState);
		gameHelper.setup(this);

	}

	@Override
	public void onStart() {
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		gameHelper.onStop();
	}

	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		gameHelper.onActivityResult(request, response, data);
	}

	@Override
	public void submitScore(float tiempoLap, String leaderBoard) {
		if (!isSignedInGPGS())// por si no esta logeado
			return;
		long tiempoLong = (long) (tiempoLap * 100.0f);// Para recorrer el punto 2 numeros a la izq, y poder enviar un lingo

		gameHelper.getGamesClient().submitScore(leaderBoard, tiempoLong);

	}

	@Override
	public void unlockAchievement(String achievementId) {
		if (!isSignedInGPGS())// por si no esta logeado
			return;
		gameHelper.getGamesClient().unlockAchievement(achievementId);

	}

	@Override
	public void getLeaderboard() {
		startActivityForResult(gameHelper.getGamesClient().getAllLeaderboardsIntent(), 100);

	}

	@Override
	public void getAchievements() {
		startActivityForResult(gameHelper.getGamesClient().getAchievementsIntent(), 101);

	}

	@Override
	public boolean isSignedInGPGS() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void signInGPGS() {
		try {
			runOnUiThread(new Runnable() {
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (final Exception ex) {
		}
	}

	@Override
	public void signOutGPGS() {
		try {
			runOnUiThread(new Runnable() {
				public void run() {
					gameHelper.signOut();
				}
			});
		}
		catch (final Exception ex) {
		}

	}

	@Override
	public void unlockIncrementalAchievementGPGS(String achievementId, int pasosDados) {
		if (!isSignedInGPGS())// por si no esta logeado
			return;
		gameHelper.getGamesClient().incrementAchievement(achievementId, pasosDados);

	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub

	}

}
