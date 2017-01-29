<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="https://fonts.googleapis.com/css?family=Neucha"
	rel="stylesheet">
<link rel="stylesheet" href="css/adminStyle.css">
</head>
<body>
	<fmt:setLocale value="${sessionScope.locale}" />
	<fmt:setBundle basename="resources.localization" var="loc" />
	<fmt:message bundle="${loc}" key="localization.add_film" var="add_film" />
	<fmt:message bundle="${loc}" key="localization.title" var="title" />
	<fmt:message bundle="${loc}" key="localization.country" var="country" />
	<fmt:message bundle="${loc}" key="localization.year" var="year" />
	<fmt:message bundle="${loc}" key="localization.description"
		var="description" />
	<fmt:message bundle="${loc}" key="localization.actor" var="actor" />
	<fmt:message bundle="${loc}" key="localization.producer" var="producer" />
	<fmt:message bundle="${loc}" key="localization.genres" var="genres" />
	<fmt:message bundle="${loc}" key="localization.poster" var="poster" />
	<fmt:message bundle="${loc}" key="localization.add_actor_producer"
		var="add_actor_producer" />
	<fmt:message bundle="${loc}" key="localization.firstname"
		var="firstname" />
	<fmt:message bundle="${loc}" key="localization.lastname" var="lastname" />
	<fmt:message bundle="${loc}" key="localization.title_tamplate"
		var="title_tamplate" />
	<fmt:message bundle="${loc}" key="localization.name_tamplate"
		var="name_tamplate" />
	<fmt:message bundle="${loc}" key="localization.actor_ad" var="actor_ad" />
	<fmt:message bundle="${loc}" key="localization.producer_ad"
		var="producer_ad" />
	<fmt:message bundle="${loc}" key="localization.add" var="add" />
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
				<h3>${add_film}</h3>
				<form action="MainController" method="post"
					enctype="multipart/form-data">
					<fieldset>
						<input type="hidden" name="command" value="add_film">
						<div class="wrapp_add">
							<label>${title}</label> <span>*</span>
							<div class="span">
								<input type="text" name="title" required
									pattern="[A-ZА-Яa-zа-яЁё0-9-,.!?\s]+" title="${title_tamplate}">
							</div>
						</div>
						<div class="wrapp_add">
							<label>${country}</label> <span>*</span>
							<div class="span">
								<select class="list" required name="country">
									<option></option>
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
							<label>${year}</label> <span>*</span>
							<div class="span">
								<input required type="number" name="year" min="1900" max="2050"
									step="1">
							</div>
						</div>
						<div class="wrapp_add">
							<label>${description}</label> <span>*</span>
							<div class="span">
								<textarea required name="description" cols="50" rows="4"> </textarea>
							</div>
						</div>
						<div class="wrapp_add">
							<label>${actor}</label> <span>*</span>
							<div class="span">
								<select class="list1" required name="actor[]"
									multiple="multiple" size="5">
									<c:forEach items="${sessionScope.actors}" var="actor">
										<option value="${actor.idPersonality}">${actor.firstName}
											${actor.lastName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="wrapp_add">
							<label>${producer}</label> <span>*</span>
							<div class="span">
								<select class="list1" required name="producer[]"
									multiple="multiple" size="5">
									<c:forEach items="${sessionScope.producers}" var="producer">
										<option value="${producer.idPersonality}">${producer.firstName}
											${producer.lastName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="wrapp_add">
							<label>${genres}</label> <span>*</span>
							<div class="span">
								<Select class="list1" required name="genre[]" placeholder="жанр"
									multiple="multiple">
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
							<label>${poster }</label>
							<div class="span">
								<input type="file" class="n" name="file" id="file" />
							</div>
							<div class="wrapp_add">
								<input type="submit" value="${add}">
							</div>
						</div>
					</fieldset>
				</form>
			</div>
			<hr>
			<div class="section">
				<h3>${add_actor_producer}</h3>
				<form action="MainController" method="post">
					<fieldset>
						<input type="hidden" name="command" value="add_actor">
						<div class="wrapp_add">
							<label>${firstname}</label> <span>*</span>
							<div class="span">
								<input type="text" name="firstname" required
									pattern="^[A-ZА-ЯЁ][A-ZА-Яa-zа-яЁё-\s]+"
									title="${name_tamplate}">
							</div>
						</div>
						<div class="wrapp_add">
							<label>${lastname}</label> <span>*</span>
							<div class="span">
								<input type="text" name="lastname" required
									pattern="^[A-ZА-ЯЁ][A-ZА-Яa-zа-яЁё-\s]+"
									title="${name_tamplate}">
							</div>
						</div>
						<div class="wrapp_add">
							<label>${actor_ad}</label>
							<div class="span">
								<input name="role" type="radio" checked="checked" value="actor">
							</div>
							<label>${producer_ad}</label>
							<div class="span">
								<input name="role" type="radio" value="producer">
							</div>
						</div>
						<div class="wrapp_add">
							<input type="submit" value="${add}">
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
</body>
</html>