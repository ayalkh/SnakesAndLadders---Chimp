package model;

import java.util.Objects;

public class Tile {

	private static int idCounter=1 ;
	private int id;

	Position pos;

	// Constructors
	public Tile() {
		super();
		this.id = idCounter++;
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

	
	public static int getIdCounter() {
		return idCounter;
	}
	public static void setIdCounter(int idCounter) {
		Tile.idCounter = idCounter;
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
	



}
