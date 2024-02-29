package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javafx.scene.image.ImageView;

public class EasyGame {
	 private List<QuestionTile> questions=new ArrayList<>();
	 private int numberofplayers;
	    private List<Player> gameplayers = new ArrayList<>();
    private Tile[][] board;
    private final int size = 7; // Easy level board size is 7x7
    private final Random random = new Random();
    private Map<Integer, Snake> snakesMap = new HashMap<>();
    private Map<Integer, Ladder> laddersMap = new HashMap<>(); 

    public EasyGame() {
        this.board = new Tile[size][size];
        initializeBoard();
        placeSnakes();
        placeLadders();
        placeSpecialTiles();
        placequestions();// If you have question tiles or surprise tiles
        // Add other initialization as needed
    }
    public int getNumberofplayers() {
		return numberofplayers;
	}

	public void setNumberofplayers(int numberofplayers) {
		this.numberofplayers = numberofplayers;
	}

	public List<Player> getGameplayers() {
		return gameplayers;
	}

	public void setGameplayers(List<Player> gameplayers) {
		this.gameplayers = gameplayers;
	}
    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Tile(i, j); // Assuming your Tile constructor takes row and column as arguments
            }
        }
    }

	private void placeLadders() {
	    laddersMap.clear();
	    int[] ladderLengths = {4,3,2,1}; // Ladder lengths
	    int maxPosition = size * size; // Maximum position on the board
	    

	    for (int length : ladderLengths) {
	        boolean placed = false;
	        int attempts = 0;

	        while (!placed && attempts < 1000) {
	            attempts++;
	            int startPosition = getRandomLadderStartPosition(length);
	            if (startPosition == -1) {
	                System.out.println("Unable to find a start position for ladder length " + length);
	                break;
	            }

	            int endPosition = startPosition + (length - 1) * size;
	            if (endPosition > maxPosition) {
	                continue; // End position is off the board, retry
	            }

	            if (isPositionOverlapFree(startPosition, endPosition)) {
	                Ladder ladder = new Ladder(startPosition, endPosition, length);
	                laddersMap.put(startPosition, ladder);
	                placed = true;
	            }
	        }
	    }
	}

	private boolean isSnakeTile(int position) {
		// Check if the position is the head or the tail of any snake
		if (snakesMap.containsKey(position)) {
			return true; // The position is a head of a snake
		}
		for (Snake snake : snakesMap.values()) {
			if (snake.getEndPosition() == position) {
				return true; // The position is a tail of a snake
			}
		}
		return false;
	}

	private boolean isPositionOverlapFree(int startPosition, int endPosition) {
		for (int position = startPosition; position <= endPosition; position++) {
			// Log the checked position and if it contains a snake or ladder
			System.out.println("Checking position " + position + " for overlap.");
			if (snakesMap.containsKey(position)) {
				System.out.println("Overlap with snake at position " + position);
				return false;
			}
			if (laddersMap.containsKey(position)) {
				System.out.println("Overlap with ladder at position " + position);
				return false;
			}
		}
		System.out.println("No overlap found for positions from " + startPosition + " to " + endPosition);
		return true;
	}

	private int getRow(int position) {
		return (position - 1) / size;
	}

	private int getColumn(int position) {
		return calculateColumn(position, size);
	}

	private int calculateColumn(int position, int size) {
		int row = getRow(position);
		int column = (position - 1) % size;
		// If the row is odd (considering 0 as the first row), then we reverse the
		// column order.
		if (row % 2 == 1) {
			column = (size - 1) - column;
		}
		return column;
	}

	private int getRandomLadderStartPosition(int ladderLength) {
	    int maxRowForStart = size - ladderLength; // Maximum row where a ladder can start
	    int startPosition;
	    int row, column;

	    int attempts = 0; // Track the number of attempts to prevent infinite loops

	    do {
	        int maxPosition = (maxRowForStart * size) - 1; // Maximum board position for a ladder to start
	        startPosition = random.nextInt(maxPosition) + 1; // Generate a random start position

	        row = getRow(startPosition);
	        column = getColumn(startPosition);

	        System.out.println("Random start position generated: " + startPosition);
	        attempts++;

	        if (attempts > 1000) {
	            System.out.println("Too many attempts to find a valid ladder start position. Exiting...");
	            return -1; // Return an invalid position to indicate failure
	        }

	        // Determine end position of the ladder
	        int endPosition = startPosition + ladderLength * size;

	        // Verify the end position does not exceed the board size
	        if (endPosition > size * size) {
	            continue; // If it does, continue to find a new start position
	        }

	    } while (column == size - 1 || // Ladder cannot start at the right edge of the board
	            isSnakeTile(startPosition) || // Ladder cannot start on a snake tile
	            isSnakeTile(startPosition + (ladderLength * size)) || // Ladder cannot end on a snake tile
	            !isPositionOverlapFree(startPosition, startPosition + (ladderLength * size)) // Entire ladder span must be free of snakes and ladders
	    );

	    return startPosition;
	}
	private void placeSpecialTiles() {
		// Place question tiles and surprise tiles, if any
	}

	private void placeSnakes() {
		snakesMap.clear();
		Map<String, Integer> snakePositions = generateSnakePositions();
		System.out.println(snakePositions.entrySet());

		for (Map.Entry<String, Integer> entry : snakePositions.entrySet()) {
			String color = entry.getKey();
			int startPosition = entry.getValue();
			int endPosition = calculateEndPosition(startPosition, color);

			// Ensure endPosition is not outside the board
			if (endPosition <= 0) {
				endPosition = 1;
			}

			Snake snake = new Snake(startPosition, endPosition, color, Math.abs(startPosition - endPosition));
			snakesMap.put(startPosition, snake);
			int row = (startPosition - 1) / size; // Adjusted for zero indexing
			int col = (startPosition - 1) % size; // Adjusted for zero indexing
			board[row][col].setSnake(snake); // Set snake on the tile
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

		positions.put("green", getRandomPosition(2 * size, maxPosition, occupiedPositions));
		occupiedPositions.add(positions.get("green"));

		positions.put("blue", getRandomPosition(3 * size, maxPosition, occupiedPositions));
		occupiedPositions.add(positions.get("blue"));

		// Generate a list of all possible positions
		Set<Integer> allPositions = new HashSet<>();
		for (int i = 2; i <= maxPosition; i++) { // Start from 2 to avoid the first position
			allPositions.add(i);
		}

		// Remove occupied positions
		allPositions.removeAll(occupiedPositions);

		// The red snake always starts from a random position that is not an occupied
		// position
		int redSnakePosition = getRandomPositionFromSet(allPositions);
		positions.put("red", redSnakePosition);
		occupiedPositions.add(redSnakePosition);

		return positions;
	}

	private int getRandomPositionFromSet(Set<Integer> availablePositions) {
		int index = random.nextInt(availablePositions.size());
		Iterator<Integer> iter = availablePositions.iterator();
		for (int i = 0; i < index; i++) {
			iter.next();
		}
		return iter.next();
	}

	private int getRandomPosition(int min, int max, Set<Integer> occupiedPositions) {
		int position;
		do {
			position = random.nextInt(max - min + 1) + min;
		} while (occupiedPositions.contains(position) || position % size == 0);
		return position;
	}

	public Map<Integer, Ladder> getLaddersMap() {
		return laddersMap;
	}

	public void setLaddersMap(Map<Integer, Ladder> laddersMap) {
		this.laddersMap = laddersMap;
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
	private void placequestions() {
    	questions.clear();
    	
    	Set<Integer> occupiedPositions = new HashSet<>();
    	int maxPosition=size*size;
    	int i=0;
       while(i<4) {
    	 QuestionTile QT = new QuestionTile(getRandomQuestion(), getRandomPosition(size,maxPosition,occupiedPositions));
    	 if (!(snakesMap.containsKey(QT.getPosition()) ||
                 snakesMap.containsKey(QT.getPosition()) ||
                 laddersMap.containsKey(QT.getPosition()))){
    		 questions.add(QT);
    	 occupiedPositions.add(questions.get(i).getPosition());
    	 int row = (QT.getPosition() ) / size;
         int col = (QT.getPosition() ) % size;
         board[row][col].setQuestiontile(QT); // Set snake on the tile
    	 i++;
    	 }
    	 
    	 
       }
       

           
  
        }
    
 

	public List<QuestionTile> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionTile> questions) {
		this.questions = questions;
	}

	private Question getRandomQuestion() {
		
		return null;
	}


}
