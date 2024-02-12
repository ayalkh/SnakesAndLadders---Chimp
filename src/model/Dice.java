package model;

import java.util.Random;

public class Dice {
	private int[] faces;
	private int currentRollResult;
	private GameLevel gameLevel;

	public Dice(GameLevel gameLevel) {
		this.gameLevel = gameLevel;
		initializeFaces();
	}

	private void initializeFaces() {
		switch (gameLevel) {
		case EASY:
			faces = new int[] { 0, 1, 2, 3, 4, 5 }; // 0-4 for steps, 5 for a question
			break;
		case MEDIUM:
			faces = new int[] { 0, 1, 2, 3, 4, 5, 6, 7 }; // 0-6 for steps, 7-8 for questions
			break;
		case HARD:
			faces = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }; // 0-6 for steps, 7-9 for questions
			break;
		default:
			throw new IllegalArgumentException("Invalid game level.");
		}
	}

	public int rollDice() {
		Random random = new Random();
		int randomIndex = random.nextInt(faces.length);
		currentRollResult = faces[randomIndex];
		return currentRollResult;
	}

	public int getCurrentRollResult() {
		return currentRollResult;
	}
}
