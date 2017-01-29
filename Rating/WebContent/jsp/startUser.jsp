<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Film rating</title>
</head>
<body>
	<c:set var="role" value="${sessionScope.role}" />
	<c:if test="${role eq null}">
		<c:import url="/jsp/user/header.jsp" />
		<c:import url="/jsp/user/popularFilm.jsp" />
		<c:import url="/jsp/user/nav.jsp" />
		<c:set var="page" value="${sessionScope.target}" />

		<c:if test="${page eq null}">
			<c:import url="/MainController?command=find_film_main"></c:import>
			<c:import url="/jsp/user/main.jsp" />
		</c:if>
		<c:if test="${page ne null}">
			<c:choose>
				<c:when test="${page eq 'giveRequest'}">
					<c:import url="/jsp/user/giveRequest.jsp" />
				</c:when>
				<c:when test="${page eq 'ratingFilm'}">
					<c:import url="/jsp/user/filmsRating.jsp" />
				</c:when>
				<c:when test="${page eq 'searchFilmFasty'}">
					<c:import url="/jsp/user/searchFilmFasty.jsp" />
				</c:when>
				<c:when test="${page eq 'filmByGenre'}">
					<c:import url="/jsp/user/filmByGenre.jsp" />
				</c:when>
				<c:when test="${page eq 'serchFilm'}">
					<c:import url="/jsp/user/searchFilm.jsp" />
				</c:when>
				<c:when test="${page eq 'infoFilm'}">
					<c:import url="/jsp/user/filmsInformation.jsp" />
				</c:when>
				<c:when test="${page eq 'main'}">
					<c:import url="/jsp/user/main.jsp" />
				</c:when>
				<c:when test="${page eq 'listFilm'}">
					<c:import url="/jsp/user/filmsList.jsp" />
				</c:when>
				<c:when test="${page eq 'registration'}">
					<c:import url="/jsp/user/registration.jsp" />
				</c:when>
				<c:when test="${page eq 'commentFilm'}">
					<c:import url="/jsp/user/filmsComments.jsp" />
				</c:when>
				<c:when test="${page eq 'ratingUser'}">
					<c:import url="/jsp/user/usersRating.jsp" />
				</c:when>

				<c:otherwise>
					<c:import url="/MainController?command=find_film_main"></c:import>
					<c:import url="/jsp/user/main.jsp" />
				</c:otherwise>
			</c:choose>
		</c:if>
		<c:import url="/jsp/user/aside.jsp" />
		<c:import url="/jsp/user/footer.jsp" />
	</c:if>

	<c:if test="${role eq 'USER'}">
		<c:import url="/jsp/user/header.jsp" />
		<c:import url="/jsp/user/popularFilm.jsp" />
		<c:import url="/jsp/user/nav.jsp" />

		<c:set var="page" value="${sessionScope.target}" />
		<c:if test="${page eq null}">
			<c:import url="/MainController?command=find_film_main"></c:import>
			<c:import url="/jsp/user/main.jsp" />
		</c:if>

		<c:if test="${page ne null}">
			<c:choose>
				<c:when test="${page eq 'giveRequest'}">
					<c:import url="/jsp/user/giveRequest.jsp" />
				</c:when>

				<c:when test="${page eq 'ratingFilm'}">
					<c:import url="/jsp/user/filmsRating.jsp" />
				</c:when>
				<c:when test="${page eq 'searchFilmFasty'}">
					<c:import url="/jsp/user/searchFilmFasty.jsp" />
				</c:when>
				<c:when test="${page eq 'filmByGenre'}">
					<c:import url="/jsp/user/filmByGenre.jsp" />
				</c:when>

				<c:when test="${page eq 'infoFilm'}">
					<c:import url="/jsp/user/filmsInformation.jsp" />
				</c:when>
				<c:when test="${page eq 'main'}">
					<c:import url="/jsp/user/main.jsp" />
				</c:when>
				<c:when test="${page eq 'personal'}">
					<c:import url="/jsp/user/personalUser.jsp" />
				</c:when>
				<c:when test="${page eq 'userChanges'}">
					<c:import url="/jsp/user/changeUser.jsp" />
				</c:when>
				<c:when test="${page eq 'comments'}">
					<c:import url="/jsp/user/usersComments.jsp" />
				</c:when>
				<c:when test="${page eq 'commentFilm'}">
					<c:import url="/jsp/user/filmsComments.jsp" />
				</c:when>
				<c:when test="${page eq'assessments'}">
					<c:import url="/jsp/user/usersAssessments.jsp" />
				</c:when>
				<c:when test="${page eq 'serchFilm'}">
					<c:import url="/jsp/user/searchFilm.jsp" />
				</c:when>
				<c:when test="${page eq 'listFilm'}">
					<c:import url="/jsp/user/filmsList.jsp" />
				</c:when>

				<c:when test="${page eq 'ratingUser'}">
					<c:import url="/jsp/user/usersRating.jsp" />
				</c:when>
				<c:otherwise>
					<c:import url="/MainController?command=find_film_main"></c:import>
					<c:import url="/jsp/user/main.jsp" />
				</c:otherwise>
			</c:choose>
		</c:if>
		<c:import url="/jsp/user/aside.jsp" />
		<c:import url="/jsp/user/footer.jsp" />
	</c:if>

	<c:if test="${role eq 'ADMIN'}">
		<c:import url="/jsp/admin/header.jsp" />
		<c:import url="/jsp/admin/nav.jsp" />
		<c:set var="page" value="${sessionScope.target}" />
		<c:if test="${page eq null}">
			<c:import url="/jsp/admin/main.jsp" />
		</c:if>
		<c:if test="${page ne null}">
			<c:choose>
				<c:when test="${page eq 'requests'}">
					<c:import url="/jsp/admin/requestsList.jsp" />
				</c:when>
				<c:when test="${page eq 'addedComment'}">
					<c:import url="/jsp/admin/addedComment.jsp" />
				</c:when>
				<c:when test="${page eq 'ratingFilm'}">
					<c:import url="/jsp/admin/ratingFilm.jsp" />
				</c:when>
				<c:when test="${page eq 'changeFilm'}">
					<c:import url="/jsp/admin/changeFilm.jsp" />
				</c:when>
				<c:when test="${page eq 'main'}">
					<c:import url="/jsp/admin/main.jsp" />
				</c:when>

				<c:when test="${page eq 'addPage'}">
					<c:import url="/jsp/admin/addFilm.jsp" />
				</c:when>
				<c:when test="${page eq 'serchFilm'}">
					<c:import url="/jsp/admin/searchFilm.jsp" />
				</c:when>
				<c:when test="${page eq 'listFilm'}">
					<c:import url="/jsp/admin/filmsList.jsp" />
				</c:when>
				<c:when test="${page eq 'removedFilm'}">
					<c:import url="/jsp/admin/removedFilm.jsp" />
				</c:when>
				<c:when test="${page eq 'ratingUser'}">
					<c:import url="/jsp/admin/ratingUser.jsp" />
				</c:when>
				<c:when test="${page eq 'usersList'}">
					<c:import url="/jsp/admin/usersList.jsp" />
				</c:when>
				<c:when test="${page eq 'personal'}">
					<c:import url="/jsp/admin/personal.jsp" />
				</c:when>
				<c:otherwise>
					<c:import url="/jsp/admin/main.jsp" />
				</c:otherwise>
			</c:choose>
		</c:if>
		<c:import url="/jsp/admin/footer.jsp" />
	</c:if>
</body>
</html>