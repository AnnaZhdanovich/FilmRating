<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tt" uri="timetag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/adminStyle.css">
<link href="https://fonts.googleapis.com/css?family=Neucha"
	rel="stylesheet">

</head>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="resources.localization" var="loc" />
<fmt:message bundle="${loc}" key="localization.admin" var="admin" />
<fmt:message bundle="${loc}" key="localization.exit" var="exit" />
<fmt:message bundle="${loc}" key="localization.enter" var="enter" />
<fmt:message bundle="${loc}" key="localization.login" var="login" />
<fmt:message bundle="${loc}" key="localization.password" var="password" />
<fmt:message bundle="${loc}" key="localization.hello" var="hello" />

<c:set var="message" value="${requestScope.errorData}" />
<c:if test="${message != null}">
	<fmt:message bundle="${loc}" key="localization.error_data"
		var="error_data" />
</c:if>
<c:set var="message" value="${sessionScope.errorData}" />
<c:if test="${message != null}">
	<fmt:message bundle="${loc}" key="localization.error_data"
		var="error_data" />
</c:if>
<c:set var="message" value="${requestScope.addMessage}" />
<c:if test="${message != null}">
	<fmt:message bundle="${loc}" key="localization.addMessage"
		var="addMessage" />
</c:if>
<c:set var="message" value="${sessionScope.addMessage}" />
<c:if test="${message != null}">
	<fmt:message bundle="${loc}" key="localization.addMessage"
		var="addMessage" />
</c:if>
<c:set var="message" value="${requestScope.error}" />
<c:if test="${message != null}">
	<fmt:message bundle="${loc}" key="localization.error" var="error" />
</c:if>
<c:set var="message" value="${sessionScope.error}" />
<c:if test="${message != null}">
	<fmt:message bundle="${loc}" key="localization.error" var="error" />
</c:if>
<c:set var="message" value="${requestScope.errorAdd}" />
<c:if test="${message != null}">
	<fmt:message bundle="${loc}" key="localization.errorAdd" var="errorAdd" />
</c:if>
<c:set var="message" value="${sessionScope.errorAdd}"/>
<c:if test="${message != null}">
	<fmt:message bundle="${loc}" key="localization.errorAdd" var="errorAdd" />
</c:if>
<c:set var="message" value="${requestScope.errorAuthorisationMessage}" />
<c:if test="${message != null}">
	<fmt:message bundle="${loc}"
		key="localization.errorAuthorisationMessage"
		var="errorAuthorisationMessage" />
</c:if>
<c:set var="message" value="${requestScope.errorSerch}" />
<c:if test="${message != null}">
	<fmt:message bundle="${loc}" key="localization.errorSerch"
		var="errorSerch" />
</c:if>

<c:set var="message" value="${requestScope.blokingMessage}" />
<c:if test="${message != null}">
	<fmt:message bundle="${loc}" key="localization.blokingMessage"
		var="blokingMessage" />
</c:if>
<c:set var="message" value="${requestScope.unblockingMessage}" />
<c:if test="${message != null}">
	<fmt:message bundle="${loc}" key="localization.unblockingMessage"
		var="unblockingMessage" />
</c:if>
<c:set var="message" value="${sessionScope.errorAuthorisationMessage}" />
<c:if test="${message != null}">
	<fmt:message bundle="${loc}"
		key="localization.errorAuthorisationMessage"
		var="errorAuthorisationMessage" />
</c:if>
<c:set var="message" value="${sessionScope.errorSerch}" />
<c:if test="${message != null}">
	<fmt:message bundle="${loc}" key="localization.errorSerch"
		var="errorSerch" />
</c:if>

<c:set var="message" value="${sessionScope.blokingMessage}" />
<c:if test="${message != null}">
	<fmt:message bundle="${loc}" key="localization.blokingMessage"
		var="blokingMessage" />
</c:if>
<c:set var="message" value="${sessionScope.unblockingMessage}" />
<c:if test="${message != null}">
	<fmt:message bundle="${loc}" key="localization.unblockingMessage"
		var="unblockingMessage" />
</c:if>
<c:set var="message" value="${requestScope.messageUpdateUser}" />
<c:if test="${message != null}">
	<fmt:message bundle="${loc}" key="localization.messageUpdateUuser"
		var="messageUpdateUuser" />
</c:if>
<c:set var="message" value="${sessionScope.messageUpdateUser}" />
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
<div class="wrapper">
	<div class="header">
		<div class="wrap_p">
			<div class="locale">
				<div class="lock">
					<form action="MainController" method="post">
						<input type="hidden" name="command" value="localization">
						<input type="hidden" name="locale" value="ru"> <input
							class="image" type="image" name="picture" src="images/rus.jpg">
					</form>
				</div>
				<div class="lock">
					<form action="MainController" method="post">
						<input type="hidden" name="command" value="localization">
						<input type="hidden" name="locale" value="en"> <input
							class="image" type="image" name="picture" src="images/uk.jpg">
					</form>
				</div>
				<div class="lock">
					<a href="index.jsp"><img class="image"
						src="images/1396636497-9b3139.gif" /> </a>
				</div>
				<tt:info-time />
			</div>

			<div class="info">${addMessage}${errorAdd }${error_data}
				${error} ${errorAuthorisationMessage} ${errorSerch}
				${blokingMessage} ${unblockingMessage} ${messageUpdateUuser }
				${errorUpdateMessag }</div>
			<c:set var="message" value="${sessionScope.id}" />
			<c:if test="${message!=null}">
				<div class="wrap_data_admin">
					<div class="logout">
						<form method="post" action="MainController">
							<input type="hidden" name="command" value="logout" /> <input
								name="go" class="but_search" type="submit" value="${exit}" />
						</form>

						<div class="ava">
							<img class="avatar" src="${sessionScope.image}" />
						</div>

						<div class="ad_dat">
							<p>${hello}
								<br> ${sessionScope.name}
							</p>
						</div>
					</div>
				</div>
			</c:if>
		</div>
	</div>
</div>
</body>
</html>