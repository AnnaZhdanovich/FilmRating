<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet" href="css/discussedFilmStyle.css" type="text/css"
	media="screen">
</head>
<body>
	<c:forEach var="element" items="${sessionScope.asideFilms}">
		<div class="wrap_aside_info">
			<form method="post" action="MainController">
				<input type="hidden" name="command" value="find_film_by_id">
				<input type="hidden" name="id" value="${element.idFilm}"> <a
					href="MainController"
					onClick="this.parentNode.submit(); return false;"><img
					class="image" src="${element.poster}" title="${element.title}" /></a>
			</form>
			&#9734; ${element.rating}
		</div>
	</c:forEach>
</body>
</html>