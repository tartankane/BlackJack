// Append a new card to the player card display.
//
// If the player goes bust, do not modify the dealer's card display at all.
// Otherwise, clear the dealer's displayed cards. This is necessary to remove the 
// image of the hidden card. Re-display the dealer's first card and append 
// the dealer's other cards to the first card.
//
// Display the updated values of the player's and dealer's hands.
// Declare an in-game message.
// Change the buttons available to the player.
function playerDoubles() {
	// On a very slow connection, prevent the player from pressing buttons
	// repeatedly
	document.getElementById('playerstandsbutton').disabled = true;
	document.getElementById('playerdoublesbutton').disabled = true;
	document.getElementById('hitplayerbutton').disabled = true;

	// jQuery.getJSON loads JSON-encoded data from the server using a GET HTTP
	// request.
	$.getJSON(
			"playerDoubles.do",
			{
			// optional map or string that is sent to the server with
			// the request
			},
			// data is the JSON object returned from the server
			function(data) {
				
				// declare variables
				var lastCardInArray, index, startGameButton, hitPlayerButton, playerStandsButton, playerDoublesButton, playerSplitsButton, cardImage;

				// append the final player card to the placeholder "playercards"
				lastCardInArray = data.playerCards.length - 1;
				cardImage = "images/"
						+ data.playerCards[lastCardInArray].rank
						+ data.playerCards[lastCardInArray].suit
						+ ".png";
				$('#playercards').append(
						$('<img src= ' + cardImage + '>').fadeIn(2000));

				
				if (data.bustPlayer !== true) {
					
					// clear the dealer's card and image of the hidden card
					// from the screen
					document.getElementById('dealercards').innerHTML = '';
	
					// Instantly display the first dealer card. This is the
					// card that was visible from the start of the round.
					// Fade in the other dealer cards.
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
				
				// Make the in-game game message visible and display it.
				document.getElementById('gamemessages').style.visibility = 'visible';
				document.getElementById('gamemessages').innerHTML = data.gameMessage;

				// Make the "start a new game" 
				// button visible and make the "hit player", "player 
				// stands" and "player doubles" buttons invisible.
				$.getScript("bjapp.shuffle-button-visibility.js");

				//Display the value of the player's credits and current bet size
				document.getElementById('credits').innerHTML = "Your Credits: "
						+ data.playerCredits;
				document.getElementById('bet').innerHTML = "Your Bet: "
						+ data.playerBet;

				// Make buttons clickable again that were earlier disabled.
				document.getElementById('playerstandsbutton').disabled = false;
				document.getElementById('playerdoublesbutton').disabled = false;
				document.getElementById('hitplayerbutton').disabled = false;

			});
			
}