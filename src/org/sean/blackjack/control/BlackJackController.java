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
public class BlackJackController {

	@Autowired
	private BlackJackService blackJackService;
	@Autowired
	private Round round;
//	private Round round = new Round();

	
	@RequestMapping("/blackJackTable")
	public ModelAndView initializeTable() {
		
		blackJackService.initializeTable(round);
		return new ModelAndView("displayTable", "round", round);
	}

	@RequestMapping("/startGame")
	public @ResponseBody Round startGame() {
		
		blackJackService.startRound(round);
		return round;
	}

	@RequestMapping("/hitPlayer")
	public @ResponseBody Round hitPlayer() {

		blackJackService.hitPlayer(round);		
		return round;
	}
	
	@RequestMapping("/playerStands")
	public @ResponseBody Round playerStands() {

		blackJackService.playerStands(round);	
		return round;
	}

}
