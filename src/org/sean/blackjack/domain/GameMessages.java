package org.sean.blackjack.domain;

/**
 * GameMessages is an enum of in-game messages to be displayed to the client during a game of BlackJack.
 *
 */
public enum GameMessages {
		BLANK_MESSAGE (""),
	    PLAYER_WINS ("You won! Credits have been added to your stack."),
	    PLAYER_WINS_WITH_BLACKJACK ("You won with a BlackJack! You received a big bonus"),
	    DEALER_WINS_WITH_BLACKJACK ("The dealer won with a BlackJack. This beats all hands."),
	    PLAYER_LOSES ("You lost!"),
	    DRAW ("The game was a draw. Your bet has been returned to you."),
	    PLAYER_BUST ("You lost because you went bust! The total value of your cards was greater than 21."),
	    DEALER_BUST ("You won because the dealer went bust. Credits have been added to your stack.");
	    
	    private final String text;
	    
	    private GameMessages(final String text) {
	        this.text = text;
	    }

	    @Override
	    public String toString() {
	        return text;
	}
}
