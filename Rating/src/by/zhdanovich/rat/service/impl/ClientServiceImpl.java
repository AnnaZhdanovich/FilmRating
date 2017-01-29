package by.zhdanovich.rat.service.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.servlet.http.Part;
import by.zhdanovich.rat.dao.ICommonDao;
import by.zhdanovich.rat.dao.IUserDao;
import by.zhdanovich.rat.dao.exception.DAOException;
import by.zhdanovich.rat.dao.factory.DAOFactory;
import by.zhdanovich.rat.entity.Assessment;
import by.zhdanovich.rat.entity.Comment;
import by.zhdanovich.rat.entity.Film;
import by.zhdanovich.rat.entity.User;
import by.zhdanovich.rat.service.IClientService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.util.BuilderPath;

/**
 * Class {@code ClientServiceImpl} implements all the methods of the interface
 * {@code IClientService}.The methods associated with the entity "user".
 * Methods used for data transmission in the level of DAO.
 * 
 * @author Anna
 */
public class ClientServiceImpl implements IClientService {

	
	@Override
	public User authorizeUser(String login, String password) throws ServiceException {

		password = md5(password);
		DAOFactory factory = DAOFactory.getInstance();
		ICommonDao commonDao = factory.getCommonDao();
		User user;
		try {
			user = commonDao.authorizeUser(login, password);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}

		return user;
	}

	@Override
	public User registrUser(Part filePart, String path, String firstname, String login, String password, String email)
			throws ServiceException {
		User result = null;
		password = md5(password);
		DAOFactory factory = DAOFactory.getInstance();
		ICommonDao commonDao = factory.getCommonDao();

		try {
			BuilderPath builder = new BuilderPath();
			String name = builder.putImage(filePart, path);
			result = commonDao.registrUser(name, firstname, login, password, email);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return result;
	}

	@Override
	public User change(int id) throws ServiceException {
		User result = null;
		DAOFactory factory = DAOFactory.getInstance();
		IUserDao user = factory.getUserDao();
		try {
			result = user.change(id);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}

		return result;
	}

	@Override
	public User findUser(int id) throws ServiceException {
		User result = null;

		DAOFactory factory = DAOFactory.getInstance();
		IUserDao user = factory.getUserDao();

		try {
			result = user.findUser(id);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean updateUserData(String firstname, String login, String password, String email, int id)
			throws ServiceException {
		boolean result = false;
		password = md5(password);
		DAOFactory factory = DAOFactory.getInstance();
		IUserDao userDao = factory.getUserDao();

		try {
			result = userDao.update(firstname, login, password, email, id);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return result;
	}

	@Override
	public boolean checkLogin(String login) throws ServiceException {
		boolean result = false;
		DAOFactory factory = DAOFactory.getInstance();
		ICommonDao commonDao = factory.getCommonDao();

		try {
			result = commonDao.checkLogin(login);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}

		return result;
	}

	@Override
	public int takeAssessment(int id, List<Assessment> list, int offset, int noOfRecords) throws ServiceException {
		int k = 0;

		DAOFactory factory = DAOFactory.getInstance();
		IUserDao userDao = factory.getUserDao();

		try {
			k = userDao.takeAssessment(id, list, offset, noOfRecords);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return k;
	}

	@Override
	public int takeComment(int id, List<Comment> list, int offset, int noOfRecords) throws ServiceException {

		int k = 0;
		DAOFactory factory = DAOFactory.getInstance();
		IUserDao userDao = factory.getUserDao();

		try {
			k = userDao.takeComment(id, list, offset, noOfRecords);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return k;
	}

	@Override
	public boolean giveAssessment(int fmUid, int usUid, int numder) throws ServiceException {
		boolean result = false;

		DAOFactory factory = DAOFactory.getInstance();
		IUserDao userDao = factory.getUserDao();

		try {
			if (userDao.checkAssessment(fmUid, usUid)) {
				result = userDao.giveAssessment(fmUid, usUid, numder);
			}
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}

		return result;
	}

	@Override
	public boolean giveComment(int fmUid, int usUid, String text) throws ServiceException {
		boolean result = false;

		DAOFactory factory = DAOFactory.getInstance();
		IUserDao userDao = factory.getUserDao();
		try {
			if (userDao.checkComment(fmUid, usUid) == null) {
				result = userDao.giveComment(fmUid, usUid, text);
			}

		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}

		return result;
	}

	@Override
	public int findFilmByGenre(int id_ganre, List<Film> list, int offset, int noOfRecords) throws ServiceException {
		int k = 0;
		try {

			DAOFactory factory = DAOFactory.getInstance();
			IUserDao userDao = factory.getUserDao();

			k = userDao.findFilmByGenre(id_ganre, list, offset, noOfRecords);

		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}
		return k;
	}

	@Override
	public boolean giveRequest(int idUser, String text) throws ServiceException {
		boolean result = false;

		DAOFactory factory = DAOFactory.getInstance();
		IUserDao userDao = factory.getUserDao();

		try {
			result = userDao.giveRequest(idUser, text);

		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}

		return result;
	}

	@Override
	public int takeCommentOfFilm(int id, List<Comment> list, int offset, int noOfRecords) throws ServiceException {
		int k = 0;

		DAOFactory factory = DAOFactory.getInstance();
		ICommonDao commonDao = factory.getCommonDao();

		try {
			k = commonDao.takeCommentOfFilm(id, list, offset, noOfRecords);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}

		return k;

	}

	@Override
	public boolean deleteUser(int idUser) throws ServiceException {
		boolean result = false;
		DAOFactory factory = DAOFactory.getInstance();
		IUserDao userDao = factory.getUserDao();
		try {
			result = userDao.deleteUser(idUser);
		} catch (DAOException e) {
			throw new ServiceException("service message", e);
		}

		return result;
	}

	/**
	 * Md5 algorithm coding for data.
	 * 
	 * @param input
	 * @return
	 */
	private String md5(String input) {
		String md5 = null;
		if (null == input)
			return null;

		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(input.getBytes(), 0, input.length());
			md5 = new BigInteger(1, digest.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		return md5;
	}
}
