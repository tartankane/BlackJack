package org.sean.blackjack.services;

import org.sean.blackjack.domain.Round;

public interface BlackJackService {
//	Round initializeTable(Round round);
//	Round startRound(Round round);
//	Round hitPlayer(Round round);
//	Round playerStands(Round round);
	
	void initializeTable(Round round);
	void startRound(Round round);
	void hitPlayer(Round round);
	void playerStands(Round round);


}
