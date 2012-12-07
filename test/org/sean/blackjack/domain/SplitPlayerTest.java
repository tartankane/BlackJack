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
		round.getSplitPlayer().getSplitLeftCards().clear();
		round.getSplitPlayer().getSplitRightCards().clear();	

		round.getSplitPlayer().getSplitLeftCards().add(new Card(Suit.DIAMONDS, Rank.TEN));
		round.getSplitPlayer().getSplitLeftCards().add(new Card(Suit.DIAMONDS, Rank.SIX));
		
		cards = round.getSplitPlayer().getSplitLeftCards();

		round.getSplitPlayer().checkSplitBust(cards, isSplitLeft);
		assertFalse(round.getSplitPlayer().isSplitLeftBust());
		
		round.getSplitPlayer().getSplitLeftCards().add(new Card(Suit.HEARTS, Rank.SIX));
		cards = round.getSplitPlayer().getSplitLeftCards();
		
		round.getSplitPlayer().checkSplitBust(cards, isSplitLeft);
		assertTrue(round.getSplitPlayer().isSplitLeftBust());
		
		assertFalse(round.getSplitPlayer().isSplitRightBust());
		round.getSplitPlayer().getSplitRightCards().add(new Card(Suit.DIAMONDS, Rank.TEN));
		round.getSplitPlayer().getSplitRightCards().add(new Card(Suit.DIAMONDS, Rank.JACK));
		round.getSplitPlayer().getSplitRightCards().add(new Card(Suit.DIAMONDS, Rank.ACE));
		round.getSplitPlayer().getSplitRightCards().add(new Card(Suit.HEARTS, Rank.ACE));
		
		isSplitLeft = false;
		cards = round.getSplitPlayer().getSplitRightCards();

		round.getSplitPlayer().checkSplitBust(cards, isSplitLeft);	
		assertTrue(round.getSplitPlayer().isSplitRightBust());
		
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
		round.getSplitPlayer().getSplitLeftCards().clear();
		round.getSplitPlayer().getSplitRightCards().clear();	

		round.getSplitPlayer().getSplitLeftCards().add(new Card(Suit.DIAMONDS, Rank.TEN));
		round.getSplitPlayer().getSplitLeftCards().add(new Card(Suit.DIAMONDS, Rank.SIX));
		
		cards = round.getSplitPlayer().getSplitLeftCards();
		round.getSplitPlayer().calculateSplitHandValues(cards, playerFinishedDrawingCards, isSplitLeft);
		assertEquals( "16", round.getSplitPlayer().getSplitLeftHandValue() );
		
		isSplitLeft = false;
		playerFinishedDrawingCards = false;
		round.getSplitPlayer().getSplitRightCards().add(new Card(Suit.CLUBS, Rank.TEN));
		round.getSplitPlayer().getSplitRightCards().add(new Card(Suit.CLUBS, Rank.ACE));
		
		cards = round.getSplitPlayer().getSplitRightCards();
		round.getSplitPlayer().calculateSplitHandValues(cards, playerFinishedDrawingCards, isSplitLeft);
		assertEquals( "11 or 21", round.getSplitPlayer().getSplitRightHandValue() );
		
		playerFinishedDrawingCards = true;
		round.getSplitPlayer().calculateSplitHandValues(cards, playerFinishedDrawingCards, isSplitLeft);
		assertEquals( "21", round.getSplitPlayer().getSplitRightHandValue() );
	}

	@Test
	public void testGetSplitLeftGameMessage() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		round.getSplitPlayer().setSplitLeftGameMessage(Consts.DEALER_BUST);
		assertEquals(Consts.DEALER_BUST, round.getSplitPlayer().getSplitLeftGameMessage());
	}

	@Test
	public void testSetSplitLeftGameMessage() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		round.getSplitPlayer().setSplitLeftGameMessage(Consts.DEALER_BUST);
		assertEquals(Consts.DEALER_BUST, round.getSplitPlayer().getSplitLeftGameMessage());
	}

	@Test
	public void testGetSplitRightGameMessage() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		round.getSplitPlayer().setSplitRightGameMessage(Consts.DEALER_BUST);
		assertEquals(Consts.DEALER_BUST, round.getSplitPlayer().getSplitRightGameMessage());
	}

	@Test
	public void testSetSplitRightGameMessage() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		round.getSplitPlayer().setSplitRightGameMessage(Consts.DEALER_BUST);
		assertEquals(Consts.DEALER_BUST, round.getSplitPlayer().getSplitRightGameMessage());		
	}

	@Test
	public void testIsSplitLeftBust() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		round.getSplitPlayer().setSplitLeftBust(true);
		assertTrue(round.getSplitPlayer().isSplitLeftBust());
	}

	@Test
	public void testSetSplitLeftBust() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		round.getSplitPlayer().setSplitLeftBust(true);
		assertTrue(round.getSplitPlayer().isSplitLeftBust());
	}

	@Test
	public void testIsSplitRightBust() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		round.getSplitPlayer().setSplitRightBust(true);
		assertTrue(round.getSplitPlayer().isSplitRightBust());
	}

	@Test
	public void testSetSplitRightBust() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		round.getSplitPlayer().setSplitRightBust(true);
		assertTrue(round.getSplitPlayer().isSplitRightBust());
	}

	@Test
	public void testGetSplitLeftHandValue() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		
		round.getSplitPlayer().setSplitLeftHandValue("1 or 11");
		assertEquals("1 or 11", round.getSplitPlayer().getSplitLeftHandValue());
	}

	@Test
	public void testSetSplitLeftHandValue() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		
		round.getSplitPlayer().setSplitLeftHandValue("1 or 11");
		assertEquals("1 or 11", round.getSplitPlayer().getSplitLeftHandValue());
	}

	@Test
	public void testGetSplitRightHandValue() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		
		round.getSplitPlayer().setSplitRightHandValue("1 or 11");
		assertEquals("1 or 11", round.getSplitPlayer().getSplitRightHandValue());
	}

	@Test
	public void testSetSplitRightHandValue() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		
		round.getSplitPlayer().setSplitRightHandValue("1 or 11");
		assertEquals("1 or 11", round.getSplitPlayer().getSplitRightHandValue());
	}

	@Test
	public void testGetSplitLeftCards() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		assertEquals( 1, round.getSplitPlayer().getSplitLeftCards().size() );
	}

	@Test
	public void testGetSplitRightCards() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		assertEquals( 1, round.getSplitPlayer().getSplitRightCards().size() );
	}

}
