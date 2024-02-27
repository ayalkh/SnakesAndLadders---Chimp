package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Question;
import model.Tile;

public class QuestionTile extends Tile{
	private Question question;
	private ImageView imageView; // ImageView to hold the questionmark image
	private int position;
	public QuestionTile(Question question, int position) {
		super();
		this.question = question;
		
		this.position = position;
		String imagePath = "/images/Black_question_mark.png"; // Ensure this path is correct
		Image image = new Image(getClass().getResourceAsStream(imagePath));

		this.imageView = new ImageView(); // Initialize the ImageView
		this.imageView.setImage(image); // Set the image to ImageView
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
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

