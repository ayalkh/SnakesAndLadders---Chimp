package model;

import java.util.Objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Snake extends Tile {
	private int startPosition;
	private int endPosition;
	private String color;
	private int length;
	private ImageView imageView; // ImageView to hold the snake's image
	private ImageView imageView_Med; // ImageView to hold the snake's image Medium level
	private ImageView imageView_Hard; // ImageView to hold the snake's image hard level


	public Snake(int startPosition, int endPosition, String color, int length) {
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.color = color;
		this.length = length;
		String imagePath = "/images/" + color + "Snake.png"; // Ensure this path is correct
		
		Image image = new Image(getClass().getResourceAsStream(imagePath));
		this.imageView = new ImageView(); // Initialize the ImageView
		this.imageView.setImage(image); // Set the image to ImageView
		
		String imagePath_Med = "/images/" + color + "SnakeMedium.png"; // Ensure this path is correct
		Image image_Med = new Image(getClass().getResourceAsStream(imagePath_Med));
		this.imageView_Med = new ImageView(); // Initialize the ImageView
		this.imageView_Med.setImage(image_Med); // Set the image to ImageView
		
		
		String imagePath_hard = "/images/" + color + "SnakeHard.png"; // Ensure this path is correct
		Image image_hard = new Image(getClass().getResourceAsStream(imagePath_hard));
		this.imageView_Hard = new ImageView(); // Initialize the ImageView
		this.imageView_Hard.setImage(image_hard); // Set the image to ImageView
		

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

	// Getter and setter for the ImageView
	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
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
		Snake other = (Snake) obj;
		return Objects.equals(color, other.color) && endPosition == other.endPosition && length == other.length
				&& startPosition == other.startPosition;
	}

}