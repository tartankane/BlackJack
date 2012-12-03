// Clear the dealer's displayed cards. This is necessary to remove the 
// image of the hidden card. Re-display the dealer's first card and append 
// the dealer's other cards to the first card.
// Declare an in-game message.
// Change the buttons available to the player.
function playerSplits() {
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
			"playerSplits.do",
			{
			// optional map or string that is sent to the server with
			// the request
			},
			// data is the JSON object returned from the server
			function(data) {

				var leftHitPlayerButton, leftPlayerStandsButton;
				// display the split cards to the placeholders
				// "playercards"
				console.log(data);
				cardImage = "images/"
						+ data.splitPlayer.splitLeftPlayerCards[0].rank
						+ data.splitPlayer.splitLeftPlayerCards[0].suit + ".png";
				$('#splitcardsleft').append(
						$('<img src= ' + cardImage + '>').fadeIn(
								2000));
								

				cardImage = "images/"
					+ data.splitPlayer.splitRightPlayerCards[0].rank
					+ data.splitPlayer.splitRightPlayerCards[0].suit + ".png";
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

			});
			
}