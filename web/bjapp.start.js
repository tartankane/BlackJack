			function startGame() {
				
				document.getElementById('dealercards').innerHTML = '';
				document.getElementById('playercards').innerHTML = '';
				document.getElementById('gamemessages').innerHTML = '';
				
				$.getJSON("startGame.do", {
					//optional return value from client here
				}, function(data) {
	//				console.log(data);
					var index1, index2, backOfCard, cardImage1, cardImage2, startGameButton, hitPlayerButton, playerStandsButton, dealerMessage, playerMessage;
					document.getElementById('credits').innerHTML = "Your Credits: " + data.playerCredits;
					document.getElementById('bet').innerHTML = "Your Bet: " + data.playerBet;
					
					//display the initial dealer card to the placeholder div "dealercards"
					for ( index1 = 0; index1 < data.dealerCards.length; index1++ ) {
						cardImage1 = "images/" + data.dealerCards[index1].rank + data.dealerCards[index1].suit + ".png";
						$('#dealercards').append($('<img src= ' + cardImage1 + '>').fadeIn(2000));
					}
					
					//append the image of a hidden card to the placeholder div "dealercards"
					backOfCard = "images/BACKOFCARD.png";
					$('#dealercards').append($('<img src= ' + backOfCard + '>').fadeIn(2000));
					

					//display the initial player cards to the placeholder div "playercards"
					for ( index2 = 0; index2 < data.playerCards.length; index2++ ) {
						cardImage2 = "images/" + data.playerCards[index2].rank + data.playerCards[index2].suit + ".png";
						$('#playercards').append($('<img src= ' + cardImage2 + '>').fadeIn(2000));
					}

					/* make the start button invisible and make the buttons for the next stage of the game visible */
					startGameButton = document.getElementById('startgamebutton');
					startGameButton.style.visibility = 'hidden';
					
					hitPlayerButton = document.getElementById('hitplayerbutton');
					hitPlayerButton.style.visibility = 'visible';
					
					playerStandsButton = document.getElementById('playerstandsbutton');
					playerStandsButton.style.visibility = 'visible';
					
					
					dealerMessage = document.getElementById('dealermessage');
					dealerMessage.style.visibility = 'visible';
					
					playerMessage = document.getElementById('playermessage');
					playerMessage.style.visibility = 'visible';

				});

			}