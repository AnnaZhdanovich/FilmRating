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
	<fmt:message bundle="${loc}" key="localization.navigation_bar"
		var="navigation_bar" />
	<fmt:message bundle="${loc}" key="localization.rating" var="rating" />
	<fmt:message bundle="${loc}" key="localization.best_film"
		var="best_film" />
	<fmt:message bundle="${loc}" key="localization.worst_film"
		var="worst_film" />
	<fmt:message bundle="${loc}" key="localization.top" var="top" />
	<fmt:message bundle="${loc}" key="localization.rating_users"
		var="rating_users" />
	<fmt:message bundle="${loc}" key="localization.films" var="films" />
	<fmt:message bundle="${loc}" key="localization.new_films"
		var="new_films" />
	<fmt:message bundle="${loc}" key="localization.recommended"
		var="recommended" />
	<fmt:message bundle="${loc}" key="localization.genres" var="genres" />
	<fmt:message bundle="${loc}" key="localization.triller" var="triller" />
	<fmt:message bundle="${loc}" key="localization.drama" var="drama" />
	<fmt:message bundle="${loc}" key="localization.melodrama"
		var="melodrama" />
	<fmt:message bundle="${loc}" key="localization.horor" var="horor" />
	<fmt:message bundle="${loc}" key="localization.action" var="action" />
	<fmt:message bundle="${loc}" key="localization.comedy" var="comedy" />
	<fmt:message bundle="${loc}" key="localization.family" var="family" />
	<fmt:message bundle="${loc}" key="localization.historical"
		var="historical" />
	<fmt:message bundle="${loc}" key="localization.detective"
		var="detective" />
	<fmt:message bundle="${loc}" key="localization.fantastic"
		var="fantastic" />
	<fmt:message bundle="${loc}" key="localization.fantasy" var="fantasy" />
	<fmt:message bundle="${loc}" key="localization.biography"
		var="biography" />
	<fmt:message bundle="${loc}" key="localization.military" var="military" />
	<fmt:message bundle="${loc}" key="localization.adventure"
		var="adventure" />
	<fmt:message bundle="${loc}" key="localization.sport" var="sport" />
	<fmt:message bundle="${loc}" key="localization.documentary"
		var="documentary" />
	<fmt:message bundle="${loc}" key="localization.crime" var="crime" />
	<div class="wrapper">
		<div class="nav">
			<div class="paragraph">
				<p>${navigation_bar }</p>
			</div>
			<hr>
			<div class="paragraph">
				<p>${rating}</p>
			</div>
			<ul>
				<li>
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="rating_films">
						<input type="hidden" name="type" value="up"> <input
							type="hidden" name="goal" value="ratingFilm"> <a href=" "
							onClick="this.parentNode.submit(); return false;">${best_film}</a>
					</form>

				</li>
				<li>
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="rating_films">
						<input type="hidden" name="type" value="down"> <input
							type="hidden" name="goal" value="ratingFilm"> <a href=" "
							onClick="this.parentNode.submit(); return false;">${worst_film }</a>
					</form>
				</li>

				<li>
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="rating_users">
						<input type="hidden" name="type" value="down"> <input
							type="hidden" name="goal" value="ratingUser"> <a href=" "
							onClick="this.parentNode.submit(); return false;">${rating_users}</a>
					</form>
				</li>
			</ul>

			<div class="paragraph">
				<p>${genres}</p>
			</div>
			<ul>
				<li>
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_films_by_genre">
						<input type="hidden" name="genre" value="1"> <a href=" "
							onClick="this.parentNode.submit(); return false;">${triller}</a>
					</form>
				</li>
				<li>
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_films_by_genre">
						<input type="hidden" name="genre" value="2"> <a href=" "
							onClick="this.parentNode.submit(); return false;">${drama}</a>
					</form>
				</li>

				<li>
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_films_by_genre">
						<input type="hidden" name="genre" value="3"> <a href=" "
							onClick="this.parentNode.submit(); return false;">${melodrama }</a>
					</form>
				</li>
				<li>
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_films_by_genre">
						<input type="hidden" name="genre" value="4"> <a href=" "
							onClick="this.parentNode.submit(); return false;">${horor}</a>
					</form>
				</li>
				<li>
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_films_by_genre">
						<input type="hidden" name="genre" value="5"> <a href=" "
							onClick="this.parentNode.submit(); return false;">${action}</a>
					</form>
				</li>
				<li>
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_films_by_genre">
						<input type="hidden" name="genre" value="6"> <a href=" "
							onClick="this.parentNode.submit(); return false;">${comedy}</a>
					</form>
				</li>
				<li>
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_films_by_genre">
						<input type="hidden" name="genre" value="7"> <a href=" "
							onClick="this.parentNode.submit(); return false;">${family}</a>
					</form>
				</li>
				<li>
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_films_by_genre">
						<input type="hidden" name="genre" value="8"> <a href=" "
							onClick="this.parentNode.submit(); return false;">${historical}</a>
					</form>
				</li>
				<li>
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_films_by_genre">
						<input type="hidden" name="genre" value="9"> <a href=" "
							onClick="this.parentNode.submit(); return false;">${detective}</a>
					</form>
				</li>
				<li>
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_films_by_genre">
						<input type="hidden" name="genre" value="10"> <a href=" "
							onClick="this.parentNode.submit(); return false;">${fantastic}</a>
					</form>
				</li>
				<li>
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_films_by_genre">
						<input type="hidden" name="genre" value="11"> <a href=" "
							onClick="this.parentNode.submit(); return false;">${fantasy }</a>
					</form>
				</li>
				<li>
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_films_by_genre">
						<input type="hidden" name="genre" value="12"> <a href=" "
							onClick="this.parentNode.submit(); return false;">${biography}</a>
					</form>
				</li>
				<li>
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_films_by_genre">
						<input type="hidden" name="genre" value="13"> <a href=" "
							onClick="this.parentNode.submit(); return false;">${military}</a>
					</form>

				</li>
				<li>
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_films_by_genre">
						<input type="hidden" name="genre" value="14"> <a href=" "
							onClick="this.parentNode.submit(); return false;">${adventure}</a>
					</form>
				</li>
				<li>
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_films_by_genre">
						<input type="hidden" name="genre" value="15"> <a href=" "
							onClick="this.parentNode.submit(); return false;">${sport}</a>
					</form>
				</li>
				<li>
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_films_by_genre">
						<input type="hidden" name="genre" value="16"> <a href=" "
							onClick="this.parentNode.submit(); return false;">${documentary}</a>
					</form>
				</li>
				<li>
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_films_by_genre">
						<input type="hidden" name="genre" value="17"> <a href=" "
							onClick="this.parentNode.submit(); return false;">${crime}</a>
					</form>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>