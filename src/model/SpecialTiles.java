package model;

import java.util.Objects;

public class SpecialTiles extends Tile {
	private Ladder ladder;
	private Sneak sneak;
	private Question question;
	private SurpriseTile surpriseTile;
	public SpecialTiles(Ladder ladder, Sneak sneak, Question question, SurpriseTile surpriseTile) {
		super();
		this.ladder = ladder;
		this.sneak = sneak;
		this.question = question;
		this.surpriseTile = surpriseTile;
	}
	public Ladder getLadder() {
		return ladder;
	}
	public void setLadder(Ladder ladder) {
		this.ladder = ladder;
	}
	public Sneak getSneak() {
		return sneak;
	}
	public void setSneak(Sneak sneak) {
		this.sneak = sneak;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public SurpriseTile getSurpriseTile() {
		return surpriseTile;
	}
	public void setSurpriseTile(SurpriseTile surpriseTile) {
		this.surpriseTile = surpriseTile;
	}
	@Override
	public String toString() {
		return "SpecialTiles [ladder=" + ladder + ", sneak=" + sneak + ", question=" + question + ", surpriseTile="
				+ surpriseTile + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(ladder, question, sneak, surpriseTile);
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
		SpecialTiles other = (SpecialTiles) obj;
		return Objects.equals(ladder, other.ladder) && Objects.equals(question, other.question)
				&& Objects.equals(sneak, other.sneak) && Objects.equals(surpriseTile, other.surpriseTile);
	}
	

}
