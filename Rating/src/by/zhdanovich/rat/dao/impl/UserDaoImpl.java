package by.zhdanovich.rat.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import by.zhdanovich.rat.dao.IUserDao;
import by.zhdanovich.rat.dao.pool.ConnectionPool;
import by.zhdanovich.rat.dao.pool.ConnectionPoolException;
import by.zhdanovich.rat.dao.pool.ProxyConnection;
import by.zhdanovich.rat.dao.exception.DAOException;
import by.zhdanovich.rat.dao.util.DAOParameter;
import by.zhdanovich.rat.dao.IDAO;
import by.zhdanovich.rat.entity.Assessment;
import by.zhdanovich.rat.entity.Comment;
import by.zhdanovich.rat.entity.Film;
import by.zhdanovich.rat.entity.User;
import by.zhdanovich.rat.entity.User.Role;
import by.zhdanovich.rat.entity.User.Status;

public class UserDaoImpl implements IUserDao {

	private final static String SQL_CHANGE_SELECT = "SELECT us_f_name, us_login, us_password, us_email FROM user WHERE user.us_uid=? ";
	private final static String SQL_FIND_USER_SELECT = "SELECT  us_login, us_email, us_f_name, us_reg_date, us_role, (Select count(as_assessment) FROM assessment WHERE user_us_uid = us_uid GROUP BY (user_us_uid)) AS us_assessment,(Select count(as_recal) FROM assessment WHERE user_us_uid = us_uid GROUP BY (user_us_uid))as us_recal, us_status, us_image, us_rating, us_reg_date, us_uid from user LEFT JOIN assessment ON user.us_uid=assessment.user_us_uid WHERE us_uid=? ";
	private final static String SQL_UPDATE_USER = "UPDATE  user SET us_f_name=?,  us_email=?, us_login=?, us_password=? WHERE us_uid=?";
	private final static String SQL_TAKE_ASSESSMENTS = "SELECT SQL_CALC_FOUND_ROWS fm_uid, us_f_name, fm_title, fm_poster, as_assessment, as_date FROM  user JOIN assessment ON us_uid=user_us_uid JOIN film on film_fm_uid=fm_uid WHERE fm_status !='block' AND user_us_uid=?  limit ?,?";
	private final static String SQL_TAKE_COMMENTS = "SELECT SQL_CALC_FOUND_ROWS fm_uid, us_f_name, fm_title, fm_poster, as_recal, as_date FROM  user  JOIN assessment ON us_uid=user_us_uid JOIN film ON film_fm_uid=fm_uid WHERE as_status_comment ='allowed' AND fm_status !='block' AND user_us_uid=? AND as_recal IS NOT NULL limit ?,?";
	private final static String SQL_CHECK_COMMENT = "SELECT as_recal FROM assessment WHERE film_fm_uid=? AND user_us_uid=?";
	private final static String SQL_CHECK_ASSESSMENT = "SELECT as_assessment FROM assessment WHERE film_fm_uid=? AND user_us_uid=?";
	private final static String SQL_GIVE_ASSESSMENT = "INSERT INTO assessment (as_assessment, film_fm_uid, user_us_uid,as_date) value (?,?,?,?)";
	private final static String SQL_GIVE_COMMENT = "UPDATE assessment  set as_recal=?, as_status_comment='new' WHERE film_fm_uid=? AND user_us_uid=?";
	private static final String SQL_FIND_FILM_BY_GENRE = "SELECT SQL_CALC_FOUND_ROWS fm_uid, fm_title,  fm_rating,  fm_poster  FROM film  JOIN film_has_genre ON fm_uid=film_fm_uid JOIN genre ON genre_gn_uid=gn_uid  WHERE fm_status !='block' AND gn_uid=? GROUP BY fm_uid limit ?,?";
	private static final String SQL_UPDATE_IMAGE = "UPDATE user SET  us_image=? WHERE us_uid=?";
	private static final String SQL_GIVE_REQUEST = "INSERT INTO request (user_us_uid, rq_text, rq_status, rq_date) value (?,?,'new',?)";
	private static final String SQL_DELETE_USER = "DELETE FROM user, assessment USING user left join assessment on user.us_uid=assessment.user_us_uid where us_uid=?";
	private static final String SQL_SELECT_ROWS = "SELECT FOUND_ROWS()";

	/**
	 * Return the user's personal data from database.
	 * 
	 * @param id
	 *            identification number of the user
	 * @return the type of user object or null
	 * @throws DAOException
	 */
	@Override
	public User change(int id) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		User result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_CHANGE_SELECT);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = new User();
				result.setFirstName(rs.getString(DAOParameter.US_F_NAME));
				result.setPassword(rs.getString(DAOParameter.US_PASSWORD));
				result.setLogin(rs.getString(DAOParameter.US_LOGIN));
				result.setEmail(rs.getString(DAOParameter.US_EMAIL));
			}

		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {
			IDAO.closeStatement(ps);
			IDAO.closeConnection(con);
		}
		return result;

	}

	/**
	 * Search user by id in the database.
	 * 
	 * @param id
	 *            identification number of the user
	 * @return the type of user object or null
	 * @throws DAOException
	 */
	@Override
	public User findUser(int id) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		User result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_FIND_USER_SELECT);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = new User();
				result.setFirstName(rs.getString(DAOParameter.US_F_NAME));
				result.setIdUser(rs.getInt(DAOParameter.US_UID));
				result.setStatus(Status.valueOf(rs.getString(DAOParameter.US_STATUS).toUpperCase()));
				result.setRating(Integer.parseInt(rs.getString(DAOParameter.US_RATING)));
				result.setImage(rs.getString(DAOParameter.US_IMAGE));
				result.setDateReg(rs.getDate(DAOParameter.US_DATE));
				result.setEmail(rs.getString(DAOParameter.US_EMAIL));
				result.setLogin(rs.getString(DAOParameter.US_LOGIN));
				result.setRole(Role.valueOf(rs.getString(DAOParameter.US_ROLE).toUpperCase()));
				if (rs.getString(DAOParameter.AMOUNT_ASSESSMENTS) != null) {
					result.setAmountOfAssessment(Integer.parseInt(rs.getString(DAOParameter.AMOUNT_ASSESSMENTS)));
				} else {
					result.setAmountOfAssessment(0);
				}
				if (rs.getString(DAOParameter.AMOUNT_REVIEWS) != null) {
					result.setAmountOfComment(Integer.parseInt(rs.getString(DAOParameter.AMOUNT_REVIEWS)));
				} else {
					result.setAmountOfComment(0);
				}
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {
			IDAO.closeStatement(ps);
			IDAO.closeConnection(con);
		}
		return result;

	}

	/**
	 * Updating the user data in the database.
	 * 
	 * @param firstname
	 *            first name of the user
	 * @param login
	 *            login of the user
	 * @param password
	 *            the password of the user
	 * @param email
	 *            email of the user
	 * @param id
	 *            identification number of the user
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws DAOException
	 */
	@Override
	public boolean update(String firstname, String login, String password, String email, int id) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		boolean result = false;
		int res;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_UPDATE_USER);
			ps.setString(1, firstname);
			ps.setString(2, email);
			ps.setString(3, login);
			ps.setString(4, password);
			ps.setInt(5, id);
			res = ps.executeUpdate();
			if (res != 0) {
				result = true;
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {
			IDAO.closeStatement(ps);
			IDAO.closeConnection(con);
		}
		return result;

	}

	/**
	 * Search all the assessments that have been set by the user in the database.
	 * 
	 * @param list
	 *            a list where will be found assessment
	 * @param id
	 *            identification number of the user
	 * @param offset
	 *            the displacement of the records in the list
	 * @param noOfRecords
	 *            the maximum number of records returned returned
	 * @return number of records retrieved
	 * @throws DAOException
	 */
	@SuppressWarnings("resource")
	@Override
	public int takeAssessment(int id, List<Assessment> list, int offset, int noOfRecords) throws DAOException {
		int k = 0;
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;

		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_TAKE_ASSESSMENTS);
			ps.setInt(1, id);
			ps.setInt(2, offset);
			ps.setInt(3, noOfRecords);
			rs = ps.executeQuery();
			Assessment assessment = null;
			while (rs.next()) {
				assessment = new Assessment();
				assessment.getFilm().setTitle(rs.getString(DAOParameter.FM_TITLE));
				assessment.setDate(rs.getDate(DAOParameter.AS_DATE));
				assessment.setValue(rs.getInt(DAOParameter.AS_ASSESSMENT));
				assessment.getFilm().setIdFilm(rs.getInt(DAOParameter.FM_UID));
				assessment.getFilm().setPoster(rs.getString(DAOParameter.FM_POSTER));
				list.add(assessment);
			}
			rs.close();
			ps = con.prepareStatement(SQL_SELECT_ROWS);
			rs = ps.executeQuery();
			if (rs.next())
				k = rs.getInt(1);

		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {
			IDAO.closeStatement(ps);
			IDAO.closeConnection(con);
		}
		return k;

	}

	/**
	 * Search all the comment that have been set by the user in the database.
	 * 
	 * @param list
	 *            a list where will be found comment
	 * @param id
	 * @param offset
	 *            the displacement of the records in the list
	 * @param noOfRecords
	 *            the maximum number of records returned returned
	 * @return number of records retrieved
	 * @throws DAOException
	 */
	@SuppressWarnings("resource")
	@Override
	public int takeComment(int id, List<Comment> list, int offset, int noOfRecords) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		int k = 0;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_TAKE_COMMENTS);
			ps.setInt(1, id);
			ps.setInt(2, offset);
			ps.setInt(3, noOfRecords);
			rs = ps.executeQuery();
			Comment comment = null;
			while (rs.next()) {
				comment = new Comment();
				comment.getFilm().setTitle(rs.getString(DAOParameter.FM_TITLE));
				comment.setDate(rs.getDate(DAOParameter.AS_DATE));
				comment.setText(rs.getString(DAOParameter.AS_RECAL));
				comment.getFilm().setIdFilm(rs.getInt(DAOParameter.FM_UID));
				comment.getFilm().setPoster(rs.getString(DAOParameter.FM_POSTER));
				list.add(comment);
			}
			rs.close();
			ps = con.prepareStatement(SQL_SELECT_ROWS);
			rs = ps.executeQuery();
			if (rs.next())
				k = rs.getInt(1);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {
			IDAO.closeStatement(ps);
			IDAO.closeConnection(con);
		}
		return k;
	}

	/**
	 * Checks whether contained in a database of the user's assessment or not.
	 * 
	 * @param fmUid
	 * @param usUid
	 * @return If the assessment is found in the database, it returns false and
	 *         if the assessment is not found the truth
	 * @throws DAOException
	 */
	@Override
	public boolean checkAssessment(int fmUid, int usUid) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		boolean result = true;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_CHECK_ASSESSMENT);
			ps.setInt(1, fmUid);
			ps.setInt(2, usUid);
			rs = ps.executeQuery();

			if (rs.next()) {
				result = false;
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {
			IDAO.closeStatement(ps);
			IDAO.closeConnection(con);
		}
		return result;
	}

	/**
	 * Checks whether contained in a database of the user's comment or not.
	 * 
	 * @param fmUid
	 * @param usUid
	 * @return If the comment is found in the database, it returns false and if
	 *         the comment is not found the true
	 * @throws DAOException
	 */
	@Override
	public String checkComment(int fmUid, int usUid) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		String result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_CHECK_COMMENT);
			ps.setInt(1, fmUid);
			ps.setInt(2, usUid);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getString(DAOParameter.AS_RECAL);
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {
			IDAO.closeStatement(ps);
			IDAO.closeConnection(con);
		}
		return result;
	}

	/**
	 * Adding new assessment to the database.
	 * 
	 * @param fmUid
	 * @param usUid
	 * @param numder
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws DAOException
	 */
	@Override
	public boolean giveAssessment(int fmUid, int usUid, int numder) throws DAOException {
		boolean result = false;
		int res;
		ProxyConnection con = null;
		PreparedStatement ps = null;
		Date d = new Date();
		SimpleDateFormat format = new SimpleDateFormat(DAOParameter.FORMAT_DATA);
		String date = format.format(d);
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_GIVE_ASSESSMENT);
			ps.setInt(1, numder);
			ps.setInt(2, fmUid);
			ps.setInt(3, usUid);
			ps.setString(4, date);
			res = ps.executeUpdate();
			if (res != 0) {
				result = true;
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {
			IDAO.closeStatement(ps);
			IDAO.closeConnection(con);
		}
		return result;
	}

	/**
	 * Adding new comment to the database.
	 * 
	 * @param fmUid
	 * @param usUid
	 * @param numder
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws DAOException
	 */
	@Override
	public boolean giveComment(int fmUid, int usUid, String comment) throws DAOException {
		boolean result = false;
		int res;
		ProxyConnection con = null;
		PreparedStatement ps = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_GIVE_COMMENT);
			ps.setString(1, comment);
			ps.setInt(2, fmUid);
			ps.setInt(3, usUid);
			res = ps.executeUpdate();
			if (res != 0) {
				result = true;
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {
			IDAO.closeStatement(ps);
			IDAO.closeConnection(con);
		}
		return result;
	}

	/**
	 * Search film by genre in the database.
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
	 * @throws DAOException
	 */
	@SuppressWarnings("resource")
	@Override
	public int findFilmByGenre(int id_ganre, List<Film> list, int offset, int noOfRecords) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		int k = 0;
		try {
			con = ConnectionPool.getInstance().takeConnection();

			ps = con.prepareStatement(SQL_FIND_FILM_BY_GENRE);
			ps.setInt(1, id_ganre);
			ps.setInt(2, offset);
			ps.setInt(3, noOfRecords);
			rs = ps.executeQuery();
			Film film;
			while (rs.next()) {
				film = new Film();
				film.setIdFilm(rs.getInt(DAOParameter.FM_UID));
				film.setPoster(rs.getString(DAOParameter.FM_POSTER));
				film.setRating(rs.getFloat(DAOParameter.FM_RATING));
				film.setTitle(rs.getString(DAOParameter.FM_TITLE));
				list.add(film);
			}
			rs.close();
			ps = con.prepareStatement(SQL_SELECT_ROWS);
			rs = ps.executeQuery();
			if (rs.next())
				k = rs.getInt(1);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {
			IDAO.closeStatement(ps);
			IDAO.closeConnection(con);
		}
		return k;
	}

	/**
	 * Adds picture for the user to the database.
	 * 
	 * @param name
	 *            the name and type of the file
	 * @param id
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws DAOException
	 */
	@Override
	public boolean addImage(String name, int id) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		int res;
		boolean result = false;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_UPDATE_IMAGE);
			ps.setString(1, name);
			ps.setString(2, String.valueOf(id));
			res = ps.executeUpdate();
			if (res != 0) {
				result = true;
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {

			IDAO.closeStatement(ps);
			IDAO.closeConnection(con);
		}
		return result;
	}

	/**
	 * Adding new request to the database.
	 * 
	 * @param idUser
	 * @param text
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws DAOException
	 */
	@Override
	public boolean giveRequest(int idUser, String text) throws DAOException {
		boolean result = false;
		ProxyConnection con = null;
		PreparedStatement ps = null;
		Date d = new Date();
		SimpleDateFormat format = new SimpleDateFormat(DAOParameter.FORMAT_DATA);
		String data = format.format(d);
		int res;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_GIVE_REQUEST);
			ps.setInt(1, idUser);
			ps.setString(2, text);
			ps.setString(3, data);
			res = ps.executeUpdate();
			if (res != 0) {
				result = true;
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {
			IDAO.closeStatement(ps);
			IDAO.closeConnection(con);
		}
		return result;
	}

	/**
	 * Deleting user data from the database.
	 * 
	 * @param idUser
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws DAOException
	 */
	@Override
	public boolean deleteUser(int idUser) throws DAOException {
		boolean result = false;
		ProxyConnection con = null;
		PreparedStatement ps = null;
		int rs;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_DELETE_USER);
			ps.setInt(1, idUser);
			rs = ps.executeUpdate();
			if (rs != 0) {
				result = true;
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {
			IDAO.closeStatement(ps);
			IDAO.closeConnection(con);
		}
		return result;

	}
}
