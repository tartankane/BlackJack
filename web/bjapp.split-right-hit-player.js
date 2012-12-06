function splitRightHitPlayer() {
	
	// On a very slow connection, prevent the player from pressing buttons
	// repeatedly
	document.getElementById('playerstandsbutton').disabled = true;
	document.getElementById('playerdoublesbutton').disabled = true;
	document.getElementById('hitplayerbutton').disabled = true;
	document.getElementById('playersplitsbutton').disabled = true;

	// jQuery.getJSON loads JSON-encoded data from the server using a GET HTTP
	// request.
	$.getJSON(
			"splitRightHitPlayer.do",
			{
			// optional map or string that is sent to the server with
			// the request
			},
			// data is the JSON object returned from the server
			function(data) {
				console.log(data);
				// declare variables
				var leftHitPlayerButton, leftPlayerStandsButton, rightHitPlayerButton, rightPlayerStandsButton, lastCardInArray, cardImage;

				// append a new player card to the placeholder "playercards"
				lastCardInArray = data.splitPlayer.splitRightCards.length - 1;
				cardImage = "images/"
						+ data.splitPlayer.splitRightCards[lastCardInArray].rank
						+ data.splitPlayer.splitRightCards[lastCardInArray].suit
						+ ".png";
				$('#splitcardsright').append(
						$('<img src= ' + cardImage + '>').fadeIn(2000));


				
				
				if (data.splitPlayer.splitRightBust=== true) {
					// If the player goes bust,  make the "start a new game" 
					// button visible and make the "hit player", "player 
					// stands" and "player doubles" buttons invisible.
					// Make the in-game game message visible and display it.
					document.getElementById('splitrightgamemessages').style.visibility = 'visible';
					document.getElementById('splitrightgamemessages').innerHTML = data.splitPlayer.splitRightGameMessage;
					
					rightHitPlayerButton = document.getElementById('righthitplayerbutton');
					rightHitPlayerButton.style.display = 'none';

					rightPlayerStandsButton = document.getElementById('rightplayerstandsbutton');
					rightPlayerStandsButton.style.display = 'none';
					
					// If the right hand has gone bust, but the left hand is still in play,
					// then need to proceed to deal the dealer's cards. Calling the
					// splitRightPlayerStands() will cause this to happen
					if (data.splitPlayer.splitLeftBust=== false){
						splitRightPlayerStands();
					}
//					rightHitPlayerButton = document
//					.getElementById('righthitplayerbutton');
//					rightHitPlayerButton.style.display = 'inline';
//					rightHitPlayerButton.disabled = false;
//
//					rightPlayerStandsButton = document
//					.getElementById('rightplayerstandsbutton');
//					rightPlayerStandsButton.style.display = 'inline';
//					rightPlayerStandsButton.disabled = false;

				}
//				
//				//Display the value of the player's and dealer's hands
//				document.getElementById('playermessage').innerHTML="The Player's Cards. Total equals " + data.playerHandValue;
//				document.getElementById('dealermessage').innerHTML="The Dealer's Cards. Total equals " + data.dealerHandValue;
//				
//				//Display the value of the player's credits and current bet size
//				document.getElementById('credits').innerHTML = "Your Credits: "
//					+ data.playerCredits;
//				document.getElementById('bet').innerHTML = "Your Bet: "
//					+ data.playerBet;
				

				
				// Make the value of the player's hand visible and display it.
				document.getElementById('splitrightplayermessage').style.visibility = 'visible';
				document.getElementById('splitrightplayermessage').innerHTML="The Player's Cards. Total equals " + data.splitPlayer.splitRightHandValue;
	
				//Display the value of the player's credits and current bet size
				document.getElementById('credits').innerHTML = "Your Credits: "
					+ data.playerCredits;
				document.getElementById('bet').innerHTML = "Your Bet: "
					+ data.playerBet;

			});	
}