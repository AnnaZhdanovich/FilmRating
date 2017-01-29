package by.zhdanovich.rat.service;

import java.util.List;

import javax.servlet.http.Part;

import by.zhdanovich.rat.entity.Assessment;
import by.zhdanovich.rat.entity.Comment;
import by.zhdanovich.rat.entity.Film;
import by.zhdanovich.rat.entity.User;
import by.zhdanovich.rat.service.exception.ServiceException;

public interface IClientService {
	User authorizeUser(String login, String password) throws ServiceException;

	User registrUser(Part filePart, String path, String firstname, String login, String password, String email)
			throws ServiceException;

	User change(int id) throws ServiceException;

	User findUser(int id) throws ServiceException;

	int takeAssessment(int id, List<Assessment> list, int offset, int noOfRecords) throws ServiceException;

	int takeComment(int id, List<Comment> list, int offset, int noOfRecords) throws ServiceException;

	boolean updateUserData(String firstname, String login, String password, String email, int id)
			throws ServiceException;

	boolean checkLogin(String login) throws ServiceException;

	int findFilmByGenre(int id_ganre, List<Film> list, int offset, int noOfRecords) throws ServiceException;

	boolean giveAssessment(int fmUid, int usUid, int numder) throws ServiceException;

	boolean giveComment(int fmUid, int usUid, String comment) throws ServiceException;

	boolean giveRequest(int idUser, String text) throws ServiceException;

	int takeCommentOfFilm(int id, List<Comment> list, int offset, int noOfRecords )throws ServiceException;

	boolean deleteUser(int idUser)throws ServiceException;
}
