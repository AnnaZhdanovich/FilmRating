package by.zhdanovich.rat.service.impl;

import java.util.List;
import java.util.Map;
import javax.servlet.http.Part;
import by.zhdanovich.rat.dao.IAdminDao;
import by.zhdanovich.rat.dao.exception.DAOException;
import by.zhdanovich.rat.dao.factory.DAOFactory;
import by.zhdanovich.rat.entity.Comment;
import by.zhdanovich.rat.entity.Film;
import by.zhdanovich.rat.entity.Personality.RoleOfActor;
import by.zhdanovich.rat.entity.RequestOfUser;
import by.zhdanovich.rat.entity.User;
import by.zhdanovich.rat.service.IAdminService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.util.BuilderPath;
import by.zhdanovich.rat.service.util.ServiceParameter;

/**
 * Class {@code AdminServiceImpl} implements all the methods of the interface
 * {@code IAdminService}. The methods associated with the entity "admin".
 * Methods used for data transmission in the level of DAO.
 * 
 * @author Anna
 */

public class AdminServiceImpl implements IAdminService {
 
	@Override
	public boolean blockUser(int id, String status) throws ServiceException {
		boolean result = false;
		DAOFactory factory = DAOFactory.getInstance();
		IAdminDao admin = factory.getAdminDao();
		try {
			result = admin.blockUser(id, status);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return result;
	}

	@Override
	public boolean addNewPersonality(String firstname, String lastname, RoleOfActor role) throws ServiceException {
		boolean result = false;
		DAOFactory factory = DAOFactory.getInstance();
		IAdminDao admin = factory.getAdminDao();
		try {
			if (admin.findPersonality(firstname, lastname, role)) {
				result = admin.addNewPersonality(firstname, lastname, role);
			}
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return result;
	}

	@Override
	public boolean addFilm(String title, String country, String[] actors, String[] producers, String year,
			String[] genre, Part filePart, String path, String description) throws ServiceException {
		boolean result = false;
		DAOFactory factory = DAOFactory.getInstance();
		IAdminDao admin = factory.getAdminDao();
		try {
			BuilderPath builder = new BuilderPath();
			String name = builder.putImage(filePart, path);
			result = admin.addFilm(title, country, actors, producers, year, genre, name, description);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return result;
	}

	@Override
	public boolean updateDataFilm(String title, String country, String year, int fmUid) throws ServiceException {

		boolean result = false;

		DAOFactory factory = DAOFactory.getInstance();
		IAdminDao admin = factory.getAdminDao();

		try {
			result = admin.updateDataFilm(title, country, year, fmUid);

		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}

		return result;
	}

	/**
	 * According to the analysis of the input data adds or removes an actor or
	 * director.
	 * 
	 * @param filmUid
	 *            identification number of the film
	 * @param personUid
	 *            identification number of the personality
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws ServiceException
	 */
	@Override
	public boolean updatePersonality(int filmUid, int personUid, String action) throws ServiceException {
		boolean result = false;

		DAOFactory factory = DAOFactory.getInstance();
		IAdminDao admin = factory.getAdminDao();
		try {
			switch (action) {
			case ServiceParameter.ADD:
				result = admin.addPersonality(filmUid, personUid);
				break;
			case ServiceParameter.DELETE:

				result = admin.deletePersonality(filmUid, personUid);
				break;
			default:
				throw new ServiceException("Mistake of date");
			}
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return result;
	}

	/**
	 * According to the analysis of the input data adds or removes an genre.
	 * 
	 * @param filmUid
	 *            identification number of the film
	 * @param genreUid
	 *            identification number of the genre
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws ServiceException
	 */
	@Override
	public boolean updateGenre(int filmUid, int genreUid, String action) throws ServiceException {
		boolean result = false;

		DAOFactory factory = DAOFactory.getInstance();
		IAdminDao admin = factory.getAdminDao();

		try {
			switch (action) {
			case ServiceParameter.ADD:
				result = admin.addGenre(filmUid, genreUid);
				break;
			case ServiceParameter.DELETE:

				result = admin.deleteGenre(filmUid, genreUid);
				break;
			default:
				throw new ServiceException("service message");

			}
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return result;
	}

	@Override
	public boolean deleteFilm(int filmUid, String type) throws ServiceException {
		boolean result = false;
		DAOFactory factory = DAOFactory.getInstance();
		IAdminDao admin = factory.getAdminDao();
		try {
			result = admin.deleteFilm(filmUid, type);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return result;
	}

	@Override
	public boolean updateFilmDescription(String description, int fmUid) throws ServiceException {
		boolean result = false;
		DAOFactory factory = DAOFactory.getInstance();
		IAdminDao admin = factory.getAdminDao();
		try {
			result = admin.updateFilmDescription(description, fmUid);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return result;

	}

	@Override
	public int findAddedComment(List<Comment> comments, int offset, int noOfRecords) throws ServiceException {
		int k = 0;
		DAOFactory factory = DAOFactory.getInstance();
		IAdminDao admin = factory.getAdminDao();
		try {
			k = admin.findAddedComment(comments, offset, noOfRecords);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return k;

	}

	@Override
	public boolean changeStatusComment(int idComment, String type) throws ServiceException {
		boolean result = false;
		DAOFactory factory = DAOFactory.getInstance();
		IAdminDao admin = factory.getAdminDao();
		try {
			result = admin.changeStatusComment(idComment, type);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return result;
	}

	@Override
	public int findRemoveFilm(List<Film> list, int offset, int noOfRecords) throws ServiceException {
		int k = 0;
		DAOFactory factory = DAOFactory.getInstance();
		IAdminDao admin = factory.getAdminDao();
		try {
			k = admin.findRemoveFilm(list, offset, noOfRecords);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return k;
	}

	@Override
	public User findUserByLogin(String login) throws ServiceException {
		User result = null;

		DAOFactory factory = DAOFactory.getInstance();
		IAdminDao admin = factory.getAdminDao();

		try {
			result = admin.findUserLogin(login);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}

		return result;
	}

	@Override
	public int findUserRequests(List<RequestOfUser> requests, int offset, int noOfRecords) throws ServiceException {
		int k = 0;
		DAOFactory factory = DAOFactory.getInstance();
		IAdminDao admin = factory.getAdminDao();
		try {
			k = admin.findUserRequests(requests, offset, noOfRecords);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return k;
	}

	@Override
	public boolean changeStatusRequest(int idRequest, String type) throws ServiceException {
		boolean result = false;

		DAOFactory factory = DAOFactory.getInstance();
		IAdminDao admin = factory.getAdminDao();
		try {
			result = admin.changeStatusRequest(idRequest, type);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return result;

	}

	@Override
	public void findAmount(Map<String, Integer> amount) throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		IAdminDao admin = factory.getAdminDao();
		try {
			admin.findAmount(amount);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
	}

	@Override
	public boolean changeRole(int idUser, String role) throws ServiceException {
		boolean result = false;
		DAOFactory factory = DAOFactory.getInstance();
		IAdminDao admin = factory.getAdminDao();
		try {
			result = admin.changeRole(idUser, role);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return result;
	}

}
