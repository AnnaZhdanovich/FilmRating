<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/generalStyle.css" type="text/css"
	media="screen">
<link href="https://fonts.googleapis.com/css?family=Neucha"
	rel="stylesheet">
</head>
<body>
	<fmt:setLocale value="${sessionScope.locale}" />
	<fmt:setBundle basename="resources.localization" var="loc" />
	<fmt:message bundle="${loc}" key="localization.give_an_assessment"
		var="give_an_assessment" />
	<fmt:message bundle="${loc}" key="localization.send" var="send" />
	<fmt:message bundle="${loc}" key="localization.year" var="year" />
	<fmt:message bundle="${loc}" key="localization.country" var="country" />
	<fmt:message bundle="${loc}" key="localization.actor" var="actor" />
	<fmt:message bundle="${loc}" key="localization.producer" var="producer" />
	<fmt:message bundle="${loc}" key="localization.plot" var="plot" />
	<fmt:message bundle="${loc}" key="localization.genres" var="genres" />
	<fmt:message bundle="${loc}" key="localization.reviews" var="reviews" />
	<fmt:message bundle="${loc}" key="localization.rating_film"
		var="rating_film" />
	<fmt:message bundle="${loc}" key="localization.out_of_ten"
		var="out_of_ten" />
	<fmt:message bundle="${loc}" key="localization.comment_film"
		var="comment_film" />
	<fmt:message bundle="${loc}" key="localization.worning_give_comment"
		var="worning_give_comment" />
	<fmt:message bundle="${loc}" key="localization.give_comment"
		var="give_comment" />
	<fmt:message bundle="${loc}" key="localization.add_date" var="add_date" />
	<div class="wrapper">
		<div class="main">
			<c:set var="film" value="${requestScope.film}" />
			<div class="fm_poster">
				<img src="${film.poster}">
			</div>
			<div class="fm_information">
				<h2>${film.title}</h2>
				<hr>
				<h3>${rating_film}</h3>
				<p>${film.rating} ${out_of_ten}</p>
				<hr>
				<h3>${year}</h3>
				<p>${film.year}</p>
				<h3>${country }</h3>
				<p>${film.country.name}</p>
				<h3>${genres }</h3>
				<p>
					<c:forEach var="ganre" items="${film.listGenre}" varStatus="stat">
			  ${ganre.name}; 
			  </c:forEach>
				</p>
				<h3>${producer}</h3>
				<p>
					<c:forEach var="producer" items="${film.listPersonality}"
						varStatus="stat">
						<c:if test="${producer.role=='PRODUCER'}">
			  ${producer.firstName} ${producer.lastName}; 
			  </c:if>
					</c:forEach>
				</p>
				<h3>${actor}</h3>
				<p>
					<c:forEach var="actor" items="${film.listPersonality}"
						varStatus="stat">
						<c:if test="${actor.role=='ACTOR'}">
			  ${actor.firstName} ${actor.lastName}; 
			  </c:if>
					</c:forEach>
				</p>
				<h3>${add_date}</h3>
				<p><fmt:formatDate type="date" value="${film.date}" /></p>
			
			<hr>
			<h3>${plot}</h3>
			<p>${film.description}</p>
			<hr>
			<h3>${give_an_assessment}</h3>
			<form method="post" action="MainController">

				<input type="hidden" name="command" value="give_assessment" /> <input
					type="hidden" name=idFilm value="${film.idFilm}" />
				<div class="fm_radio">
					<p>1</p>
					<input type="radio" name=number value="1">
				</div>

				<div class="fm_radio">
					<p>2</p>
					<input type="radio" name=number value="2">
				</div>

				<div class="fm_radio">
					<p>3</p>
					<input type="radio" name=number value="3">
				</div>
				<div class="fm_radio">
					<p>4</p>
					<input type="radio" name=number value="4">
				</div>
				<div class="fm_radio">
					<p>5</p>
					<input type="radio" name=number value="5">
				</div>
				<div class="fm_radio">
					<p>6</p>
					<input type="radio" name=number value="6">
				</div>

				<div class="fm_radio">
					<p>7</p>
					<input type="radio" name=number value="7">
				</div>

				<div class="fm_radio">
					<p>8</p>
					<input class="fm_radio" type="radio" name=number value="8">
				</div>
				<div class="fm_radio">
					<p>9</p>
					<input type="radio" name=number value="9">
				</div>
				<div class="fm_radio">
					<p>10</p>
					<input type="radio" name=number value="10">
				</div>
				<div>
					<input class="take_com" type="submit" value="${send}" />
				</div>
			</form>
			<hr>
			<h3>${reviews}</h3>
			<p class="worning">${worning_give_comment}</p>
			<form method="post" action="MainController">
				<input type="hidden" name="idFilm" value="${film.idFilm}" /> <input
					type="hidden" name="command" value="give_comment">
				<h4>${give_comment}</h4>
				<textarea rows="8" cols="72" name="comment"></textarea>
				<input class="take_com" type="submit" value="${send}" />
			</form>
			<hr>
			<form method="post" action="MainController">
				<input type="hidden" name="filmUid" value="${film.idFilm}" /> <input
					type="hidden" name="command" value="take_comments_film"> <input
					class="take_com" type="submit" value="${comment_film }" />
			</form>
		</div>
	</div>
	</div>
</body>
</html>