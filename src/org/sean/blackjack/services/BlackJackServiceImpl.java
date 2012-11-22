package org.sean.blackjack.services;

import java.util.Random;

import org.sean.blackjack.domain.Card;
import org.sean.blackjack.domain.Rank;
import org.sean.blackjack.domain.Round;
import org.sean.blackjack.domain.Suit;
import org.sean.blackjack.domain.Deck;
import org.springframework.stereotype.Service;

@Service
public class BlackJackServiceImpl implements BlackJackService {

	public Round initializeRound() {
		Round round = new Round();
		round.setBustPlayer(false);
		round.setBustPlayer(false);
		round.setGameMessage("");
		round.setPlayerWon(false);
		round.setPush(false);
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

		return round;
	}

	public Round hitPlayer(Round round) {
		Card card = Deck.dealRandomCard();
		round.getPlayerCards().add(card);

		round.checkBustPlayer();
		return round;
	}

	public Round playerStands(Round round) {
		//loop until either the dealer must stand or the dealer is bust
		while  ( !round.dealerMustStand() )  {
			Card card = Deck.dealRandomCard();
			round.getDealerCards().add(card);
			if (round.checkBustDealer()) {
				return round;
			}
		}
		round.checkWhoWon();
		return round;

	}

}
