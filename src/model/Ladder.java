package model;

import java.util.Objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ladder extends Tile {
	  private int startPosition;
	    private int endPosition;
	    private int length;
		private ImageView imageView; // ImageView to hold the snake's image

		public Ladder(int startPosition, int endPosition, int length) {
			super();
			this.startPosition = startPosition;
			this.endPosition = endPosition;
			this.length = length;
			String imagePath = "/images/" + length + "RowLadder.png"; // Ensure this path is correct
			Image image = new Image(getClass().getResourceAsStream(imagePath));

			this.imageView = new ImageView(); // Initialize the ImageView
			this.imageView.setImage(image); // Set the image to ImageView
		}
		
		public int getStartPosition() {
			return startPosition;
		}
		   public void setStartPosition(int startPosition) {
		        this.startPosition = startPosition;
		        this.endPosition = startPosition + length; // Recalculate endPosition
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
		        this.endPosition = startPosition + length; // Recalculate endPosition
		    }
		 
		public ImageView getImageView() {
			return imageView;
		}

		public void setImageView(ImageView imageView) {
			this.imageView = imageView;
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
