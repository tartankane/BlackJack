package org.sean.blackjack.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * The SplitPlayer class describes the player's two active hands when the player
 * enables the split card feature during a BlackJack hand. This feature is only
 * available when the player is dealt two cards of equal hand value.
 * 
 */
public class SplitPlayer {
	private String splitLeftGameMessage = "";
	private String splitRightGameMessage = "";
	private boolean splitLeftBust = false;
	private boolean splitRightBust = false;
	private boolean splitLeftHasBlackJack;
	private boolean splitRightHasBlackJack;
	private String splitLeftHandValue;
	private String splitRightHandValue;

	private List<Card> splitLeftCards = new ArrayList<Card>();
	private List<Card> splitRightCards = new ArrayList<Card>();

	/**
	 * Check that the value of the hand has not gone above 21. For the purposes
	 * of this check, an ace will always have a value of 1. If the player is
	 * bust, deduct the player's bet from the player's credits.
	 * 
	 * @param cards
	 *            - the list of cards to be checked
	 * @param isSplitLeft
	 *            - true if this is the player's left hand split, false if it is
	 *            the right hand split
	 * @return boolean - true if the cards are over 21
	 */
	public boolean checkSplitBust(List<Card> cards, boolean isSplitLeft) {
		// The total value of cards allowed in a hand of blackjack
		// is 21.
		int total = 0;
		for (Card card : cards) {
			total = total + card.getRank().getCardValue();
		}
		if (isSplitLeft) {
			if (total > Consts.TWENTY_ONE) {
				this.splitLeftBust = true;
				this.splitLeftGameMessage = Consts.PLAYER_BUST;
				return true;
			}
		} else {
			if (total > Consts.TWENTY_ONE) {
				this.splitRightBust = true;
				this.splitRightGameMessage = Consts.PLAYER_BUST;
				return true;
			}
		}
		return false;
	}

	public void calculateSplitHandValues(List<Card> cards,
			boolean playerFinishedDrawingCards, boolean isSplitLeft) {
		// The boolean playerFinishedDrawingCards indicates that the player has
		// received all
		// their cards. In this case, the Ace will be set to a fixed value of
		// either 1 or 11.
		// boolean playerFinishedDrawingCards = thePlayerIsFinishedDrawingCards;
		int total = 0;

		int numberOfPlayersAces = 0;

		for (Card card : cards) {
			total = total + card.getRank().getCardValue();
			numberOfPlayersAces = ifAceThenIncrementAceCount(
					numberOfPlayersAces, card);
		}

		if (isSplitLeft) {
			this.splitLeftHandValue = calculateHandValue(playerFinishedDrawingCards,
					total, numberOfPlayersAces);
			System.out.println("Can we even get here?");
		} else {
			this.splitRightHandValue = calculateHandValue(
					playerFinishedDrawingCards, total, numberOfPlayersAces);
		}
	}

	private String calculateHandValue(boolean playerFinishedDrawingCards,
			int total, int numberOfPlayersAces) {
		String handValue;
		handValue = String.valueOf(total);
		if ((total <= 11) && numberOfPlayersAces != 0) {
			System.out.println("What about here? and playerFinishedDrawingCards is " + playerFinishedDrawingCards);
			if (playerFinishedDrawingCards) {
				handValue = String.valueOf(total + 10);
				System.out.println("cant get here at all" );
			} else {
				handValue = handValue + " or " + String.valueOf(total + 10);
			}
		}
		return handValue;
	}

	/**
	 * If the card passed to the method is an ace, increment and return the
	 * numberOfAces count.
	 * 
	 * @param numberOfAces
	 *            - the number of aces
	 * @param card
	 *            - the card to be checked if an ace or not
	 * @return numberOfAces - the number of aces
	 */
	private int ifAceThenIncrementAceCount(int numberOfAces, Card card) {
		if ((card.getRank().getCardValue() == 1)) {
			numberOfAces++;
		}
		System.out.println("Number of aces in spltplayer ifAceThenIncrementAceCount is " + numberOfAces);
		return numberOfAces;
	}

//	public void checkWhoWon(Round round) {
//		System.out.println("I got here. I am not crazy");
//		int totalPlayer = 0;
//		int totalDealer = 0;
//		int numberOfPlayersAces = 0;
//		int numberOfDealersAces = 0;
//
//		
//		//Compare left hand side first
//
//		
//
//		// Check to see if player has a BlackJack (an Ace and a card of value
//		// 10).
//		// This is possible here because the player's hand value has been
//		// adjusted for aces
////		if ((totalPlayer == Consts.TWENTY_ONE) && (playerCards.size() == 2)) {
////			this.playerHasBlackJack = true;
////		}
//
//		if (!this.splitLeftBust) {
//			for (Card card : this.splitLeftCards) {
//				totalPlayer = totalPlayer + card.getRank().getCardValue();
//				numberOfPlayersAces = ifAceThenIncrementAceCount(
//						numberOfPlayersAces, card);
//			}
//			// Adjust player's hand value for any aces
//			for (int i = 0; i < numberOfPlayersAces; i++) {
//				if ((totalPlayer <= 11)) {
//					totalPlayer = totalPlayer + 10;
//				}
//			}
//			for (Card card : round.getDealerCards()) {
//				totalDealer = totalDealer + card.getRank().getCardValue();
//				numberOfDealersAces = ifAceThenIncrementAceCount(
//						numberOfDealersAces, card);
//			}
//			// Adjust dealer's hand value for any aces
//			for (int i = 0; i < numberOfDealersAces; i++) {
//				if ((totalDealer <= 11)) {
//					totalDealer = totalDealer + 10;
//				}
//			}
//			if (totalPlayer == totalDealer) {
//
//				// Check to see if the player has BlackJack (an Ace and a card of
//				// value
//				// 10) but the dealer doesn't. If so, the player wins.
//				//			if (this.playerHasBlackJack && (dealerCards.size() > 2)) {
//				//				this.gameMessage = Consts.PLAYER_WINS_WITH_BLACKJACK;
//				//				this.playerCredits += 1.5 * this.playerBet;
//				//				// Check to see if the dealer has BlackJack (an Ace and a card
//				//				// of value 10) but the player doesn't. If so, the dealer wins.
//				//			} else if ((totalDealer == Consts.TWENTY_ONE) && (dealerCards.size() == 2)
//				//					&& (!this.playerHasBlackJack)) {
//				//				this.gameMessage = Consts.DEALER_WINS_WITH_BLACKJACK;
//				//				this.playerCredits -= this.playerBet;
//				//				// If the player is now low on credits, set playerLowOnCredits
//				//				// to true.
//				//				checkIfPlayerLowOnCredits();
//				//			} else {
//				//				this.gameMessage = Consts.DRAW;
//				//			}
//				this.splitLeftGameMessage = Consts.DRAW;
//			}
//			if (totalPlayer > totalDealer) {
//
//				//			if (this.playerHasBlackJack) {
//				//				this.gameMessage = Consts.PLAYER_WINS_WITH_BLACKJACK;
//				//				this.playerCredits += 1.5 * this.playerBet;
//				//			} else {
//				//				this.gameMessage = Consts.PLAYER_WINS;
//				//				this.playerCredits += this.playerBet;
//				//			}
//				this.splitLeftGameMessage = Consts.PLAYER_WINS;
//				if (splitRightBust) {
//					//All of the remaining player's bet is on this hand
//					round.setPlayerCredits(round.getPlayerCredits()
//							+ round.getPlayerBet());
//
//				} else {
//					//Half the player's total bet is on this hand
//					round.setPlayerCredits(round.getPlayerCredits()
//							+ (round.getPlayerBet() / 2));
//				}
//			}
//			if (totalPlayer < totalDealer) {
//				//			if ((totalDealer == Consts.TWENTY_ONE) && (round.getDealerCards().size() == 2)) {
//				//				this.gameMessage = Consts.DEALER_WINS_WITH_BLACKJACK;
//				//			} else {
//				//				this.gameMessage = Consts.PLAYER_LOSES;
//				//			}
//				this.splitLeftGameMessage = Consts.PLAYER_LOSES;
//
//				if (splitRightBust) {
//					//All of the remaining player's bet is on this hand
//					round.setPlayerCredits(round.getPlayerCredits()
//							- round.getPlayerBet());
//
//				} else {
//					//Half the player's total bet is on this hand
//					round.setPlayerCredits(round.getPlayerCredits()
//							- (round.getPlayerBet() / 2));
//				}
//
//				// If the player is now low on credits, set playerLowOnCredits
//				// to true.
//				round.checkIfPlayerLowOnCredits();
//			}
//			System.out.println(this.splitLeftGameMessage);
//		}
//		
//		if (!this.splitRightBust) {
//			
//			totalPlayer = 0;
//			totalDealer = 0;
//			numberOfPlayersAces = 0;
//			numberOfDealersAces = 0;
//			
//			for (Card card : this.splitRightCards) {
//				totalPlayer = totalPlayer + card.getRank().getCardValue();
//				numberOfPlayersAces = ifAceThenIncrementAceCount(
//						numberOfPlayersAces, card);
//			}
//			// Adjust player's hand value for any aces
//			for (int i = 0; i < numberOfPlayersAces; i++) {
//				if ((totalPlayer <= 11)) {
//					totalPlayer = totalPlayer + 10;
//				}
//			}
//			for (Card card : round.getDealerCards()) {
//				totalDealer = totalDealer + card.getRank().getCardValue();
//				numberOfDealersAces = ifAceThenIncrementAceCount(
//						numberOfDealersAces, card);
//			}
//			// Adjust dealer's hand value for any aces
//			for (int i = 0; i < numberOfDealersAces; i++) {
//				if ((totalDealer <= 11)) {
//					totalDealer = totalDealer + 10;
//				}
//			}
//			if (totalPlayer == totalDealer) {
//
//				// Check to see if the player has BlackJack (an Ace and a card of
//				// value
//				// 10) but the dealer doesn't. If so, the player wins.
//				//			if (this.playerHasBlackJack && (dealerCards.size() > 2)) {
//				//				this.gameMessage = Consts.PLAYER_WINS_WITH_BLACKJACK;
//				//				this.playerCredits += 1.5 * this.playerBet;
//				//				// Check to see if the dealer has BlackJack (an Ace and a card
//				//				// of value 10) but the player doesn't. If so, the dealer wins.
//				//			} else if ((totalDealer == Consts.TWENTY_ONE) && (dealerCards.size() == 2)
//				//					&& (!this.playerHasBlackJack)) {
//				//				this.gameMessage = Consts.DEALER_WINS_WITH_BLACKJACK;
//				//				this.playerCredits -= this.playerBet;
//				//				// If the player is now low on credits, set playerLowOnCredits
//				//				// to true.
//				//				checkIfPlayerLowOnCredits();
//				//			} else {
//				//				this.gameMessage = Consts.DRAW;
//				//			}
//				this.splitRightGameMessage = Consts.DRAW;
//			}
//			if (totalPlayer > totalDealer) {
//
//				//			if (this.playerHasBlackJack) {
//				//				this.gameMessage = Consts.PLAYER_WINS_WITH_BLACKJACK;
//				//				this.playerCredits += 1.5 * this.playerBet;
//				//			} else {
//				//				this.gameMessage = Consts.PLAYER_WINS;
//				//				this.playerCredits += this.playerBet;
//				//			}
//				this.splitRightGameMessage = Consts.PLAYER_WINS;
//				if (splitLeftBust) {
//					//All of the remaining player's bet is on this hand
//					round.setPlayerCredits(round.getPlayerCredits()
//							+ round.getPlayerBet());
//
//				} else {
//					//Half the player's total bet is on this hand
//					round.setPlayerCredits(round.getPlayerCredits()
//							+ (round.getPlayerBet() / 2));
//				}
//			}
//			if (totalPlayer < totalDealer) {
//				//			if ((totalDealer == Consts.TWENTY_ONE) && (round.getDealerCards().size() == 2)) {
//				//				this.gameMessage = Consts.DEALER_WINS_WITH_BLACKJACK;
//				//			} else {
//				//				this.gameMessage = Consts.PLAYER_LOSES;
//				//			}
//				this.splitRightGameMessage = Consts.PLAYER_LOSES;
//
//				if (splitLeftBust) {
//					//All of the remaining player's bet is on this hand
//					round.setPlayerCredits(round.getPlayerCredits()
//							- round.getPlayerBet());
//
//				} else {
//					//Half the player's total bet is on this hand
//					round.setPlayerCredits(round.getPlayerCredits()
//							- (round.getPlayerBet() / 2));
//				}
//
//				// If the player is now low on credits, set playerLowOnCredits
//				// to true.
//				round.checkIfPlayerLowOnCredits();
//			}
//			System.out.println(this.splitRightGameMessage);
//		}
//		
//	}

	/**
	 * @return the splitLeftGameMessage
	 */
	public String getSplitLeftGameMessage() {
		return splitLeftGameMessage;
	}

	/**
	 * @param splitLeftGameMessage
	 *            the splitLeftGameMessage to set
	 */
	public void setSplitLeftGameMessage(String splitLeftGameMessage) {
		this.splitLeftGameMessage = splitLeftGameMessage;
	}

	/**
	 * @return the splitRightGameMessage
	 */
	public String getSplitRightGameMessage() {
		return splitRightGameMessage;
	}

	/**
	 * @param splitRightGameMessage
	 *            the splitRightGameMessage to set
	 */
	public void setSplitRightGameMessage(String splitRightGameMessage) {
		this.splitRightGameMessage = splitRightGameMessage;
	}

	/**
	 * @return the splitLeftBust
	 */
	public boolean isSplitLeftBust() {
		return splitLeftBust;
	}

	/**
	 * @param splitLeftBust
	 *            the splitLeftBust to set
	 */
	public void setSplitLeftBust(boolean splitLeftBust) {
		this.splitLeftBust = splitLeftBust;
	}

	/**
	 * @return the splitRightBust
	 */
	public boolean isSplitRightBust() {
		return splitRightBust;
	}

	/**
	 * @param splitRightBust
	 *            the splitRightBust to set
	 */
	public void setSplitRightBust(boolean splitRightBust) {
		this.splitRightBust = splitRightBust;
	}

	/**
	 * @return the splitLeftHasBlackJack
	 */
	public boolean isSplitLeftHasBlackJack() {
		return splitLeftHasBlackJack;
	}

	/**
	 * @param splitLeftHasBlackJack
	 *            the splitLeftHasBlackJack to set
	 */
	public void setSplitLeftHasBlackJack(boolean splitLeftHasBlackJack) {
		this.splitLeftHasBlackJack = splitLeftHasBlackJack;
	}

	/**
	 * @return the splitRightHasBlackJack
	 */
	public boolean isSplitRightHasBlackJack() {
		return splitRightHasBlackJack;
	}

	/**
	 * @param splitRightHasBlackJack
	 *            the splitRightHasBlackJack to set
	 */
	public void setSplitRightHasBlackJack(boolean splitRightHasBlackJack) {
		this.splitRightHasBlackJack = splitRightHasBlackJack;
	}

	/**
	 * @return the splitLeftHandValue
	 */
	public String getSplitLeftHandValue() {
		return splitLeftHandValue;
	}

	/**
	 * @param splitLeftHandValue
	 *            the splitLeftHandValue to set
	 */
	public void setSplitLeftHandValue(String splitLeftHandValue) {
		this.splitLeftHandValue = splitLeftHandValue;
	}

	/**
	 * @return the splitRightHandValue
	 */
	public String getSplitRightHandValue() {
		return splitRightHandValue;
	}

	/**
	 * @param splitRightHandValue
	 *            the splitRightHandValue to set
	 */
	public void setSplitRightHandValue(String splitRightHandValue) {
		this.splitRightHandValue = splitRightHandValue;
	}

	/**
	 * @return the splitLeftCards
	 */
	public List<Card> getSplitLeftCards() {
		return splitLeftCards;
	}

	/**
	 * @param splitLeftCards
	 *            the splitLeftCards to set
	 */
	public void setSplitLeftCards(List<Card> splitLeftCards) {
		this.splitLeftCards = splitLeftCards;
	}

	/**
	 * @return the splitRightCards
	 */
	public List<Card> getSplitRightCards() {
		return splitRightCards;
	}

	/**
	 * @param splitRightCards
	 *            the splitRightCards to set
	 */
	public void setSplitRightCards(List<Card> splitRightCards) {
		this.splitRightCards = splitRightCards;
	}

}