<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/prevCommentStyle.css" type="text/css"
	media="screen">
<link href="https://fonts.googleapis.com/css?family=Neucha"
	rel="stylesheet">
</head>
<body>
	<c:forEach var="element" items="${sessionScope.asideComments}">
		<form method="post" action="MainController">
			<input type="hidden" name="command" value="find_film_by_id"> <input
				type="hidden" name="id" value="${element.film.idFilm}"> <a class="text_link"
				href="MainController "
				onClick="this.parentNode.submit(); return false;">${ element.film.title}</a>
		</form>
		<form method="post" action="MainController">
			<input type="hidden" name="command" value="find_user"> <input
				type="hidden" name="idUser" value="${element.user.idUser}"> <a class="text_link"
				href="MainController "
				onClick="this.parentNode.submit(); return false;">${element.user.firstName}</a>
		</form>
		<div class="comment">
			<p>${element.text}</p>
		</div>
		<hr>
	</c:forEach>

</body>
</html>