package org.sean.blackjack.services;

import org.sean.blackjack.domain.Card;
import org.sean.blackjack.domain.Deck;
import org.sean.blackjack.domain.GameMessages;
import org.sean.blackjack.domain.Round;
import org.springframework.stereotype.Service;

/**
 * This Class is part of Spring MVC's Service Layer. It implements
 * BlackJackService. The instance of the Round class is passed to it's methods which
 * update the instance of the Round class appropriately for that stage of the BlackJack game.
 * 
 */
@Service
public class BlackJackServiceImpl implements BlackJackService {

//Note that I have no getters and setters for following fields. Is this a problem?
	//I guess I am not injecting into these fields.
	private int betSize = 20;
	private int initialPlayerCredits = 500;
	private Deck deck;

	/** 
	 * Initialize the Round object so that it has the values that need to be seen at 
	 * the client when the player "sits down at" the BlackJack table.
	 */
	public void initializeTable(Round round) {
		round.setPlayerCredits(initialPlayerCredits);
		round.setPlayerBet(betSize);
	}

	/** 
	 * Clear the values of all fields in the round object except for the player's credits
	 * and the player's bet size.
	 * Remove the player's bet from the player's credits.
	 * Deal two visible cards to the player. Deal one visible and one hidden card
	 * to the dealer.
	 */
	public void startRound(Round round) {
		Card card;
		
		round.setBustPlayer(false);
		round.setBustDealer(false);
		round.setPlayerWon(false);
		round.setPush(false);
		round.setPlayerHasBlackJack(false);
		round.setGameMessage(GameMessages.BLANK_MESSAGE.toString());
		round.setPlayerCredits(round.getPlayerCredits() - betSize);
		round.setPlayerBet(betSize);
		round.getDealerCards().clear();
		round.getPlayerCards().clear();
		
		//Get a new 52 card deck
		deck = Deck.getInstance();

		// Deal a single card to the dealer.
		// The dealer's hidden card is added to the display in bjapp.start.js
		// Note that there is no actual hidden card. The image of a hidden card is displayed
		// at the client and represents the dealer's second card. The dealer's second card is added
		// after the player stands.
		card = deck.dealRandomCard();
		round.getDealerCards().add(card);

		// Deal the two starting cards to the player
		card = deck.dealRandomCard();
		round.getPlayerCards().add(card);
		card = deck.dealRandomCard();
		round.getPlayerCards().add(card);
	}

	
	/** 
	 * Deal a single card to the player and check to see if the player has gone bust.
	 */
	public void hitPlayer(Round round) {

		Card card = deck.dealRandomCard();
		round.getPlayerCards().add(card);
		round.checkBustPlayer();
	}

	
	/** 
	 * When the player stands, first of all, check if the player has a winning blackjack. 
	 * If not, deal cards to the dealer until the dealer either goes bust or stands. 
	 * When the dealer stands, check who won.
	 */
	public void playerStands(Round round) {

		// Check if the player has blackjack and that the dealer's visible card 
		// excludes the possibility of making a jackpot (visible card has value of 2 to 9).
		// If so, immediately run the round.checkWhoWon() which will assign the win to the player
		if (round.playerHasBlackJack() && round.dealerCanNotMakeBlackJack()) {
			round.checkWhoWon();
			return;
		}

		// Deal cards to the dealer until the dealer either goes bust or stands
		while (!round.dealerMustStand()) {
			Card card = deck.dealRandomCard();
			round.getDealerCards().add(card);
			// if (round.playerHasBlackJack() &&
			// round.getDealerCards().size()==2){
			// round.checkWhoWon();
			// return;
			// }
			if (round.checkBustDealer()) {
				return;
			}
		}
		// If the dealer has not gone bust, check who won.
		round.checkWhoWon();
	}

}
