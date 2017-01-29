<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/generalStyle.css" type="text/css" 	media="screen">
<link rel="stylesheet" href="css/supportStyle.css" >
<script src="js/request.js"></script>
<link href="https://fonts.googleapis.com/css?family=Neucha"
	rel="stylesheet">
</head>
<body>
	<fmt:setLocale value="${sessionScope.locale}" />
	<fmt:setBundle basename="resources.localization" var="loc" />
	<fmt:message bundle="${loc}" key="localization.request_form"
		var="request_form" />

	<fmt:message bundle="${loc}" key="localization.login" var="login" />
	<fmt:message bundle="${loc}" key="localization.password" var="password" />

	<fmt:message bundle="${loc}" key="localization.enter_request"
		var="enter_request" />
	<fmt:message bundle="${loc}" key="localization.request" var="request" />

	<fmt:message bundle="${loc}" key="localization.login_tamplate"
		var="login_tamplate" />
	<fmt:message bundle="${loc}" key="localization.password_tamplate"
		var="password_tamplate" />
	<fmt:message bundle="${loc}" key="localization.fill_in" var="fill_in" />
	<fmt:message bundle="${loc}" key="localization.fill_field"
		var="fill_field" />

	<div class="wrapper">
		<div class="main"> <div class="section">
		<h2>${request_form}</h2>
		<br/>
		${fill_field}
		<form name="frm2" onsubmit="return validateRequest()" method="post"
			action="MainController">
			<input type="hidden" name="command" value="give_request">
			<div class="label">
				<label>${login} </label><span>*</span>
			</div>
			<div class="input">
				<input type="text" name="login" title="${login_tamplate}">
			</div>
			<div class="span">
				<div id="err-log">${fill_in}</div>
			</div>

			<div class="label">
				<label>${password} </label><span>*</span>
			</div>

			<div class="input">
				<input type="password" name="password" title="${password_tamplate}">
			</div>

			<div class="span">
				<div id="err-pass">${fill_in}</div>
			</div>

			<div class="label">
				<label>${request} </label><span>*</span>
			</div>
			<div class="input">
				<textarea autofocus name="text" cols="35" rows="7">
				</textarea>
			</div>
			<div class="span">
				<div id="err-text">${fill_in}</div>
			</div>
			<input type="submit" class="sub" value="${enter_request}">
		</form>
		</div>
	</div>
	</div>
</body>
</html>