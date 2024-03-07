package model;

import javafx.scene.image.ImageView;

public class Player {
	private String name;
	private String color;
	private int position;
	private ImageView object;

	public void setName(String name) {
		this.name = name;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setPosition(int roll) {
		this.position += roll;
	}

	public void setPositionAfterClimbing(int stairs) {
		this.position = stairs;
	}

	public Player() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Player(String name, String color) {
		this.name = name;
		this.color = color;
		this.position = 0;
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public int getPosition() {
		return position;
	}

	public void move(int steps) {
		if (steps > 0) {
			position += steps;
			System.out.println(name + " moved " + steps + " steps. New position: " + position);
		} else {
			System.out.println("Invalid number of steps. Please provide a positive number.");
		}
	}

	public void answerQuestion() {

		System.out.println(name + " is answering a question.");

	}

	public void displayPlayerInfo() {
		System.out.println("Player: " + name);
		System.out.println("Color: " + color);
		System.out.println("Position: " + position);
	}

	public ImageView getObject() {
		return object;
	}

	public void setObject(ImageView object) {
		this.object = object;
	}

}