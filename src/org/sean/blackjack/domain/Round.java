package org.sean.blackjack.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * The round object represents the current state of the BlackJack game. It is
 * the object that is passed to the client for display. There is a new instance
 * of the Round Class per session.
 * 
 */
@Component
public class Round {

	private boolean playerCanSplit;
	private boolean bustPlayer;
	private boolean playerHasBlackJack;
	private double playerCredits;
	private int playerBet;
	private String gameMessage;
	// playerHandValue is a String because Aces have a value of "1 or 11"
	// and sometimes this fact needs to be displayed to the client.
	private String playerHandValue;
	private int dealerHandValue;
	private List<Card> dealerCards = new ArrayList<Card>();
	private List<Card> playerCards = new ArrayList<Card>();
	private SplitPlayer splitPlayer;

	public Round() {
		super();
	}

	/**
	 * Calculate the values of both the player's and dealer's hands for display
	 * to the client. Convert these values to strings. In the case that the
	 * player has different possible hand values due to an ace, modify the
	 * string to show these two possible values.
	 * 
	 * @param thePlayerIsFinishedDrawingCards
	 *            - boolean indicating that the player is finished taking cards.
	 */
	public void calculateHandValues(boolean thePlayerIsFinishedDrawingCards) {
		// The boolean playerFinishedDrawingCards indicates that the player has
		// received all
		// their cards. In this case, the Ace will be set to a fixed value of
		// either 1 or 11.
		boolean playerFinishedDrawingCards = thePlayerIsFinishedDrawingCards;
		int total = 0;
		int numberOfDealersAces = 0;
		int numberOfPlayersAces = 0;

		for (Card card : playerCards) {
			total = total + card.getRank().getCardValue();
			numberOfPlayersAces = ifAceThenIncrementAceCount(
					numberOfPlayersAces, card);
		}
		this.playerHandValue = String.valueOf(total);
		if ((total <= 11) && numberOfPlayersAces != 0) {
			if (playerFinishedDrawingCards) {
				this.playerHandValue = String.valueOf(total + 10);
			} else {
				this.playerHandValue = this.playerHandValue + " or "
						+ String.valueOf(total + 10);
			}
		}

		total = 0;
		for (Card card : dealerCards) {
			total = total + card.getRank().getCardValue();
			numberOfDealersAces = ifAceThenIncrementAceCount(
					numberOfDealersAces, card);
		}
		if ((total <= 11) && numberOfDealersAces != 0) {
			dealerHandValue = total + 10;
		} else {
			dealerHandValue = total;
		}

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
		return numberOfAces;
	}

	/**
	 * Check that the value of the hand has not gone above 21. For the purposes
	 * of this check, an ace will always have a value of 1. If the player is
	 * bust, deduct the player's bet from the player's credits. If the dealer is
	 * bust, add an amount matching the player's bet to the player's credits.
	 * 
	 * @param cards
	 *            - the list of cards to be checked
	 * @param isPlayer
	 *            - true if this is the player's hand
	 * @return boolean - true if the cards are over 21
	 */
	public boolean checkBust(List<Card> cards, boolean isPlayer, boolean isSplit) {
		// The total value of cards allowed in a hand of blackjack
		// is 21.
		int totalValueOfCardsAllowed = Consts.TWENTY_ONE;
		int total = 0;
		for (Card card : cards) {
			total = total + card.getRank().getCardValue();
		}
		if (isPlayer) {
			if (total > totalValueOfCardsAllowed) {
				this.bustPlayer = true;
				this.gameMessage = Consts.PLAYER_BUST;
				this.playerCredits -= this.playerBet;
				checkIfPlayerLowOnCredits();
				return true;
			}
		} else {
			if (total > totalValueOfCardsAllowed) {
				this.gameMessage = Consts.DEALER_BUST;
				if (isSplit){
					if ( !this.splitPlayer.isSplitLeftBust() ) {
						this.splitPlayer.setSplitLeftGameMessage(Consts.DEALER_BUST);
					}
					if ( !this.splitPlayer.isSplitRightBust() ) {
						this.splitPlayer.setSplitRightGameMessage(Consts.DEALER_BUST);
					}
					
					// The dealer is bust. If neither of the player's hands are bust, then increment 
					// the player credits by both bets
					if ( !this.splitPlayer.isSplitLeftBust() && !this.splitPlayer.isSplitRightBust() ) {
						this.playerCredits += 2* this.playerBet;
					} else {
						//One of the split hands is bust. This code is not reached if both hands are bust
						this.playerCredits += this.playerBet;
					}
				} else {
					// Not a split hand
					this.playerCredits += this.playerBet;
				}

				return true;
			}
		}
		return false;
	}

	/**
	 * Check if the player has a blackjack, i.e. an Ace and a card with a value
	 * of 10.
	 * 
	 * @return boolean
	 */
	public boolean playerHasBlackJack() {

		int totalPlayer = 0;
		int numberOfPlayersAces = 0;
		for (Card card : this.playerCards) {
			totalPlayer = totalPlayer + card.getRank().getCardValue();
			numberOfPlayersAces = ifAceThenIncrementAceCount(
					numberOfPlayersAces, card);
		}
		// An ace has a value of 1 in the enum "Rank". So a Blackjack will add
		// up to a value of 11
		if ((totalPlayer == 11) && (this.playerCards.size() == 2)
				&& (numberOfPlayersAces == 1)) {
			this.playerHasBlackJack = true;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check the dealer's initial visible card to see if it is possible for the
	 * dealer to make a BlackJack or not. If the initial visible card has a
	 * value between 2 and 9 inclusive, then it is impossible for the dealer to
	 * make a BlackJack. If it is an Ace, a ten or a royal, then it is possible.
	 * 
	 * @return boolean
	 */
	public boolean dealerCanNotMakeBlackJack() {
		int cardValueOfAce = 1;
		int cardValueOfTenOrRoyal = 10;
		if ((dealerCards.get(0).getRank().getCardValue() != cardValueOfAce)
				&& (dealerCards.get(0).getRank().getCardValue() != cardValueOfTenOrRoyal)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check to see if the total value of the dealer's hand has a value of
	 * between 17 and 21 inclusive.
	 * 
	 * @return boolean
	 */
	public boolean dealerMustStand() {
		int total = 0;
		int minValueToStand = 17;
		int numberOfDealersAces = 0;
		for (Card card : dealerCards) {
			total = total + card.getRank().getCardValue();
			numberOfDealersAces = ifAceThenIncrementAceCount(
					numberOfDealersAces, card);
		}

		// A dealer's ace must have a value of 11 as long as that does not
		// cause the dealer to go bust. Otherwise it keeps the default value of
		// 1.
		for (int i = 0; i < numberOfDealersAces; i++) {
			if (total <= 11) {
				total = total + 10;
			}
		}

		if ((total >= minValueToStand) && (total <= Consts.TWENTY_ONE)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check to see whether the player or the dealer has the best BlackJack
	 * hand. If the dealer wins, the player loses the bet already placed. If
	 * both player and dealer are equal strength (called a push) then the
	 * player's bet is returned. If the player wins with a BlackJack, then the
	 * player's bet is returned along with an additional 1.5 times the bet. If
	 * the player wins without a BlackJack, then the player's bet is returned
	 * along with a matching amount.
	 */
	public void checkWhoWon() {
		int totalPlayer = 0;
		int totalDealer = 0;
		int numberOfPlayersAces = 0;
		int numberOfDealersAces = 0;

		for (Card card : playerCards) {
			totalPlayer = totalPlayer + card.getRank().getCardValue();
			numberOfPlayersAces = ifAceThenIncrementAceCount(
					numberOfPlayersAces, card);
		}

		// Adjust player's hand value for any aces
		for (int i = 0; i < numberOfPlayersAces; i++) {
			if ((totalPlayer <= 11)) {
				totalPlayer = totalPlayer + 10;
			}
		}

		// Check to see if player has a BlackJack (an Ace and a card of value
		// 10).
		// This is possible here because the player's hand value has been
		// adjusted for aces
		if ((totalPlayer == Consts.TWENTY_ONE) && (playerCards.size() == 2)) {
			this.playerHasBlackJack = true;
		}

		for (Card card : dealerCards) {
			totalDealer = totalDealer + card.getRank().getCardValue();
			numberOfDealersAces = ifAceThenIncrementAceCount(
					numberOfDealersAces, card);
		}

		// Adjust dealer's hand value for any aces
		for (int i = 0; i < numberOfDealersAces; i++) {
			if ((totalDealer <= 11)) {
				totalDealer = totalDealer + 10;
			}
		}

		if (totalPlayer == totalDealer) {

			// Check to see if the player has BlackJack (an Ace and a card of
			// value
			// 10) but the dealer doesn't. If so, the player wins.
			if (this.playerHasBlackJack && (dealerCards.size() > 2)) {
				this.gameMessage = Consts.PLAYER_WINS_WITH_BLACKJACK;
				this.playerCredits += 1.5 * this.playerBet;
				// Check to see if the dealer has BlackJack (an Ace and a card
				// of value 10) but the player doesn't. If so, the dealer wins.
			} else if ((totalDealer == Consts.TWENTY_ONE) && (dealerCards.size() == 2)
					&& (!this.playerHasBlackJack)) {
				this.gameMessage = Consts.DEALER_WINS_WITH_BLACKJACK;
				this.playerCredits -= this.playerBet;
				// If the player is now low on credits, set playerLowOnCredits
				// to true.
				checkIfPlayerLowOnCredits();
			} else {
				this.gameMessage = Consts.DRAW;
			}

		}

		if (totalPlayer > totalDealer) {

			if (this.playerHasBlackJack) {
				this.gameMessage = Consts.PLAYER_WINS_WITH_BLACKJACK;
				this.playerCredits += 1.5 * this.playerBet;
			} else {
				this.gameMessage = Consts.PLAYER_WINS;
				this.playerCredits += this.playerBet;
			}
		}

		if (totalPlayer < totalDealer) {
			if ((totalDealer == Consts.TWENTY_ONE) && (dealerCards.size() == 2)) {
				this.gameMessage = Consts.DEALER_WINS_WITH_BLACKJACK;
			} else {
				this.gameMessage = Consts.PLAYER_LOSES;
			}
			this.playerCredits -= this.playerBet;
			// If the player is now low on credits, set playerLowOnCredits
			// to true.
			checkIfPlayerLowOnCredits();
		}
	}

	/**
	 * Check whether the player is low on credits. If so, set playerLowOnCredits
	 * to "true"
	 */
	public void checkIfPlayerLowOnCredits() {
		if (this.playerCredits < Consts.LOW_CREDITS_VALUE) {
			this.playerCredits = Consts.STARTING_CREDITS;
			gameMessage = Consts.LOW_CREDITS_MESSAGE;
		}
	}

	/**
	 * Check if the player's initial two cards have the same card value.
	 * If so, then set a boolean flag to true. Note that 10, Jack, Queen 
	 * and King all have the same card value in BlackJack.
	 */
	public void checkIfPlayerCanSplit() {
		//Only for testing!!!!!!!!!!!!!!!!!!!
//		if (this.playerCards.get(0).getRank().getCardValue() == this.playerCards
//				.get(1).getRank().getCardValue()) {
//			this.setPlayerCanSplit(true);
//		}
		this.setPlayerCanSplit(true);
	}

	/**
	 * If the player splits the cards, make two new empty hands. Move the player's
	 * first card into one of the new hands. Move the player's second card into the 
	 * other new hand.
	 */
	public void playerSplits() {
		splitPlayer = new SplitPlayer();
		this.splitPlayer.getSplitLeftCards().add(this.playerCards.get(0));
		this.splitPlayer.getSplitRightCards().add(this.playerCards.get(1));
	}

	
	public void checkWhoWonAfterSplit() {
		System.out.println("I got here. I am not crazy");
		int totalPlayer = 0;
		int totalDealer = 0;
		int numberOfPlayersAces = 0;
		int numberOfDealersAces = 0;

		
		//Compare left hand side first

		

		// Check to see if player has a BlackJack (an Ace and a card of value
		// 10).
		// This is possible here because the player's hand value has been
		// adjusted for aces
//		if ((totalPlayer == Consts.TWENTY_ONE) && (playerCards.size() == 2)) {
//			this.playerHasBlackJack = true;
//		}

		if (!this.splitPlayer.isSplitLeftBust()) {
			for (Card card : this.splitPlayer.getSplitLeftCards()) {
				totalPlayer = totalPlayer + card.getRank().getCardValue();
				numberOfPlayersAces = ifAceThenIncrementAceCount(
						numberOfPlayersAces, card);
			}
			// Adjust player's hand value for any aces
			for (int i = 0; i < numberOfPlayersAces; i++) {
				if ((totalPlayer <= 11)) {
					totalPlayer = totalPlayer + 10;
				}
			}
			for (Card card : this.dealerCards) {
				totalDealer = totalDealer + card.getRank().getCardValue();
				numberOfDealersAces = ifAceThenIncrementAceCount(
						numberOfDealersAces, card);
			}
			// Adjust dealer's hand value for any aces
			for (int i = 0; i < numberOfDealersAces; i++) {
				if ((totalDealer <= 11)) {
					totalDealer = totalDealer + 10;
				}
			}
			if (totalPlayer == totalDealer) {

				// Check to see if the player has BlackJack (an Ace and a card of
				// value
				// 10) but the dealer doesn't. If so, the player wins.
				//			if (this.playerHasBlackJack && (dealerCards.size() > 2)) {
				//				this.gameMessage = Consts.PLAYER_WINS_WITH_BLACKJACK;
				//				this.playerCredits += 1.5 * this.playerBet;
				//				// Check to see if the dealer has BlackJack (an Ace and a card
				//				// of value 10) but the player doesn't. If so, the dealer wins.
				//			} else if ((totalDealer == Consts.TWENTY_ONE) && (dealerCards.size() == 2)
				//					&& (!this.playerHasBlackJack)) {
				//				this.gameMessage = Consts.DEALER_WINS_WITH_BLACKJACK;
				//				this.playerCredits -= this.playerBet;
				//				// If the player is now low on credits, set playerLowOnCredits
				//				// to true.
				//				checkIfPlayerLowOnCredits();
				//			} else {
				//				this.gameMessage = Consts.DRAW;
				//			}
				this.splitPlayer.setSplitLeftGameMessage(Consts.DRAW);
			}
			if (totalPlayer > totalDealer) {

				//			if (this.playerHasBlackJack) {
				//				this.gameMessage = Consts.PLAYER_WINS_WITH_BLACKJACK;
				//				this.playerCredits += 1.5 * this.playerBet;
				//			} else {
				//				this.gameMessage = Consts.PLAYER_WINS;
				//				this.playerCredits += this.playerBet;
				//			}
				this.splitPlayer.setSplitLeftGameMessage(Consts.PLAYER_WINS);


					this.playerCredits += this.playerBet;

			}
			if (totalPlayer < totalDealer) {
				//			if ((totalDealer == Consts.TWENTY_ONE) && (round.getDealerCards().size() == 2)) {
				//				this.gameMessage = Consts.DEALER_WINS_WITH_BLACKJACK;
				//			} else {
				//				this.gameMessage = Consts.PLAYER_LOSES;
				//			}
				this.splitPlayer.setSplitLeftGameMessage(Consts.PLAYER_LOSES);

					this.playerCredits -= this.playerBet;


			}
			System.out.println(this.splitPlayer.getSplitLeftGameMessage());
		}
		


		if (!this.splitPlayer.isSplitRightBust()) {
			
			totalPlayer = 0;
			totalDealer = 0;
			numberOfPlayersAces = 0;
			numberOfDealersAces = 0;
			
			
			for (Card card : this.splitPlayer.getSplitRightCards()) {
				totalPlayer = totalPlayer + card.getRank().getCardValue();
				numberOfPlayersAces = ifAceThenIncrementAceCount(
						numberOfPlayersAces, card);
			}
			// Adjust player's hand value for any aces
			for (int i = 0; i < numberOfPlayersAces; i++) {
				if ((totalPlayer <= 11)) {
					totalPlayer = totalPlayer + 10;
				}
			}
			for (Card card : this.dealerCards) {
				totalDealer = totalDealer + card.getRank().getCardValue();
				numberOfDealersAces = ifAceThenIncrementAceCount(
						numberOfDealersAces, card);
			}
			// Adjust dealer's hand value for any aces
			for (int i = 0; i < numberOfDealersAces; i++) {
				if ((totalDealer <= 11)) {
					totalDealer = totalDealer + 10;
				}
			}
			if (totalPlayer == totalDealer) {

				this.splitPlayer.setSplitRightGameMessage(Consts.DRAW);
			}
			if (totalPlayer > totalDealer) {
				this.splitPlayer.setSplitRightGameMessage(Consts.PLAYER_WINS);

					this.playerCredits += this.playerBet;

			}
			if (totalPlayer < totalDealer) {
				this.splitPlayer.setSplitRightGameMessage(Consts.PLAYER_LOSES);


					this.playerCredits -= this.playerBet;


				// If the player is now low on credits, set playerLowOnCredits
				// to true.
				this.checkIfPlayerLowOnCredits();
			}
			System.out.println(this.splitPlayer.getSplitRightGameMessage());
		}
	
	}

	/**
	 * Peter, I read in stack overflow that I shouldn't have javadoc comments
	 * for normal getters and setters as it is just clutter. What say you?
	 */
	public boolean isBustPlayer() {
		return bustPlayer;
	}

	public void setBustPlayer(boolean bustPlayer) {
		this.bustPlayer = bustPlayer;
	}

	public double getPlayerCredits() {
		return playerCredits;
	}

	public void setPlayerCredits(double playerCredits) {
		this.playerCredits = playerCredits;
	}

	public int getPlayerBet() {
		return playerBet;
	}

	public void setPlayerBet(int playerBet) {
		this.playerBet = playerBet;
	}

	public List<Card> getDealerCards() {
		return dealerCards;
	}

	public void setDealerCards(List<Card> dealerCards) {
		this.dealerCards = dealerCards;
	}

	public List<Card> getPlayerCards() {
		return playerCards;
	}

	public void setPlayerCards(List<Card> playerCards) {
		this.playerCards = playerCards;
	}

	public String getGameMessage() {
		return gameMessage;
	}

	public void setGameMessage(String gameMessage) {
		this.gameMessage = gameMessage;
	}

	public boolean isPlayerHasBlackJack() {
		return playerHasBlackJack;
	}

	public void setPlayerHasBlackJack(boolean playerHasBlackJack) {
		this.playerHasBlackJack = playerHasBlackJack;
	}

	public String getPlayerHandValue() {
		return playerHandValue;
	}

	public void setPlayerHandValue(String playerHandValue) {
		this.playerHandValue = playerHandValue;
	}

	public int getDealerHandValue() {
		return dealerHandValue;
	}

	public void setDealerHandValue(int dealerHandValue) {
		this.dealerHandValue = dealerHandValue;
	}

	public boolean isPlayerCanSplit() {
		return playerCanSplit;
	}

	public void setPlayerCanSplit(boolean playerCanSplit) {
		this.playerCanSplit = playerCanSplit;
	}

	public SplitPlayer getSplitPlayer() {
		return splitPlayer;
	}

	public void setSplitPlayer(SplitPlayer splitPlayer) {
		this.splitPlayer = splitPlayer;
	}

}
