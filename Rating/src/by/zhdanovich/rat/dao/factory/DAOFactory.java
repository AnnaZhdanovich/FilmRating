package by.zhdanovich.rat.dao.factory;

import by.zhdanovich.rat.dao.IAdminDao;
import by.zhdanovich.rat.dao.ICommonDao;
import by.zhdanovich.rat.dao.IUserDao;
import by.zhdanovich.rat.dao.impl.AdminDaoImpl;
import by.zhdanovich.rat.dao.impl.CommonDaoImpl;
import by.zhdanovich.rat.dao.impl.UserDaoImpl;

/**
 * Class {@code DAOFactory } is used to obtain a reference to instances of DAO
 * classes. It creates only one instance of an object of class
 * {@code DAOFactory .
 * 
 * @author Anna
 *
 */

public class DAOFactory {
	private static final DAOFactory FACTORY = new DAOFactory();
	private final ICommonDao commonDAO = new CommonDaoImpl();
	private final IUserDao userDAO = new UserDaoImpl();
	private final IAdminDao adminDAO = new AdminDaoImpl();

	private DAOFactory() {
	}

	public static DAOFactory getInstance() {
		return FACTORY;
	}

	public ICommonDao getCommonDao() {
		return commonDAO;
	}

	public IUserDao getUserDao() {
		return userDAO;
	}

	public IAdminDao getAdminDao() {
		return adminDAO;

	}

}
