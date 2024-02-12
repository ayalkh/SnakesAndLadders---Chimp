package model;

import java.util.Objects;

public class Ladder extends Tile {
	  private int startPosition;
	    private int endPosition;
	    private int length;
	
		public Ladder(int startPosition, int endPosition, int length) {
			super();
			this.startPosition = startPosition;
			this.endPosition = endPosition;
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
		public int getLength() {
			return length;
		}
		public void setLength(int length) {
			this.length = length;
		}
		@Override
		public int hashCode() {
			return Objects.hash(endPosition, length, startPosition);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Ladder other = (Ladder) obj;
			return endPosition == other.endPosition && length == other.length && startPosition == other.startPosition;
		}
	    
	    
}
