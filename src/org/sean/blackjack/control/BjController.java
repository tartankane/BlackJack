package org.sean.blackjack.control;

import org.sean.blackjack.domain.Round;
import org.sean.blackjack.services.BlackJackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("session")
public class BjController {

	@Autowired
	private BlackJackService blackJackService;

	private Round round = new Round();

	@RequestMapping("/displayRound")
	public ModelAndView RoundWeb() {

		return new ModelAndView("displayRound");
	}

	@RequestMapping("/startGame")
	public @ResponseBody Round startGame() {
		round = blackJackService.initializeRound();
		return round;
	}

	@RequestMapping("/hitPlayer")
	public @ResponseBody Round hitPlayer() {

//		round = blackJackService.hitPlayer(round);
		blackJackService.hitPlayer(round);

		System.out.println(round.getPlayerCards().size() + " player cards and "
				+ round.getDealerCards().size() + " dealer cards");
		
		return round;
	}
	
	@RequestMapping("/playerStands")
	public @ResponseBody Round playerStands() {

		blackJackService.playerStands(round);
		
		return round;
	}

}
