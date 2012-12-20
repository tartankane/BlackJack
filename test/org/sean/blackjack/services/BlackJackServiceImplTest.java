package org.sean.blackjack.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.sean.blackjack.domain.Card;
import org.sean.blackjack.domain.Consts;
import org.sean.blackjack.domain.Rank;
import org.sean.blackjack.domain.Round;
import org.sean.blackjack.domain.Suit;

public class BlackJackServiceImplTest {

	@Test
	public void testInitializeTable() {
		int betSize = 10;
		int initialPlayerCredits = 1000;
		BlackJackService blackJackService = new BlackJackServiceImpl();
		Round round = Round.getInstance();
		blackJackService.initializeTable(round);
		assertEquals(initialPlayerCredits, round.getPlayerCredits(), 0);
		assertEquals(betSize, round.getPlayerBet());
	}

	@Test(expected=IllegalArgumentException.class)
	public void negativeTestInitializeTable() {
		new BlackJackServiceImpl().initializeTable(null);
	}
	
	@Test
	public void testStartRound() {
		int betSize = 10;
		int initialPlayerCredits = 1000;
		int numberOfDealerCards = 1;
		int numberOfPlayerCards = 2;
		int indexOfFirstCardInList = 0;
		BlackJackService blackJackService = new BlackJackServiceImpl();
		Round round = Round.getInstance();
		blackJackService.initializeTable(round);
		blackJackService.startRound(round);

		// assertFalse(round.isPush());
		// assertFalse(round.isPlayerWon());
		assertFalse(round.isBustPlayer());
		assertFalse(round.isBustPlayer());
		assertFalse(round.isPlayerHasBlackJack());
		assertEquals(Consts.BLANK_MESSAGE, round.getGameMessage());
		// assertEquals(GameMessages.BLANK_MESSAGE.toString(),
		// round.getGameMessage());
		assertEquals(initialPlayerCredits, round.getPlayerCredits(), 0);
		assertEquals(betSize, round.getPlayerBet());
		assertEquals(numberOfDealerCards, round.getDealerCards().size());
		assertEquals(numberOfPlayerCards, round.getPlayerCards().size());

		// Test that the objects in the dealerCards and playerCards are Cards
		assertEquals(new Card(Suit.SPADES, Rank.ACE).getClass(),
				round.getDealerCards().get(indexOfFirstCardInList).getClass());
		assertEquals(new Card(Suit.SPADES, Rank.ACE).getClass(),
				round.getPlayerCards().get(indexOfFirstCardInList).getClass());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestStartRound() {
		new BlackJackServiceImpl().startRound(null);
	}

	@Test
	public void testHitPlayer() {
		int numberOfPlayerCardsBeforeHit = 2;
		int numberOfPlayerCardsPerHit = 1;
		BlackJackService blackJackService = new BlackJackServiceImpl();
		Round round = Round.getInstance();
		blackJackService.initializeTable(round);
		blackJackService.startRound(round);
		blackJackService.hitPlayer(round);
		assertEquals(numberOfPlayerCardsBeforeHit + numberOfPlayerCardsPerHit,
				round.getPlayerCards().size());
		blackJackService.hitPlayer(round);
		assertEquals(numberOfPlayerCardsBeforeHit
				+ (2 * numberOfPlayerCardsPerHit), round.getPlayerCards()
				.size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestHitPlayer() {
		new BlackJackServiceImpl().hitPlayer(null);
	}

	// This method has random cards internally which makes it impossible to test
	// it completely.
	@Test
	public void testPlayerDoubles() {
		int betBeforeDouble;
		int betAfterDouble;
		Round round = Round.getInstance();
		BlackJackService blackJackService = new BlackJackServiceImpl();
		blackJackService.initializeTable(round);
		blackJackService.startRound(round);
		betBeforeDouble = round.getPlayerBet();
		blackJackService.playerDoubles(round);
		betAfterDouble = round.getPlayerBet();

		assertEquals(3, round.getPlayerCards().size());
		assertEquals(betBeforeDouble * 2, betAfterDouble);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestPlayerDoubles() {
		new BlackJackServiceImpl().playerDoubles(null);
	}

	// This method has random cards internally which makes it impossible to test
	// it completely.
	@Test
	public void testPlayerStands() {
		// Set up a new round with blank player and dealer hands
		Round round = Round.getInstance();
		BlackJackService blackJackService = new BlackJackServiceImpl();
		blackJackService.initializeTable(round);
		blackJackService.startRound(round);
		round.getPlayerCards().clear();
		round.getDealerCards().clear();

		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.ACE));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.SIX));
		blackJackService.playerStands(round);
		assertTrue(round.isPlayerHasBlackJack());
		assertTrue(round.getDealerCards().size() == 1);

		round.getDealerCards().clear();
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		blackJackService.playerStands(round);
		assertTrue(round.isPlayerHasBlackJack());
		assertTrue(round.getDealerCards().size() != 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestPlayerStands() {
		new BlackJackServiceImpl().playerStands(null);
	}

	@Test
	public void testChangeBet() {
		Round round = Round.getInstance();
		String betSize = "100";
		BlackJackService blackJackService = new BlackJackServiceImpl();
		blackJackService.changeBet(round, betSize);
		assertEquals(100, round.getPlayerBet());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTest1ChangeBet() {
		String betSize = "100";
		new BlackJackServiceImpl().changeBet(null, betSize);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTest2ChangeBet() {
		String betSize = "0";
		new BlackJackServiceImpl().changeBet(Round.getInstance(), betSize);
	}

	@Test
	public void testPlayerSplits() {

		Round round = Round.getInstance();
		round.getPlayerCards().add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.TEN));

		BlackJackService blackJackService = new BlackJackServiceImpl();
		blackJackService.playerSplits(round);

		assertEquals(1, round.getSplitHand().getSplitLeftCards().size());
		assertEquals(1, round.getSplitHand().getSplitRightCards().size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestPlayerSplits() {
		new BlackJackServiceImpl().playerSplits(null);
	}

	@Test
	public void testSplitLeftHitPlayer() {
		Round round = Round.getInstance();
		BlackJackService blackJackService = new BlackJackServiceImpl();
		// Necessary to call blackJackService.startRound to make a deck.
		blackJackService.startRound(round);

		round.getPlayerCards().clear();
		round.getPlayerCards().add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		blackJackService.playerSplits(round);

		assertEquals(1, round.getSplitHand().getSplitLeftCards().size());
		blackJackService.splitLeftHitPlayer(round);
		assertEquals(2, round.getSplitHand().getSplitLeftCards().size());

	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestSplitLeftHitPlayer() {
		new BlackJackServiceImpl().splitLeftHitPlayer(null);
	}

	@Test
	public void testSplitLeftPlayerStands() {
		Round round = Round.getInstance();
		BlackJackService blackJackService = new BlackJackServiceImpl();
		// Necessary to call blackJackService.startRound to make a deck.
		blackJackService.startRound(round);
		blackJackService.playerSplits(round);

		round.getSplitHand().getSplitLeftCards().clear();
		round.getSplitHand().getSplitLeftCards()
				.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		round.getSplitHand().getSplitLeftCards()
				.add(new Card(Suit.HEARTS, Rank.ACE));

		blackJackService.splitLeftPlayerStands(round);
		assertEquals("21", round.getSplitHand().getSplitLeftHandValue());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestSplitLeftPlayerStands() {
		new BlackJackServiceImpl().splitLeftPlayerStands(null);
	}

	@Test
	public void testSplitRightHitPlayer() {
		Round round = Round.getInstance();
		BlackJackService blackJackService = new BlackJackServiceImpl();
		// Necessary to call blackJackService.startRound to make a deck.
		blackJackService.startRound(round);
		// (Consts.LOW_CREDITS_VALUE - 100) chosen as it will definitely
		// be lower than the required amount for credit top up to occur.
		round.setPlayerCredits(Consts.LOW_CREDITS_VALUE - 100);
		round.getPlayerCards().clear();
		round.getPlayerCards().add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		blackJackService.playerSplits(round);

		assertEquals(1, round.getSplitHand().getSplitRightCards().size());
		blackJackService.splitRightHitPlayer(round);
		assertEquals(2, round.getSplitHand().getSplitRightCards().size());

		assertTrue(round.getPlayerCredits() < Consts.STARTING_CREDITS);
		round.getSplitHand().getSplitRightCards()
				.add(new Card(Suit.HEARTS, Rank.JACK));
		round.getSplitHand().getSplitRightCards()
				.add(new Card(Suit.HEARTS, Rank.ACE));
		// splitRightCards now has 4 cards with a value of at least 22. So it
		// will always be bust
		blackJackService.splitRightHitPlayer(round);
		assertEquals(Consts.STARTING_CREDITS, round.getPlayerCredits(), 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestSplitRightHitPlayer() {
		new BlackJackServiceImpl().splitRightHitPlayer(null);
	}

	@Test
	public void testSplitRightPlayerStands() {
		Round round = Round.getInstance();
		BlackJackService blackJackService = new BlackJackServiceImpl();
		// Necessary to call blackJackService.startRound to make a deck.
		blackJackService.startRound(round);
		blackJackService.playerSplits(round);

		round.getSplitHand().getSplitRightCards().clear();
		round.getSplitHand().getSplitRightCards()
				.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		round.getSplitHand().getSplitRightCards()
				.add(new Card(Suit.HEARTS, Rank.ACE));

		assertEquals(1, round.getDealerCards().size());
		blackJackService.splitRightPlayerStands(round);
		assertTrue(round.getDealerCards().size() > 1);
		assertEquals("21", round.getSplitHand().getSplitRightHandValue());

	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestSplitRightPlayerStands() {
		new BlackJackServiceImpl().splitRightPlayerStands(null);
	}

}
