<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/adminStyle.css">
<link rel="stylesheet" href="css/tableStyle.css">
<link href="https://fonts.googleapis.com/css?family=Neucha"
	rel="stylesheet">
</head>
<body>
	<fmt:setLocale value="${sessionScope.locale}" />
	<fmt:setBundle basename="resources.localization" var="loc" />
	<fmt:message bundle="${loc}" key="localization.avatar" var="avatar" />
	<fmt:message bundle="${loc}" key="localization.firstname"
		var="firstname" />
	<fmt:message bundle="${loc}" key="localization.login" var="login" />
	<fmt:message bundle="${loc}" key="localization.email" var="email" />
	<fmt:message bundle="${loc}" key="localization.rating_us"
		var="rating_us" />
	<fmt:message bundle="${loc}" key="localization.status_user"
		var="status_user" />
	<fmt:message bundle="${loc}" key="localization.role" var="role" />
	<fmt:message bundle="${loc}" key="localization.data_reg" var="data_reg" />
	<fmt:message bundle="${loc}" key="localization.add_actor_producer"
		var="add_actor_producer" />
	<fmt:message bundle="${loc}" key="localization.change_rating"
		var="change_rating" />	
	<fmt:message bundle="${loc}" key="localization.best_user"
		var="best_user" />
	<fmt:message bundle="${loc}" key="localization.worst_user"
		var="worst_user" />
	<fmt:message bundle="${loc}" key="localization.look" var="look" />


	<div class="wrapper">
		<div class="main">
			<c:if test="${sessionScope.type eq 'down'}">
				<h3>${best_user}</h3>
				<form method="post" action="MainController">
					<input type="hidden" name="command" value="rating_users"> <input
						type="hidden" name="type" value="up"> <input type="hidden"
						name="goal" value="ratingUser"> <a href=" "
						onClick="this.parentNode.submit(); return false;">${look}
						${worst_user}</a>
				</form>
			</c:if>

			<c:if test="${sessionScope.type eq 'up'}">
				<h3>${worst_user}</h3>
				<form method="post" action="MainController">
					<input type="hidden" name="command" value="rating_users"> <input
						type="hidden" name="type" value="down"> <input
						type="hidden" name="goal" value="ratingUser"> <a href=" "
						onClick="this.parentNode.submit(); return false;">${look}
						${best_user}</a>
				</form>

			</c:if>
			<table class="simple-little-table">
				<tr>
					<td>ID</td>
					<td>${avatar}</td>
					<td>${firstname}</td>
					<td>${login }</td>
					<td>${email}</td>
					<td>${rating_us}</td>
					<td>${status_user}</td>
					<td>${role}</td>
					<td>${data_reg}</td>
				</tr>

				<c:forEach items="${requestScope.users}" var="user">
					<tr>
						<td>${user.idUser}</td>
						<td class="img"><img src="${user.image}" /></td>
						<td>${user.firstName}</td>
						<td>${user.login}</td>
						<td>${user.email}</td>
						<td>${user.rating}</td>
						<td>${user.status}</td>
						<td>${user.role}</td>
						<td><fmt:formatDate type="date" value="${user.dateReg}" /></td>
				</c:forEach>
			</table>			
		</div>
	</div>
</body>