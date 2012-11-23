			function playerStands() {
				//clear the dealer's card from the screen
				document.getElementById('dealercards').innerHTML = '';
				$.getJSON("playerStands.do", {
					//optional return value from client here
				}, function(data) {
					//Instantly display the first dealer card. This is the card that was visible from the start of the round.
					//Fade in the other dealer cards.
					for (var index in data.dealerCards) {
						//note that (index === 0) did not work here
						if (index == 0)
							{
							var cardImage = "images/" + data.dealerCards[index].rank + data.dealerCards[index].suit + ".png";
							$('#dealercards').append($('<img src= ' + cardImage + '>'));
							}
						else
							{
							var cardImage = "images/" + data.dealerCards[index].rank + data.dealerCards[index].suit + ".png";
							$('#dealercards').append($('<img src= ' + cardImage + '>').fadeIn(5000));
							}
					}
					
						
						document.getElementById('gamemessages').style.visibility = 'visible';
						document.getElementById('gamemessages').innerHTML = data.gameMessage;
						
						var startGameButton = document.getElementById('startgamebutton');
						startGameButton.style.visibility = 'visible';
						
						var hitPlayerButton = document.getElementById('hitplayerbutton');
						hitPlayerButton.style.visibility = 'hidden';
						
						var playerStandsButton = document.getElementById('playerstandsbutton');
						playerStandsButton.style.visibility = 'hidden';	
						
						document.getElementById('credits').innerHTML = "Your Credits: " + data.playerCredits;
						document.getElementById('bet').innerHTML = "Your Bet: " + data.playerBet;


				});
			}