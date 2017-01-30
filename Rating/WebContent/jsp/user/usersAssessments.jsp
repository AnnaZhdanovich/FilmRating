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
	<fmt:message bundle="${loc}" key="localization.previous" var="previous" />
	<fmt:message bundle="${loc}" key="localization.next" var="next" />
	<div class="wrapper">
		<div class="main">
		<table>
			<c:forEach items="${requestScope.assessments}" var="assessment">
				<tr>
					<td><img
						src="${assessment.film.poster}" /></td>
					<td class="a1"><form method="post" action="MainController">
							<input type="hidden" name="command" value="find_film_by_id">
							<input type="hidden" name="id" value="${assessment.film.idFilm}">
							<a href=" " onClick="this.parentNode.submit(); return false;">${assessment.film.title}</a>
						</form></td>
					<td class="a1">${assessment.value}</td>
					<td class="a2" ><fmt:formatDate type="date" value="${assessment.date}" /></td>
				</tr>
			</c:forEach>			
		</table>

		<c:if test="${not empty requestScope.assessments }">
			<c:if test="${sessionScope.currentPage != 1}">
				<td><a class="link"
					href="/Rating/MainController?command=take_assessments&idUser=${sessionScope.idUser}&page=${sessionScope.currentPage-1}">${previous}</a></td>
			</c:if>
			<table border="1" cellpadding="5" cellspacing="5">
				<tr>
					<c:forEach begin="1" end="${sessionScope.noOfPages}" var="i">
						<c:choose>
							<c:when test="${sessionScope.currentPage eq i}">
								<td>${i}</td>
							</c:when>
							<c:otherwise>
								<td><a class="link"
									href="/Rating/MainController?command=take_assessments&idUser=${sessionScope.idUser}&page=${i}">${i}</a></td>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</tr>
			</table>


			<c:if test="${sessionScope.currentPage lt sessionScope.noOfPages}">
				<td><a class="link"
					href="/Rating/MainController?command=take_assessments&idUser=${sessionScope.idUser}&page=${sessionScope.currentPage + 1}">${next}</a></td>
			</c:if>
		</c:if>
		 </div>
	</div>
</body>

</html>