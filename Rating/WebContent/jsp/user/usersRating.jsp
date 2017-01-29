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
<link rel="stylesheet" href="css/searchFilmStyle.css">
<link href="https://fonts.googleapis.com/css?family=Neucha"
	rel="stylesheet">
</head>
<body>
	<fmt:setLocale value="${sessionScope.locale}" />
	<fmt:setBundle basename="resources.localization" var="loc" />
	<fmt:message bundle="${loc}" key="localization.registration_form"
		var="registration_form" />
	<fmt:message bundle="${loc}" key="localization.name" var="name" />
	<fmt:message bundle="${loc}" key="localization.login" var="login" />
	<fmt:message bundle="${loc}" key="localization.password" var="password" />
	<fmt:message bundle="${loc}" key="localization.email" var="email" />
	<fmt:message bundle="${loc}" key="localization.repit" var="repit" />
	<fmt:message bundle="${loc}" key="localization.avatar" var="avatar" />
	<fmt:message bundle="${loc}" key="localization.go" var="go" />
	<fmt:message bundle="${loc}" key="localization.best_user"
		var="best_user" />
	<fmt:message bundle="${loc}" key="localization.worst_user"
		var="worst_user" />
	<fmt:message bundle="${loc}" key="localization.look" var="look" />
	<fmt:message bundle="${loc}" key="localization.show" var="show" />
	<fmt:message bundle="${loc}" key="localization.previous" var="previous" />
	<fmt:message bundle="${loc}" key="localization.next" var="next" />


	<div class="wrapper">
		<div class="main">
			<div class="wrap_title_rating">
				<c:if test="${sessionScope.type eq 'down'}">
					<h2>${best_user}</h2>
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="rating_users">
						<input type="hidden" name="type" value="up"> <input
							type="hidden" name="goal" value="ratingUser"> <a href=" "
							class="link" onClick="this.parentNode.submit(); return false;">${look}
							${worst_user}</a>
					</form>
				</c:if>
				<c:if test="${sessionScope.type eq 'up'}">
					<h2>${worst_user}</h2>
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="rating_users">
						<input type="hidden" name="type" value="down"> <input
							type="hidden" name="goal" value="ratingUser"> <a href=" "
							class="link" onClick="this.parentNode.submit(); return false;">${look}
							${best_user}</a>
					</form>
				</c:if>
			</div>
			<c:forEach var="user" items="${requestScope.users}">
				<div class="wrap_film">
					<div class="wrap_film_img">
						<form method="post" action="MainController">
							<input type="hidden" name="command" value="find_user"> <input
								type="hidden" name="idUser" value="${user.idUser}"> <a
								href="MainController"
								onClick="this.parentNode.submit(); return false;"><img
								class="fm_img" src="${user.image}" /></a>
						</form>
					</div>

					<div class="wrap_film_data">

						<form method="post" action="MainController">
							<input type="hidden" name="command" value="find_user"> <input
								type="hidden" name="idUser" value="${user.idUser}"> <a
								href="MainController"
								onClick="this.parentNode.submit(); return false;">${user.firstName}</a>
						</form>

						<div class="fm_rating">
							<c:out value="${user.rating}"></c:out>
						</div>

					</div>
				</div>
			</c:forEach>
			<c:if test="${not empty requestScope.users }">
				<c:if test="${sessionScope.currentPage ne 1}">
					<td><a class="link"
						href="/Rating/MainController?command=rating_users&goal=${sessionScope.goal}&type=${sessionScope.type}&page=${sessionScope.currentPage-1}">${previous}</a></td>
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
										href="/Rating/MainController?command=rating_users&goal=${sessionScope.goal}&type=${sessionScope.type}&page=${i}">${i}</a></td>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</tr>
				</table>


				<c:if test="${sessionScope.currentPage lt sessionScope.noOfPages}">
					<td><a class="link"
						href="/Rating/MainController?command=rating_users&goal=${sessionScope.goal}&type=${sessionScope.type}&page=${sessionScope.currentPage + 1}">${next}</a></td>
				</c:if>
			</c:if>
		</div>
	</div>
</body>
</html>