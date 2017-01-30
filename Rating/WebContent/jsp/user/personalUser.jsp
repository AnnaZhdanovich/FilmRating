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
	<fmt:message bundle="${loc}" key="localization.personal_data"
		var="personal_data" />
	<fmt:message bundle="${loc}" key="localization.name" var="name" />
	<fmt:message bundle="${loc}" key="localization.rating_user"
		var="rating_user" />
	<fmt:message bundle="${loc}" key="localization.amount_assessment"
		var="amount_assessment" />
	<fmt:message bundle="${loc}" key="localization.amount_reviews"
		var="amount_reviews" />
	<fmt:message bundle="${loc}" key="localization.status_user"
		var="status_user" />
	<fmt:message bundle="${loc}" key="localization.data_reg" var="data_reg" />
	<fmt:message bundle="${loc}" key="localization.data_last_visit"
		var="data_last_visit" />
	<fmt:message bundle="${loc}" key="localization.status_user"
		var="status_user" />
	<fmt:message bundle="${loc}" key="localization.change" var="change" />
	<fmt:message bundle="${loc}" key="localization.assessment"
		var="assessment" />
	<fmt:message bundle="${loc}" key="localization.reviews" var="reviews" />

	<div class="wrapper">
		<div class="main">
			<h2>${personal_data}</h2>
			<hr>
			<div class="wrap_avatar">
				<img class="avatar" src="${requestScope.user.image}" />
			</div>
			<div class="personal_data">
				<div class="data">
					<h4>${name}</h4>
					<p>${requestScope.user.firstName}</p>
				</div>
				<div class="data">
					<h4>${rating_user }</h4>
					<p>${requestScope.user.rating}</p>
				</div>
				<div class="data">
					<h4>${amount_assessment}</h4>

					<p>${requestScope.user.amountOfAssessment}</p>
				</div>
				<div class="data">
					<h4>${amount_reviews}</h4>

					<p>${requestScope.user.amountOfComment}</p>
				</div>

				<div class="data">
					<h4>${status_user}</h4>

					<p>${requestScope.user.status}</p>
				</div>
				<div class="data">
					<h4>${data_reg}</h4>
					<p>
						<fmt:formatDate type="date" value="${requestScope.user.dateReg}" />
					</p>
				</div>

			</div>

			<div class="wrapp_fuche">
				<c:if test="${sessionScope.id eq requestScope.user.idUser}">
					<div class="change">
						<form method="post" action="MainController">
							<input type="hidden" name="command" value="change"> <input
								class="sub1" type="submit" value="${change}">
						</form>
					</div>
				</c:if>
				<div class="coment">
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="take_assessments">
						<input type="hidden" name="idUser"
							value="${requestScope.user.idUser}"> <input class="sub1"
							type="submit" value="${assessment}">
					</form>
				</div>

				<div class="coment">
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="take_comments">
						<input type="hidden" name="idUser"
							value="${requestScope.user.idUser}"> <input class="sub1"
							type="submit" value="${reviews}">
					</form>
				</div>

			</div>

		</div>
	</div>

</body>
</html>