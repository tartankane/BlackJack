package org.sean.blackjack.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameMessagesTest {

	//Example String tested
	@Test
	public void testToString() {
		String testString = "You lost! The Dealer has taken your bet.";
		assertEquals(testString, GameMessages.PLAYER_LOSES.toString());
	}

}
