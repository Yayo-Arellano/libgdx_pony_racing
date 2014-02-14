package com.tiarsoft.ponyrace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.tiarsoft.handlers.GameServicesHandler;
import com.tiarsoft.handlers.GoogleGameServicesHandler;

public class Achievements {

	GameServicesHandler gameServicesHandler;

	private final String prefName = "com.tiarsoft.ponyRace.achievements";
	private final Preferences prefAchiv = Gdx.app.getPreferences(prefName);

	boolean isGoogleGameServices = false;
	final String EASY;
	final String _18plus;
	final String BIG_LEAGES;
	final String _20_COOLER;
	final String FASTER_THAN_THE_MAKER;
	final String I_WORK_OUT;

	public Achievements(MainPonyRace game) {
		this.gameServicesHandler = game.gameServiceHandler;

		if (gameServicesHandler instanceof GoogleGameServicesHandler) {
			isGoogleGameServices = true;
			EASY = "CgkI55iksNMTEAIQCQ";
			_18plus = "CgkI55iksNMTEAIQEw";
			BIG_LEAGES = "CgkI55iksNMTEAIQFA";
			_20_COOLER = "CgkI55iksNMTEAIQFQ";
			FASTER_THAN_THE_MAKER = "CgkI55iksNMTEAIQKQ";
			I_WORK_OUT = "CgkI55iksNMTEAIQKg";

		}
		else {
			EASY = "easy";
			_18plus = "18plus";
			BIG_LEAGES = "bigleagues";
			_20_COOLER = "20Cooler";
			FASTER_THAN_THE_MAKER = "iworkout";
			I_WORK_OUT = "fasterThanTheMaker";
		}

		world_1_easy = prefAchiv.getBoolean("world_1_easy", false);
		world_1_normal = prefAchiv.getBoolean("world_1_normal", false);
		world_1_hard = prefAchiv.getBoolean("world_1_hard", false);
		world_1_superHard = prefAchiv.getBoolean("world_1_superHard", false);

		world_2_easy = prefAchiv.getBoolean("world_2_easy", false);
		world_2_normal = prefAchiv.getBoolean("world_2_normal", false);
		world_2_hard = prefAchiv.getBoolean("world_2_hard", false);
		world_2_superHard = prefAchiv.getBoolean("world_2_superHard", false);

		world_3_easy = prefAchiv.getBoolean("world_3_easy", false);
		world_3_normal = prefAchiv.getBoolean("world_3_normal", false);
		world_3_hard = prefAchiv.getBoolean("world_3_hard", false);
		world_3_superHard = prefAchiv.getBoolean("world_3_superHard", false);

		world_4_easy = prefAchiv.getBoolean("world_4_easy", false);
		world_4_normal = prefAchiv.getBoolean("world_4_normal", false);
		world_4_hard = prefAchiv.getBoolean("world_4_hard", false);
		world_4_superHard = prefAchiv.getBoolean("world_4_superHard", false);

		world_5_easy = prefAchiv.getBoolean("world_5_easy", false);
		world_5_normal = prefAchiv.getBoolean("world_5_normal", false);
		world_5_hard = prefAchiv.getBoolean("world_5_hard", false);
		world_5_superHard = prefAchiv.getBoolean("world_5_superHard", false);

		world_6_easy = prefAchiv.getBoolean("world_6_easy", false);
		world_6_normal = prefAchiv.getBoolean("world_6_normal", false);
		world_6_hard = prefAchiv.getBoolean("world_6_hard", false);
		world_6_superHard = prefAchiv.getBoolean("world_6_superHard", false);

		world_7_easy = prefAchiv.getBoolean("world_7_easy", false);
		world_7_normal = prefAchiv.getBoolean("world_7_normal", false);
		world_7_hard = prefAchiv.getBoolean("world_7_hard", false);
		world_7_superHard = prefAchiv.getBoolean("world_7_superHard", false);

		world_8_easy = prefAchiv.getBoolean("world_8_easy", false);
		world_8_normal = prefAchiv.getBoolean("world_8_normal", false);
		world_8_hard = prefAchiv.getBoolean("world_8_hard", false);
		world_8_superHard = prefAchiv.getBoolean("world_8_superHard", false);

		world_1_15Secs = prefAchiv.getBoolean("world_1_15Secs", false);
		world_2_15Secs = prefAchiv.getBoolean("world_2_15Secs", false);
		world_3_15Secs = prefAchiv.getBoolean("world_3_15Secs", false);
		world_4_15Secs = prefAchiv.getBoolean("world_4_15Secs", false);
		world_5_15Secs = prefAchiv.getBoolean("world_5_15Secs", false);
		world_6_15Secs = prefAchiv.getBoolean("world_6_15Secs", false);
		world_7_15Secs = prefAchiv.getBoolean("world_7_15Secs", false);
		world_8_15Secs = prefAchiv.getBoolean("world_8_15Secs", false);

	}

	public void checkHitBombAchievements() {
		if (!isGoogleGameServices)
			return;
		gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQDw", 1);// Did someone said bombs?
		gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQEA", 1);// Rain Boom Pony
		gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQEQ", 1);// Bomb - A - Tron
	}

	public void checkHitSpikeAchievements() {
		if (!isGoogleGameServices)
			return;
		gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQFg", 1);// Hold On!
		gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQFw", 1);// Trap Master
		gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQGQ", 1);// You will never catch me!
	}

	public void checkEatChocolateAchievements(int duclesTomadosLevel) {
		if (!isGoogleGameServices)
			return;
		if (duclesTomadosLevel <= 0)
			return;
		gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQGg", duclesTomadosLevel);// Chocolate Taster
		gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQGw", duclesTomadosLevel);// Sugar Rush
		gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQHA", duclesTomadosLevel);// i might need a diet
	}

	public void checkWinFirstPlaceAchievements() {
		if (!isGoogleGameServices)
			return;
		gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQHw", 1);// Need for Speed
		gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQIA", 1);// Speed Demon
		gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQIQ", 1);// Racing Blood
		gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQIg", 1);// Sure you love this game!

	}

	public void checkEatChiliAchievements(int chilesTomadosLevel) {
		if (!isGoogleGameServices)
			return;
		if (chilesTomadosLevel <= 0)
			return;
		gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQIw", chilesTomadosLevel);// 18 Spicy
		gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQJA", chilesTomadosLevel);// 19 Breath of fire
		gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQJQ", chilesTomadosLevel);// 20 Iron pony
	}

	public void checkMonedasAchivments(int monedasTomadasLevel) {
		if (!isGoogleGameServices)
			return;
		if (monedasTomadasLevel <= 0)
			return;
		if (Settings.statMonedasTotal < 1000)
			gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQCg", monedasTomadasLevel);// 21 You got any spare change?

		if (Settings.statMonedasTotal < 2000)
			gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQCw", monedasTomadasLevel);// 22 Got my mind on my money

		if (Settings.statMonedasTotal < 12000)
			gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQDA", monedasTomadasLevel);// 23 My name is not Filthy Rich

		int auxMonedasTomadas = monedasTomadasLevel;
		int steps = 0;
		// Primero mando updates para el cienMil;
		while (auxMonedasTomadas >= 10) {
			// Cada 10 monedas mando un updates para que sean 10 * 10,000 = 100 mil monedas
			steps++;
			auxMonedasTomadas -= 10;
		}
		if (steps > 0)
			gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQDQ", steps);// 24 A Lot of Bits!

		auxMonedasTomadas = monedasTomadasLevel;
		steps = 0;
		while (auxMonedasTomadas >= 100) {
			// Cada 100 monedas mando un updates para que sean 100 * 10,000 = 1millon de monedas
			steps++;
			auxMonedasTomadas -= 100;
		}
		if (steps > 0)
			gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQDg", steps);// 25 One in a Million!
	}

	public void checkGetBallonsAchievements(int globosTomadosLevel) {
		if (!isGoogleGameServices)
			return;
		if (globosTomadosLevel <= 0)
			return;
		gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQJg", globosTomadosLevel);// 26 Time Seeker
		gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQJw", globosTomadosLevel);// 27 Time to Spare
		gameServicesHandler.unlockIncrementalAchievementGPGS("CgkI55iksNMTEAIQKA", globosTomadosLevel);// 28 Time Agent
	}

	// ------
	private boolean world_1_15Secs = false;
	private boolean world_2_15Secs = false;
	private boolean world_3_15Secs = false;
	private boolean world_4_15Secs = false;
	private boolean world_5_15Secs = false;
	private boolean world_6_15Secs = false;
	private boolean world_7_15Secs = false;
	private boolean world_8_15Secs = false;

	public void checkVictoryMoreThan15Secs(int nivelTiled, float timeLeft) {
		if (timeLeft >= 15) {
			switch (nivelTiled) {
				case 1:
					prefAchiv.putBoolean("world_1_15Secs", world_1_15Secs);
					break;
				case 2:
					prefAchiv.putBoolean("world_2_15Secs", world_2_15Secs);
					break;
				case 3:
					prefAchiv.putBoolean("world_3_15Secs", world_3_15Secs);
					break;
				case 4:
					prefAchiv.putBoolean("world_4_15Secs", world_4_15Secs);
					break;
				case 5:
					prefAchiv.putBoolean("world_5_15Secs", world_5_15Secs);
					break;
				case 6:
					prefAchiv.putBoolean("world_6_15Secs", world_6_15Secs);
					break;
				case 7:
					prefAchiv.putBoolean("world_7_15Secs", world_7_15Secs);
					break;
				case 8:
					prefAchiv.putBoolean("world_8_15Secs", world_8_15Secs);
					break;

			}
			prefAchiv.flush();
		}

		if (world_1_15Secs && world_2_15Secs && world_3_15Secs && world_4_15Secs && world_5_15Secs && world_6_15Secs && world_7_15Secs && world_8_15Secs) {
			gameServicesHandler.unlockAchievement(FASTER_THAN_THE_MAKER);// 29 Faster than the maker!
		}
	}

	public void checkUpgradesAchivmentes() {
		if (Settings.nivelChocolate >= 5 && Settings.nivelChili >= 5 && Settings.nivelBallon >= 5) {
			gameServicesHandler.unlockAchievement(I_WORK_OUT);// 30 I work out!
		}
	}

	/**
	 * En esta parte voy a revisar que cada mundo se alla pasado en todas las dificultades para liberar el achivment
	 * 
	 * @param nivelTiled
	 */

	private boolean world_1_easy = false;
	private boolean world_1_normal = false;
	private boolean world_1_hard = false;
	private boolean world_1_superHard = false;

	private boolean world_2_easy = false;
	private boolean world_2_normal = false;
	private boolean world_2_hard = false;
	private boolean world_2_superHard = false;

	private boolean world_3_easy = false;
	private boolean world_3_normal = false;
	private boolean world_3_hard = false;
	private boolean world_3_superHard = false;

	private boolean world_4_easy = false;
	private boolean world_4_normal = false;
	private boolean world_4_hard = false;
	private boolean world_4_superHard = false;

	private boolean world_5_easy = false;
	private boolean world_5_normal = false;
	private boolean world_5_hard = false;
	private boolean world_5_superHard = false;

	private boolean world_6_easy = false;
	private boolean world_6_normal = false;
	private boolean world_6_hard = false;
	private boolean world_6_superHard = false;

	private boolean world_7_easy = false;
	private boolean world_7_normal = false;
	private boolean world_7_hard = false;
	private boolean world_7_superHard = false;

	private boolean world_8_easy = false;
	private boolean world_8_normal = false;
	private boolean world_8_hard = false;
	private boolean world_8_superHard = false;

	public void checkWorldComplete(int nivelTiled) {
		switch (nivelTiled) {
			case 1:
				switch (Settings.dificultadActual) {
					case Settings.DIFICULTAD_EASY:
						world_1_easy = true;
						break;
					case Settings.DIFICULTAD_NORMAL:
						world_1_normal = true;
						break;
					case Settings.DIFICULTAD_HARD:
						world_1_hard = true;
						break;
					case Settings.DIFICULTAD_SUPERHARD:
						world_1_superHard = true;
						break;
				}
				break;
			case 2:
				switch (Settings.dificultadActual) {
					case Settings.DIFICULTAD_EASY:
						world_2_easy = true;
						break;
					case Settings.DIFICULTAD_NORMAL:
						world_2_normal = true;
						break;
					case Settings.DIFICULTAD_HARD:
						world_2_hard = true;
						break;
					case Settings.DIFICULTAD_SUPERHARD:
						world_2_superHard = true;
						break;
				}
				break;
			case 3:
				switch (Settings.dificultadActual) {
					case Settings.DIFICULTAD_EASY:
						world_3_easy = true;
						break;
					case Settings.DIFICULTAD_NORMAL:
						world_3_normal = true;
						break;
					case Settings.DIFICULTAD_HARD:
						world_3_hard = true;
						break;
					case Settings.DIFICULTAD_SUPERHARD:
						world_3_superHard = true;
						break;
				}
				break;
			case 4:
				switch (Settings.dificultadActual) {
					case Settings.DIFICULTAD_EASY:
						world_4_easy = true;
						break;
					case Settings.DIFICULTAD_NORMAL:
						world_4_normal = true;
						break;
					case Settings.DIFICULTAD_HARD:
						world_4_hard = true;
						break;
					case Settings.DIFICULTAD_SUPERHARD:
						world_4_superHard = true;
						break;
				}
				break;
			case 5:
				switch (Settings.dificultadActual) {
					case Settings.DIFICULTAD_EASY:
						world_5_easy = true;
						break;
					case Settings.DIFICULTAD_NORMAL:
						world_5_normal = true;
						break;
					case Settings.DIFICULTAD_HARD:
						world_5_hard = true;
						break;
					case Settings.DIFICULTAD_SUPERHARD:
						world_5_superHard = true;
						break;
				}
				break;
			case 6:
				switch (Settings.dificultadActual) {
					case Settings.DIFICULTAD_EASY:
						world_6_easy = true;
						break;
					case Settings.DIFICULTAD_NORMAL:
						world_6_normal = true;
						break;
					case Settings.DIFICULTAD_HARD:
						world_6_hard = true;
						break;
					case Settings.DIFICULTAD_SUPERHARD:
						world_6_superHard = true;
						break;
				}
				break;
			case 7:
				switch (Settings.dificultadActual) {
					case Settings.DIFICULTAD_EASY:
						world_7_easy = true;
						break;
					case Settings.DIFICULTAD_NORMAL:
						world_7_normal = true;
						break;
					case Settings.DIFICULTAD_HARD:
						world_7_hard = true;
						break;
					case Settings.DIFICULTAD_SUPERHARD:
						world_7_superHard = true;
						break;
				}
				break;
			case 8:
				switch (Settings.dificultadActual) {
					case Settings.DIFICULTAD_EASY:
						world_8_easy = true;
						break;
					case Settings.DIFICULTAD_NORMAL:
						world_8_normal = true;
						break;
					case Settings.DIFICULTAD_HARD:
						world_8_hard = true;
						break;
					case Settings.DIFICULTAD_SUPERHARD:
						world_8_superHard = true;
						break;
				}
				break;
		}

		prefAchiv.putBoolean("world_1_easy", world_1_easy);
		prefAchiv.putBoolean("world_1_normal", world_1_normal);
		prefAchiv.putBoolean("world_1_hard", world_1_hard);
		prefAchiv.putBoolean("world_1_superHard", world_1_superHard);

		prefAchiv.putBoolean("world_2_easy", world_2_easy);
		prefAchiv.putBoolean("world_2_normal", world_2_normal);
		prefAchiv.putBoolean("world_2_hard", world_2_hard);
		prefAchiv.putBoolean("world_2_superHard", world_2_superHard);

		prefAchiv.putBoolean("world_3_easy", world_3_easy);
		prefAchiv.putBoolean("world_3_normal", world_3_normal);
		prefAchiv.putBoolean("world_3_hard", world_3_hard);
		prefAchiv.putBoolean("world_3_superHard", world_3_superHard);

		prefAchiv.putBoolean("world_4_easy", world_4_easy);
		prefAchiv.putBoolean("world_4_normal", world_4_normal);
		prefAchiv.putBoolean("world_4_hard", world_4_hard);
		prefAchiv.putBoolean("world_4_superHard", world_4_superHard);

		prefAchiv.putBoolean("world_5_easy", world_5_easy);
		prefAchiv.putBoolean("world_5_normal", world_5_normal);
		prefAchiv.putBoolean("world_5_hard", world_5_hard);
		prefAchiv.putBoolean("world_5_superHard", world_5_superHard);

		prefAchiv.putBoolean("world_6_easy", world_6_easy);
		prefAchiv.putBoolean("world_6_normal", world_6_normal);
		prefAchiv.putBoolean("world_6_hard", world_6_hard);
		prefAchiv.putBoolean("world_6_superHard", world_6_superHard);

		prefAchiv.putBoolean("world_7_easy", world_7_easy);
		prefAchiv.putBoolean("world_7_normal", world_7_normal);
		prefAchiv.putBoolean("world_7_hard", world_7_hard);
		prefAchiv.putBoolean("world_7_superHard", world_7_superHard);

		prefAchiv.putBoolean("world_8_easy", world_8_easy);
		prefAchiv.putBoolean("world_8_normal", world_8_normal);
		prefAchiv.putBoolean("world_8_hard", world_8_hard);
		prefAchiv.putBoolean("world_8_superHard", world_8_superHard);
		prefAchiv.flush();

		// Ahora si checo los achivmentes
		switch (Settings.dificultadActual) {
			case Settings.DIFICULTAD_EASY:
				if (world_1_easy && world_2_easy && world_3_easy && world_4_easy && world_5_easy && world_6_easy && world_7_easy && world_8_easy) {
					gameServicesHandler.unlockAchievement(EASY); // Easy
				}
				break;
			case Settings.DIFICULTAD_NORMAL:
				if (world_1_normal && world_2_normal && world_3_normal && world_4_normal && world_5_normal && world_6_normal && world_7_normal && world_8_normal) {
					gameServicesHandler.unlockAchievement(_18plus); // 18+
				}
				break;
			case Settings.DIFICULTAD_HARD:
				if (world_1_hard && world_2_hard && world_3_hard && world_4_hard && world_5_hard && world_6_hard && world_7_hard && world_8_hard) {
					gameServicesHandler.unlockAchievement(BIG_LEAGES);// Big leagues
				}
				break;
			case Settings.DIFICULTAD_SUPERHARD:
				if (world_1_superHard && world_2_superHard && world_3_superHard && world_4_superHard && world_5_superHard && world_6_superHard && world_7_superHard && world_8_superHard) {
					gameServicesHandler.unlockAchievement(_20_COOLER);// 20% Cooler
				}
				break;
		}

	}

}
