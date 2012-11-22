package org.sean.blackjack.domain;



public enum Suit {
	//The int values, 1 to 4,  associated with the suits are purely to support the Comparable interface in class Card.
	//They do not imply that Hearts is superior to Clubs. In holdem Poker, all suits have equal value.
	
	HEARTS(1), DIAMONDS(2), SPADES(3), CLUBS(4);

	private final int suit;

	Suit(int size) {
		this.suit = size;
	}

	public int suit() {
		return suit;
	}
}

