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
	<fmt:message bundle="${loc}" key="localization.blocking" var="blocking" />
	<fmt:message bundle="${loc}" key="localization.unblocking"
		var="unblocking" />
	<fmt:message bundle="${loc}" key="localization.change_rating"
		var="change_rating" />
	<fmt:message bundle="${loc}" key="localization.make_by_user"
		var="make_by_user" />
	<fmt:message bundle="${loc}" key="localization.make_by_admin"
		var="make_by_admin" />
	<fmt:message bundle="${loc}" key="localization.amount_assessment"
		var="amount_assessment" />
	<fmt:message bundle="${loc}" key="localization.amount_reviews"
		var="amount_reviews" />
	<div class="wrapper">
		<div class="main">
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
					<td></td>
				</tr>
				<c:set var="user" value="${requestScope.user}" />
				<tr>
					<td>${user.idUser}</td>
					<td class="img"><img src="${user.image}" /></td>
					<td>${user.firstName}</td>
					<td>${user.login}</td>
					<td>${user.email}</td>
					<td>${user.rating}</td>
					<td>${user.status}</td>
					<td><c:if test="${user.role eq 'USER'}">
${user.role}
<form method="post" action="MainController">
								<input type="hidden" name="command" value="change_role">
								<input type="hidden" name="id" value="${user.idUser}"> <input
									type="hidden" name="role" value="admin"> <a
									class="link_table" href=" "
									onClick="this.parentNode.submit(); return false;">${make_by_admin}</a>
							</form>
						</c:if> <c:if test="${user.role eq 'ADMIN'}">
${user.role}
<form method="post" action="MainController">
								<input type="hidden" name="command" value="change_role">
								<input type="hidden" name="id" value="${user.idUser}"> <input
									type="hidden" name="role" value="user"> <a
									class="link_table" href=" "
									onClick="this.parentNode.submit(); return false;">${make_by_user}</a>
							</form>
						</c:if></td>
					<td><fmt:formatDate type="date" value="${user.dateReg}" /></td>

					<td><c:if test="${user.status=='UNBLOCK'}">

							<form method="post" action="MainController">
								<input type="hidden" name="command" value="block_user">
								<input type="hidden" name="id" value="${user.idUser}"> <input
									type="hidden" name="status" value="block"> <a
									class="link_table" href=" "
									onClick="this.parentNode.submit(); return false;">${blocking}</a>
							</form>

						</c:if> <c:if test="${user.status=='BLOCK'}">

							<form method="post" action="MainController">
								<input type="hidden" name="command" value="block_user">
								<input type="hidden" name="id" value="${user.idUser}"> <input
									type="hidden" name="status" value="unblock"> <a
									class="link_table" href=" "
									onClick="this.parentNode.submit(); return false;">${unblocking}</a>
							</form>
						</c:if></td>
			</table>
		</div>
	</div>
</body>