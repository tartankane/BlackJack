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

	<div id="credits" title="This is your credit total">Your Credits here </div>
	<div id="bet" title="This is the total you have bet for this game" style="text-align: right">Your bet here</div>

	<div id="dealercards" class="card"></div>

	<div id="dealermessage" class="msg">The Dealer's Cards</div>

	<div id="playercards" class="card"></div>

	<div id="playermessage" class="msg">The Player's Cards</div>

	<div id="gamemessages" class="msg"></div>
	
	<!-- This block of javascript also includes some jstl (I think that's what it is - I need to investigate)
	That is why it needs to be placed in the .jsp file itself and not called from a separate
	javascript file -->
	<script>
		document.getElementById('credits').innerHTML = "Your Credits: " + ${round.playerCredits};
		document.getElementById('bet').innerHTML = "Your Bet: " + ${round.playerBet};
	</script>


	<p>
		<button type="button" onclick="startGame();" id="startgamebutton" >
			Start A New Game</button>

		<button type="button" class="btn" onclick="hitPlayer();" title="Get Another Card"
			id="hitplayerbutton">Hit Me</button>

		<button type="button" class="btn" onclick="playerStands();" title="Stick With These Cards"
			id="playerstandsbutton">Stand</button>
			
		<button type="button" class="btn" onclick="playerDoubles();" title="Double Your Bet and Get One More Card"
			id="playerdoublesbutton">Double</button>
	</p>

	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
	
	<script src="bjapp.start.js"></script>
	<script src="bjapp.hitplayer.js"></script>
	<script src="bjapp.player-stands.js"></script>
	<script src="bjapp.player-doubles.js"></script>

<!-- <script type="text/javascript">
document.getElementById('hitplayerbutton').title="changed";
</script>
 -->

	<jsp:include page="/footer.jsp" />

</body>

</html>
