package model;

import java.io.InputStream;
import java.util.Objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ladder extends Tile {
	  private int startPosition;
	    private int endPosition;
	    private int length;
		private ImageView imageView; // ImageView to hold the snake's image
		private ImageView imageView_Med; // ImageView to hold the ladders's image Medium level
		private ImageView imageView_Hard; // ImageView to hold the ladders's image Medium level

		public Ladder(int startPosition, int endPosition, int length,String gameMode) {
			super();
			this.startPosition = startPosition;
			this.endPosition = endPosition;
			this.length = length;

			String basePath = "/images/" + length + "RowLadder";
		    String imagePath = basePath + (gameMode.equals("medium") ? "Med.png" : gameMode.equals("hard") ? "Hard.png" : ".png");

		    // Debugging statement to verify the imagePath
		    System.out.println("Attempting to load image from path: " + imagePath);

		    InputStream stream = getClass().getResourceAsStream(imagePath);
		    if (stream == null) {
		        throw new IllegalArgumentException("Image resource not found: " + imagePath);
		    }

		    Image image = new Image(stream);
		    this.imageView = new ImageView(image); // Initialize and set the image to ImageView in one step
		}
		
		public ImageView getImageView_Hard() {
			return imageView_Hard;
		}

		public void setImageView_Hard(ImageView imageView_Hard) {
			this.imageView_Hard = imageView_Hard;
		}

		public ImageView getImageView_Med() {
			return imageView_Med;
		}

		public void setImageView_Med(ImageView imageView_Med) {
			this.imageView_Med = imageView_Med;
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
