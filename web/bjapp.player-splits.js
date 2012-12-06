
function playerSplits() {
	// Declare variables
	var hitPlayerButton, playerStandsButton, playerDoublesButton, playerSplitsButton;
	
	hitPlayerButton = document.getElementById('hitplayerbutton');
	hitPlayerButton.style.display = 'none';

	playerStandsButton = document.getElementById('playerstandsbutton');
	playerStandsButton.style.display = 'none';

	playerDoublesButton = document.getElementById('playerdoublesbutton');
	playerDoublesButton.style.display = 'none';

	playerSplitsButton = document.getElementById('playersplitsbutton');
	playerSplitsButton.style.display = 'none';

	splitCardsLeft = document.getElementById('splitcardsleft');
	splitCardsLeft.style.display = 'inline';
	
	splitCardsRight = document.getElementById('splitcardsright');
	splitCardsRight.style.display = 'inline';


	// jQuery.getJSON loads JSON-encoded data from the server using a GET HTTP
	// request.
	$.getJSON(
			"playerSplits.do",
			{
			// optional map or string that is sent to the server with
			// the request
			},
			// data is the JSON object returned from the server
			function(data) {
				// Declare variables
				var leftHitPlayerButton, leftPlayerStandsButton;
		
				//Hide the existing player cards and messages
				playerCards = document.getElementById('playercards');
				playerCards.style.display = 'none';

				playerMessage = document.getElementById('playermessage');
				playerMessage.style.display = 'none';

				gameMessages = document.getElementById('gamemessages');
//				gameMessages.style.display = 'none';
				gameMessages.style.display = 'hidden';
				
				
				console.log(data);
				cardImage = "images/"
						+ data.splitPlayer.splitLeftCards[0].rank
						+ data.splitPlayer.splitLeftCards[0].suit + ".png";
				$('#splitcardsleft').append(
						$('<img src= ' + cardImage + '>').fadeIn(
								2000));
								

				cardImage = "images/"
					+ data.splitPlayer.splitRightCards[0].rank
					+ data.splitPlayer.splitRightCards[0].suit + ".png";
				$('#splitcardsright').append(
						$('<img src= ' + cardImage + '>').fadeIn(
								2000));
				
				leftHitPlayerButton = document
					.getElementById('lefthitplayerbutton');
				leftHitPlayerButton.style.display = 'inline';
				leftHitPlayerButton.disabled = false;

				leftPlayerStandsButton = document
					.getElementById('leftplayerstandsbutton');
				leftPlayerStandsButton.style.display = 'inline';
				leftPlayerStandsButton.disabled = false;
				
				//Display the value of the player's credits and current bet size
				document.getElementById('credits').innerHTML = "Your Credits: "
					+ data.playerCredits;
				document.getElementById('bet').innerHTML = "Your Bet: "
					+ data.playerBet;

			});
			
}