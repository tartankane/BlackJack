package org.sean.blackjack.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.sean.blackjack.services.BlackJackServiceImpl;

public class SplitPlayerTest {

	@Test
	public void testCheckSplitBust() {
		boolean isSplitLeft = true;
		List<Card> cards = new ArrayList<Card>();
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);

		round.getDealerCards().clear();
		round.getSplitHand().getSplitLeftCards().clear();
		round.getSplitHand().getSplitRightCards().clear();	

		round.getSplitHand().getSplitLeftCards().add(new Card(Suit.DIAMONDS, Rank.TEN));
		round.getSplitHand().getSplitLeftCards().add(new Card(Suit.DIAMONDS, Rank.SIX));
		
		cards = round.getSplitHand().getSplitLeftCards();

		round.getSplitHand().checkSplitBust(cards, isSplitLeft);
		assertFalse(round.getSplitHand().isSplitLeftBust());
		
		round.getSplitHand().getSplitLeftCards().add(new Card(Suit.HEARTS, Rank.SIX));
		cards = round.getSplitHand().getSplitLeftCards();
		
		round.getSplitHand().checkSplitBust(cards, isSplitLeft);
		assertTrue(round.getSplitHand().isSplitLeftBust());
		
		assertFalse(round.getSplitHand().isSplitRightBust());
		round.getSplitHand().getSplitRightCards().add(new Card(Suit.DIAMONDS, Rank.TEN));
		round.getSplitHand().getSplitRightCards().add(new Card(Suit.DIAMONDS, Rank.JACK));
		round.getSplitHand().getSplitRightCards().add(new Card(Suit.DIAMONDS, Rank.ACE));
		round.getSplitHand().getSplitRightCards().add(new Card(Suit.HEARTS, Rank.ACE));
		
		isSplitLeft = false;
		cards = round.getSplitHand().getSplitRightCards();

		round.getSplitHand().checkSplitBust(cards, isSplitLeft);	
		assertTrue(round.getSplitHand().isSplitRightBust());
		
	}

	@Test
	public void testCalculateSplitHandValues() {
		boolean playerFinishedDrawingCards = true;
		boolean isSplitLeft = true;
		List<Card> cards = new ArrayList<Card>();
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);

		round.getDealerCards().clear();
		round.getSplitHand().getSplitLeftCards().clear();
		round.getSplitHand().getSplitRightCards().clear();	

		round.getSplitHand().getSplitLeftCards().add(new Card(Suit.DIAMONDS, Rank.TEN));
		round.getSplitHand().getSplitLeftCards().add(new Card(Suit.DIAMONDS, Rank.SIX));
		
		cards = round.getSplitHand().getSplitLeftCards();
		round.getSplitHand().calculateSplitHandValues(cards, playerFinishedDrawingCards, isSplitLeft);
		assertEquals( "16", round.getSplitHand().getSplitLeftHandValue() );
		
		isSplitLeft = false;
		playerFinishedDrawingCards = false;
		round.getSplitHand().getSplitRightCards().add(new Card(Suit.CLUBS, Rank.TEN));
		round.getSplitHand().getSplitRightCards().add(new Card(Suit.CLUBS, Rank.ACE));
		
		cards = round.getSplitHand().getSplitRightCards();
		round.getSplitHand().calculateSplitHandValues(cards, playerFinishedDrawingCards, isSplitLeft);
		assertEquals( "11 or 21", round.getSplitHand().getSplitRightHandValue() );
		
		playerFinishedDrawingCards = true;
		round.getSplitHand().calculateSplitHandValues(cards, playerFinishedDrawingCards, isSplitLeft);
		assertEquals( "21", round.getSplitHand().getSplitRightHandValue() );
	}

	@Test
	public void testGetSplitLeftGameMessage() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		round.getSplitHand().setSplitLeftGameMessage(Consts.DEALER_BUST);
		assertEquals(Consts.DEALER_BUST, round.getSplitHand().getSplitLeftGameMessage());
	}

	@Test
	public void testSetSplitLeftGameMessage() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		round.getSplitHand().setSplitLeftGameMessage(Consts.DEALER_BUST);
		assertEquals(Consts.DEALER_BUST, round.getSplitHand().getSplitLeftGameMessage());
	}

	@Test
	public void testGetSplitRightGameMessage() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		round.getSplitHand().setSplitRightGameMessage(Consts.DEALER_BUST);
		assertEquals(Consts.DEALER_BUST, round.getSplitHand().getSplitRightGameMessage());
	}

	@Test
	public void testSetSplitRightGameMessage() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		round.getSplitHand().setSplitRightGameMessage(Consts.DEALER_BUST);
		assertEquals(Consts.DEALER_BUST, round.getSplitHand().getSplitRightGameMessage());		
	}

	@Test
	public void testIsSplitLeftBust() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		round.getSplitHand().setSplitLeftBust(true);
		assertTrue(round.getSplitHand().isSplitLeftBust());
	}

	@Test
	public void testSetSplitLeftBust() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		round.getSplitHand().setSplitLeftBust(true);
		assertTrue(round.getSplitHand().isSplitLeftBust());
	}

	@Test
	public void testIsSplitRightBust() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		round.getSplitHand().setSplitRightBust(true);
		assertTrue(round.getSplitHand().isSplitRightBust());
	}

	@Test
	public void testSetSplitRightBust() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		round.getSplitHand().setSplitRightBust(true);
		assertTrue(round.getSplitHand().isSplitRightBust());
	}

	@Test
	public void testGetSplitLeftHandValue() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		
		round.getSplitHand().setSplitLeftHandValue("1 or 11");
		assertEquals("1 or 11", round.getSplitHand().getSplitLeftHandValue());
	}

	@Test
	public void testSetSplitLeftHandValue() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		
		round.getSplitHand().setSplitLeftHandValue("1 or 11");
		assertEquals("1 or 11", round.getSplitHand().getSplitLeftHandValue());
	}

	@Test
	public void testGetSplitRightHandValue() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		
		round.getSplitHand().setSplitRightHandValue("1 or 11");
		assertEquals("1 or 11", round.getSplitHand().getSplitRightHandValue());
	}

	@Test
	public void testSetSplitRightHandValue() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		
		round.getSplitHand().setSplitRightHandValue("1 or 11");
		assertEquals("1 or 11", round.getSplitHand().getSplitRightHandValue());
	}

	@Test
	public void testGetSplitLeftCards() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		assertEquals( 1, round.getSplitHand().getSplitLeftCards().size() );
	}

	@Test
	public void testGetSplitRightCards() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		assertEquals( 1, round.getSplitHand().getSplitRightCards().size() );
	}

}
