package org.sean.blackjack.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


//Creates a deck of cards based on the fields of the enums Suit and Rank.
public class Deck {
	private List<Card> cardsInDeck = new ArrayList<Card>();
	

	public List<Card> getTheDeck() {
		return cardsInDeck;
	}

	//Adds 52 cards to the List 'cardsInDeck'
	private Deck() {
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				cardsInDeck.add(new Card(suit, rank));
			}
		}
	}

	//Static factory method creates decks so that the constructor can be set as private.
	//This prevents subclassing.
	public static Deck getInstance() {
		return new Deck();
	}
	
	public static Card dealRandomCard() {
		Deck deck = new Deck();
		return deck.getTheDeck().get(Deck.randomInRange(0, 51));
	}
	
	static int randomInRange(int min, int max) {
		//Generates a random integer between min and max inclusive.
		//For the full deck of cards, set min = 0 and max = 51		
		if (min > max) {
			throw new IllegalArgumentException(
					"Random number range is invalid.");
		}		
		Random random = new Random();
		int randomNum = random.nextInt(max - min + 1) + min;
		return randomNum;
	}
	
}
