package org.sean.blackjack.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class DeckTest {

	@Test
	public void testGetTheDeck() {
		int numberOfCardsInADeck=52;
		int indexOfFirstCardInDeck = 0;
		Deck deck = Deck.getInstance();
		deck.getTheDeck();
		List<Card> deckOfCards = deck.getTheDeck();
		assertEquals( numberOfCardsInADeck, deckOfCards.size() );
		assertEquals( Suit.HEARTS, deckOfCards.get(indexOfFirstCardInDeck).getSuit() );
		assertEquals( Rank.ACE, deckOfCards.get(indexOfFirstCardInDeck).getRank() );
		
	}

	@Test
	public void testGetInstance() {
		int numberOfCardsInADeck=52;
		int indexOfFirstCardInDeck = 0;
		Deck deck = Deck.getInstance();
		deck.getTheDeck();
		List<Card> deckOfCards = deck.getTheDeck();
		assertEquals( numberOfCardsInADeck, deckOfCards.size() );
		assertEquals( Suit.HEARTS, deckOfCards.get(indexOfFirstCardInDeck).getSuit() );
		assertEquals( Rank.ACE, deckOfCards.get(indexOfFirstCardInDeck).getRank() );

	}

	//This is a very basic test. All it does is test that the method successfully returns 
	//an object of class Card. No randomness is tested here.
	@Test
	public void testDealRandomCard() {
		assertEquals( (new Card()).getClass(), Deck.dealRandomCard().getClass() );
	}
	
	

	//A very basic test of the random number generator method. It assumes that the 
	//java.util.Random class functions correctly. It tests that the random number generated
	//lies within the expected minimum (inclusive) and maximum (inclusive) range.
	//Is running this test in a loop for 1000 times a sufficient test or how should it be done?
	@Test
	public void testRandomInRange() {
		int minNumber = 0;
		int maxNumber = 51;
		int randomNumber = Deck.randomInRange(minNumber, maxNumber);
		for (int i=0; i < 1000; i++){
			assertTrue( (randomNumber >= minNumber) && (randomNumber <= maxNumber) );
		}
	}

}
