<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="todaysDate" value="<%= new java.util.Date() %>"/>

<h1>BLACK <span>JACK</span> <small>a variant of pontoon and 21</small></h1>

<div id="links">
	<ul>
		<li><a href='displayRound.do'>New Round</a></li>		
		<li><a href='showRules.do'>Game Rules</a></li>
		<li><a href='addAccount.do'>Add Account</a></li>
		<li><a href='showCards.do'>Show Cards</a></li>
		
		<li><a href='https://github.com/tartankane/BlackJack'>The Code</a></li>
		
	</ul>
</div>