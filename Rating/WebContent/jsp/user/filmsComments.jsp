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
<link rel="stylesheet" href="css/prevCommentStyle.css" type="text/css"
	media="screen">
<link href="https://fonts.googleapis.com/css?family=Neucha"
	rel="stylesheet">
</head>
<body>
	<fmt:setLocale value="${sessionScope.locale}" />
	<fmt:setBundle basename="resources.localization" var="loc" />
	<fmt:message bundle="${loc}" key="localization.change_go"
		var="change_go" />
	<fmt:message bundle="${loc}" key="localization.change_form"
		var="change_form" />
	<fmt:message bundle="${loc}" key="localization.name" var="name" />
	<fmt:message bundle="${loc}" key="localization.login" var="login" />
	<fmt:message bundle="${loc}" key="localization.password" var="password" />
	<fmt:message bundle="${loc}" key="localization.email" var="email" />
	<fmt:message bundle="${loc}" key="localization.repit" var="repit" />
	<fmt:message bundle="${loc}" key="localization.avatar" var="avatar" />
	<fmt:message bundle="${loc}" key="localization.go" var="go" />
	<fmt:message bundle="${loc}" key="localization.previous" var="previous" />
	<fmt:message bundle="${loc}" key="localization.next" var="next" />



	<div class="wrapper">
		<div class="main">
			<c:forEach var="element" items="${requestScope.comments}">
				<div class="comment1">
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="find_user"> <input
							type="hidden" name="idUser" value="${element.user.idUser}">
						<a class="text_link" href="MainController "
							onClick="this.parentNode.submit(); return false;">${element.user.firstName}</a>
					</form>
					<hr>
					<p>${element.text}</p>
					<hr>
					<p>${element.date}</p>
				</div>


			</c:forEach>
			<c:if test="${not empty requestScope.comments }">
				<c:if test="${sessionScope.currentPage ne 1}">
					<td><a
						href="/Rating/MainController?command=take_comments_film&filmUid=${sessionScope.filmUid}&page=${sessionScope.currentPage-1}">${previous}</a></td>
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
										href="/Rating/MainController?command=take_comments_film&filmUid=${sessionScope.filmUid}&page=${i}">${i}</a></td>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</tr>
				</table>


				<c:if test="${sessionScope.currentPage lt sessionScope.noOfPages}">
					<td><a
						href="/Rating/MainController?command=take_comments_film&filmUid=${sessionScope.filmUid}&page=${sessionScope.currentPage + 1}">${next}</a></td>
				</c:if>
			</c:if>
		</div>
	</div>
</body>
</html>