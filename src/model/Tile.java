package model;

import java.util.Objects;

public class Tile {

	
	private int id;

	Position pos;
	  private Ladder ladder; // Reference to Ladder
	    private Snake snake;   // Reference to Snake
	    private QuestionTile questiontile;
	// Constructors
	public Tile() {
		super();
		this.id = id;
	}
	public Tile(int id) {
		super();
		this.id = id;
	}
	
	public Tile( int x, int y, Tile parent,Boolean visited) {
		super();
		pos=new Position(x, y);
	;
	}

	public Tile( int x, int y) {
		super();
		pos=new Position(x, y);
	}
	
	public Position getPos() {
		return pos;
	}
	public void setPos(Position pos) {
		this.pos = pos;
	}
	// Getter and Setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Ladder getLadder() {
        return ladder;
    }

    public void setLadder(Ladder ladder) {
        this.ladder = ladder;
    }

    // Getter and Setter for Snake
    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

	@Override
	public int hashCode() {
		return Objects.hash(id, pos);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		return id == other.id && Objects.equals(pos, other.pos);
	}
	public QuestionTile getQuestiontile() {
		return questiontile;
	}
	public void setQuestiontile(QuestionTile questiontile) {
		this.questiontile = questiontile;
	}
	



}