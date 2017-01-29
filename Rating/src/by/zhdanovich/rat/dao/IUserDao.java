package by.zhdanovich.rat.dao;

import java.util.List;

import by.zhdanovich.rat.dao.exception.DAOException;
import by.zhdanovich.rat.entity.Assessment;
import by.zhdanovich.rat.entity.Comment;
import by.zhdanovich.rat.entity.Film;
import by.zhdanovich.rat.entity.User;

public interface IUserDao extends IDAO {
	User change(int id) throws DAOException;

	User findUser(int id) throws DAOException;

	boolean update(String firstname, String login, String password, String email, int id) throws DAOException;

	int takeAssessment(int id, List<Assessment> list, int offset, int noOfRecords) throws DAOException;

	int takeComment(int id, List<Comment> list, int offset, int noOfRecords) throws DAOException;

	boolean checkAssessment(int fmUid, int usUid) throws DAOException;

	String checkComment(int fmUid, int usUid) throws DAOException;

	boolean giveAssessment(int fmUid, int usUid, int numder) throws DAOException;

	boolean giveComment(int fmUid, int usUid, String comment) throws DAOException;

	int findFilmByGenre(int id_ganre, List<Film> list, int offset, int noOfRecords) throws DAOException;

	boolean addImage(String name, int id) throws DAOException;

	boolean giveRequest(int idUser, String text) throws DAOException;

	boolean deleteUser(int idUser) throws DAOException;
}
