package org.sean.blackjack.domain;

import java.util.ArrayList;
import java.util.List;

public class Round {

	// a push in BlackJack means that both player and dealer have equal value
	// hands, so neither loses. The player's bet is returned to the player.
	private boolean push;
	private boolean playerWon;
	private String gameMessage;
	private boolean bustPlayer;
	private boolean bustDealer;
	private int playerCredits;
	private int playerBet;
	private List<Card> dealerCards = new ArrayList<Card>();
	private List<Card> playerCards = new ArrayList<Card>();

	public Round() {
		super();
	}

	public Round(boolean push, boolean playerWon, String gameMessage,
			boolean bustPlayer, boolean bustDealer, int playerCredits,
			int playerBet, List<Card> dealerCards, List<Card> playerCards) {
		super();
		this.push = push;
		this.playerWon = playerWon;
		this.gameMessage = gameMessage;
		this.bustPlayer = bustPlayer;
		this.bustDealer = bustDealer;
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
			this.setGameMessage("You lost because you went bust! The total value of your cards was greater than 21.");
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
			this.setGameMessage("You won because the dealer went bust. Credits have been added to your stack.");
			return true;
		}
		return false;
	}

	public boolean dealerMustStand() {
		int total = 0;
		int minValueToStand = 17;
		int maxValueToStand = 21;
		for (Card card : dealerCards) {
			total = total + card.getRank().getCardValue();
			//A dealer's ace must have a value of 11 as long as that does not cause the dealer
			//to go bust. Otherwise it keeps the default value of 1. This if statement checks that
			//the card is an ace and that the total hand value is 11 if the ace is given a value of one.
			//If so, 10 is added to the hand value to give a total value of 11 for the ace.
			if ( (card.getRank().getCardValue() == 1) && (total <= 11) ) {
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
			if ( (card.getRank().getCardValue() == 1) ) {
				numberOfPlayersAces++;
			}
		}
		
		//Adjust player's hand value for any aces
		for (int i = 0; i < numberOfPlayersAces; i++) {
			if ( (totalPlayer <= 11) ) {
				totalPlayer = totalPlayer + 10;
			}
		}

		for (Card card : dealerCards) {
			totalDealer = totalDealer + card.getRank().getCardValue();
			if ( (card.getRank().getCardValue() == 1) ) {
				numberOfDealersAces++;
			}
		}
		
		//Adjust dealer's hand value for any aces
		for (int i = 0; i < numberOfDealersAces; i++) {
			if ( (totalDealer <= 11) ) {
				totalDealer = totalDealer + 10;
			}
		}

		if (totalPlayer == totalDealer) {
			this.setPush(true);
			this.setGameMessage("The game was a draw. Your bet has been returned to you.");
		} else {
			if (totalPlayer > totalDealer) {
				this.setPlayerWon(true);
				this.setGameMessage("You won! Credits have been added to your stack.");
			} else {
				this.setPlayerWon(false);
				this.setGameMessage("You lost!");
			}
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
	public int getPlayerCredits() {
		return playerCredits;
	}

	/**
	 * @param playerCredits
	 *            the playerCredits to set
	 */
	public void setPlayerCredits(int playerCredits) {
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

}
