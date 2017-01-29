<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/adminStyle.css">
<link rel="stylesheet" href="css/tableStyle.css">
<link href="https://fonts.googleapis.com/css?family=Neucha"
	rel="stylesheet">
</head>
<body>
	<fmt:setLocale value="${sessionScope.locale}" />
	<fmt:setBundle basename="resources.localization" var="loc" />
	<fmt:message bundle="${loc}" key="localization.add_film" var="add_film" />
	<fmt:message bundle="${loc}" key="localization.title" var="title" />
	<fmt:message bundle="${loc}" key="localization.country" var="country" />
	<fmt:message bundle="${loc}" key="localization.year" var="year" />
	<fmt:message bundle="${loc}" key="localization.description"
		var="description" />
	<fmt:message bundle="${loc}" key="localization.actor" var="actor" />
	<fmt:message bundle="${loc}" key="localization.producer" var="producer" />
	<fmt:message bundle="${loc}" key="localization.genres" var="genres" />
	<fmt:message bundle="${loc}" key="localization.poster" var="poster" />
	<fmt:message bundle="${loc}" key="localization.data_add" var="data_add" />
	<fmt:message bundle="${loc}" key="localization.delete" var="delete" />
	<fmt:message bundle="${loc}" key="localization.change" var="change" />
	<fmt:message bundle="${loc}" key="localization.rating_film"
		var="rating_film" />
	<fmt:message bundle="${loc}" key="localization.input" var="input" />
	<fmt:message bundle="${loc}" key="localization.output" var="output" />
	<fmt:message bundle="${loc}" key="localization.previous" var="previous" />
	<fmt:message bundle="${loc}" key="localization.next" var="next" />
	<div class="wrapper">
		<div class="main">
			<table class="simple-little-table">
				<tr>
					<td>${poster }</td>
					<td>${title}</td>
					<td>${genres}</td>
					<td>${country }</td>
					<td>${year }</td>
					<td>${actor }</td>
					<td>${producer }</td>
					<td>${rating_film }</td>
					<td>${data_add }</td>
					<td>${output}</td>
					<td>${change}</td>
				</tr>
				<c:forEach items="${requestScope.films}" var="film">
					<tr>
						<td class="img"><img src="${film.poster}" /></td>
						<td><c:out value="${film.title}" /></td>

						<td><c:forEach items="${film.listGenre}" var="ganre">
								<p>
									<c:out value="${ganre.name}" />
								</p>
							</c:forEach>
						<td><c:out value="${film.country.name}" /></td>
						<td><c:out value="${film.year}" /></td>

						<td><c:forEach items="${film.listPersonality}" var="actor">
								<c:if test="${actor.role eq 'ACTOR'}">
									<p>
										<c:out value="${actor.firstName} ${actor.lastName}" />
									</p>
								</c:if>
							</c:forEach></td>
						<td><c:forEach items="${film.listPersonality}" var="actor">
								<c:if test="${actor.role eq 'PRODUCER'}">
									<p>
										<c:out value="${actor.firstName}${actor.lastName}" />
									</p>
								</c:if>
							</c:forEach></td>
						<td><c:out value="${film.rating}" /></td>
						<td><c:out value="${film.date}" /></td>
						<td>
							<form method="post" action="MainController">
								<input type="hidden" name="command" value="remove_film">
								<input type="hidden" name="filmUid" value="${film.idFilm}">
								<input type="hidden" name="type" value="unblock"><a
									href=" " onClick="this.parentNode.submit(); return false;">${output}</a>
							</form>
						</td>
						<td>
							<form method="post" action="MainController">
								<input type="hidden" name="command" value="change_film">
								<input type="hidden" name="id" value="${film.idFilm}"> <a
									href=" " onClick="this.parentNode.submit(); return false;">${change}</a>
							</form>
						</td>
					</tr>
				</c:forEach>
			</table>
			<c:if test="${not empty requestScope.films }">
				<c:if test="${sessionScope.currentPage != 1}">
					<td><a
						href="/Rating/MainController?command=find_removed_films&page=${sessionScope.currentPage-1}">${previous}</a></td>
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
										href="/Rating/MainController?command=find_removed_films&page=${i}">${i}</a></td>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</tr>
				</table>


				<c:if test="${sessionScope.currentPage lt sessionScope.noOfPages}">
					<td><a
						href="/Rating/MainController?command=find_removed_films&page=${sessionScope.currentPage + 1}">${next}</a></td>
				</c:if>
			</c:if>
		</div>
	</div>
</body>