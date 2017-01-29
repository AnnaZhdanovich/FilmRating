package by.zhdanovich.rat.dao;


import java.util.List;
import java.util.Map;
import by.zhdanovich.rat.dao.exception.DAOException;
import by.zhdanovich.rat.entity.Personality.RoleOfActor;
import by.zhdanovich.rat.entity.Comment;
import by.zhdanovich.rat.entity.Film;
import by.zhdanovich.rat.entity.RequestOfUser;
import by.zhdanovich.rat.entity.User;

public interface IAdminDao extends IDAO {
	public boolean addFilm(String title, String country, String actors[], String producers[], String year,
			String[] genre, String name, String description) throws DAOException;

	boolean updateDataFilm(String title, String country, String year, int fmUid) throws DAOException;

	boolean addPersonality(int filmUid, int personUid) throws DAOException;

	boolean deletePersonality(int filmUid, int personUid) throws DAOException;

	boolean addGenre(int filmUid, int genreUid) throws DAOException;

	boolean deleteGenre(int filmUid, int genreUid) throws DAOException;

	boolean blockUser(int id, String status) throws DAOException;

	boolean findPersonality(String firstname, String lastname, RoleOfActor role) throws DAOException;

	boolean addNewPersonality(String firstname, String lastname, RoleOfActor role) throws DAOException;

	boolean deleteFilm(int filmUid, String type) throws DAOException;

	boolean updateFilmDescription(String description, int fmUid) throws DAOException;

	int findAddedComment(List<Comment> comments, int offset, int noOfRecords) throws DAOException;

	boolean changeStatusComment(int idComment, String type) throws DAOException;

	int findRemoveFilm(List<Film> listint, int offset, int noOfRecords) throws DAOException;

	User findUserLogin(String login) throws DAOException;

	int findUserRequests(List<RequestOfUser> requests, int offset, int noOfRecords) throws DAOException;

	boolean changeStatusRequest(int idRequest, String type) throws DAOException;

	void findAmount(Map<String, Integer> amount) throws DAOException;

	boolean changeRole(int idUser, String role) throws DAOException;
}
