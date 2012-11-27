function hitPlayer() {

	$.getJSON(
			"hitPlayer.do",
			{
			// optional return value from client here
			},
			function(data) {
				// When the player presses the "hitplayerbutton" button
				// append a new card to the player card display
				var startGameButton, hitPlayerButton, playerStandsButton, lastCardInArray, cardImage;
				
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
					startGameButton.style.visibility = 'visible';

					hitPlayerButton = document
							.getElementById('hitplayerbutton');
					hitPlayerButton.style.visibility = 'hidden';

					playerStandsButton = document
							.getElementById('playerstandsbutton');
					playerStandsButton.style.visibility = 'hidden';
				}
			});
			
}