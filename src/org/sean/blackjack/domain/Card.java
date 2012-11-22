package org.sean.blackjack.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity (name="mycardtable")
public class Card implements Comparable<Card> {

	@Id
	@GeneratedValue
	private int cardId;
	@Enumerated(EnumType.STRING)
	private Suit suit;
	@Enumerated(EnumType.STRING)
	private Rank rank;
	public int getCardId() {
		return cardId;
	}
//	public void setCardId(int cardId) {
//		this.cardId = cardId;
//	}
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
	public Card(Suit suit, Rank rank) {
		super();
		this.rank = rank;
		this.suit = suit;
	}
	public Card() {
		super();
	}
	
	@Override
	public int compareTo(Card card) {
		if (this.rank.getRank() > card.rank.getRank())
			return +1;
		else if (this.rank.getRank() < card.rank.getRank())
			return -1;
		else if (this.suit.suit() > card.suit.suit())
			return +1;
		else if (this.suit.suit() < card.suit.suit())
			return -1;
		else
			return 0;
	}

	//WHY DO I OVERRIDE EQUALS AND HASHCODE? I CANT REMEMBER OR FIGURE IT OUT. SEEMS TO WORK FINE WITHOUT IT
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (rank != other.rank)
			return false;
		if (suit != other.suit)
			return false;
		return true;
	}

}


