// This function is called by bjapp.hitplayer.js, 
// bjapp.player-doubles.js and bjapp.player-stands.js
//
// Make the "start a new game" 
// button and drop down menu for changing the bet visible and make the "hit player", "player 
// stands" and "player doubles" buttons invisible.

function shuffleButtonVisibility() {
	// declare variables
	var startGameButton, betDropDown, hitPlayerButton, playerStandsButton, playerDoublesButton, playerSplitsButton;

	startGameButton = document.getElementById('startgamebutton');
	startGameButton.style.display = 'inline';

	betDropDown = document.getElementById('betdropdown');
	betDropDown.style.display = 'inline';
	betDropDown.style.visibility = "visible";

	hitPlayerButton = document.getElementById('hitplayerbutton');
	hitPlayerButton.style.display = 'none';

	playerStandsButton = document.getElementById('playerstandsbutton');
	playerStandsButton.style.display = 'none';

	playerDoublesButton = document.getElementById('playerdoublesbutton');
	playerDoublesButton.style.display = 'none';

	playerSplitsButton = document.getElementById('playersplitsbutton');
	playerSplitsButton.style.display = 'none';

}