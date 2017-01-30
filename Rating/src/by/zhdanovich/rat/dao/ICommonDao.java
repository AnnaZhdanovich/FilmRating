package by.zhdanovich.rat.dao;


import java.util.List;
import java.util.Map;
import by.zhdanovich.rat.dao.exception.DAOException;
import by.zhdanovich.rat.entity.Comment;
import by.zhdanovich.rat.entity.Film;
import by.zhdanovich.rat.entity.Personality;
import by.zhdanovich.rat.entity.User;

public interface ICommonDao extends IDAO {

	User authorizeUser(String login, String password) throws DAOException;

	User registrUser(String name, String firstname, String login, String password, String email) throws DAOException;

	boolean checkLogin(String login) throws DAOException;

	List<Personality> takePersonality() throws DAOException;

	Film findFilmById(int id) throws DAOException;

	int findListOfFilms(List<Film> list, String title, String country, String actor, String producer, String year,
			String genre, int offset, int noOfRecords) throws DAOException;

	int findFilmsByRating(List<Film> list, int offset, int noOfRecords, String type) throws DAOException;

	void findNewFilms(List<Film> list) throws DAOException;

	int findFilmFasty(List<Film> list, String text, int offset, int noOfRecords) throws DAOException;

	void findFilm(List<Film> films) throws DAOException;

	void findFilmComment(List<Comment> comments) throws DAOException;

	void colculateFilmRating(Map<Integer, Integer> newCountFilm, Map<Integer, Float> newRatingFilm)
			throws DAOException;

	void takeCountFilm(Map<Integer, Integer> count) throws DAOException;

	void updateCountFilm(Integer filmUid, Integer count) throws DAOException;

	void colculateUserRating(Integer filmUid, Float newRating) throws DAOException;

	int findListMain(List<Film> list, int offset, int noOfRecords) throws DAOException;

	void findUsersByRating(List<User> list, String type) throws DAOException;

	int takeCommentOfFilm(int id, List<Comment> list, int offset, int noOfRecords) throws DAOException;

	int findUsersList(List<User> list, int offset, int noOfRecords) throws DAOException;

	boolean addImage(String name, int id) throws DAOException;
}
