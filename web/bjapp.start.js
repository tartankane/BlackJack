function startGame() {

	// At the start of each game, clear the dealer cards, player cards and
	// in-game messages
	document.getElementById('dealercards').innerHTML = '';
	document.getElementById('playercards').innerHTML = '';
	document.getElementById('gamemessages').innerHTML = '';

	// jQuery.getJSON loads JSON-encoded data from the server using a GET HTTP
	// request.
	$.getJSON(
			"startGame.do",
			{
			// optional map or string that is sent to the server with
			// the request
			},
			function(data) {

				// declare variables
				var index, backOfCard, cardImage, startGameButton, hitPlayerButton, playerStandsButton, dealerMessage, playerMessage, playerDoublesButton;

				// Display the player credits and player bet to the
				// placeholders "credits" and "bet"
				document.getElementById('credits').innerHTML = "Your Credits: "
						+ data.playerCredits;
				document.getElementById('bet').innerHTML = "Your Bet: "
						+ data.playerBet;

				// display the initial dealer card to the placeholder
				// "dealercards"
				cardImage = "images/" + data.dealerCards[0].rank
						+ data.dealerCards[0].suit + ".png";
				$('#dealercards').append(
						$('<img src= ' + cardImage + '>').fadeIn(2000));

				// append the image of a hidden card to the placeholder
				// "dealercards". This is a dummy for display purposes
				// only. In effect
				// the dealer is only dealt one real card at the start
				// of the game.
				backOfCard = "images/BACKOFCARD.png";
				$('#dealercards')
						.append(
								$('<img src= ' + backOfCard + '>')
										.fadeIn(2000));

				// display the initial player cards to the placeholder
				// "playercards"
				for (index = 0; index < data.playerCards.length; index++) {
					cardImage = "images/"
							+ data.playerCards[index].rank
							+ data.playerCards[index].suit + ".png";
					$('#playercards').append(
							$('<img src= ' + cardImage + '>').fadeIn(
									2000));
				}

				// Display the value of the player's and dealer's hands
				// to the placeholders "playermessage" and
				// "dealermessage". Make these messages visible.
				document.getElementById('playermessage').innerHTML = "The Player's Cards. Total equals "
						+ data.playerHandValue;
				document.getElementById('dealermessage').innerHTML = "The Dealer's Cards. Total equals "
						+ data.dealerHandValue;
				dealerMessage = document
						.getElementById('dealermessage');
				dealerMessage.style.visibility = 'visible';

				playerMessage = document
						.getElementById('playermessage');
				playerMessage.style.visibility = 'visible';

				// make the "start a new game" button invisible and make
				// the "hit player", "player stands" and "player doubles" 
				// buttons visible and clickable
				startGameButton = document
						.getElementById('startgamebutton');
				startGameButton.style.display = 'none';

				hitPlayerButton = document
						.getElementById('hitplayerbutton');
				hitPlayerButton.style.display = 'inline';
				hitPlayerButton.disabled = false;

				playerStandsButton = document
						.getElementById('playerstandsbutton');
				playerStandsButton.style.display = 'inline';
				playerStandsButton.disabled = false;

				playerDoublesButton = document
						.getElementById('playerdoublesbutton');
				playerDoublesButton.style.display = 'inline';
				playerDoublesButton.disabled = false;

			});
			
}