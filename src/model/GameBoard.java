package model;

import java.util.*;

public abstract class GameBoard {
    protected Tile[][] board;
    protected int size; // Varies by game difficulty
    protected Random random = new Random();
    protected List<Player> gamePlayers = new ArrayList<>();
    protected Map<Integer, Snake> snakesMap = new HashMap<>();
    protected Map<Integer, Ladder> laddersMap = new HashMap<>();
    protected List<QuestionTile> questions = new ArrayList<>();

    public GameBoard(int size) {
        this.size = size;
        this.board = new Tile[size][size];
    }

    public GameBoard() {
       
    }

    protected void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Tile(i, j);
            }
        }
    }

    protected abstract void placeSnakes();
    protected abstract void placeLadders();
    protected abstract void placequestions();
    
   
    // Other common methods and abstract methods as necessary

	protected void placeSnakes1() {
		// TODO Auto-generated method stub
		
	}

	protected void placeLadders1() {
		// TODO Auto-generated method stub
		
	}
}
