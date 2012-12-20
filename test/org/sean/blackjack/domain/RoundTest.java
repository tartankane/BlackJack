package org.sean.blackjack.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.sean.blackjack.services.BlackJackServiceImpl;

public class RoundTest {
	
	Round round = null;
	
	@Before
	 public void init() {
		round = Round.getInstance();
		round.getPlayerCards().clear();
		round.getDealerCards().clear();
	 }

	@Test
	public void testCalculateHandValues() {
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
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.ACE));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.JACK));
		round.checkBust(round.getPlayerCards(), isPlayer, isSplit);
		assertFalse(round.isBustPlayer());

		round.getPlayerCards().add(new Card(Suit.DIAMONDS, Rank.ACE));
		round.checkBust(round.getPlayerCards(), isPlayer, isSplit);
		assertTrue(round.isBustPlayer());

	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestCheckBust() {
		boolean isSplit = false;
		boolean isPlayer = true;

		round.checkBust(null, isPlayer, isSplit);
	}
	
	@Test
	public void testPlayerHasBlackJack() {
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.ACE));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		assertTrue(round.hasPlayerABlackJack());

		round = Round.getInstance();
		round.getPlayerCards().clear();
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.ACE));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.NINE));
		assertFalse(round.hasPlayerABlackJack());

	}

	@Test
	public void testDealerCanNotMakeBlackJack() {
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.SIX));
		assertTrue(round.dealerCanNotMakeBlackJack());

		round = Round.getInstance();
		round.getDealerCards().clear();
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.ACE));
		assertFalse(round.dealerCanNotMakeBlackJack());
	}

	@Test
	public void testDealerMustStand() {
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.SIX));
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		assertFalse(round.dealerMustStand());

		round.getDealerCards().add(new Card(Suit.DIAMONDS, Rank.ACE));
		assertTrue(round.dealerMustStand());

		Round nextRound = Round.getInstance();
		round.getDealerCards().clear();
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
		round.setPlayerCredits(Consts.LOW_CREDITS_VALUE - 1);
		assertTrue (Consts.LOW_CREDITS_VALUE > round.getPlayerCredits());
		round.checkIfPlayerLowOnCredits();
		assertTrue (round.getPlayerCredits() > Consts.LOW_CREDITS_VALUE);
		assertEquals (Consts.STARTING_CREDITS, round.getPlayerCredits(), 0);
	}

	@Test
	public void testCheckIfPlayerCanSplit() {
		round.setPlayerCanSplit(false);
		round.getPlayerCards().add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		round.checkIfPlayerCanSplit();
		assertTrue(round.isPlayerCanSplit());

		round = Round.getInstance();
		round.getPlayerCards().clear();
		round.setPlayerCanSplit(false);
		round.getPlayerCards().add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.ACE));
		round.checkIfPlayerCanSplit();
		assertFalse(round.isPlayerCanSplit());

	}

	@Test
	public void testPlayerSplits() {
		round.getPlayerCards().add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		round.playerSplits();

		assertEquals(1, round.getSplitHand().getSplitLeftCards().size());
		assertEquals(1, round.getSplitHand().getSplitRightCards().size());
	}

	@Test
	public void testCheckWhoWonAfterSplit() {
		round.getSplitHand().getSplitLeftCards().clear();
		round.getSplitHand().getSplitRightCards().clear();	
		
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.SEVEN));
		round.getSplitHand().getSplitLeftCards().add(new Card(Suit.DIAMONDS, Rank.TEN));
		round.getSplitHand().getSplitLeftCards().add(new Card(Suit.DIAMONDS, Rank.SIX));
		round.getSplitHand().getSplitRightCards().add(new Card(Suit.CLUBS, Rank.TEN));
		round.getSplitHand().getSplitRightCards().add(new Card(Suit.CLUBS, Rank.EIGHT));
		
		round.checkWhoWonAfterSplit();
		assertEquals(Consts.PLAYER_LOSES, round.getSplitHand().getSplitLeftGameMessage());
		assertEquals(Consts.PLAYER_WINS, round.getSplitHand().getSplitRightGameMessage());
	}

	@Test
	public void testIsBustPlayer() {
		round.setBustPlayer(true);
		assertTrue(round.isBustPlayer());
	}

	@Test
	public void testSetBustPlayer() {
		round.setBustPlayer(true);
		assertTrue(round.isBustPlayer());
	}

	@Test
	public void testGetPlayerCredits() {
		double playerCredits = 1000.0;
		round.setPlayerCredits(playerCredits);
		assertEquals(playerCredits, round.getPlayerCredits(), 0);
	}

	@Test
	public void testSetPlayerCredits() {
		double playerCredits = 1000.0;
		round.setPlayerCredits(playerCredits);
		assertEquals(playerCredits, round.getPlayerCredits(), 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestSetPlayerCredits() {
		double playerCredits = -1.0;
		round.setPlayerCredits(playerCredits);
	}
	

	@Test
	public void testGetPlayerBet() {
		int playerBet = 25;
		round.setPlayerBet(playerBet);
		assertEquals(playerBet, round.getPlayerBet());
	}

	@Test
	public void testSetPlayerBet() {
		int playerBet = 25;
		round.setPlayerBet(playerBet);
		assertEquals(playerBet, round.getPlayerBet());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestSetPlayerBet() {
		int playerBet = 0;
		round.setPlayerBet(playerBet);
	}

	@Test
	public void testGetDealerCards() {
		Card card = new Card(Suit.HEARTS, Rank.ACE);
		round.getDealerCards().add(card);

		assertEquals(Suit.HEARTS, round.getDealerCards().get(0).getSuit());
		assertEquals(Rank.ACE, round.getDealerCards().get(0).getRank());

	}

	@Test
	public void testGetPlayerCards() {
		Card card = new Card(Suit.HEARTS, Rank.ACE);
		round.getPlayerCards().add(card);
		assertEquals(Suit.HEARTS, round.getPlayerCards().get(0).getSuit());
		assertEquals(Rank.ACE, round.getPlayerCards().get(0).getRank());
	}

	@Test
	public void testGetGameMessage() {
		round.setGameMessage(Consts.PLAYER_WINS);
		assertEquals(Consts.PLAYER_WINS, round.getGameMessage());
	}

	@Test
	public void testSetGameMessage() {
		round.setGameMessage(Consts.PLAYER_WINS);
		assertEquals(Consts.PLAYER_WINS, round.getGameMessage());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestSetGameMessage() {
		round.setGameMessage(null);
	}

	@Test
	public void testIsPlayerHasBlackJack() {
		round.setPlayerHasBlackJack(true);
		assertTrue(round.isPlayerHasBlackJack());
	}

	@Test
	public void testSetPlayerHasBlackJack() {
		round.setPlayerHasBlackJack(true);
		assertTrue(round.isPlayerHasBlackJack());
	}

	@Test
	public void testGetPlayerHandValue() {
		round.setPlayerHandValue("11 or 21");
		assertEquals("11 or 21", round.getPlayerHandValue());
	}

	@Test
	public void testSetPlayerHandValue() {
		round.setPlayerHandValue("11 or 21");
		assertEquals("11 or 21", round.getPlayerHandValue());
	}

	@Test
	public void testGetDealerHandValue() {
		round.setDealerHandValue(Consts.TWENTY_ONE);
		assertEquals(Consts.TWENTY_ONE, round.getDealerHandValue());
	}

	@Test
	public void testSetDealerHandValue() {
		round.setDealerHandValue(Consts.TWENTY_ONE);
		assertEquals(Consts.TWENTY_ONE, round.getDealerHandValue());
	}

	@Test
	public void testIsPlayerCanSplit() {
		round.setPlayerCanSplit(true);
		assertTrue(round.isPlayerCanSplit());
	}

	@Test
	public void testSetPlayerCanSplit() {
		round.setPlayerCanSplit(true);
		assertTrue(round.isPlayerCanSplit());
	}

	@Test
	public void testGetSplitPlayer() {
		BlackJackServiceImpl blackJackServiceImpl = new BlackJackServiceImpl();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		assertEquals( 1, round.getSplitHand().getSplitLeftCards().size() );
	}

}
