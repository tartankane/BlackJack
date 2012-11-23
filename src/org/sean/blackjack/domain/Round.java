package org.sean.blackjack.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Round {

	// a push in BlackJack means that both player and dealer have equal value
	// hands, so neither loses. The player's bet is returned to the player.
	private boolean push;
	private boolean playerWon;
	private boolean bustPlayer;
	private boolean bustDealer;
	private boolean playerHasBlackJack;
	private String gameMessage;
	private double playerCredits;
	private int playerBet;
	private List<Card> dealerCards = new ArrayList<Card>();
	private List<Card> playerCards = new ArrayList<Card>();

	public Round() {
		super();
	}

	public Round(boolean push, boolean playerWon, boolean bustPlayer,
			boolean bustDealer, boolean playerHasBlackJack, String gameMessage,
			double playerCredits, int playerBet, List<Card> dealerCards,
			List<Card> playerCards) {
		super();
		this.push = push;
		this.playerWon = playerWon;
		this.bustPlayer = bustPlayer;
		this.bustDealer = bustDealer;
		this.playerHasBlackJack = playerHasBlackJack;
		this.gameMessage = gameMessage;
		this.playerCredits = playerCredits;
		this.playerBet = playerBet;
		this.dealerCards = dealerCards;
		this.playerCards = playerCards;
	}

	public boolean checkBustPlayer() {
		int total = 0;
		// The total value of cards allowed in the player's hand in
		// a game of blackjack is 21.
		int totalValueOfCardsAllowed = 21;
		for (Card card : playerCards) {
			total = total + card.getRank().getCardValue();
		}
		if (total > totalValueOfCardsAllowed) {
			this.setBustPlayer(true);
			this.setPlayerWon(false);
			this.setGameMessage(GameMessages.PLAYER_BUST.toString());
			return true;
		}
		return false;
	}

	public boolean checkBustDealer() {
		int total = 0;
		// The total value of cards allowed in the dealer's hand in
		// a game of blackjack is 21.
		int totalValueOfCardsAllowed = 21;
		for (Card card : dealerCards) {
			total = total + card.getRank().getCardValue();
		}
		if (total > totalValueOfCardsAllowed) {
			this.setBustDealer(true);
			this.setPlayerWon(true);
			this.setGameMessage(GameMessages.DEALER_BUST.toString());
			this.setPlayerCredits(this.getPlayerCredits() + (2 * this.playerBet));		
			return true;
		}
		return false;
	}

	public boolean dealerMustStand() {
		int total = 0;
		int minValueToStand = 17;
		int maxValueToStand = 21;
		int numberOfDealersAces = 0;
		for (Card card : dealerCards) {
			total = total + card.getRank().getCardValue();
			// A dealer's ace must have a value of 11 as long as that does not
			// cause the dealer
			// to go bust. Otherwise it keeps the default value of 1. This if
			// statement checks that
			// the card is an ace and that the total hand value is 11 if the ace
			// is given a value of one.
			// If so, 10 is added to the hand value to give a total value of 11
			// for the ace.
			if ((card.getRank().getCardValue() == 1)) {
				numberOfDealersAces++;
			}
		}
		
		//Change the card value of an ace to 11 where appropriate. Be
		//careful changing this logic as it can be tricky.
		for (int i = 0; i < numberOfDealersAces; i++) {
			if (total <= 11) {
				total = total + 10;
			}		
		}

		if ((total >= minValueToStand) && (total <= maxValueToStand)) {
			return true;
		} else {
			return false;
		}
	}

	public void checkWhoWon() {
		int totalPlayer = 0;
		int totalDealer = 0;
		int numberOfPlayersAces = 0;
		int numberOfDealersAces = 0;

		for (Card card : playerCards) {
			totalPlayer = totalPlayer + card.getRank().getCardValue();
			if ((card.getRank().getCardValue() == 1)) {
				numberOfPlayersAces++;
			}
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
		if ((totalPlayer == 21) && (playerCards.size() == 2)) {
			this.setPlayerHasBlackJack(true);
		}

		for (Card card : dealerCards) {
			totalDealer = totalDealer + card.getRank().getCardValue();
			if ((card.getRank().getCardValue() == 1)) {
				numberOfDealersAces++;
			}
		}

		// Adjust dealer's hand value for any aces
		for (int i = 0; i < numberOfDealersAces; i++) {
			if ((totalDealer <= 11)) {
				totalDealer = totalDealer + 10;
			}
		}

		// I made the following if statements less convoluted.
		//they were all if else if etc and difficult to read
		if (totalPlayer == totalDealer) {
			
			// Check to see if the player has BlackJack (an Ace and a card of value
			// 10) but the dealer doesn't. If so, the player wins.
			if ( this.isPlayerHasBlackJack() && (dealerCards.size() > 2) ) {
				this.setPlayerWon(true);
				this.setGameMessage(GameMessages.PLAYER_WINS_WITH_BLACKJACK
						.toString());
				this.setPlayerCredits(this.getPlayerCredits() + (2.5 * this.playerBet));
				
			} else {
				this.setPush(true);
				this.setGameMessage(GameMessages.DRAW.toString());
				this.setPlayerCredits(this.getPlayerCredits() + this.playerBet);
			}

		}

		if (totalPlayer > totalDealer) {

			if (this.isPlayerHasBlackJack()) {
				this.setPlayerWon(true);
				this.setGameMessage(GameMessages.PLAYER_WINS_WITH_BLACKJACK
						.toString());
				this.setPlayerCredits(this.getPlayerCredits() + (2.5 * this.playerBet));
			} else {
				this.setPlayerWon(true);
				this.setGameMessage(GameMessages.PLAYER_WINS.toString());
				this.setPlayerCredits(this.getPlayerCredits() + (2 * this.playerBet));
			}
			
//			this.setPlayerWon(true);
		}
		
		if (totalPlayer < totalDealer) {
			this.setPlayerWon(false);
			this.setGameMessage(GameMessages.PLAYER_LOSES.toString());
			
			//credits were already deducted from the player at the start of the round so no need to do anything
		}

	}

	/**
	 * @return the bustPlayer
	 */
	public boolean isBustPlayer() {
		return bustPlayer;
	}

	/**
	 * @param bustPlayer
	 *            the bustPlayer to set
	 */
	public void setBustPlayer(boolean bustPlayer) {
		this.bustPlayer = bustPlayer;
	}

	/**
	 * @return the playerCredits
	 */
	public double getPlayerCredits() {
		return playerCredits;
	}

	/**
	 * @param playerCredits
	 *            the playerCredits to set
	 */
	public void setPlayerCredits(double playerCredits) {
		this.playerCredits = playerCredits;
	}

	/**
	 * @return the playerBet
	 */
	public int getPlayerBet() {
		return playerBet;
	}

	/**
	 * @param playerBet
	 *            the playerBet to set
	 */
	public void setPlayerBet(int playerBet) {
		this.playerBet = playerBet;
	}

	/**
	 * @return the dealerCards
	 */
	public List<Card> getDealerCards() {
		return dealerCards;
	}

	/**
	 * @param dealerCards
	 *            the dealerCards to set
	 */
	public void setDealerCards(List<Card> dealerCards) {
		this.dealerCards = dealerCards;
	}

	/**
	 * @return the playerCards
	 */
	public List<Card> getPlayerCards() {
		return playerCards;
	}

	/**
	 * @param playerCards
	 *            the playerCards to set
	 */
	public void setPlayerCards(List<Card> playerCards) {
		this.playerCards = playerCards;
	}

	public boolean isBustDealer() {
		return bustDealer;
	}

	public void setBustDealer(boolean bustDealer) {
		this.bustDealer = bustDealer;
	}

	public String getGameMessage() {
		return gameMessage;
	}

	public void setGameMessage(String gameMessage) {
		this.gameMessage = gameMessage;
	}

	/**
	 * @return the playerWon
	 */
	public boolean isPlayerWon() {
		return playerWon;
	}

	/**
	 * @param playerWon
	 *            the playerWon to set
	 */
	public void setPlayerWon(boolean playerWon) {
		this.playerWon = playerWon;
	}

	public boolean isPush() {
		return push;
	}

	public void setPush(boolean push) {
		this.push = push;
	}

	/**
	 * @return the playerHasBlackJack
	 */
	public boolean isPlayerHasBlackJack() {
		return playerHasBlackJack;
	}

	/**
	 * @param playerHasBlackJack
	 *            the playerHasBlackJack to set
	 */
	public void setPlayerHasBlackJack(boolean playerHasBlackJack) {
		this.playerHasBlackJack = playerHasBlackJack;
	}

}
