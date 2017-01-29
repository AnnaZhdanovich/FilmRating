<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/generalStyle.css" type="text/css" />
<link href="https://fonts.googleapis.com/css?family=Neucha"
	rel="stylesheet">
</head>
<body>
	<div class="wrapper">
		<div class="carousel-wrapper">
			<div class='sliderA'>
				<c:forEach var="element" items="${sessionScope.newFilmList}"
					varStatus="stat">
					<div class="carousel_img">
						<form method="post" action="MainController">
							<input type="hidden" name=command value="find_film_by_id">
							<input type="hidden" name="id" value="${element.idFilm}"> <a
								href=" " onClick="this.parentNode.submit(); return false;"><img
								src="${element.poster}" title="${element.title}" /></a>
						</form>
						&#9734; ${element.rating}
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>