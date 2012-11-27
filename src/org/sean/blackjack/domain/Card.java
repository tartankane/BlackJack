package org.sean.blackjack.domain;

/**
 * A playing card has a suit and a rank. For example, the Queen of Spades has a
 * suit of Spades and a rank of Queen.
 * 
 */
public class Card {

	private Suit suit;
	private Rank rank;

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public Card() {
		super();
	}

	public Card(Suit suit, Rank rank) {
		super();
		this.rank = rank;
		this.suit = suit;
	}

}
