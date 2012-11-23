package org.sean.blackjack.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class RoundTest {
	
//Do I need to test this? If so, how should I test it?
//	@Test
//	public void testRound() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testRoundBooleanBooleanBooleanBooleanBooleanStringDoubleIntListOfCardListOfCard() {
		double doubleNum = 500.0;
		int intNum = 20;
		List<Card> dealerCards = new ArrayList<Card>();
		List<Card> playerCards = new ArrayList<Card>();
		Round round = new Round(false, false, false, false, false, GameMessages.PLAYER_WINS.toString(), doubleNum, intNum, dealerCards, playerCards);
		
		assertFalse(round.isPush());
		assertFalse(round.isPlayerWon());
		assertFalse(round.isBustPlayer());
		assertFalse(round.isBustPlayer());
		assertFalse(round.isPlayerHasBlackJack());
		assertEquals(GameMessages.PLAYER_WINS.toString(), round.getGameMessage());
		assertEquals(doubleNum, round.getPlayerCredits(), 0);
		assertEquals(intNum, round.getPlayerBet());
		assertEquals(0, round.getDealerCards().size());
		assertEquals(0, round.getPlayerCards().size());
	}

	@Test
	public void testCheckBustPlayer() {
		
		Round round = new Round();
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.ACE));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.JACK));
		assertFalse(round.checkBustPlayer());
		
		round.getPlayerCards().add(new Card(Suit.DIAMONDS, Rank.ACE));		
		assertTrue(round.checkBustPlayer());

	}

	@Test
	public void testCheckBustDealer() {
		Round round = new Round();
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.ACE));
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.JACK));
		assertFalse(round.checkBustDealer());
		
		round.getDealerCards().add(new Card(Suit.DIAMONDS, Rank.ACE));		
		assertTrue(round.checkBustDealer());
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
		
		//Do I need to test for all the specific cases like BlackJack here?
		Round round = new Round();
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.SEVEN));
		
		round.getPlayerCards().add(new Card(Suit.DIAMONDS, Rank.TEN));
		round.getPlayerCards().add(new Card(Suit.DIAMONDS, Rank.SIX));
		round.checkWhoWon();
		assertFalse(round.isPlayerWon());
		
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.ACE));
		round.checkWhoWon();
		assertTrue(round.isPush());
		
		round.getPlayerCards().add(new Card(Suit.DIAMONDS, Rank.ACE));
		round.checkWhoWon();
		assertTrue(round.isPlayerWon());
		
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

	//Should I have methods setDealerCards/setPlayerCards if I dont use it? Will I need it to 
	//persist the round with hibernate if that is what I do? Investigate this
	@Test
	public void testSetDealerCards() {
		Card card = new Card(Suit.HEARTS, Rank.ACE);
		List<Card> cards = new ArrayList<>();
		cards.add(card);
		Round round = new Round();
		round.setDealerCards(cards);
		
		assertEquals(Suit.HEARTS, round.getDealerCards().get(0).getSuit());
		assertEquals(Rank.ACE, round.getDealerCards().get(0).getRank());
		
	}

	@Test
	public void testGetPlayerCards() {
		Card card = new Card(Suit.HEARTS, Rank.ACE);
		Round round = new Round();
		round.getPlayerCards().add(card);
		
		assertEquals(Suit.HEARTS, round.getPlayerCards().get(0).getSuit());
		assertEquals(Rank.ACE, round.getPlayerCards().get(0).getRank());
	}

	@Test
	public void testSetPlayerCards() {
		Card card = new Card(Suit.HEARTS, Rank.ACE);
		List<Card> cards = new ArrayList<>();
		cards.add(card);
		Round round = new Round();
		round.setPlayerCards(cards);
		
		assertEquals(Suit.HEARTS, round.getPlayerCards().get(0).getSuit());
		assertEquals(Rank.ACE, round.getPlayerCards().get(0).getRank());
	}

	@Test
	public void testIsBustDealer() {
		Round round = new Round();
		round.setBustDealer(true);
		assertTrue(round.isBustDealer());
	}

	@Test
	public void testSetBustDealer() {
		Round round = new Round();
		round.setBustDealer(true);
		assertTrue(round.isBustDealer());
	}

	@Test
	public void testGetGameMessage() {
		Round round = new Round();
		round.setGameMessage(GameMessages.PLAYER_WINS.toString());
		assertEquals(GameMessages.PLAYER_WINS.toString(), round.getGameMessage());
	}

	@Test
	public void testSetGameMessage() {
		Round round = new Round();
		round.setGameMessage(GameMessages.PLAYER_WINS.toString());
		assertEquals(GameMessages.PLAYER_WINS.toString(), round.getGameMessage());
	}

	@Test
	public void testIsPlayerWon() {
		Round round = new Round();
		round.setPlayerWon(true);
		assertTrue(round.isPlayerWon());
	}

	@Test
	public void testSetPlayerWon() {
		Round round = new Round();
		round.setPlayerWon(true);
		assertTrue(round.isPlayerWon());
	}

	@Test
	public void testIsPush() {
		Round round = new Round();
		round.setPush(true);
		assertTrue(round.isPush());
	}

	@Test
	public void testSetPush() {
		Round round = new Round();
		round.setPush(true);
		assertTrue(round.isPush());
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

}