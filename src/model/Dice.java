package model;

import java.util.Random;

public class Dice {
	// private int[] faces;
	private int currentRollResult;
	private GameLevel gameLevel;
	private Random random = new Random();

	public Dice(GameLevel gameLevel) {
		this.gameLevel = gameLevel;
		// initializeFaces();
	}

//	private void initializeFaces() {
//		switch (gameLevel) {
//		case EASY:
//			faces = new int[] { 1, 2, 3, 4, 5 }; // 0-4 for steps, 5 for a question
//			break;
//		case MEDIUM:
//			faces = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 }; // 0-6 for steps, 7-8 for questions
//			break;
//		case HARD:
//			faces = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 }; // 0-6 for steps, 7-9 for questions
//			break;
//		default:
//			throw new IllegalArgumentException("Invalid game level.");
//		}
//	}

	public int rollDice() {
		if (this.gameLevel.equals(GameLevel.EASY)) {
			currentRollResult = random.nextInt(8) + 1;
		} else if (this.gameLevel.equals(GameLevel.MEDIUM)) {
			currentRollResult = random.nextInt(4) + 1;

		} else
			currentRollResult = random.nextInt(4) + 1;

		return currentRollResult;
	}

	public int getCurrentRollResult() {
		return currentRollResult;
	}
}
