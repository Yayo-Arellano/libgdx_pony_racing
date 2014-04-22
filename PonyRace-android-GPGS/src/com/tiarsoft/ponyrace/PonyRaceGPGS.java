package com.tiarsoft.ponyrace;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.tiarsoft.handlers.GoogleGameServicesHandler;

public class PonyRaceGPGS extends MainActivityFacebook implements
		GameHelperListener, GoogleGameServicesHandler {

	private GameHelper gameHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		game = new MainPonyRace(tienda, this, this);
		super.onCreate(savedInstanceState);
		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
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

		Games.Leaderboards.submitScore(gameHelper.getApiClient(), leaderBoard,
				tiempoLong);
	}

	@Override
	public void unlockAchievement(String achievementId) {
		if (!isSignedInGPGS())// por si no esta logeado
			return;
		Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);

	}

	@Override
	public void getLeaderboard() {
		startActivityForResult(
				Games.Leaderboards.getAllLeaderboardsIntent(gameHelper
						.getApiClient()), 100);

	}

	@Override
	public void getAchievements() {
		startActivityForResult(
				Games.Achievements.getAchievementsIntent(gameHelper
						.getApiClient()), 101);

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
	public void unlockIncrementalAchievementGPGS(String achievementId,
			int pasosDados) {
		if (!isSignedInGPGS())// por si no esta logeado
			return;
		Games.Achievements.increment(gameHelper.getApiClient(), achievementId,
				pasosDados);

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
