function playerChangesBet(value) {
	// On a very slow connection, prevent the player from pressing buttons
	// repeatedly
	document.getElementById('playerstandsbutton').disabled = true;
	document.getElementById('playerdoublesbutton').disabled = true;
	document.getElementById('hitplayerbutton').disabled = true;
	
	$.getJSON(
			"changeBet.do",
			{
				BETSIZE: value
			},
			function(data) {
				document.getElementById('bet').innerHTML = "Your Bet: "
					+ data.playerBet;
//				alert("So you want to change your bet eh");
			});
}