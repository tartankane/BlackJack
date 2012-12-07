package org.sean.blackjack.domain;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.sean.blackjack.services.BlackJackService;
import org.sean.blackjack.services.BlackJackServiceImpl;

public class RoundTest {

	@Test
	public void testCalculateHandValues() {
		Round round = new Round();
		boolean playerFinishedDrawingCards = false;
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.ACE));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.ACE));
		round.calculateHandValues(playerFinishedDrawingCards);
		assertEquals("11 or 21", round.getPlayerHandValue());
		assertEquals(11, round.getDealerHandValue());

		playerFinishedDrawingCards = true;
		round.calculateHandValues(playerFinishedDrawingCards);
		assertEquals("21", round.getPlayerHandValue());
	}

	@Test
	public void testCheckBust() {
		boolean isSplit = false;
		boolean isPlayer = true;
		Round round = new Round();
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.ACE));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.JACK));
		round.checkBust(round.getPlayerCards(), isPlayer, isSplit);
		assertFalse(round.isBustPlayer());

		round.getPlayerCards().add(new Card(Suit.DIAMONDS, Rank.ACE));
		round.checkBust(round.getPlayerCards(), isPlayer, isSplit);
		assertTrue(round.isBustPlayer());

	}

	@Test
	public void testPlayerHasBlackJack() {
		Round round = new Round();
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.ACE));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		assertTrue(round.playerHasBlackJack());

		round = new Round();
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.ACE));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.NINE));
		assertFalse(round.playerHasBlackJack());

	}

	@Test
	public void testDealerCanNotMakeBlackJack() {
		Round round = new Round();
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.SIX));
		assertTrue(round.dealerCanNotMakeBlackJack());

		round = new Round();
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.ACE));
		assertFalse(round.dealerCanNotMakeBlackJack());
	}

	@Test
	public void testDealerMustStand() {
		Round round = new Round();
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.SIX));
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		assertFalse(round.dealerMustStand());

		round.getDealerCards().add(new Card(Suit.DIAMONDS, Rank.ACE));
		assertTrue(round.dealerMustStand());

		Round nextRound = new Round();
		nextRound.getDealerCards().add(new Card(Suit.HEARTS, Rank.ACE));
		nextRound.getDealerCards().add(new Card(Suit.HEARTS, Rank.FIVE));
		nextRound.getDealerCards().add(new Card(Suit.HEARTS, Rank.KING));
		nextRound.getDealerCards().add(new Card(Suit.DIAMONDS, Rank.ACE));
		assertTrue(nextRound.dealerMustStand());
	}

	@Test
	public void testCheckWhoWon() {

		// Do I need to test for all the specific cases like BlackJack here?
		double playerCredits = 1000.0;
		Round round = new Round();
		round.setPlayerCredits(playerCredits);
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.SEVEN));

		round.getPlayerCards().add(new Card(Suit.DIAMONDS, Rank.TEN));
		round.getPlayerCards().add(new Card(Suit.DIAMONDS, Rank.SIX));
		round.checkWhoWon();
		assertEquals(Consts.PLAYER_LOSES, round.getGameMessage());
	}

	@Test
	public void testCheckIfPlayerLowOnCredits() {
		Round round = new Round();
		round.setPlayerCredits(Consts.LOW_CREDITS_VALUE - 1);
		assertTrue (Consts.LOW_CREDITS_VALUE > round.getPlayerCredits());
		round.checkIfPlayerLowOnCredits();
		assertTrue (round.getPlayerCredits() > Consts.LOW_CREDITS_VALUE);
		assertEquals (Consts.STARTING_CREDITS, round.getPlayerCredits(), 0);
	}

	@Test
	public void testCheckIfPlayerCanSplit() {
		Round round = new Round();
		round.getPlayerCards().add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		round.checkIfPlayerCanSplit();
		assertTrue(round.isPlayerCanSplit());

		round = new Round();
		round.getPlayerCards().add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.ACE));
		round.checkIfPlayerCanSplit();
		assertFalse(round.isPlayerCanSplit());

	}

	@Test
	public void testPlayerSplits() {
		Round round = new Round();
		round.getPlayerCards().add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		round.playerSplits();

		assertEquals(1, round.getSplitPlayer().getSplitLeftCards().size());
		assertEquals(1, round.getSplitPlayer().getSplitRightCards().size());
	}

	@Test
	public void testCheckWhoWonAfterSplit() {
		
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);

		round.getDealerCards().clear();
		round.getSplitPlayer().getSplitLeftCards().clear();
		round.getSplitPlayer().getSplitRightCards().clear();	
		
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.SEVEN));
		round.getSplitPlayer().getSplitLeftCards().add(new Card(Suit.DIAMONDS, Rank.TEN));
		round.getSplitPlayer().getSplitLeftCards().add(new Card(Suit.DIAMONDS, Rank.SIX));
		round.getSplitPlayer().getSplitRightCards().add(new Card(Suit.CLUBS, Rank.TEN));
		round.getSplitPlayer().getSplitRightCards().add(new Card(Suit.CLUBS, Rank.EIGHT));
		
		round.checkWhoWonAfterSplit();
		assertEquals(Consts.PLAYER_LOSES, round.getSplitPlayer().getSplitLeftGameMessage());
		assertEquals(Consts.PLAYER_WINS, round.getSplitPlayer().getSplitRightGameMessage());
	}

	@Test
	public void testIsBustPlayer() {
		Round round = new Round();
		round.setBustPlayer(true);
		assertTrue(round.isBustPlayer());
	}

	@Test
	public void testSetBustPlayer() {
		Round round = new Round();
		round.setBustPlayer(true);
		assertTrue(round.isBustPlayer());
	}

	@Test
	public void testGetPlayerCredits() {
		double playerCredits = 1000.0;
		Round round = new Round();
		round.setPlayerCredits(playerCredits);
		assertEquals(playerCredits, round.getPlayerCredits(), 0);
	}

	@Test
	public void testSetPlayerCredits() {
		double playerCredits = 1000.0;
		Round round = new Round();
		round.setPlayerCredits(playerCredits);
		assertEquals(playerCredits, round.getPlayerCredits(), 0);

	}

	@Test
	public void testGetPlayerBet() {
		int playerBet = 25;
		Round round = new Round();
		round.setPlayerBet(playerBet);
		assertEquals(playerBet, round.getPlayerBet());
	}

	@Test
	public void testSetPlayerBet() {
		int playerBet = 25;
		Round round = new Round();
		round.setPlayerBet(playerBet);
		assertEquals(playerBet, round.getPlayerBet());
	}

	@Test
	public void testGetDealerCards() {
		Card card = new Card(Suit.HEARTS, Rank.ACE);
		Round round = new Round();
		round.getDealerCards().add(card);

		assertEquals(Suit.HEARTS, round.getDealerCards().get(0).getSuit());
		assertEquals(Rank.ACE, round.getDealerCards().get(0).getRank());

	}

	// Should I have methods setDealerCards/setPlayerCards if I dont use it?
	// Will I need it to
	// persist the round with hibernate if that is what I do? Investigate this
	// @Test
	// public void testSetDealerCards() {
	// Card card = new Card(Suit.HEARTS, Rank.ACE);
	// List<Card> cards = new ArrayList<>();
	// cards.add(card);
	// Round round = new Round();
	// round.setDealerCards(cards);
	//
	// assertEquals(Suit.HEARTS, round.getDealerCards().get(0).getSuit());
	// assertEquals(Rank.ACE, round.getDealerCards().get(0).getRank());
	//
	// }

	@Test
	public void testGetPlayerCards() {
		Card card = new Card(Suit.HEARTS, Rank.ACE);
		Round round = new Round();
		round.getPlayerCards().add(card);

		assertEquals(Suit.HEARTS, round.getPlayerCards().get(0).getSuit());
		assertEquals(Rank.ACE, round.getPlayerCards().get(0).getRank());
	}

	// @Test
	// public void testSetPlayerCards() {
	// Card card = new Card(Suit.HEARTS, Rank.ACE);
	// List<Card> cards = new ArrayList<>();
	// cards.add(card);
	// Round round = new Round();
	// round.setPlayerCards(cards);
	//
	// assertEquals(Suit.HEARTS, round.getPlayerCards().get(0).getSuit());
	// assertEquals(Rank.ACE, round.getPlayerCards().get(0).getRank());
	// }

	@Test
	public void testGetGameMessage() {
		Round round = new Round();
		round.setGameMessage(Consts.PLAYER_WINS);
		assertEquals(Consts.PLAYER_WINS, round.getGameMessage());
	}

	@Test
	public void testSetGameMessage() {
		Round round = new Round();
		round.setGameMessage(Consts.PLAYER_WINS);
		assertEquals(Consts.PLAYER_WINS, round.getGameMessage());
	}

	@Test
	public void testIsPlayerHasBlackJack() {
		Round round = new Round();
		round.setPlayerHasBlackJack(true);
		assertTrue(round.isPlayerHasBlackJack());
	}

	@Test
	public void testSetPlayerHasBlackJack() {
		Round round = new Round();
		round.setPlayerHasBlackJack(true);
		assertTrue(round.isPlayerHasBlackJack());
	}

	@Test
	public void testGetPlayerHandValue() {
		Round round = new Round();
		round.setPlayerHandValue("11 or 21");
		assertEquals("11 or 21", round.getPlayerHandValue());
	}

	@Test
	public void testSetPlayerHandValue() {
		Round round = new Round();
		round.setPlayerHandValue("11 or 21");
		assertEquals("11 or 21", round.getPlayerHandValue());
	}

	@Test
	public void testGetDealerHandValue() {
		Round round = new Round();
		round.setDealerHandValue(Consts.TWENTY_ONE);
		assertEquals(Consts.TWENTY_ONE, round.getDealerHandValue());
	}

	@Test
	public void testSetDealerHandValue() {
		Round round = new Round();
		round.setDealerHandValue(Consts.TWENTY_ONE);
		assertEquals(Consts.TWENTY_ONE, round.getDealerHandValue());
	}

	@Test
	public void testIsPlayerCanSplit() {
		Round round = new Round();
		round.setPlayerCanSplit(true);
		assertTrue(round.isPlayerCanSplit());
	}

	@Test
	public void testSetPlayerCanSplit() {
		Round round = new Round();
		round.setPlayerCanSplit(true);
		assertTrue(round.isPlayerCanSplit());
	}

	@Test
	public void testGetSplitPlayer() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		assertEquals( 1, round.getSplitPlayer().getSplitLeftCards().size() );
	}


}
