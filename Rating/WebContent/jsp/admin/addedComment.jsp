<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/adminStyle.css">
<link rel="stylesheet" href="css/commentStyle.css">
<link href="https://fonts.googleapis.com/css?family=Neucha"
	rel="stylesheet">
</head>
<body>
	<fmt:setLocale value="${sessionScope.locale}" />
	<fmt:setBundle basename="resources.localization" var="loc" />
	<fmt:message bundle="${loc}" key="localization.previous" var="previous" />
	<fmt:message bundle="${loc}" key="localization.add" var="add" />
	<fmt:message bundle="${loc}" key="localization.next" var="next" />
	<fmt:message bundle="${loc}" key="localization.unblocking"
		var="unblocking" />
	<fmt:message bundle="${loc}" key="localization.rejected" var="rejected" />
	<fmt:message bundle="${loc}" key="localization.data_add1"
		var="data_add" />
	<fmt:message bundle="${loc}" key="localization.title1" var="title" />
	<fmt:message bundle="${loc}" key="localization.firstname1"
		var="firstname" />
	<div class="wrapper">
		<div class="main">
			<c:forEach items="${requestScope.comment}" var="comment">
				<div class="wrap_comment">
					<div class="name_film">
						${title} <span class="type1"> ${comment.film.title}</span>
					</div>
					<div class="name_person">
						${firstname } <span class="type1">${comment.user.firstName}</span>
					</div>
					<div class="data_comment">
						${data_add} <span class="type1">${comment.date}</span>
					</div>
				</div>
				<div class="comment_full">
					<p>${comment.text}</p>
				</div>
				<div class="wrap_butt">
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="unblock_comment">
						<input type="hidden" name="type" value="allowed"> <input
							type="hidden" name="idComment" value="${comment.idComment}">
						<a href=" " onClick="this.parentNode.submit(); return false;">${add}</a>
					</form>
				</div>
				<div class="wrap_butt">
					<form method="post" action="MainController">
						<input type="hidden" name="command" value="unblock_comment">
						<input type="hidden" name="type" value="rejected"> <input
							type="hidden" name="idComment" value="${comment.idComment}">
						<a href=" " onClick="this.parentNode.submit(); return false;">${rejected}</a>
					</form>
				</div>
			</c:forEach>

			<c:if test="${not empty requestScope.comment}">
				<c:if test="${requestScope.currentPage != 1}">
					<td><a
						href="/Rating/MainController?command=find_added_comment&page=${sessionScope.currentPage-1}">${previous}</a></td>
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
										href="/Rating/MainController?command=find_added_comment&page=${i}">${i}</a></td>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</tr>
				</table>
				<c:if test="${sessionScopen.currentPage lt noOfPages}">
					<td><a
						href="/Rating/MainController?command=find_added_comment&page=${sessionScope.currentPage + 1}">${next}</a></td>
				</c:if>
			</c:if>
		</div>
	</div>
</body>