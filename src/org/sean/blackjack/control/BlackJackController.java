package org.sean.blackjack.control;

import org.sean.blackjack.domain.Consts;
import org.sean.blackjack.domain.Round;
import org.sean.blackjack.services.BlackJackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Sean O'Regan
 * Version 1.0
 * 
 * This class is a Spring Controller in the Spring Model-View-Controller
 * (MVC) architecture
 * 
 * The following objects are injected into this class: the Round class
 * and an implementation of the BlackJackService class (in this case the
 * BlackJackServiceImpl class)
 * 
 * In this controller, for each client request, the instance of the
 * Round class is updated by methods of BlackJackService and then
 * returned to the client
 * 
 * The web.xml file causes ".do" to be appended to all @RequestMapping
 * requests such that @RequestMapping("/blackJackTable") becomes a
 * request to 'domain'/blackJackTable.do
 */
@Controller
@Scope("session")
public class BlackJackController {

	@Autowired
	private BlackJackService blackJackService;
//	@Autowired
//	private Round round;

	private Round round = new Round();

	/**
	 * This method will be automatically run when a client sends a request to
	 * the url 'domain'/blackJackTable.do A Spring ModelAndView object is
	 * returned which represents the object passed to ModelAndView and the
	 * associated jsp page, in this case the round object and displayTable.jsp.
	 * 
	 * @return Round and displayTable.jsp as a ModelAndView
	 */
	@RequestMapping("/blackJackTable")
	public ModelAndView initializeTable() {

		blackJackService.initializeTable(round);
		return new ModelAndView("displayTable", "round", round);
	}

	/**
	 * This method will be automatically run when a jQuery.getJSON function
	 * containing the request to "startGame.do" is called by the client.
	 * 
	 * @return Round as a JSON object
	 */
	@RequestMapping("/startGame")
	public @ResponseBody
	Round startGame() {

		blackJackService.startRound(round);
		return round;
	}

	/**
	 * This method will be automatically run when a jQuery.getJSON function
	 * containing the request to "playerDoubles.do" is called by the client.
	 * 
	 * @return Round as a JSON object
	 */
	@RequestMapping("/playerDoubles")
	public @ResponseBody
	Round playerDoubles() {
		blackJackService.playerDoubles(round);
		return round;
	}

	/**
	 * This method will be automatically run when a jQuery.getJSON function
	 * containing the request to "hitPlayer.do" is called by the client.
	 * 
	 * @return Round as a JSON object
	 */
	@RequestMapping("/hitPlayer")
	public @ResponseBody
	Round hitPlayer() {

		blackJackService.hitPlayer(round);
		return round;
	}

	/**
	 * This method will be automatically run when a jQuery.getJSON function
	 * containing the request to "playerStands.do" is called by the client.
	 * 
	 * @return Round as a JSON object
	 */
	@RequestMapping("/playerStands")
	public @ResponseBody
	Round playerStands() {

		blackJackService.playerStands(round);
		return round;
	}
	
	/**
	 * This method will be automatically run when a jQuery.getJSON function
	 * containing the request to "getMoreCredits.do" is called by the client.
	 * 
	 * @return Round as a JSON object
	 */
//	@RequestMapping("/getMoreCredits")
//	public @ResponseBody
//	Round getMoreCredits() {
//		
//		blackJackService.getMoreCredits(round);
//		return round;
//	}
	
	/**
	 * This method will be automatically run when a jQuery.getJSON function
	 * containing the request to "getMoreCredits.do" is called by the client.
	 * 
	 * @return Round as a JSON object
	 */
	@RequestMapping("/changeBet")
	public @ResponseBody
	Round changeBet(@RequestParam("BETSIZE") String betSize) {
//		System.out.println("You got to controller");
//		System.out.println(betSize);
		blackJackService.changeBet(round, betSize);
		return round;
	}

	/**
	 * This method will be automatically run when a client sends a request to
	 * the url 'domain'/showRules.do A Spring ModelAndView object is returned
	 * which represents the object passed to it and the associated jsp page in
	 * this case only the displayRules.jsp is returned. No model object is
	 * returned.
	 * 
	 * @return displayRules.jsp as a ModelAndView
	 */
	@RequestMapping("/showRules")
	public ModelAndView showRules() {
		return new ModelAndView("displayRules");
	}

}
