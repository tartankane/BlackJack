package org.sean.blackjack.services;

import org.sean.blackjack.domain.Consts;
import org.sean.blackjack.domain.Deck;
import org.sean.blackjack.domain.Round;
import org.springframework.stereotype.Service;

/**
 * This Class is part of Spring MVC's Service Layer. It implements
 * BlackJackService. The instance of the Round class is passed to it's methods
 * and updated appropriately for that stage of the BlackJack game.
 * 
 */
@Service
public class BlackJackServiceImpl implements BlackJackService {

	// Note to self: I have no getters and setters for following fields. Is this
	// a problem?
	// I guess I am not injecting into these fields.
	private Deck deck;
	private boolean playerDoubledBet = false;

	/**
	 * Initialize the Round object so that it has the values that need to be
	 * seen at the client when the player "sits down at" the BlackJack table.
	 * 
	 * @param round
	 *            - The round object represents the current state of the
	 *            BlackJack game
	 */
	public void initializeTable(Round round) {
		round.setPlayerCredits(Consts.STARTING_CREDITS);
		round.setPlayerBet(Consts.STARTING_BET);
	}

	/**
	 * Clear the values of all fields in the round object except for the
	 * player's credits and the player's bet size. Remove the player's bet from
	 * the player's credits. Deal two visible cards to the player. Deal one
	 * visible and one hidden card to the dealer.
	 * 
	 * @param round
	 *            - The round object represents the current state of the
	 *            BlackJack game
	 */
	public void startRound(Round round) {
		boolean playerFinishedDrawingCards = false;
//		Card card;
	
		round.setBustPlayer(false);
//		round.setBustDealer(false);
//		round.setPlayerWon(false);
//		round.setPush(false);
		round.setPlayerHasBlackJack(false);
		round.setGameMessage(Consts.BLANK_MESSAGE);
		// If the player doubled the bet the previous round, set it 
		// back to the correct value.
		if (this.playerDoubledBet) {
			round.setPlayerBet(round.getPlayerBet()/2);
			this.playerDoubledBet = false;
		}
		round.getDealerCards().clear();
		round.getPlayerCards().clear();

		// Get a new 52 card deck
		deck = Deck.getInstance();

		// Deal a single card to the dealer.
		// The dealer's hidden card is added to the display in start.js
		// Note that there is no actual hidden card. The image of a hidden card
		// is displayed
		// at the client and represents the dealer's second card. The dealer's
		// actual second card is added
		// after the player stands.
//		card = deck.dealRandomCard();
//		round.getDealerCards().add(card);
		round.getDealerCards().add(deck.dealRandomCard());

		// Deal the two starting cards to the player
//		card = deck.dealRandomCard();
//		round.getPlayerCards().add(card);
//		card = deck.dealRandomCard();
//		round.getPlayerCards().add(card);
		round.getPlayerCards().add(deck.dealRandomCard());
		round.getPlayerCards().add(deck.dealRandomCard());

		round.calculateHandValues(playerFinishedDrawingCards);
	}

	/**
	 * Deal a single card to the player and check to see if the player has gone
	 * bust.
	 * 
	 * @param round
	 *            - The round object represents the current state of the
	 *            BlackJack game
	 */
	public void hitPlayer(Round round) {
		boolean isPlayer = true;
		boolean playerFinishedDrawingCards = false;
//		Card card = deck.dealRandomCard();
//		round.getPlayerCards().add(card);
		round.getPlayerCards().add(deck.dealRandomCard());
//		round.checkBustPlayer();
		round.checkBust(round.getPlayerCards(), isPlayer);
		round.calculateHandValues(playerFinishedDrawingCards);
	}

	/**
	 * Double the player's bet. The player receives only a single card when
	 * doubling. Check if the player has gone bust. Deal cards to the dealer
	 * until the dealer either goes bust or stands. When the dealer stands,
	 * check who won.
	 * 
	 * @param round
	 *            - The round object represents the current state of the
	 *            BlackJack game
	 */
	public void playerDoubles(Round round) {
		boolean isPlayer = true;
		boolean playerFinishedDrawingCards = false;
		
		//Double the player's bet
		round.setPlayerBet(2 * round.getPlayerBet());
		this.playerDoubledBet = true;
//		Card card;
//		card = deck.dealRandomCard();
//		round.getPlayerCards().add(card);
		round.getPlayerCards().add(deck.dealRandomCard());
		playerFinishedDrawingCards = true;
//		if (round.checkBustPlayer()) {
		if (round.checkBust(round.getPlayerCards(), isPlayer)) {
			round.calculateHandValues(playerFinishedDrawingCards);
			return;
		}
		this.playerStands(round);
	}

	/**
	 * When the player stands, first of all, check if the player has a winning
	 * blackjack. If not, deal cards to the dealer until the dealer either goes
	 * bust or stands. When the dealer stands, check who won.
	 * 
	 * @param round
	 *            - The round object represents the current state of the
	 *            BlackJack game
	 */
	public void playerStands(Round round) {
		boolean isPlayer = false;
		boolean playerFinishedDrawingCards = true;

		// Check if the player has blackjack and that the dealer's visible card
		// excludes the possibility of making a jackpot (visible card has value
		// of 2 to 9).
		// If so, immediately run the round.checkWhoWon() which will assign the
		// win to the player
		if (round.playerHasBlackJack() && round.dealerCanNotMakeBlackJack()) {
			round.checkWhoWon();
			round.calculateHandValues(playerFinishedDrawingCards);
			return;
		}

		// Deal cards to the dealer until the dealer either goes bust or stands
		while (!round.dealerMustStand()) {
//			Card card = deck.dealRandomCard();
//			round.getDealerCards().add(card);
			round.getDealerCards().add(deck.dealRandomCard());
			/*
			 * If the player has a BlackJack, then the dealer should not be
			 * dealt more than two cards in total
			 */
			if (round.playerHasBlackJack()
					&& round.getDealerCards().size() == 2) {
				round.checkWhoWon();
				round.calculateHandValues(playerFinishedDrawingCards);
				return;
			}
//			if (round.checkBustDealer()) {
			if (round.checkBust(round.getDealerCards(), isPlayer)) {
				round.calculateHandValues(playerFinishedDrawingCards);
				return;
			}
		}
		// The dealer stands. Check who won.
		round.checkWhoWon();
		round.calculateHandValues(playerFinishedDrawingCards);
	}

	/**
	 * Change the player's bet
	 * 
	 * @param round
	 *            - The round object represents the current state of the
	 *            BlackJack game
	 * @param betSize - a String received from the client and set by a 
	 * 					drop down list
	 */
	public void changeBet(Round round, String betSize) {
		round.setPlayerBet(Integer.valueOf(betSize));
		// In case the player just doubled the bet, set playerDoubledBet
		// to false so that the new bet value set by the client will not be 
		// modified by this.startRound().
		playerDoubledBet=false;
	}

}
