package org.sean.blackjack.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class CardTest {


	
	@Test
	public void testGetRank() {
		Card card = new Card();
		card.setRank(Rank.ACE);
		assertEquals(Rank.ACE, card.getRank());
	}

	@Test
	public void testSetRank() {
		Card card = new Card();
		card.setRank(Rank.ACE);
		assertEquals(Rank.ACE, card.getRank());
	}

	@Test
	public void testGetSuit() {
		Card card = new Card();
		card.setSuit(Suit.HEARTS);
		assertEquals(Suit.HEARTS, card.getSuit());
	}

	@Test
	public void testSetSuit() {
		Card card = new Card();
		card.setSuit(Suit.HEARTS);
		assertEquals(Suit.HEARTS, card.getSuit());
	}

	//Is it a bad idea to test against default values? Should I even be unit testing this constructor?
	@Test
	public void testCard() {
		Card card = new Card();
		assertEquals(null, card.getSuit());
		assertEquals(null, card.getRank());
		
	}

	@Test
	public void testCardSuitRank() {
		Card card = new Card(Suit.HEARTS, Rank.ACE);
		assertEquals(Suit.HEARTS, card.getSuit());
		assertEquals(Rank.ACE, card.getRank());
	}

}
