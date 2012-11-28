function playerStands() {
	// On a very slow connection, prevent the player from pressing buttons
	// repeatedly
	document.getElementById('playerstandsbutton').disabled = true;
	document.getElementById('hitplayerbutton').disabled = true;
	document.getElementById('playerdoublesbutton').disabled = true;

	$.getJSON(
			"playerStands.do",
			{
			// optional return value from client here
			},
			function(data) {
				// Instantly display the first dealer card. This is the
				// card that was
				// visible from the start of the round.
				// Fade in the other dealer cards.
				var index, startGameButton, hitPlayerButton, playerStandsButton, playerDoublesButton, cardImage;
				// Do not update the display of dealer cards to the
				// screen if the dealer is not dealt any additional
				// cards after
				// the player stands. This happens if the dealer has
				// already lost the game. This means only one card will
				// be in the
				// dealer's hand because the blank card is only a dummy.
				// This case arises when the player has BlackJack and
				// the
				// dealer does not have a starting card that is an Ace,
				// 10 or picture card.
				if (data.dealerCards.length > 1) {
					// clear the dealer's card from the screen
					document.getElementById('dealercards').innerHTML = '';

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
				
				//Display the value of the player's and dealer's hands
				document.getElementById('playermessage').innerHTML="The Player's Cards. Total equals " + data.playerHandValue;
				document.getElementById('dealermessage').innerHTML="The Dealer's Cards. Total equals " + data.dealerHandValue;


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
				document.getElementById('hitplayerbutton').disabled = false;
				document.getElementById('playerdoublesbutton').disabled = false;

			});
			
}