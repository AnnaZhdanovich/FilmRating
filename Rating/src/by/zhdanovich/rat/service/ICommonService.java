package by.zhdanovich.rat.service;

import java.util.List;

import javax.servlet.http.Part;

import by.zhdanovich.rat.entity.Comment;
import by.zhdanovich.rat.entity.Film;
import by.zhdanovich.rat.entity.Personality;
import by.zhdanovich.rat.entity.User;
import by.zhdanovich.rat.service.exception.ServiceException;

public interface ICommonService {
	List<Personality> takePersonality(List<Personality> actors, List<Personality> producers) throws ServiceException;

	Film findFilmById(int id) throws ServiceException;

	int findListOfFilms(List<Film> list, String title, String country, String actor, String producer, String year,
			String genre, int offset, int noOfRecords) throws ServiceException;

	int findFilmsByRating(List<Film> list, String type, int offset, int noOfRecords) throws ServiceException;

	void findNewFilms(List<Film> list) throws ServiceException;

	void findFilm(List<Film> films) throws ServiceException;

	void findFilmComment(List<Comment> comments) throws ServiceException;

	int findFilmFasty(List<Film> list, String text, int offset, int noOfRecords) throws ServiceException;

	int findListMain(List<Film> list, int offset, int noOfRecords) throws ServiceException;

	boolean addImage(Part filePart, String path, int id, String type) throws ServiceException;

	void colculateRating() throws ServiceException;

	int findUsersByRating(List<User> list, int offset, int noOfRecords, String type) throws ServiceException;

	int findUsersList(List<User> list,  int offset, int noOfRecords) throws ServiceException;
}
