function playerDoubles() {
	// On a very slow connection, prevent the player from pressing buttons
	// repeatedly
	document.getElementById('playerstandsbutton').disabled = true;
	document.getElementById('playerdoublesbutton').disabled = true;
	document.getElementById('hitplayerbutton').disabled = true;

	$.getJSON(
			"playerDoubles.do",
			{
			// optional return value from client here
			},
			function(data) {
				// Instantly display the first dealer card. This is the
				// card that was
				// visible from the start of the round.
				// Fade in the other dealer cards.
				var lastCardInArray, index, startGameButton, hitPlayerButton, playerStandsButton, playerDoublesButton, cardImage;

				// Display the player's final card
				lastCardInArray = data.playerCards.length - 1;
				cardImage = "images/"
						+ data.playerCards[lastCardInArray].rank
						+ data.playerCards[lastCardInArray].suit
						+ ".png";
				$('#playercards').append(
						$('<img src= ' + cardImage + '>').fadeIn(2000));

				// Do not update the display of dealer cards to the
				// screen if the player has a blackjack
				// and the dealer's first visible card is a 2 to 9
				// inclusive.
				if (data.dealerCards.length > 1) {
					// clear the dealer's card from the screen
					document.getElementById('dealercards').innerHTML = '';

					// Display the dealer's cards to the screen.
					for (index = 0; index < data.dealerCards.length; index++) {
						if (index === 0) {
							cardImage = "images/"
									+ data.dealerCards[index].rank
									+ data.dealerCards[index].suit
									+ ".png";
							$('#dealercards').append(
									$('<img src= ' + cardImage + '>'));
						} else {
							cardImage = "images/"
									+ data.dealerCards[index].rank
									+ data.dealerCards[index].suit
									+ ".png";
							$('#dealercards').append(
									$('<img src= ' + cardImage + '>')
											.fadeIn(3000));
						}
					}
				}

				document.getElementById('gamemessages').style.visibility = 'visible';
				document.getElementById('gamemessages').innerHTML = data.gameMessage;

				startGameButton = document
						.getElementById('startgamebutton');
				startGameButton.style.display = 'inline';

				hitPlayerButton = document
						.getElementById('hitplayerbutton');
				hitPlayerButton.style.display = 'none';

				playerStandsButton = document
						.getElementById('playerstandsbutton');
				playerStandsButton.style.display = 'none';

				playerDoublesButton = document
						.getElementById('playerdoublesbutton');
				playerDoublesButton.style.display = 'none';

				document.getElementById('credits').innerHTML = "Your Credits: "
						+ data.playerCredits;
				document.getElementById('bet').innerHTML = "Your Bet: "
						+ data.playerBet;

				// Make buttons clickable again that were earlier
				// disabled to prevent
				// the player from pressing buttons repeatedly
				document.getElementById('playerstandsbutton').disabled = false;
				document.getElementById('playerdoublesbutton').disabled = false;
				document.getElementById('hitplayerbutton').disabled = false;

			});
			
}