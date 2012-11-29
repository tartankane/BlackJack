package org.sean.blackjack.services;

import org.sean.blackjack.domain.Round;

/**
 * This Interface is part of Spring MVC's Service Layer. 
 * It is implemented by BlackJackServiceImpl
 *
 */
public interface BlackJackService {

	void initializeTable(Round round);

	void startRound(Round round);

	void hitPlayer(Round round);

	void playerDoubles(Round round);
	
	void playerStands(Round round);

//	void getMoreCredits(Round round);

	void changeBet(Round round, String betSize);

}
