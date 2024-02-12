package model;

import java.util.Objects;

public class Sneak extends Tile {
private int startPosition;
    private int endPosition;
    private String color;
    private int length;
	public Sneak(int startPosition, int endPosition, String color, int length) {
		super();
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.color = color;
		this.length = length;
		
	}
	
	

	
	public int getStartPosition() {
		return startPosition;
	}
	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}
	public int getEndPosition() {
		return endPosition;
	}
	public void setEndPosition(int endPosition) {
		this.endPosition = endPosition;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	@Override
	public String toString() {
		return "Sneak [startPosition=" + startPosition + ", endPosition=" + endPosition + ", color=" + color
				+ ", length=" + length + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(color, endPosition, length, startPosition);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sneak other = (Sneak) obj;
		return Objects.equals(color, other.color) && endPosition == other.endPosition && length == other.length
				&& startPosition == other.startPosition;
	}
    
}
