package org.sean.blackjack.domain;

/**
 * GameMessages is an enum of in-game messages to be displayed to the client during a game of BlackJack.
 *
 */
public enum GameMessages {
		BLANK_MESSAGE (""),
	    PLAYER_WINS ("You won! Your bet plus a matching amount has been added to your credits."),
	    PLAYER_WINS_WITH_BLACKJACK ("You won with a BlackJack! A bonus of half your bet was added to your winnings."),
	    DEALER_WINS_WITH_BLACKJACK ("The Dealer won with a BlackJack. This beats all hands."),
	    PLAYER_LOSES ("You lost! The Dealer has taken your bet."),
	    DRAW ("The game was a draw. Your bet has been returned to you."),
	    PLAYER_BUST ("You lost because you went bust! The total value of your cards was greater than 21."),
	    DEALER_BUST ("You won because the Dealer went bust. Your bet plus a matching amount has been added to your credits.");
	    
	    private final String text;
	    
	    private GameMessages(final String text) {
	        this.text = text;
	    }

	    @Override
	    public String toString() {
	        return text;
	}
}
