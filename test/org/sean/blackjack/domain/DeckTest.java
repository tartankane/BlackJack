package org.sean.blackjack.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class DeckTest {

//	@Test
//	public void testGetTheDeck() {
//		int numberOfCardsInADeck=52;
//		int indexOfFirstCardInDeck = 0;
//		Deck deck = Deck.getInstance();
//		deck.getTheDeck();
//		List<Card> deckOfCards = deck.getTheDeck();
//		assertEquals( numberOfCardsInADeck, deckOfCards.size() );
//		assertEquals( Suit.HEARTS, deckOfCards.get(indexOfFirstCardInDeck).getSuit() );
//		assertEquals( Rank.ACE, deckOfCards.get(indexOfFirstCardInDeck).getRank() );
//		
//	}

	@Test
	public void testGetInstance() {
		int numberOfCardsInADeck=52;
		int indexOfFirstCardInDeck = 0;
		Set<Card> uniqueCardsInDeck = new HashSet<Card>();
		Deck deck = Deck.getInstance();
		//Demonstrate that there are 52 unique cards in a deck.
		//Note that an IllegalArgumentException will be generated 
		//if an attempt is made to deal more than 52 cards because the
		//deck will be empty.
		for (int i = 0; i < numberOfCardsInADeck; i++){
			uniqueCardsInDeck.add(deck.dealRandomCard());
		}
		assertEquals( numberOfCardsInADeck, uniqueCardsInDeck.size() );
	}

	//This is a very basic test. All it does is test that the method successfully returns 
	//an object of class Card. No randomness is tested here.
	@Test
	public void testDealRandomCard() {
		Deck deck = Deck.getInstance();
		assertEquals( (new Card()).getClass(), deck.dealRandomCard().getClass() );
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
