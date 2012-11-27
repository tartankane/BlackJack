function playerStands() {
	// clear the dealer's card from the screen
	document.getElementById('dealercards').innerHTML = '';
	$.getJSON("playerStands.do", {
	// optional return value from client here
	}, function(data) {
		// Instantly display the first dealer card. This is the card that was
		// visible from the start of the round.
		// Fade in the other dealer cards.
		var index, startGameButton, hitPlayerButton, playerStandsButton, cardImage;
		for ( index = 0; index < data.dealerCards.length; index++ ) {
			if (index === 0) {
				cardImage = "images/" + data.dealerCards[index].rank
						+ data.dealerCards[index].suit + ".png";
				$('#dealercards').append($('<img src= ' + cardImage + '>'));
			} else {
				cardImage = "images/" + data.dealerCards[index].rank
						+ data.dealerCards[index].suit + ".png";
				$('#dealercards').append(
						$('<img src= ' + cardImage + '>').fadeIn(3000));
			}
		}

		document.getElementById('gamemessages').style.visibility = 'visible';
		document.getElementById('gamemessages').innerHTML = data.gameMessage;

		startGameButton = document.getElementById('startgamebutton');
		startGameButton.style.visibility = 'visible';

		hitPlayerButton = document.getElementById('hitplayerbutton');
		hitPlayerButton.style.visibility = 'hidden';

		playerStandsButton = document.getElementById('playerstandsbutton');
		playerStandsButton.style.visibility = 'hidden';

		document.getElementById('credits').innerHTML = "Your Credits: "
				+ data.playerCredits;
		document.getElementById('bet').innerHTML = "Your Bet: "
				+ data.playerBet;

	});
}