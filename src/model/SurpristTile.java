package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SurpristTile extends Tile{
	private ImageView imageView; // ImageView to hold the question mark image
	private int position;


	
	public SurpristTile( int position) {
		super();
		
		
		
		this.position = position;
		
		
		String imagePath = "/images/Surprise.png"; // Ensure this path is correct
		Image image = new Image(getClass().getResourceAsStream(imagePath));

		this.imageView = new ImageView(); // Initialize the ImageView
		this.imageView.setImage(image); // Set the image to ImageView
	}
	

	public ImageView getImageView() {
		return imageView;
	}
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	

}
