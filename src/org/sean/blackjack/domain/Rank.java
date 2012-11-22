package org.sean.blackjack.domain;



public enum Rank {
	TWO(1,2), THREE(2,3), FOUR(3,4), FIVE(4,5), SIX(5, 6), SEVEN(6, 7), EIGHT(7, 8), NINE(8, 9), TEN(
			9, 10), JACK(10, 10), QUEEN(11, 10), KING(12, 10), ACE(13, 1);

	private final int cardRank;
	private final int cardValue;

	Rank(int cardRank, int cardValue) {
		this.cardRank = cardRank;
		this.cardValue=cardValue;
	}
	


	public int getRank() {
		return cardRank;
	}



	public int getCardValue() {
		return cardValue;
	}
}
