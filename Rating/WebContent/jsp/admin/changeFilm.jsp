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
<head>
</head>
<body>
	<fmt:setLocale value="${sessionScope.locale}" />
	<fmt:setBundle basename="resources.localization" var="loc" />
	<fmt:message bundle="${loc}" key="localization.change_film"
		var="change_film" />
	<fmt:message bundle="${loc}" key="localization.title" var="title" />
	<fmt:message bundle="${loc}" key="localization.country" var="country" />
	<fmt:message bundle="${loc}" key="localization.year" var="year" />
	<fmt:message bundle="${loc}" key="localization.description"
		var="description" />
	<fmt:message bundle="${loc}" key="localization.actor" var="actor" />
	<fmt:message bundle="${loc}" key="localization.producer" var="producer" />
	<fmt:message bundle="${loc}" key="localization.genres" var="genres" />
	<fmt:message bundle="${loc}" key="localization.poster" var="poster" />
	<fmt:message bundle="${loc}" key="localization.change_go"
		var="change_go" />
	<fmt:message bundle="${loc}" key="localization.delete" var="delete" />
	<fmt:message bundle="${loc}" key="localization.add" var="add" />
	<fmt:message bundle="${loc}" key="localization.title_tamplate"
		var="title_tamplate" />
	<fmt:message bundle="${loc}" key="localization.name_tamplate"
		var="name_tamplate" />
	<fmt:message bundle="${loc}" key="localization.fill_field"
		var="fill_field" />
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
				<h3>${change_film}</h3>
				<br /> ${fill_field}
				<form action="MainController" method="post">
					<fieldset>
						<input type="hidden" name="command" value="update_film_data">
						<input type="hidden" name="filmUid"
							value="${requestScope.film.idFilm}">
						<div class="wrapp_add">
							<label>${title}</label> <span>*</span>
							<div class="span">
								<input class="list" type="text" name="title" required
									pattern="[A-ZА-Яa-zа-яЁё0-9-\s]+$" title="${title_tamplate}"
									value="${requestScope.film.title}">
							</div>
						</div>

						<div class="wrapp_add">
							<label>${country}</label><span>*</span>
							<div class="span">
								<Select class="list" required name="country">
									<option selected value="${requestScope.film.country.idCountry}">${requestScope.film.country.name}</option>
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
								<input class="list" type="number" name="year" min="1900"
									max="2050" step="1" required value="${requestScope.film.year}">
							</div>
							<input type="submit" value="${change_go}">
						</div>
					</fieldset>
				</form>

				<form action="MainController" method="post">
					<input type="hidden" name="command" value="update_film_description">
					<input type="hidden" name="filmUid"
						value="${requestScope.film.idFilm}">
					<fieldset>
						<div class="wrapp_add">
							<label>${description}</label><span>*</span>
							<div class="span">
								<textarea  required name="description" cols="50"
									rows="4">${requestScope.film.description}</textarea>
								<input type="submit" value="${change_go}">
							</div>
						</div>
					</fieldset>
				</form>

				<form action="MainController" method="post"
					enctype="multipart/form-data">
					<input type="hidden" name="type" value="film"> <input
						type="hidden" name="command" value="load_file"> <input
						type="hidden" name="filmUid" value="${requestScope.film.idFilm}">
					<fieldset>
						<div class="wrapp_add">
							<label>${poster}</label>
							<div class="span">
								<input class="list" type="file" required class="n" name="file"
									id="file" /> <input type="submit" value="${change_go}">
							</div>
						</div>

					</fieldset>
				</form>
			</div>
			<div class="section">
				<div class="wrapp_add">
					<label>${actor}</label>
					<fieldset>
						<div>
							<c:forEach items="${requestScope.film.listPersonality}"
								var="actor">
								<c:if test="${actor.role=='ACTOR'}">
									<c:out value="${actor.firstName} ${actor.lastName}" />
									<form method="post" action="MainController">
										<input type="hidden" name=command
											value="update_film_personality"> <input type="hidden"
											name="actor" value="${actor.idPersonality}"> <input
											type="hidden" name="filmUid"
											value="${requestScope.film.idFilm}"> <input
											type="hidden" name="act" value="delete"> <a
											href="MainController"
											onClick="this.parentNode.submit(); return false;">${delete}</a>
									</form>
								</c:if>
							</c:forEach>
						</div>
						<div>
							<form action="MainController" method="post">
								<input type="hidden" name=command
									value="update_film_personality"> <input type="hidden"
									name="filmUid" value="${requestScope.film.idFilm}"> <input
									type="hidden" name="act" value="add"> <select
									class="list" name="actor">
									<c:forEach items="${requestScope.actors}" var="actor">
										<option value="${actor.idPersonality}">${actor.firstName}
											${actor.lastName}</option>
									</c:forEach>
								</select> <input type="submit" value="${add}">
							</form>
						</div>
					</fieldset>
				</div>
				<div class="section">
					<div class="wrapp_add">
						<label>${producer}</label>
						<fieldset>
							<div>
								<c:forEach items="${requestScope.film.listPersonality}"
									var="actor">
									<c:if test="${actor.role=='PRODUCER'}">
										<c:out value="${actor.firstName} ${actor.lastName}" />
										<form method="post" action="MainController">
											<input type="hidden" name=command
												value="update_film_personality"> <input
												type="hidden" name="actor" value="${actor.idPersonality}">
											<input type="hidden" name="filmUid"
												value="${requestScope.film.idFilm}"> <input
												type="hidden" name="act" value="delete"> <a
												href="MainController"
												onClick="this.parentNode.submit(); return false;">${delete}</a>
										</form>
									</c:if>
								</c:forEach>
							</div>
							<div>
								<form action="MainController" method="post">
									<input type="hidden" name=command
										value="update_film_personality"> <input type="hidden"
										name="filmUid" value="${requestScope.film.idFilm}"> <input
										type="hidden" name="act" value="add"> <select
										class="list" name="actor">
										<c:forEach items="${requestScope.producers}" var="producer">
											<option value="${producer.idPersonality}">${producer.firstName}
												${producer.lastName}</option>
										</c:forEach>
									</select> <input type="submit" value="${add}">
								</form>
							</div>
						</fieldset>
					</div>
				</div>
				<div class="wrapp_add">
					<label>${genres}</label>
					<fieldset>
						<div>
							<c:forEach items="${requestScope.film.listGenre}" var="genre">
								<c:out value="${genre.name}" />
								<form method="post" action="MainController">
									<input type="hidden" name="filmUid"
										value="${requestScope.film.idFilm}"> <input
										type="hidden" name=command value="update_genre"> <input
										type="hidden" name="genre" value="${genre.idGenre}"> <input
										type="hidden" name="act" value="delete"><a
										href="MainController"
										onClick="this.parentNode.submit(); return false;">${delete}</a>
								</form>
							</c:forEach>
						</div>
						<div>
							<form action="MainController" method="post">
								<input type="hidden" name="filmUid"
									value="${requestScope.film.idFilm}"> <input
									type="hidden" name=command value="update_genre"> <input
									type="hidden" name="act" value="add">
								<div class="span">
									<Select class="list" required name="genre" placeholder="жанр">
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
								<input type="submit" value="${add}">
							</form>
						</div>
					</fieldset>
				</div>
			</div>
		</div>
	</div>
</body>
</html>