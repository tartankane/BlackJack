<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>

<head>
<title>Round Of Blackjack</title>
<link href="<c:url value="/styles.css"/>" rel="Stylesheet"
	type="text/css" />

<style>
p {
	text-align: center;
}
</style>

</head>

<body>

	<jsp:include page="/header.jsp" />


	<div id="credits" class="msg1" title="This is your credit total" >Holder for player credits</div>
	
	<div id="bet" class="msg1" style="text-align: right" title="This is the total you have bet for this game">Holder for player bet</div>

	<!-- Holder for the dealer's cards -->
	<div id="dealercards" class="card"></div>

	<div id="dealermessage" class="msg2">Holder for the dealer message</div>

	<!-- Holder for the player's cards -->
	<div id="playercards" class="card"></div>

	<div id="playermessage" class="msg2">Holder for the player message</div>

	<div id="gamemessages" class="msg2">Holder for the in-game messages</div>



	<!-- This block of javascript also includes some jstl.
	That is why it needs to be placed in the .jsp file itself and not called from a separate
	javascript file. I need to investigate if there is a better method to do this. Mixing jstl and JS probably bad -->
	<script>
		document.getElementById('credits').innerHTML = "Your Credits: " + ${round.playerCredits};
		document.getElementById('bet').innerHTML = "Your Bet: " + ${round.playerBet};
	</script>


	<p>
		<button id="startgamebutton" type="button" class="btn1"
			onclick="startGame();">Start A New Game</button>

		<button id="hitplayerbutton" type="button" class="btn2"
			onclick="hitPlayer();" title="Get Another Card">Hit Me</button>

		<button id="playerstandsbutton" type="button" class="btn2"
			onclick="playerStands();" title="Stick With These Cards">Stand</button>

		<button id="playerdoublesbutton" type="button" class="btn2"
			onclick="playerDoubles();"
			title="Double Your Bet and Get One More Card. This Option Only Exists When You Have Two Cards">Double</button>
	</p>
	
	<p>
		<!-- Drop down list to select bet size -->
		<select id="betdropdown" name="setBetSize" onchange="playerChangesBet(this.value);" title="Change The Size Of Your Bet"> 
			<option value="10" selected="selected">Bet 10 Credits</option>
			<option value="20">Bet 20 Credits</option>
			<option value="40">Bet 40 Credits</option>
			<option value="80">Bet 80 Credits</option>
			<option value="160">Bet 160 Credits</option>
		</select>
	</p>
	
	<div class="wrap">
  		<div id="leftie" class="left"></div>
  		<!-- <div id="middlie" class="middle">MIDDLE</div>-->
  		<div id="rightie" class="right"></div>
	</div>
	<p> </p>
	<div class="wrap2">
 		<button id="hitplayerbutton1" type="button" class="btn3"
			onclick="hitPlayer();" title="Get Another Card">Hit Me</button>

		<button id="playerstandsbutton1" type="button" class="btn3"
			onclick="playerStands();" title="Stick With These Cards">Stand</button>
			
		<button id="hitplayerbutton2" type="button" class="btn4"
			onclick="hitPlayer();" title="Get Another Card">Hit Me</button>

		<button id="playerstandsbutton2" type="button" class="btn4"
			onclick="playerStands();" title="Stick With These Cards">Stand</button>
	</div>
	
	
<!--  	<div class="wrapper">

        <div class="column1">left</div>
        <div ><h2>Page Ran Away</h2></div>
        <div class="column2">right</div>

	</div>
-->

	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>	

	<script src="bjapp.start.js"></script>
	<script src="bjapp.hitplayer.js"></script>
	<script src="bjapp.player-stands.js"></script>
	<script src="bjapp.player-doubles.js"></script>
	<script src="bjapp.player-changes-bet.js"></script>
	<jsp:include page="/footer.jsp" />
	


</body>

</html>
