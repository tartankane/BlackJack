// Append a new card to the player card display. Display the updated values 
// of the player's and dealer's hands. If the player goes bust, change the buttons
// availabel to the player.
function hitPlayer() {
	
	// On a very slow connection, prevent the player from pressing buttons
	// repeatedly
	document.getElementById('playerstandsbutton').disabled = true;
	document.getElementById('playerdoublesbutton').disabled = true;
	document.getElementById('hitplayerbutton').disabled = true;

	// jQuery.getJSON loads JSON-encoded data from the server using a GET HTTP
	// request.
	$.getJSON(
			"hitPlayer.do",
			{
			// optional map or string that is sent to the server with
			// the request
			},
			function(data) {

				// declare variables
				var startGameButton, hitPlayerButton, playerStandsButton, playerDoublesButton, lastCardInArray, cardImage;

				// append a new card to the player card display
				lastCardInArray = data.playerCards.length - 1;
				cardImage = "images/"
						+ data.playerCards[lastCardInArray].rank
						+ data.playerCards[lastCardInArray].suit
						+ ".png";
				$('#playercards').append(
						$('<img src= ' + cardImage + '>').fadeIn(2000));

				if (data.bustPlayer === true) {

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

				} else {
					// Make buttons clickable again that were earlier
					// disabled to prevent
					// the player from pressing buttons repeatedly
					document.getElementById('playerstandsbutton').disabled = false;
					// document.getElementById('playerdoublesbutton').disabled
					// = false;
					document.getElementById('hitplayerbutton').disabled = false;
				}
				
				//Display the value of the player's and dealer's hands
				document.getElementById('playermessage').innerHTML="The Player's Cards. Total equals " + data.playerHandValue;
				document.getElementById('dealermessage').innerHTML="The Dealer's Cards. Total equals " + data.dealerHandValue;
			});	
}