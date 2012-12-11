package org.sean.blackjack.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Deck creates a 52 card deck based on the fields of the enums, Suit and Rank.
 * The 52 cards are stored in a Collection of cards. When a random card is dealt
 * from the deck, then that card is removed from the Collection.
 * 
 */
public class Deck {
	private final List<Card> cardsInDeck = new ArrayList<Card>();

	/**
	 * Adds 52 cards to the List 'cardsInDeck'. This constructor is private to
	 * prevent subclassing.
	 */
	private Deck() {
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				cardsInDeck.add(new Card(suit, rank));
			}
		}
	}

	/**
	 * Static factory method creates decks so that the constructor can be set as
	 * private. This prevents subclassing.
	 * 
	 * @return Deck - a new deck.
	 */
	public static Deck getInstance() {
		return new Deck();
	}

	/**
	 * Randomly selects a card from the remaining cards in the deck. This card
	 * is removed from the deck and returned by the method.
	 * 
	 * @return card - a random card from the deck.
	 */
	public Card dealRandomCard() {
		Card card = this.cardsInDeck.get(Deck.randomInRange(0,
				this.cardsInDeck.size() - 1));
		this.cardsInDeck.remove(card);
		return card;
	}

	/**
	 * Generates a random integer between min (inclusive) and max (inclusive).
	 * For the full deck of cards, set min = 0 and max = 51 to generate a random
	 * int from 52 possible values.
	 * 
	 * @param min
	 *            normally set to zero
	 * @param max
	 *            normally set to "size of the deck minus one"
	 * @return randomNum - a random integer in the specified range
	 */
	static int randomInRange(int min, int max) {
		if (min > max) {
			throw new IllegalArgumentException(
					"Random number range is invalid.");
		}
		Random random = new Random();
		int randomNum = random.nextInt(max - min + 1) + min;
		return randomNum;
	}

}
