package by.zhdanovich.rat.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.Part;
import by.zhdanovich.rat.dao.ICommonDao;
import by.zhdanovich.rat.dao.IUserDao;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.dao.exception.DAOException;
import by.zhdanovich.rat.dao.factory.DAOFactory;
import by.zhdanovich.rat.entity.Comment;
import by.zhdanovich.rat.entity.Film;
import by.zhdanovich.rat.entity.Personality;
import by.zhdanovich.rat.entity.Personality.RoleOfActor;
import by.zhdanovich.rat.entity.User;
import by.zhdanovich.rat.service.ICommonService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.util.BuilderPath;
import by.zhdanovich.rat.service.util.ServiceParameter;

/**
 * Class {@code CommonServiceImpl} implements all the methods of the interface
 * {@code ICommonService}.The methods associated with the entity "admin" and
 * "user". Methods used for data transmission in the level of DAO.
 * 
 * @author Anna
 */
public class CommonServiceImpl implements ICommonService {

	/**
	 * Search all the actors and producers.
	 * 
	 * @return list of actors and producers
	 * @throws ServiceException
	 */

	@Override
	public List<Personality> takePersonality(List<Personality> actors, List<Personality> producers)
			throws ServiceException {
		List<Personality> list = null;
		DAOFactory factory = DAOFactory.getInstance();
		ICommonDao common = factory.getCommonDao();
		try {
			list = common.takePersonality();
			if (list != null && !list.isEmpty()) {
				for (Personality person : list) {
					if (person.getRole() == RoleOfActor.ACTOR)
						actors.add(person);
					else
						producers.add(person);
				}
			}
		} catch (DAOException e) {
			throw new ServiceException("Wrong", e);
		}
		return list;

	}

	/**
	 * Recalculation ratings and search film on Id.
	 * 
	 * @param id
	 *            identification number of the film
	 * @return the type of user object or null
	 * @throws ServiceException
	 */
	@Override
	public Film findFilmById(int id) throws ServiceException {
		Film film = null;

		DAOFactory factory = DAOFactory.getInstance();
		ICommonDao common = factory.getCommonDao();
		try {
			colculateRating();
			film = common.findFilmById(id);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return film;
	}

	@Override

	/**
	 * Recalculation ratings and search the films.
	 * 
	 * @param list
	 *            list where will be posted the films
	 * @param title
	 *            name of film
	 * @param country
	 *            country of film
	 * @param actors
	 *            actors of film
	 * @param producers
	 *            producers of film
	 * @param year
	 *            year of film
	 * @param genre
	 *            genre of film
	 *
	 * @param offset
	 *            the displacement of the records in the list
	 * @param noOfRecords
	 *            the maximum number of records returned returned
	 * @return number of records retrieved
	 * @throws ServiceException
	 */
	public int findListOfFilms(List<Film> list, String title, String country, String actor, String producer,
			String year, String genre, int offset, int noOfRecords) throws ServiceException {
		int k = 0;

		DAOFactory factory = DAOFactory.getInstance();
		ICommonDao common = factory.getCommonDao();

		try {
			colculateRating();
			k = common.findListOfFilms(list, title, country, actor, producer, year, genre, offset, noOfRecords);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}

		return k;
	}

	/**
	 * Recalculation ratings and search films by rating.
	 * 
	 * @param list
	 *            a list where will be posted films
	 * @param type
	 *            parameter that defines how the sorting will be rated films
	 * @param offset
	 *            the displacement of the records in the list
	 * @param noOfRecords
	 *            the maximum number of records returned returned
	 * @return number of records retrieved
	 * @throws ServiceException
	 */
	@Override
	public int findFilmsByRating(List<Film> list, String type, int offset, int noOfRecords) throws ServiceException {
		int k = 0;

		DAOFactory factory = DAOFactory.getInstance();
		ICommonDao common = factory.getCommonDao();

		try {
			colculateRating();
			k = common.findFilmsByRating(list, offset, noOfRecords, type);

		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return k;
	}

	@Override
	public void findNewFilms(List<Film> list) throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		ICommonDao common = factory.getCommonDao();

		try {
			common.findNewFilms(list);
			Collections.sort(list, new Comparator<Film>() {
				@Override
				public int compare(Film f1, Film f2) {
					return f2.getRating() - f1.getRating();
				}
			});
			Iterator<Film> iter = list.iterator();
			int count = 0;
			while (iter.hasNext()) {

				if (count >= ServiceParameter.COUNT_NEW_FILMS) {
					iter.next();
					iter.remove();
				} else {
					iter.next();
				}
				count++;
			}
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return;
	}

	@Override
	public void findFilm(List<Film> films) throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		ICommonDao common = factory.getCommonDao();
		try {
			colculateRating();
			common.findFilm(films);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return;
	}

	@Override
	public void findFilmComment(List<Comment> comments) throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		ICommonDao common = factory.getCommonDao();

		try {
			common.findFilmComment(comments);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return;
	}

	@Override
	public int findFilmFasty(List<Film> list, String text, int offset, int noOfRecords) throws ServiceException {
		int k = 0;
		DAOFactory factory = DAOFactory.getInstance();
		ICommonDao common = factory.getCommonDao();
		try {
			colculateRating();
			k = common.findFilmFasty(list, text, offset, noOfRecords);

		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}

		return k;
	}

	/**
	 * It saves the image to the server, and the path to it retains the base.
	 * 
	 * @param type
	 * @param path
	 * @param id
	 * @param filePart
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws ServiceException
	 */
	@Override
	public boolean addImage(Part filePart, String path, int id, String type) throws ServiceException {

		boolean result = false;

		if (type.equals(CommandParameter.FILM)) {
			DAOFactory factory = DAOFactory.getInstance();
			ICommonDao commonDao = factory.getCommonDao();
			try {
				BuilderPath builder = new BuilderPath();
				String name = builder.putImage(filePart, path);
				result = commonDao.addImage(name, id);
			} catch (DAOException e) {
				throw new ServiceException("service message", e);
			}
		}
		if (type.equals(CommandParameter.USER)) {
			DAOFactory factory = DAOFactory.getInstance();
			IUserDao userDao = factory.getUserDao();
			try {
				BuilderPath builder = new BuilderPath();
				String name = builder.putImage(filePart, path);
				result = userDao.addImage(name, id);
			} catch (DAOException e) {
				throw new ServiceException("service message", e);
			}
		}
		return result;
	}

	/**
	 * Calculation of the film and user ratings.
	 * 
	 * @throws ServiceException
	 */
	@Override
	public void colculateRating() throws ServiceException {

		DAOFactory factory = DAOFactory.getInstance();
		ICommonDao common = factory.getCommonDao();

		Map<Integer, Integer> newCountFilm = new HashMap<Integer, Integer>();
		Map<Integer, Integer> newRatingFilm = new HashMap<Integer, Integer>();
		Map<Integer, Integer> prevCountFilm = new HashMap<Integer, Integer>();

		try {
			common.colculateFilmRating(newCountFilm, newRatingFilm);
			common.takeCountFilm(prevCountFilm);
			if (!newCountFilm.isEmpty() && !prevCountFilm.isEmpty()) {
				Set<Integer> keysFilm = newCountFilm.keySet();
				for (Integer s : keysFilm) {
					if ((newCountFilm.get(s) - prevCountFilm.get(s)) == ServiceParameter.VALUE) {
						common.updateCountFilm(s, newCountFilm.get(s));
						common.colculateUserRating(s, newRatingFilm.get(s));
					}
				}
			}
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
	}

	@Override
	public int findListMain(List<Film> list, int offset, int noOfRecords) throws ServiceException {
		int k = 0;
		DAOFactory factory = DAOFactory.getInstance();
		ICommonDao common = factory.getCommonDao();
		try {
			colculateRating();
			k = common.findListMain(list, offset, noOfRecords);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return k;
	}

	@Override
	public int findUsersByRating(List<User> list, int offset, int noOfRecords, String type) throws ServiceException {
		int k = 0;
		DAOFactory factory = DAOFactory.getInstance();
		ICommonDao common = factory.getCommonDao();
		try {
			k = common.findUsersByRating(list, offset, noOfRecords, type);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return k;
	}

	@Override
	public int findUsersList(List<User> list, int offset, int noOfRecords) throws ServiceException {
		int k = 0;
		DAOFactory factory = DAOFactory.getInstance();
		ICommonDao common = factory.getCommonDao();
		try {
			colculateRating();
			k = common.findUsersList(list, offset, noOfRecords);

		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}

		return k;
	}

}