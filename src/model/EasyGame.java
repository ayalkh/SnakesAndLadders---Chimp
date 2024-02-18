package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class EasyGame {
    private Tile[][] board;
    private final int size = 7; // Easy level board size is 7x7
    private final Random random = new Random();
    private Map<Integer, Snake> snakesMap = new HashMap<>();

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
        snakesMap.clear();
        Map<String, Integer> snakePositions = generateSnakePositions();

        for (Map.Entry<String, Integer> entry : snakePositions.entrySet()) {
            String color = entry.getKey();
            int startPosition = entry.getValue();
            int endPosition = calculateEndPosition(startPosition, color);

            Snake snake = new Snake(startPosition, endPosition, color, Math.abs(startPosition - endPosition));
            snakesMap.put(startPosition, snake);
            // Update the board with the snake - convert startPosition to row & column if necessary
        }
    }
    private int calculateEndPosition(int startPosition, String color) {
        int endPosition = startPosition; // Default

        switch (color.toLowerCase()) {
            case "yellow":
                endPosition -= size; // Move back one row
                break;
            case "green":
                endPosition -= 2 * size; // Move back two rows
                break;
            case "blue":
                endPosition -= 3 * size; // Move back three rows
                break;
            case "red":
                endPosition = 1; // Back to start
                break;
        }
        return Math.max(1, endPosition); // Ensure end position is not less than 1
    }
    private Map<String, Integer> generateSnakePositions() {
        Map<String, Integer> positions = new HashMap<>();
        Set<Integer> occupiedPositions = new HashSet<>();

        // Assume the board has 'size * size' number of squares
        int maxPosition = size * size;

        // Generate positions for each snake, ensuring no overlapping
        positions.put("yellow", getRandomPosition(size, maxPosition, occupiedPositions));
        occupiedPositions.add(positions.get("yellow"));
        
        positions.put("green", getRandomPosition(2*size, maxPosition, occupiedPositions));
        occupiedPositions.add(positions.get("green"));
        
        positions.put("blue", getRandomPosition(3*size, maxPosition, occupiedPositions));
        occupiedPositions.add(positions.get("blue"));
        
        positions.put("red", getRandomPosition(1, maxPosition, occupiedPositions));
        occupiedPositions.add(positions.get("red"));

        return positions;
    }
    private int getRandomPosition(int min, int max, Set<Integer> occupiedPositions) {
        int position;
        do {
            position = random.nextInt(max - min + 1) + min;
        } while (occupiedPositions.contains(position) || position % size == 0);
        return position;
    }


	public Tile[][] getBoard() {
		return board;
	}

	public void setBoard(Tile[][] board) {
		this.board = board;
	}

	public int getSize() {
		return size;
	}

	public Random getRandom() {
		return random;
	}

	public Map<Integer, Snake> getSnakesMap() {
		return snakesMap;
	}

	public void setSnakesMap(Map<Integer, Snake> snakesMap) {
		this.snakesMap = snakesMap;
	}
	


}
