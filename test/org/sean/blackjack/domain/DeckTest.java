package org.sean.blackjack.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class DeckTest {


	@Test
	public void testGetInstance() {
		int numberOfCardsInDeck=52;
		List<Card> uniqueCardsInDeck = new ArrayList<Card>();
		Deck deck = new Deck();
		//Demonstrate that there are 52 unique cards in a deck.
		for (int i = 0; i < numberOfCardsInDeck; i++){
			uniqueCardsInDeck.add(deck.dealRandomCard());
		}
		assertEquals( numberOfCardsInDeck, uniqueCardsInDeck.size() );
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void negativeTestGetInstance() {
		int numberOfCardsInDeck=52;
		Deck deck = new Deck();
		// Demonstrate that trying to deal more than 52 cards from a deck
		// will throw an IllegalArgumentException.
		for (int i = 0; i < numberOfCardsInDeck + 1; i++){
			deck.dealRandomCard();
		}

	}
	
	
	//This is a very basic test. All it does is test that the method successfully returns 
	//an object of class Card. No randomness is tested here.
	@Test
	public void testDealRandomCard() {
		Deck deck = new Deck();
		assertEquals( (new Card(Suit.SPADES, Rank.ACE)).getClass(), deck.dealRandomCard().getClass() );
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
