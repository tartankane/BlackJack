function hitPlayer() {
	// On a very slow connection, prevent the player from pressing buttons
	// repeatedly
	document.getElementById('playerstandsbutton').disabled = true;
	document.getElementById('playerdoublesbutton').disabled = true;
	document.getElementById('hitplayerbutton').disabled = true;

	$.getJSON(
			"hitPlayer.do",
			{
			// optional return value from client here
			},
			function(data) {
				// When the player presses the "hitplayerbutton" button
				// append a new card to the player card display
				var startGameButton, hitPlayerButton, playerStandsButton, playerDoublesButton, lastCardInArray, cardImage;

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
			});	
}