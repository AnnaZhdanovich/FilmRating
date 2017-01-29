package by.zhdanovich.rat.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Part;

import by.zhdanovich.rat.entity.Comment;
import by.zhdanovich.rat.entity.Film;
import by.zhdanovich.rat.entity.Personality.RoleOfActor;
import by.zhdanovich.rat.entity.RequestOfUser;
import by.zhdanovich.rat.entity.User;
import by.zhdanovich.rat.service.exception.ServiceException;

public interface IAdminService {

	boolean addNewPersonality(String firstname, String Lastname, RoleOfActor role) throws ServiceException;

	boolean addFilm(String title, String country, String actors[], String producers[], String year, String[] genre,
			Part filePart, String path, String description) throws ServiceException;

	boolean blockUser(int id, String status) throws ServiceException;

	boolean updateDataFilm(String title, String country, String year, int fmUid) throws ServiceException;

	boolean updatePersonality(int filmUid, int personUid, String action) throws ServiceException;

	boolean updateGenre(int filmUid, int genreUid, String action) throws ServiceException;

	boolean deleteFilm(int filmUid, String type) throws ServiceException;

	int findRemoveFilm(List<Film> listint, int offset, int noOfRecords) throws ServiceException;

	boolean updateFilmDescription(String description, int fmUid) throws ServiceException;

	int findAddedComment(List<Comment> comments, int offset, int noOfRecords) throws ServiceException;

	boolean changeStatusComment(int idComment, String type) throws ServiceException;

	User findUserByLogin(String login) throws ServiceException;

	int findUserRequests(List<RequestOfUser> requests, int offset, int noOfRecords) throws ServiceException;

	boolean changeStatusRequest(int idRequest, String type) throws ServiceException;

	void findAmount(Map<String, Integer> amoun) throws ServiceException;

	boolean changeRole(int idUser, String role) throws ServiceException;
}
