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
<link rel="stylesheet" href="css/supportStyle.css">
<link href="https://fonts.googleapis.com/css?family=Neucha"
	rel="stylesheet">
</head>
<body>
	<fmt:setLocale value="${sessionScope.locale}" />
	<fmt:setBundle basename="resources.localization" var="loc" />	
	<fmt:message bundle="${loc}" key="localization.previous" var="previous" />
	<fmt:message bundle="${loc}" key="localization.next" var="next" />

	<div class="wrapper">
		<div class="main"> <c:forEach items="${requestScope.comments}"
			var="comment">
			<div class="wrap_comment">
				<div class="wrap_poster_film">
					<img class="wrap_poster_film"
						src="${comment.film.poster}" />
				</div>

				<div class="name_film">
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_film_by_id">
						<input type="hidden" name="id" value="${comment.film.idFilm}">
						<a href=" " class="title1"
							onClick="this.parentNode.submit(); return false;">${comment.film.title}</a>
					</form>
				</div>
				<div class="comment_full">
					<p>${comment.text}</p>
				</div>
			</div>
		</c:forEach> <c:if test="${not empty requestScope.comments }">
			<c:if test="${sessionScope.currentPage ne 1}">
				<td><a
					href="/Rating/MainController?command=take_comments&idUser=${sessionScope.userId}&page=${sessionScope.currentPage-1}">${previous}</a></td>
			</c:if>
			<table border="1" cellpadding="5" cellspacing="5">
				<tr>
					<c:forEach begin="1" end="${sessionScope.noOfPages}" var="i">
						<c:choose>
							<c:when test="${sessionScope.currentPage eq i}">
								<td>${i}</td>
							</c:when>
							<c:otherwise>
								<td><a
									href="/Rating/MainController?command=take_comments&idUser=${sessionScope.userId}&page=${i}">${i}</a></td>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</tr>
			</table>
			<c:if test="${sessionScope.currentPage lt sessionScope.noOfPages}">
				<td><a
					href="/Rating/MainController?command=take_comments&idUser=${sessionScope.userId}&page=${sessionScope.currentPage + 1}">${next}</a></td>
			</c:if>
		</c:if> </div>
	</div>
</body>
</html>