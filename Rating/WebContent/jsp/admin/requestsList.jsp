<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/adminStyle.css">
<link rel="stylesheet" href="css/requestStyle.css">
<link href="https://fonts.googleapis.com/css?family=Neucha"
	rel="stylesheet">
</head>

<body>
	<fmt:setLocale value="${sessionScope.locale}" />
	<fmt:setBundle basename="resources.localization" var="loc" />
	<fmt:message bundle="${loc}" key="localization.previous" var="previous" />
	<fmt:message bundle="${loc}" key="localization.next" var="next" />
	<fmt:message bundle="${loc}" key="localization.assept" var="assept" />
	<fmt:message bundle="${loc}" key="localization.rejected" var="rejected" />
	<fmt:message bundle="${loc}" key="localization.user" var="user" />
	<fmt:message bundle="${loc}" key="localization.status_message"
		var="status_message" />
	<fmt:message bundle="${loc}" key="localization.date_message"
		var="date_message" />
	<div class="wrapper">
		<div class="main">
			<c:forEach items="${requestScope.requests}" var="request">
				<div class="wrap_comment">
					<div class="wrap_n">

						<div class="wrapp_first">
							<div class="name_person">${user} ${request.user.login}</div>
							<div class="name_person">${status_message}
								${request.status}</div>
						</div>

						<div class="wrapp_second">
							<div class="data_comment">${date_message} ${request.date}</div>
						</div>
					</div>

					<div class="comment_full">
						<p>${request.text}</p>
					</div>
					<div class="wrap_butt">
						<form method="post" action="MainController">
							<input type="hidden" name="command" value="marker_of_request">
							<input type="hidden" name=type value="allowed"> <input
								type="hidden" name="idRequest" value="${request.idRequest}">
							<a href=" " onClick="this.parentNode.submit(); return false;">${assept}</a>
						</form>
					</div>
					<div class="wrap_butt">
						<form method="post" action="MainController">
							<input type="hidden" name="command" value="marker_of_request">
							<input type="hidden" name="type" value="rejected"> <input
								type="hidden" name="idRequest" value="${request.idRequest}">
							<a href=" " onClick="this.parentNode.submit(); return false;">${rejected}</a>
						</form>
					</div>
				</div>

			</c:forEach>


			<c:if test="${not empty requestScope.requests }">
				<c:if test="${sessionScope.currentPage != 1}">
					<td><a
						href="/Rating/MainController?command=find_user_request&page=${sessionScope.currentPage-1}">${previous}</a></td>
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
										href="/Rating/MainController?command=find_user_request&page=${i}">${i}</a></td>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</tr>
				</table>

				<c:if test="${sessionScope.currentPage lt sessionScope.noOfPages}">
					<td><a
						href="/Rating/MainController?command=find_user_request&page=${sessionScope.currentPage + 1}">${next}</a></td>
				</c:if>
			</c:if>
		</div>

	</div>

</body>