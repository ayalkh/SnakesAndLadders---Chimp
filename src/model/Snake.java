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

	public Snake(int startPosition, int endPosition, String color, int length) {
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.color = color;
		this.length = length;
		 
		 String imagePath = "/images/" + color + "Snake.png"; // Ensure this path is correct
		    Image image = new Image(getClass().getResourceAsStream(imagePath));


		    this.imageView = new ImageView(); // Initialize the ImageView
		    this.imageView.setImage(image); // Set the image to ImageView

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