package org.sean.blackjack.services;

import org.sean.blackjack.domain.Card;
import org.sean.blackjack.domain.Deck;
import org.sean.blackjack.domain.GameMessages;
import org.sean.blackjack.domain.Round;
import org.springframework.stereotype.Service;

@Service
public class BlackJackServiceImpl implements BlackJackService {

	private int betSize = 20;
	private int initialPlayerCredits = 500;

	// public Round initializeTable(Round round) {
	public void initializeTable(Round round) {
		round.setPlayerCredits(initialPlayerCredits);
		round.setPlayerBet(betSize);
		// return round;
	}

	// public Round startRound(Round round) {
	public void startRound(Round round) {

		round.setBustPlayer(false);
		round.setBustDealer(false);
		round.setPlayerWon(false);
		round.setPush(false);
		round.setPlayerHasBlackJack(false);
		round.setGameMessage(GameMessages.BLANK_MESSAGE.toString());
		// There is a fixed bet of betSize credits per round
		round.setPlayerCredits(round.getPlayerCredits() - betSize);
		round.setPlayerBet(betSize);
		round.getDealerCards().clear();
		round.getPlayerCards().clear();

		// Deal a single card to the dealer.
		// The dealer's hidden card is added to the display in bjapp.start.js
		// Note that there is no actual hidden card. It is added later.
		Card card = Deck.dealRandomCard();
		round.getDealerCards().add(card);

		// Deal the two starting cards to the player
		card = Deck.dealRandomCard();
		round.getPlayerCards().add(card);
		card = Deck.dealRandomCard();
		round.getPlayerCards().add(card);

		// return round;
	}

	public void hitPlayer(Round round) {
		// public Round hitPlayer(Round round) {
		Card card = Deck.dealRandomCard();
		round.getPlayerCards().add(card);

		round.checkBustPlayer();
		// return round;
	}

	public void playerStands(Round round) {
		// public Round playerStands(Round round) {
		// loop until either the dealer must stand or the dealer is bust
		while (!round.dealerMustStand()) {
			Card card = Deck.dealRandomCard();
			round.getDealerCards().add(card);
			if (round.checkBustDealer()) {
				// If the player wins, return the player's bet plus an equal
				// amount for winning
				
				
// commented out for test				
//				round.setPlayerCredits(round.getPlayerCredits() + (2 * betSize));
				
				
				// return round;
				return;
			}
		}
		round.checkWhoWon();

//		if (round.isPush()) {
//			// If the round is a draw. Return the player's bet.
//			round.setPlayerCredits(round.getPlayerCredits() + betSize);
//		} else if (round.isPlayerWon()) {
//			// Check for BlackJack (Ace and a card of value 10), which pays a
//			// winning bonus of half the
//			// player's bet
//			if (round.isPlayerHasBlackJack()) {
//				round.setPlayerCredits(round.getPlayerCredits()
//						+ (2.5 * betSize));
//			} else {
//				// If the player wins, return the player's bet plus an equal
//				// amount for winning
//				round.setPlayerCredits(round.getPlayerCredits() + (2 * betSize));
//			}
//		}
		// If the player loses, there is no need to do anything as the bet has
		// already been
		// deducted from the player's credits.

		// return round;

	}

}
