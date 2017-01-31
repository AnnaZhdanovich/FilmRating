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
<script src="js/registration.js"></script>
<script src="js/remove.js"></script>
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
	<fmt:message bundle="${loc}" key="localization.email_tamplate"
		var="email_tamplate" />
	<fmt:message bundle="${loc}" key="localization.name_tamplate"
		var="name_tamplate" />
	<fmt:message bundle="${loc}" key="localization.login_tamplate"
		var="login_tamplate" />
	<fmt:message bundle="${loc}" key="localization.password_tamplate"
		var="password_tamplate" />
	<fmt:message bundle="${loc}" key="localization.wrong_data"
		var="wrong_data" />
	<fmt:message bundle="${loc}" key="localization.password_donot_match"
		var="password_donot_match" />
	<fmt:message bundle="${loc}" key="localization.delete_user"
		var="delete_user" />
	<fmt:message bundle="${loc}" key="localization.fill_field"
		var="fill_field" />
	<div class="wrapper">

		<div class="main">
			<div class="section">
				<h2>${change_form}</h2>
				<hr>
				${fill_field} <br />
				<form name="frm1" onsubmit="return validate()" method="post"
					action="MainController">
					<input type="hidden" name="command" value="update_user">
					<div class="label">
						<label>${name} </label><span>*</span>
					</div>
					<div class="input">
						<input type="text" name="firstname"
							value="${requestScope.user.firstName}" title="${name_tamplate}">
					</div>
					<div class="span">
						<div id="err-fname">${wrong_data}</div>

					</div>

					<div class="label">
						<label>${login} </label><span>*</span>
					</div>
					<div class="input">
						<input type="text" name="login" title="${name_login}"
							value="${requestScope.user.login}">
					</div>
					<div class="span">
						<div id="err-login">${wrong_data}</div>
					</div>

					<div class="label">
						<label>${email} </label><span>*</span>
					</div>
					<div class="input">
						<input type="text" name="email" title="${email_tamplate }"
							value="${requestScope.user.email}">
					</div>
					<div class="span">
						<div id="err-email">${wrong_data}</div>

					</div>
					<div class="label">
						<label>${password} </label><span>*</span>
					</div>

					<div class="input">
						<input type="password" name="pwd1" title="${password_tamplate }">
					</div>

					<div class="span">
						<div id="err-pwd1">${wrong_data}</div>
					</div>

					<div class="label">
						<label>${repit} </label><span>*</span>
					</div>
					<div class="input">
						<input type="password" name="pwd2" title="${password_tamplate }">
					</div>
					<div class="span">
						<div id="err-pwd2">${password_donot_match}</div>

					</div>
					<input type="submit" class="sub" value="${change_go}">
				</form>
				<hr>
				<form method="post" action="MainController"
					enctype="multipart/form-data">
					<input type="hidden" name="command" value="load_file"> <input
						type="hidden" name="type" value="user">
					<div class="label">
						<label>${avatar}</label>
					</div>
					<div class="input">
						<input type="file" required class="n" name="file" id="file" />
					</div>
					<input type="submit" class="sub" value="${change_go}">
				</form>
				<hr>
				<form onsubmit="return del()" method="post" action="MainController">
					<input type="hidden" name="command" value="delete_user"> <input
						type="submit" class="sub" value="${delete_user}">
				</form>
			</div>
		</div>
	</div>
</body>
</html>