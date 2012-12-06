
function splitRightPlayerStands() {
	hitPlayerButton = document.getElementById('hitplayerbutton');
	hitPlayerButton.style.display = 'none';

	playerStandsButton = document.getElementById('playerstandsbutton');
	playerStandsButton.style.display = 'none';

	playerDoublesButton = document.getElementById('playerdoublesbutton');
	playerDoublesButton.style.display = 'none';

	playerSplitsButton = document.getElementById('playersplitsbutton');
	playerSplitsButton.style.display = 'none';

	// jQuery.getJSON loads JSON-encoded data from the server using a GET HTTP
	// request.
	$.getJSON(
			"splitRightPlayerStands.do",
			{
			// optional map or string that is sent to the server with
			// the request
			},
			// data is the JSON object returned from the server
			function(data) {

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
				
				// Display the value of the dealer's hands.
				document.getElementById('dealermessage').innerHTML="The Dealer's Cards. Total equals " + data.dealerHandValue;


				// Make the in-game game message visible and display it.
				document.getElementById('gamemessages').style.visibility = 'visible';
				document.getElementById('gamemessages').innerHTML = data.gameMessage;
				
				// Make the split in-game game messages visible and display them.
				document.getElementById('splitleftgamemessages').style.visibility = 'visible';
				document.getElementById('splitleftgamemessages').innerHTML = data.splitPlayer.splitLeftGameMessage;				
				document.getElementById('splitrightgamemessages').style.visibility = 'visible';
				document.getElementById('splitrightgamemessages').innerHTML = data.splitPlayer.splitRightGameMessage;
				
				// Make the value of the player's hand visible and display it.
				document.getElementById('splitrightplayermessage').style.visibility = 'visible';
				document.getElementById('splitrightplayermessage').innerHTML="The Player's Cards. Total equals " + data.splitPlayer.splitRightHandValue;
				
				// Make buttons clickable again that were earlier disabled. 
				document.getElementById('playerstandsbutton').disabled = false;
				document.getElementById('hitplayerbutton').disabled = false;
				document.getElementById('playerdoublesbutton').disabled = false;

				//Display the value of the player's credits and current bet size
				document.getElementById('credits').innerHTML = "Your Credits: "
						+ data.playerCredits;
				document.getElementById('bet').innerHTML = "Your Bet: "
						+ data.playerBet;
				

				//Make the starting buttons visible
				startGameButton = document.getElementById('startgamebutton');
				startGameButton.style.display = 'inline';

				betDropDown = document
				.getElementById('betdropdown');
				betDropDown.style.display = 'inline';

				//Hide the split buttons
				rightHitPlayerButton = document.getElementById('righthitplayerbutton');
				rightHitPlayerButton.style.display = 'none';

				rightPlayerStandsButton = document.getElementById('rightplayerstandsbutton');
				rightPlayerStandsButton.style.display = 'none';


			});
			
}