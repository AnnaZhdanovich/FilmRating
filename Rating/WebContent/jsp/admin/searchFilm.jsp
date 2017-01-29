<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="https://fonts.googleapis.com/css?family=Neucha"
	rel="stylesheet">
<link rel="stylesheet" href="css/adminStyle.css">
</head>
<body>
	<fmt:setLocale value="${sessionScope.locale}" />
	<fmt:setBundle basename="resources.localization" var="loc" />
	<fmt:message bundle="${loc}" key="localization.find_the_film"
		var="find_the_film" />
	<fmt:message bundle="${loc}" key="localization.title" var="title" />
	<fmt:message bundle="${loc}" key="localization.country" var="country" />
	<fmt:message bundle="${loc}" key="localization.year" var="year" />
	<fmt:message bundle="${loc}" key="localization.actor" var="actor" />
	<fmt:message bundle="${loc}" key="localization.producer" var="producer" />
	<fmt:message bundle="${loc}" key="localization.genres" var="genres" />
	<fmt:message bundle="${loc}" key="localization.search" var="search" />
	<fmt:message bundle="${loc}" key="localization.usa" var="usa" />
	<fmt:message bundle="${loc}" key="localization.england" var="england" />
	<fmt:message bundle="${loc}" key="localization.belarus" var="belarus" />
	<fmt:message bundle="${loc}" key="localization.brazil" var="brazil" />
	<fmt:message bundle="${loc}" key="localization.germany" var="germany" />
	<fmt:message bundle="${loc}" key="localization.italy" var="italy" />
	<fmt:message bundle="${loc}" key="localization.kazakhstan"
		var="kazakhstan" />
	<fmt:message bundle="${loc}" key="localization.russia" var="russia" />
	<fmt:message bundle="${loc}" key="localization.ukraine" var="ukraine" />
	<fmt:message bundle="${loc}" key="localization.france" var="france" />
	<fmt:message bundle="${loc}" key="localization.triller" var="triller" />
	<fmt:message bundle="${loc}" key="localization.drama" var="drama" />
	<fmt:message bundle="${loc}" key="localization.melodrama"
		var="melodrama" />
	<fmt:message bundle="${loc}" key="localization.horor" var="horor" />
	<fmt:message bundle="${loc}" key="localization.action" var="action" />
	<fmt:message bundle="${loc}" key="localization.comedy" var="comedy" />
	<fmt:message bundle="${loc}" key="localization.family" var="family" />
	<fmt:message bundle="${loc}" key="localization.historical"
		var="historical" />
	<fmt:message bundle="${loc}" key="localization.detective"
		var="detective" />
	<fmt:message bundle="${loc}" key="localization.fantastic"
		var="fantastic" />
	<fmt:message bundle="${loc}" key="localization.fantasy" var="fantasy" />
	<fmt:message bundle="${loc}" key="localization.biography"
		var="biography" />
	<fmt:message bundle="${loc}" key="localization.military" var="military" />
	<fmt:message bundle="${loc}" key="localization.adventure"
		var="adventure" />
	<fmt:message bundle="${loc}" key="localization.sport" var="sport" />
	<fmt:message bundle="${loc}" key="localization.documentary"
		var="documentary" />
	<fmt:message bundle="${loc}" key="localization.crime" var="crime" />
	<div class="wrapper">
		<div class="main">
			<div class="section">
				<h3>${find_the_film}</h3>
				<form action="MainController" method="post"
					enctype="multipart/form-data">
					<fieldset>
						<input type="hidden" name="command" value="find_list_of_films">
						<input type="hidden" name="goal" value="listFilm">
						<div class="wrapp_add">
							<label>${title}</label>
							<div class="span">
								<input class="list" type="text" name="title">
							</div>
							<div class="wrapp_add">
								<label>${country}</label>
								<div class="span">
									<select  class="list" name="country"
										placeholder="${country}">
										<option value=""></option>
										<option value="5">${usa}</option>
										<option value="7">${england}</option>
										<option value="3">${belarus}</option>
										<option value="6">${brazil }</option>
										<option value="10">${germany}</option>
										<option value="8">${italy}</option>
										<option value="2">${kazakhstan}</option>
										<option value="1">${russia}</option>
										<option value="4">${ukraine}</option>
										<option value="9">${france}</option>
									</select>
								</div>
							</div>
							<div class="wrapp_add">
								<label>${year}</label>
								<div class="span">
									<input class="list" type="number" name="year" min="1900"
										max="2050" step="1">
								</div>
							</div>
							<div class="wrapp_add">
								<label>${actor}</label> <select class="list" name="actor">
									<option value=""></option>
									<c:forEach items="${sessionScope.actors}" var="actor">
										<option value="${actor.idPersonality}">${actor.firstName}
											${actor.lastName}</option>
									</c:forEach>
								</select>
							</div>
							<div class="wrapp_add">
								<label>${producer}</label> <select class="list" name="producer">
									<option value=""></option>
									<c:forEach items="${sessionScope.producers}" var="producer">
										<option value="${producer.idPersonality}">${producer.firstName}
											${producer.lastName}</option>
									</c:forEach>
								</select>
							</div>
							<div class="wrapp_add">
								<label>${genres}</label>
								<div class="span">
									<select class="list" name="genre">
										<option value=""></option>
										<option value="1">${triller}</option>
										<option value="2">${drama}</option>
										<option value="3">${melodrama}</option>
										<option value="4">${horor}</option>
										<option value="5">${action}</option>
										<option value="6">${comedy}</option>
										<option value="7">${family}</option>
										<option value="8">${historical}</option>
										<option value="9">${detective}</option>
										<option value="10">${fantastic}</option>
										<option value="11">${fantasy}</option>
										<option value="12">${biography}</option>
										<option value="13">${military}</option>
										<option value="14">${adventure}</option>
										<option value="15">${sport}</option>
										<option value="16">${documentary}</option>
										<option value="17">${crime}</option>
									</select>
								</div>
							</div>
							<div class="wrapp_add">
								<input type="submit" value="${search}">
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
</body>
</html>