package org.sean.blackjack.domain;

import java.util.ArrayList;
import java.util.List;

public class SplitPlayer {
	private String splitLeftGameMessage;
	private String splitRightGameMessage;
	private List<Card> splitLeftPlayerCards = new ArrayList<Card>();
	private List<Card> splitRightPlayerCards = new ArrayList<Card>();

	/**
	 * Check that the value of the hand has not gone above 21. For the purposes
	 * of this check, an ace will always have a value of 1. If the player is
	 * bust, deduct the player's bet from the player's credits. 
	 * 
	 * @param cards
	 *            - the list of cards to be checked
	 * @param isSplitLeft
	 *            - true if this is the player's left hand split, false if
	 *            	it is the right hand split
	 * @return boolean - true if the cards are over 21
	 */
	public boolean checkSplitBust(List<Card> cards, boolean isSplitLeft) {
		// The total value of cards allowed in a hand of blackjack
		// is 21.
		int totalValueOfCardsAllowed = 21;
		int total = 0;
		for (Card card : cards) {
			total = total + card.getRank().getCardValue();
		}
		if (isSplitLeft) {
			if (total > totalValueOfCardsAllowed) {
				// this.bustPlayer = true;
				this.splitLeftGameMessage = Consts.PLAYER_BUST;
				// this.playerCredits -= this.playerBet;
				// // If the player is now low on credits, set
				// playerLowOnCredits
				// // to true.
				// checkIfPlayerLowOnCredits();
				return true;
			}
		} else {
			if (total > totalValueOfCardsAllowed) {
				// this.bustPlayer = true;
				// this.gameMessage = Consts.PLAYER_BUST;
				// this.playerCredits -= this.playerBet;
				// // If the player is now low on credits, set
				// playerLowOnCredits
				// // to true.
				// checkIfPlayerLowOnCredits();
				return true;
			}
		}
		return false;
	}

	public List<Card> getSplitLeftPlayerCards() {
		return splitLeftPlayerCards;
	}

	public void setSplitLeftPlayerCards(List<Card> splitLeftPlayerCards) {
		this.splitLeftPlayerCards = splitLeftPlayerCards;
	}

	public List<Card> getSplitRightPlayerCards() {
		return splitRightPlayerCards;
	}

	public void setSplitRightPlayerCards(List<Card> splitRightPlayerCards) {
		this.splitRightPlayerCards = splitRightPlayerCards;
	}

	public String getSplitLeftGameMessage() {
		return splitLeftGameMessage;
	}

	public void setSplitLeftGameMessage(String splitLeftGameMessage) {
		this.splitLeftGameMessage = splitLeftGameMessage;
	}

	public String getSplitRightGameMessage() {
		return splitRightGameMessage;
	}

	public void setSplitRightGameMessage(String splitRightGameMessage) {
		this.splitRightGameMessage = splitRightGameMessage;
	}

}
