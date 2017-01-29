<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tb" uri="tabletag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/adminStyle.css">
</head>
<body>
<fmt:setLocale value="${sessionScope.locale}" />
	<fmt:setBundle basename="resources.localization" var="loc" />
	<fmt:message bundle="${loc}" key="localization.amount_users"
		var="amount_users" />
	<fmt:message bundle="${loc}" key="localization.amount_films"
		var="amount_films" />
	<div class="wrapper">
		<div class="main">
		    <c:import url="/MainController?command=amount_films_and_users"></c:import>
			<tb:table-data rows="1" head="${amount_users}"> ${sessionScope.amountUsers}</tb:table-data>	
			<tb:table-data rows="1" head="${amount_films}"> ${sessionScope.amountFilms}</tb:table-data>			
		</div>
	</div>
</body>
</html>