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
	

	<div id="dealercards" class="row"></div>

	<div id="playercards" class="row"></div>

	<div id="gamemessages" class="row"></div>


	<p>
		<button type="button" onclick="startGame();" id="startgamebutton">
			Start A New Game</button>
	</p>

	<p>
		<button type="button" class="btn" onclick="hitPlayer();"
			id="hitplayerbutton">Hit Me</button>
			
				<button type="button" class="btn" onclick="playerStands();"
			id="playerstandsbutton">Stand</button>
	</p>

	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
	<script src="bjapp.start.js"></script>
	<script src="bjapp.hitplayer.js"></script>
	<script src="bjapp.player-stands.js"></script>

	<jsp:include page="/footer.jsp" />

</body>

</html>
