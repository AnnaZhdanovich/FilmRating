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
	<fmt:message bundle="${loc}" key="localization.navigation_bar"
		var="navigation_bar" />
	<fmt:message bundle="${loc}" key="localization.discussed"
		var="discussed" />
	<fmt:message bundle="${loc}" key="localization.last_reviews"
		var="last_reviews" />

	<div class="wrapper">
		<div class="aside">
		<div class="paragraph">
			<p>${discussed}</p>
		</div>
		<hr>
		<c:import url="/MainController?command=aside_film"></c:import> <c:import
			url="/jsp/user/discussedFilms.jsp" />
		<hr>
		<div class="paragraph">
			<p>${last_reviews}</p>
		</div>
		<hr>
		<c:import url="/MainController?command=aside_film_comment"></c:import>
		<c:import url="/jsp/user/lastComment.jsp" /> </div>
	</div>
</body>
</html>