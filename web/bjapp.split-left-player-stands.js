
function splitLeftPlayerStands() {
	
	// declare variables
	var hitPlayerButton, playerStandsButton, playerDoublesButton, playerSplitsButton;
	
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
			"splitLeftPlayerStands.do",
			{
			// optional map or string that is sent to the server with
			// the request
			},
			// data is the JSON object returned from the server
			function(data) {

				// declare variables
				var leftHitPlayerButton, leftPlayerStandsButton, rightHitPlayerButton, rightPlayerStandsButton;
				console.log(data);

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
				
				// Make the value of the player's hand visible and display it.
				document.getElementById('splitleftplayermessage').style.visibility = 'visible';
				document.getElementById('splitleftplayermessage').innerHTML="The Player's Cards. Total equals " + data.splitHand.splitLeftHandValue;
				


			});
			
}