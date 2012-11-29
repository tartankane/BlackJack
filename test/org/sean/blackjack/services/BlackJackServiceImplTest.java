package org.sean.blackjack.services;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.sean.blackjack.domain.Card;
import org.sean.blackjack.domain.Consts;
//import org.sean.blackjack.domain.GameMessages;
import org.sean.blackjack.domain.Rank;
import org.sean.blackjack.domain.Round;
import org.sean.blackjack.domain.Suit;

public class BlackJackServiceImplTest {

	@Test
	public void testInitializeTable() {
		int betSize = 10;
		int initialPlayerCredits = 1000;
		BlackJackService blackJackService = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackService.initializeTable(round);
		assertEquals( initialPlayerCredits, round.getPlayerCredits(), 0 );
		assertEquals( betSize, round.getPlayerBet() );
		
	}

	@Test
	public void testStartRound() {
		int betSize = 10;
		int initialPlayerCredits = 1000;
		int numberOfDealerCards = 1;
		int numberOfPlayerCards = 2;
		int indexOfFirstCardInList = 0;
		BlackJackService blackJackService = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackService.initializeTable(round);
		blackJackService.startRound(round);
		
		assertFalse(round.isPush());
		assertFalse(round.isPlayerWon());
		assertFalse(round.isBustPlayer());
		assertFalse(round.isBustPlayer());
		assertFalse(round.isPlayerHasBlackJack());
		assertEquals(Consts.BLANK_MESSAGE, round.getGameMessage());
//		assertEquals(GameMessages.BLANK_MESSAGE.toString(), round.getGameMessage());
		assertEquals(initialPlayerCredits, round.getPlayerCredits(), 0);
		assertEquals(betSize, round.getPlayerBet());
		assertEquals(numberOfDealerCards, round.getDealerCards().size());
		assertEquals(numberOfPlayerCards, round.getPlayerCards().size());
		
		//Test that the objects in the dealerCards and playerCards are Cards
		assertEquals( (new Card()).getClass(), round.getDealerCards().get(indexOfFirstCardInList).getClass() );
		assertEquals( (new Card()).getClass(), round.getPlayerCards().get(indexOfFirstCardInList).getClass() );
	}

	@Test
	public void testHitPlayer() {
		int numberOfPlayerCardsBeforeHit = 2;
		int numberOfPlayerCardsPerHit = 1;
		BlackJackService blackJackService = new BlackJackServiceImpl();
		Round round = new Round();
		blackJackService.initializeTable(round);
		blackJackService.startRound(round);
		blackJackService.hitPlayer(round);
		assertEquals(numberOfPlayerCardsBeforeHit + numberOfPlayerCardsPerHit, round.getPlayerCards().size());
		blackJackService.hitPlayer(round);
		assertEquals(numberOfPlayerCardsBeforeHit + (2 * numberOfPlayerCardsPerHit), round.getPlayerCards().size());
	}
	
	// This method has random cards internally which makes it impossible to test it completely.
		@Test
		public void testPlayerDoubles() {
			int betBeforeDouble;
			int betAfterDouble;			
			Round round = new Round();
			BlackJackService blackJackService = new BlackJackServiceImpl();
			blackJackService.initializeTable(round);
			blackJackService.startRound(round);
			betBeforeDouble = round.getPlayerBet();
			blackJackService.playerDoubles(round);
			betAfterDouble = round.getPlayerBet(); 
			
			assertEquals(3, round.getPlayerCards().size());
			assertEquals(betBeforeDouble*2, betAfterDouble);
			

		}
	
// This method has random cards internally which makes it impossible to test it completely.
	@Test
	public void testPlayerStands() {
		//Set up a new round with blank player and dealer hands
		Round round = new Round();
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
		assertTrue(round.getDealerCards().size()==1);
		

		round.getDealerCards().clear();
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		blackJackService.playerStands(round);
		assertTrue(round.isPlayerHasBlackJack());
		assertTrue(round.getDealerCards().size()!=1);

	}
	
//	@Test
//	public void testGetMoreCredits() {
//		Round round = new Round();
//		round.setPlayerCredits(0);
//		BlackJackService blackJackService = new BlackJackServiceImpl();
//		blackJackService.getMoreCredits(round);
//		assertEquals(Consts.STARTING_CREDITS, round.getPlayerCredits(), 0);
//	}
	
	@Test
	public void testChangeBet() {
		Round round = new Round();
		String betSize = "100";
		BlackJackService blackJackService = new BlackJackServiceImpl();
		blackJackService.changeBet(round, betSize);
		assertEquals(100, round.getPlayerBet());
	}

}
