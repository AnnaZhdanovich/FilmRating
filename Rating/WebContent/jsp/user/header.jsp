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
	<fmt:message bundle="${loc}" key="localization.fill_in" var="fill_in" />
	<fmt:message bundle="${loc}" key="localization.greet" var="greet" />
	<fmt:message bundle="${loc}" key="localization.text" var="text" />
	<fmt:message bundle="${loc}" key="localization.extended" var="extended" />
	<fmt:message bundle="${loc}" key="localization.password1"
		var="password1" />
	<fmt:message bundle="${loc}" key="localization.login1" var="login1" />
	<fmt:message bundle="${loc}" key="localization.search" var="search" />
	<fmt:message bundle="${loc}" key="localization.exit" var="exit" />
	<fmt:message bundle="${loc}" key="localization.hello" var="hello" />
	<fmt:message bundle="${loc}" key="localization.private_data"
		var="private_data" />
	<fmt:message bundle="${loc}" key="localization.registration"
		var="registration" />
	<fmt:message bundle="${loc}" key="localization.enter" var="enter" />
	<fmt:message bundle="${loc}" key="localization.give_request"
		var="give_request" />
	<c:set var="message" value="${sessionScope.errorStatusMessage}" />
	<c:if test="${message != null}">
		<fmt:message bundle="${loc}" key="localization.error_status_message"
			var="user_block" />
	</c:if>
	<c:set var="message" value="${sessionScope.errorLoginPassMessage}" />
	<c:if test="${message != null}">
		<fmt:message bundle="${loc}"
			key="localization.error_login_pass_message" var="user_not_found" />
	</c:if>
	<c:set var="message" value="${sessionScope.errorData}" />
	<c:if test="${message != null}">
		<fmt:message bundle="${loc}" key="localization.error_data"
			var="error_data" />
	</c:if>

	<c:set var="message" value="${requestScope.errorData}" />
	<c:if test="${message != null}">
		<fmt:message bundle="${loc}" key="localization.error_data"
			var="error_data" />
	</c:if>

	<c:set var="message" value="${sessionScope.errorInputData}" />
	<c:if test="${message != null}">
		<fmt:message bundle="${loc}" key="localization.error_input_data"
			var="error_input_data" />
	</c:if>
	<c:set var="message" value="${sessionScope.errorFreeLogin}" />
	<c:if test="${message != null}">
		<fmt:message bundle="${loc}" key="localization.errorFreeLogin"
			var="errorFreeLogin" />
	</c:if>
	<c:set var="message" value="${sessionScope.errorRegistrationMessage}" />
	<c:if test="${message != null}">
		<fmt:message bundle="${loc}"
			key="localization.errorRegistrationMessage"
			var="errorRegistrationMessage" />
	</c:if>
	<c:set var="message" value="${sessionScope.errorAuthorisationMessage}" />
	<c:if test="${message != null}">
		<fmt:message bundle="${loc}"
			key="localization.errorAuthorisationMessage"
			var="errorAuthorisationMessage" />
	</c:if>
	<c:set var="message" value="${requestScope.errorAuthorisationMessage}" />
	<c:if test="${message != null}">
		<fmt:message bundle="${loc}"
			key="localization.errorAuthorisationMessage"
			var="errorAuthorisationMessage" />
	</c:if>
	<c:set var="message" value="${requestScope.messageUpdateUser}" />
	<c:if test="${message != null}">
		<fmt:message bundle="${loc}" key="localization.messageUpdateUuser"
			var="messageUpdateUuser" />
	</c:if>

	<c:set var="message" value="${requestScope.errorUpdateMessage}" />
	<c:if test="${message != null}">
		<fmt:message bundle="${loc}" key="localization.errorUpdateMessage"
			var="errorUpdateMessage" />
	</c:if>
	<c:set var="message" value="${sessionScope.errorUpdateMessage}" />
	<c:if test="${message != null}">
		<fmt:message bundle="${loc}" key="localization.errorUpdateMessage"
			var="errorUpdateMessage" />
	</c:if>
	<c:set var="message" value="${sessionScope.error}" />
	<c:if test="${message != null}">
		<fmt:message bundle="${loc}" key="localization.error" var="error" />
	</c:if>
	<c:set var="message" value="${requestScope.errorSerch}" />
	<c:if test="${message != null}">
		<fmt:message bundle="${loc}" key="localization.errorSerch"
			var="errorSerch" />
	</c:if>
	<c:set var="message" value="${sessionScope.errorSerch}" />
	<c:if test="${message != null}">
		<fmt:message bundle="${loc}" key="localization.errorSerch"
			var="errorSerch" />
	</c:if>
	<c:set var="message" value="${sessionScope.addRequest}" />
	<c:if test="${message != null}">
		<fmt:message bundle="${loc}" key="localization.add_request"
			var="addRequest" />
	</c:if>
	<c:set var="message" value="${sessionScope.errorGiveAssessment}" />
	<c:if test="${message != null}">
		<fmt:message bundle="${loc}" key="localization.error_give_assessment"
			var="error_give_assessment" />

	</c:if>
	<c:set var="message" value="${sessionScope.errorGiveComment}" />
	<c:if test="${message != null}">
		<fmt:message bundle="${loc}" key="localization.error_give_comment"
			var="error_give_comment" />

	</c:if>
	<c:set var="message" value="${sessionScope.addMessage}" />
	<c:if test="${message != null}">
		<fmt:message bundle="${loc}" key="localization.addMessage"
			var="addMessage" />
	</c:if>

	<div class="wrapper">
		<div class="header">
			<div class="head_first">
				<div class="logotip">
					<a href="index.jsp"><img src="images/CinemaLigotip.jpg"></a>
				</div>
				<div class="locale">
					<div class="lock">
						<form action="MainController" method="post">
							<input type="hidden" name="command" value="localization">
							<input type="hidden" name="locale" value="ru"> <input
								type="image" name="picture" src="images/rus.jpg">
						</form>
					</div>
					<div class="lock">
						<form action="MainController" method="post">
							<input type="hidden" name="command" value="localization">
							<input type="hidden" name="locale" value="en"> <input
								type="image" name="picture" src="images/uk.jpg">
						</form>
					</div>
					<div class="lock">
						<a href="index.jsp"><img class="image1"
							src="images/1396636497-9b3139.gif" /> </a>
					</div>
				</div>


				<c:set var="user" value="${sessionScope.id}" />
				<c:if test="${user eq null}">
					<div class="wrap_login">
						<form name="frmLog" onsubmit="return validateLogin();" method="post"
							action="MainController">

							<input type="hidden" name="command" value="authorisation" /> <input
								required name="log" class="login" type="text" placeholder="${login1}" />

							<input required name="pass" class="password" type="password"
								placeholder=" ${password1}" /> <input name="go"
								class="sub_search" type="submit" value="${enter}" />
						</form>

						<div class="reg">
							<form class="reg1" method="post" action="MainController">
								<input type="hidden" name="command"
									value="send_registration_page"> <a href=" "
									onClick="this.parentNode.submit(); return false;">${registration}</a>
							</form>
							<form class="reg1" method="post" action="MainController">
								<input type="hidden" name="command" value="send_request_page">
								<a href=" " onClick="this.parentNode.submit(); return false;">${give_request}</a>
							</form>

						</div>
					</div>
				</c:if>
				<c:set var="user" value="${sessionScope.id}" />
				<c:if test="${user ne null}">
					<div class="wrap_login">
						<form class="form_user" method="post" action="MainController">
							<input type="hidden" name="command" value="find_user" /> <input
								type="hidden" name="idUser" value="${sessionScope.id}" /> <input
								class="sub_search" type="submit" value="${private_data}" />
						</form>
						<form class="form_user" method="post" action="MainController">
							<input type="hidden" name="command" value="logout" /> <input
								name="go" class="sub_search" type="submit" value="${exit}" />
						</form>

						<form class="reg2" method="post" action="MainController">
							<input type="hidden" name="command" value="send_request_page">
							<a href=" " onClick="this.parentNode.submit(); return false;">${give_request}</a>
						</form>

					</div>
				</c:if>
				<div class="wrap_search">
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="fast_search"> <input
							type="text" class="search1" placeholder="${text}" name="text" />
						<input type="submit" class="sub_search" value="${search}" />
					</form>
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="get_personality">
						<input type="hidden" name="goal" value="serchFilm"> <a
							href=" " onClick="this.parentNode.submit(); return false;">${extended}</a>
					</form>
				</div>
			</div>
			<div class="info">
				<c:if test="${user ne null}">				
			${greet} ${sessionScope.name}
			</c:if>
				<hr>
				${user_block}
				${user_not_found} ${error_data} ${errorFreeLogin}
				${errorRegistrationMessage} ${errorAuthorisationMessage} ${error}
				${error_give_assessment} ${errorSerch} ${messageUpdateUuser}
				${errorUpdateMessage}${error_input_data} ${addRequest}
				${addMessage}${error_give_comment}
				
			</div>
		</div>
	</div>
</body>
</html>