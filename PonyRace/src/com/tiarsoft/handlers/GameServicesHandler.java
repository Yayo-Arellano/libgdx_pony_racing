package com.tiarsoft.handlers;

public interface GameServicesHandler {

	/**
	 * Este metodo abstrae a GPGS o a AGC
	 * 
	 * @param tiempoLap
	 */
	public void submitScore(float tiempoLap, String leaderBoard);

	/**
	 * Este metodo abstrae a GPGS o a AGC
	 * 
	 * @param score
	 */
	public void unlockAchievement(String achievementId);

	/**
	 * Este metodo abstrae a GPGS o a AGC
	 * 
	 * @param score
	 */
	public void getLeaderboard();

	/**
	 * Este metodo abstrae a GPGS o a AGC
	 * 
	 * @param score
	 */
	public void getAchievements();

	public boolean isSignedInGPGS();

	public void signInGPGS();

	public void signOutGPGS();

	public void unlockIncrementalAchievementGPGS(String achievementId, int pasosDados);

}
