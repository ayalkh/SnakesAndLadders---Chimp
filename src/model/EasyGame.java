package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EasyGame {
    private Tile[][] board;
    private final int size = 7; // Easy level board size is 7x7
    private final Random random = new Random();

    public EasyGame() {
        this.board = new Tile[size][size];
        initializeBoard();
        placeSnakes();
        placeLadders();
        placeSpecialTiles(); // If you have question tiles or surprise tiles
        // Add other initialization as needed
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Tile(i, j); // Assuming your Tile constructor takes row and column as arguments
            }
        }
    }


    private void placeLadders() {
        // Place ladders of lengths 1, 2, 3, and 4 on the board
    }

    private void placeSpecialTiles() {
        // Place question tiles and surprise tiles, if any
    }
    private void placeSnakes() {
        Map<String, Integer> snakePositions = generateSnakePositions();
        for (Map.Entry<String, Integer> entry : snakePositions.entrySet()) {
            String color = entry.getKey();
            int startPosition = entry.getValue();
            int row = startPosition / size;
            int col = startPosition % size;
            int endPosition = calculateEndPosition(startPosition, color, size); // Implement this method
            int length = Math.abs(startPosition - endPosition);

            Sneak snake = new Sneak(startPosition, endPosition, color, length);
            board[row][col] = snake;
        }
    }
    private int calculateEndPosition(int startPosition, String color, int boardSize) {
        int rowLength = boardSize;
        int endPosition = startPosition; // Default

        switch (color.toLowerCase()) {
            case "yellow":
                endPosition = Math.max(1, startPosition - rowLength);
                break;
            case "green":
                endPosition = Math.max(1, startPosition - 2 * rowLength);
                break;
            case "blue":
                endPosition = Math.max(1, startPosition - 3 * rowLength);
                break;
            case "red":
                endPosition = 1; // Back to the start
                break;
            default:
                break;
        }
        return endPosition;
    }
    private Map<String, Integer> generateSnakePositions() {
        Map<String, Integer> positions = new HashMap<>();

        positions.put("yellow", getRandomPosition(1, size * size));
        positions.put("green", getRandomPosition(size + 1, size * size));
        positions.put("blue", getRandomPosition(2 * size + 1, size * size));
        positions.put("red", random.nextInt(size * size) + 1);

        return positions;
    }

    private int getRandomPosition(int min, int max) {
        int position;
        do {
            position = random.nextInt(max - min + 1) + min;
        } while (position % size == 0); // Avoid placing snakes on the last column
        return position;
    }


}
