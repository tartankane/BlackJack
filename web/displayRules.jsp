<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<html>

	<style>
	p
	{
	font-size:200%;
	text-align:center;
	} 
	</style>
	
	
<head>
	<title>All my cards  </title>
	<link href="<c:url value="/styles.css"/>" rel="Stylesheet" type="text/css"/>
</head>
  
<body>

	<jsp:include page="/header.jsp"/>
	

	<p>
 <br>Basic BlackJack Rules </br>
	<br></br>

	<br>BlackJack is a variant of Pontoon and "21"</br>
	<br>Only one deck is used. Take advantage of this. In casinos, multiple decks are used.</br>
	<br>A new deck is used for each game</br>
	<br>After the opening cards have been dealt, the player can double down to double the bet, but only receives one card</br>
	<br>Stand is also known as stick</br>
	<br>Hit is also known as twist</br>
	<br>An ace has a value of 1 or 11</br>
	<br>In Blackjack, there are no 5 card tricks, unlike in Pontoon.</br>
	<br>The dealer has to stand on a hand of 17 to 21</br>
	<br>The dealer has to hit on a hand of 16 or less</br>
	<br>A normal win pays 1:1</br>
	<br>A win with a blackjack pays 3:2</br>
	<br>A draw (called a push in BlackJack) results in the player's bet being returned</br>
	<br>The dealer must stand if the dealer has an ace that, when treated as 11, brings the dealer's hand to between 17 and 21, e.g. a 6 and an ace</br>
	<br></br>
	 <br>Advanced BlackJack Rules </br>
	</p>

	
	<jsp:include page="/footer.jsp"/>
	
</body>

</html>