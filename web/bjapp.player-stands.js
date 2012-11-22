			function playerStands() {
				document.getElementById('dealercards').innerHTML = '';
				$.getJSON("playerStands.do", {
					//optional return value from client here
				}, function(data) {
					for (var index in data.dealerCards) {
						var cardImage = "images/" + data.dealerCards[index].rank + data.dealerCards[index].suit + ".png";
						$('#dealercards').append($('<img src= ' + cardImage + '>').fadeIn(2000));
					}
					
						
						document.getElementById('gamemessages').style.visibility = 'visible';
						document.getElementById('gamemessages').innerHTML = data.gameMessage;
						
						var startGameButton = document.getElementById('startgamebutton');
						startGameButton.style.visibility = 'visible';
						
						var hitPlayerButton = document.getElementById('hitplayerbutton');
						hitPlayerButton.style.visibility = 'hidden';
						
						var playerStandsButton = document.getElementById('playerstandsbutton');
						playerStandsButton.style.visibility = 'hidden';				


				});
			}