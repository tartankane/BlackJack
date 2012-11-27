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

	/**
	 * Constructor containing all fields
	 */
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

	/**
	 * Check that the value of the player's hand has not gone above 21. For the
	 * purposes of this check, an ace will always have a value of 1.
	 * 
	 * @return boolean
	 */
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

	/**
	 * Check that the value of the dealer's hand has not gone above 21. For the
	 * purposes of this check, an ace will always have a value of 1. If the
	 * dealer is bust, return the player's bet and a matching amount for the win
	 * to the player's credits.
	 * 
	 * @return boolean
	 */
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
			this.setPlayerCredits(this.getPlayerCredits()
					+ (2 * this.playerBet));
			return true;
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
		for (Card card : playerCards) {
			totalPlayer = totalPlayer + card.getRank().getCardValue();
			if ((card.getRank().getCardValue() == 1)) {
				numberOfPlayersAces++;
			}
		}
		// An ace has a value of 1 in the enum "Rank". So a Blackjack will add
		// up to a value of 11
		if ((totalPlayer == 11) && (playerCards.size() == 2)
				&& (numberOfPlayersAces == 1)) {
			this.setPlayerHasBlackJack(true);
			return true;
		} else {
			return false;
		}
	}

	// IS this necessary?
	// public boolean dealerHasBlackJack() {
	//
	// int totalDealer = 0;
	// int numberOfDealersAces = 0;
	// for (Card card : dealerCards) {
	// totalDealer = totalDealer + card.getRank().getCardValue();
	// if ((card.getRank().getCardValue() == 1)) {
	// numberOfDealersAces++;
	// }
	// }
	// // An ace has a value of 1 in the enum "Rank". So a Blackjack will add
	// // up to a value of 11
	// if ((totalDealer == 11) && (dealerCards.size() == 2) &&
	// (numberOfDealersAces == 1)) {
	// return true;
	// } else {
	// return false;
	// }
	// }

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
		int maxValueToStand = 21;
		int numberOfDealersAces = 0;
		for (Card card : dealerCards) {
			total = total + card.getRank().getCardValue();
			if ((card.getRank().getCardValue() == 1)) {
				numberOfDealersAces++;
			}
		}

		// A dealer's ace must have a value of 11 as long as that does not
		// cause the dealer to go bust. Otherwise it keeps the default value of
		// 1.
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
		// they were all if else if etc and difficult to read
		if (totalPlayer == totalDealer) {

			// Check to see if the player has BlackJack (an Ace and a card of
			// value
			// 10) but the dealer doesn't. If so, the player wins.
			if (this.isPlayerHasBlackJack() && (dealerCards.size() > 2)) {
				this.setPlayerWon(true);
				this.setGameMessage(GameMessages.PLAYER_WINS_WITH_BLACKJACK
						.toString());
				this.setPlayerCredits(this.getPlayerCredits()
						+ (2.5 * this.playerBet));

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
				this.setPlayerCredits(this.getPlayerCredits()
						+ (2.5 * this.playerBet));
			} else {
				this.setPlayerWon(true);
				this.setGameMessage(GameMessages.PLAYER_WINS.toString());
				this.setPlayerCredits(this.getPlayerCredits()
						+ (2 * this.playerBet));
			}

			// this.setPlayerWon(true);
		}

		if (totalPlayer < totalDealer) {
			this.setPlayerWon(false);
			this.setGameMessage(GameMessages.PLAYER_LOSES.toString());

			// credits were already deducted from the player at the start of the
			// round so no need to modify the player's credits.
		}

	}

	/**
	 * Peter, I read in stack overflow that I shouldnt have javadoc comments for
	 * normal getters and setters as it is just clutter. What say you?
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

	public boolean isPlayerWon() {
		return playerWon;
	}

	public void setPlayerWon(boolean playerWon) {
		this.playerWon = playerWon;
	}

	public boolean isPush() {
		return push;
	}

	public void setPush(boolean push) {
		this.push = push;
	}

	public boolean isPlayerHasBlackJack() {
		return playerHasBlackJack;
	}

	public void setPlayerHasBlackJack(boolean playerHasBlackJack) {
		this.playerHasBlackJack = playerHasBlackJack;
	}

}
