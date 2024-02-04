package model;

import java.util.Random;

public class Dice {
	private int[] faces;
    private int currentRollResult;

    public Dice(int[] faces) {
        if (faces == null || faces.length == 0) {
            throw new IllegalArgumentException("Faces array must not be null or empty.");
        }
        this.faces = faces;
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
