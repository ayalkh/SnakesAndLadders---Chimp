package control;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Dice;
import model.GameLevel;

public class DiceSimulationController {
	@FXML
	private ImageView diceImageView;
	private Dice dice = new Dice(GameLevel.EASY);

	@FXML
	public void rollDice() {
		int face = dice.rollDice();
		System.out.println("rolling dice result : " + face);
		// String imageFileName = "/images/dice" + face + ".png";
		// System.out.println("the image name is1 : " + imageFileName);
		Image diceImage = new Image(getClass().getResourceAsStream("/images/dice" + face + ".png"));
		diceImageView.setImage(diceImage);
	}
}
