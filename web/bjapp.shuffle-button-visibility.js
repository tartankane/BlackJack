// This code is called by bjapp.hitplayer.js, 
// bjapp.player-doubles.js and bjapp.player-stands.js
//
// Make the "start a new game" 
// button visible and make the "hit player", "player 
// stands" and "player doubles" buttons invisible.
startGameButton = document.getElementById('startgamebutton');
startGameButton.style.display = 'inline';

changeBetDial = document
.getElementById('changebetdial');
changeBetDial.style.display = 'inline';

hitPlayerButton = document.getElementById('hitplayerbutton');
hitPlayerButton.style.display = 'none';

playerStandsButton = document.getElementById('playerstandsbutton');
playerStandsButton.style.display = 'none';

playerDoublesButton = document.getElementById('playerdoublesbutton');
playerDoublesButton.style.display = 'none';