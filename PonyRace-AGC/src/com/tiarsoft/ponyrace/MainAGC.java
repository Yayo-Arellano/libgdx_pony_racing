package com.tiarsoft.ponyrace;

import android.os.Bundle;

import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmAchievement;
import com.swarmconnect.SwarmLeaderboard;
import com.tiarsoft.handlers.AmazonGameServicesHandler;

public class MainAGC extends MainActivityFacebook implements
		AmazonGameServicesHandler {

	String swarmAPPKey = "ef623e8c86500bb583e2606cceb963d3";
	int swarmAppID = 11154;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		game = new MainPonyRace(tienda, this, this);
		super.onCreate(savedInstanceState);
		Swarm.setActive(this);
		Swarm.enableAlternativeMarketCompatability();

		if (Swarm.isEnabled()) {
			Swarm.init(this, 11154, swarmAPPKey);
		}

	}

	public void onResume() {
		super.onResume();
		Swarm.setActive(this);
	}

	public void onPause() {
		super.onPause();
		Swarm.setInactive(this);
	}

	@Override
	public void submitScore(float tiempoLap, String leaderBoard) {
		int id = Integer.parseInt(leaderBoard);
		SwarmLeaderboard.submitScore(id, tiempoLap);

	}

	@Override
	public void unlockAchievement(String achievementId) {
		int id = Integer.parseInt(achievementId);
		SwarmAchievement.unlock(id);

	}

	@Override
	public void getLeaderboard() {
		Swarm.showLeaderboards();

	}

	@Override
	public void getAchievements() {
		Swarm.showAchievements();

	}

	@Override
	public boolean isSignedIn() {
		return Swarm.isLoggedIn();

	}

	@Override
	public void signIn() {
		Swarm.init(this, 11154, swarmAPPKey);

	}

	@Override
	public void signOut() {
		Swarm.logOut();

	}

	@Override
	public void unlockIncrementalAchievement(String achievementId,
			int pasosDados) {
		// TODO Auto-generated method stub

	}

}
