// Append a new card to the player card display. Display the updated values 
// of the player's and dealer's hands. If the player goes bust, change the buttons
// available to the player.
function splitLeftHitPlayer() {
	
	// On a very slow connection, prevent the player from pressing buttons
	// repeatedly
	document.getElementById('playerstandsbutton').disabled = true;
	document.getElementById('playerdoublesbutton').disabled = true;
	document.getElementById('hitplayerbutton').disabled = true;
	document.getElementById('playersplitsbutton').disabled = true;

	// jQuery.getJSON loads JSON-encoded data from the server using a GET HTTP
	// request.
	$.getJSON(
			"splitLeftHitPlayer.do",
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
				lastCardInArray = data.splitPlayer.splitLeftCards.length - 1;
				cardImage = "images/"
						+ data.splitPlayer.splitLeftCards[lastCardInArray].rank
						+ data.splitPlayer.splitLeftCards[lastCardInArray].suit
						+ ".png";
				$('#splitcardsleft').append(
						$('<img src= ' + cardImage + '>').fadeIn(2000));


				
				
				if (data.splitPlayer.splitLeftBust === true) {
					// If the player goes bust,  make the "start a new game" 
					// button visible and make the "hit player", "player 
					// stands" and "player doubles" buttons invisible.
					// Make the in-game game message visible and display it.
					document.getElementById('splitleftgamemessages').style.visibility = 'visible';
					document.getElementById('splitleftgamemessages').innerHTML = data.splitPlayer.splitLeftGameMessage;
					
					leftHitPlayerButton = document.getElementById('lefthitplayerbutton');
					leftHitPlayerButton.style.display = 'none';

					leftPlayerStandsButton = document.getElementById('leftplayerstandsbutton');
					leftPlayerStandsButton.style.display = 'none';
					
					
					rightHitPlayerButton = document
					.getElementById('righthitplayerbutton');
					rightHitPlayerButton.style.display = 'inline';
					rightHitPlayerButton.disabled = false;

					rightPlayerStandsButton = document
					.getElementById('rightplayerstandsbutton');
					rightPlayerStandsButton.style.display = 'inline';
					rightPlayerStandsButton.disabled = false;

				} else {
					// If the player doesn't go bust, make the playerStandsButton
					// and the hitPlayerButton clickable again.
					// The playerSplitsButton and playerDoublesButton will stay disabled as 
					// they are only valid when the player has the starting two cards.
//					document.getElementById('playerstandsbutton').disabled = false;
//					document.getElementById('hitplayerbutton').disabled = false;
				}
				
				// Make the value of the player's hand visible and display it.
				document.getElementById('splitleftplayermessage').style.visibility = 'visible';
				document.getElementById('splitleftplayermessage').innerHTML="The Player's Cards. Total equals " + data.splitPlayer.splitLeftHandValue;
	
				//Display the value of the player's credits and current bet size
				document.getElementById('credits').innerHTML = "Your Credits: "
					+ data.playerCredits;
				document.getElementById('bet').innerHTML = "Your Bet: "
					+ data.playerBet;

			});	
}