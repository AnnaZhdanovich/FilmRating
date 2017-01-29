<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/adminStyle.css">
<link rel="stylesheet" href="css/screenStyle.css" type="text/css"
	media="screen" />
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="js/tabbed.js"></script>
<link href="https://fonts.googleapis.com/css?family=Neucha"
	rel="stylesheet">
</head>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="resources.localization" var="loc" />
<fmt:message bundle="${loc}" key="localization.removed_films"
	var="removed_films" />
<fmt:message bundle="${loc}" key="localization.users" var="users" />
<fmt:message bundle="${loc}" key="localization.films" var="films" />
<fmt:message bundle="${loc}" key="localization.added_comment"
	var="added_comment" />
<fmt:message bundle="${loc}" key="localization.find_the_user"
	var="find_the_user" />
<fmt:message bundle="${loc}" key="localization.blocked_users"
	var="blocked_users" />
<fmt:message bundle="${loc}" key="localization.rating_users"
	var="rating_users" />
<fmt:message bundle="${loc}" key="localization.find_the_film"
	var="find_the_film" />
<fmt:message bundle="${loc}" key="localization.find_by_id"
	var="find_by_id" />
<fmt:message bundle="${loc}" key="localization.pattern_id"
	var="pattern_id" />
<fmt:message bundle="${loc}" key="localization.find_by_login"
	var="find_by_login" />
<fmt:message bundle="${loc}" key="localization.requests" var="requests" />
<fmt:message bundle="${loc}" key="localization.add_film" var="add_film" />
<fmt:message bundle="${loc}" key="localization.rating_films"
	var="rating_films" />
<fmt:message bundle="${loc}" key="localization.users_list"
	var="users_list" />
<body>
	<div class="wrapper">
		<div class="aside">
			<div class="nav">
				<ul>
					<li><a href="#tab1">${users}</a></li>
					<li><a href="#tab2">${films}</a></li>
				</ul>
			</div>
			<div class="section_nav" class="tab" id="tab1">
				<div class="but">
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_users_list">
						<a href=" " onClick="this.parentNode.submit(); return false;">${users_list}</a>
					</form>
				</div>

				<hr>
				<div class="but">
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="rating_users">
						<input type="hidden" name="type" value="down"> <input
							type="hidden" name="goal" value="ratingUser"><a href=" "
							onClick="this.parentNode.submit(); return false;">${rating_users}</a>
					</form>

				</div>

				<hr>
				<div class="but">
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_user_requests">
						<a href=" " onClick="this.parentNode.submit(); return false;">${requests }</a>
					</form>
				</div>
				<hr>
				<div class="but">
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_user"> <input
							class="but_login" type="text" name="idUser" pattern="[0-9]+"
							title="${pattern_id}"> <input type="submit"
							value="${find_by_id }">
					</form>
				</div>
				<hr>
				<div class="but">
					<form method="post" action="MainController">
						<input name="command" type="hidden" value="find_user_by_login">
						<input class="but_login" type="text" name="login"> <input
							type="submit" value="${find_by_login }">
					</form>
				</div>

			</div>
			<div class="section_nav" class="tab" id="tab2">
				<p>
				<div class="but">
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="get_personality">
						<input type="hidden" name="goal" value="serchFilm"> <a
							href=" " onClick="this.parentNode.submit(); return false;">${find_the_film}</a>
					</form>
				</div>
				<hr>
				<div class="but">
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="get_personality">
						<input type="hidden" name="goal" value="addPage"> <a
							href=" " onClick="this.parentNode.submit(); return false;">
							${add_film} </a>
					</form>
				</div>
				<hr>
				<div class="but">
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="rating_films">
						<input type="hidden" name="type" value="up"> <input
							type="hidden" name=goal value="ratingFilm"> <a href=" "
							onClick="this.parentNode.submit(); return false;">
							${rating_films} </a>
					</form>
				</div>

				<hr>
				<div class="but">
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_added_comment">
						<a href=" " onClick="this.parentNode.submit(); return false;">
							${added_comment}</a>
					</form>
				</div>
				<hr>
				<div class="but">
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_removed_films">
						<a href=" " onClick="this.parentNode.submit(); return false;">
							${removed_films}</a>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>