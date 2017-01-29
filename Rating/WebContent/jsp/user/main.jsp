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
	<fmt:message bundle="${loc}" key="localization.about_film"
		var="about_film" />
	<fmt:message bundle="${loc}" key="localization.give_an_estimate"
		var="give_an_estimate" />
	<fmt:message bundle="${loc}" key="localization.previous" var="previous" />
	<fmt:message bundle="${loc}" key="localization.next" var="next" />
	<div class="wrapper">
		<div class="main"> <c:forEach var="element"
			items="${sessionScope.mainFilms}" varStatus="stat">
			<div class="section">
			<div class="wrap_title">
				<div class="title">
					<p>${element.title}
					<p>
				</div>
			</div>
			<div class="wrap_img">
			<form method="post" action="MainController">
			<input type="hidden" name="command" value="find_film_by_id">
			<input type="hidden" name="id" value="${element.idFilm}">
				<a href=" " onClick="this.parentNode.submit(); return false;"><img
					src="${element.poster}" /></a>
			</form>
			</div>
			<div class="assessment">
				<p>	&#9734; ${element.rating}</p>
			</div>
			<div class="information">
				<form method="post" action="MainController">
					<input type="hidden" name="command" value="find_film_by_id">
					<input type="hidden" name="id" value="${element.idFilm}"> <input
						name="go" class="sub_search" type="submit" value="${about_film }" />
				</form>
			</div>

			</div>
		</c:forEach> <c:if test="${sessionScope.currentPage ne 1}">
			<td><a class="link"
				href="/Rating/MainController?command=find_film_main&pageParam=main&page=${sessionScope.currentPage-1}">${previous}</a></td>
		</c:if>


		<table border="1" cellpadding="5" cellspacing="5">
			<tr>
				<c:forEach begin="1" end="${sessionScope.noOfPages}" var="i">
					<c:choose>
						<c:when test="${sessionScope.currentPage eq i}">
							<td>${i}</td>
						</c:when>
						<c:otherwise>
							<td><a class="link"
								href="/Rating/MainController?command=find_film_main&pageParam=main&page=${i}">${i}</a></td>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</tr>
		</table>


		<c:if test="${sessionScope.currentPage lt sessionScope.noOfPages}">
			<td><a class="link"
				href="/Rating/MainController?command=find_film_main&pageParam=main&page=${sessionScope.currentPage + 1}">${next}</a></td>
		</c:if> </div>
	</div>
</body>
</html>