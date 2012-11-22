package org.sean.blackjack.services;

import org.sean.blackjack.domain.Round;

public interface BlackJackService {
	Round initializeRound();
	Round hitPlayer(Round round);
	Round playerStands(Round round);

}
