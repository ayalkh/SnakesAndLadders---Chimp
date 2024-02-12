package model;

import java.util.Objects;

public class SurpriseTile extends Tile {
    private int steps=10; // Number of steps to move, can be positive (forward) or negative (backward)

    // Constructor
    public SurpriseTile(int x, int y, int steps) {
        super(x, y);
        this.steps = steps;
    }

    // Getters and Setters
    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }



    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(steps);
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
		SurpriseTile other = (SurpriseTile) obj;
		return steps == other.steps;
	}

	@Override
    public String toString() {
        return "SurpriseTile [steps=" + steps + ", " + super.toString() + "]";
    }

    // Other surprise-tile-specific methods can be added here
}
