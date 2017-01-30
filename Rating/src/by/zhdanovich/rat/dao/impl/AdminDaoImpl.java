package by.zhdanovich.rat.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import by.zhdanovich.rat.dao.IAdminDao;
import by.zhdanovich.rat.dao.IDAO;
import by.zhdanovich.rat.dao.pool.ConnectionPool;
import by.zhdanovich.rat.dao.pool.ConnectionPoolException;
import by.zhdanovich.rat.dao.pool.ProxyConnection;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.dao.exception.DAOException;
import by.zhdanovich.rat.dao.util.DAOParameter;
import by.zhdanovich.rat.entity.Comment;
import by.zhdanovich.rat.entity.Film;
import by.zhdanovich.rat.entity.Genre;
import by.zhdanovich.rat.entity.Personality;
import by.zhdanovich.rat.entity.Personality.RoleOfActor;
import by.zhdanovich.rat.entity.RequestOfUser;
import by.zhdanovich.rat.entity.RequestOfUser.StatusOfRequest;
import by.zhdanovich.rat.entity.User;
import by.zhdanovich.rat.entity.User.Role;
import by.zhdanovich.rat.entity.User.Status;

/**
 * Class {@code AdminDaoImpl} implements all the methods of the interface
 * {@code IAdminDao}.
 * 
 * @author Anna
 */
public class AdminDaoImpl implements IAdminDao {

	private static final String SQL_TAKE_COMMENTS = "SELECT SQL_CALC_FOUND_ROWS fm_uid, us_f_name, us_uid, fm_title, fm_poster, as_recal, as_date, as_assessment, as_uid FROM  user  JOIN assessment ON us_uid=user_us_uid JOIN film ON film_fm_uid=fm_uid WHERE as_status_comment='new' AND as_recal is not null GROUP BY as_uid limit ?,?";
	private static final String SQL_BLOKING = "UPDATE user SET us_status=? WHERE us_uid=?";
	private static final String SQL_CHANGE_ROLE = "UPDATE user SET us_role=? WHERE us_uid=?";
	private static final String SQL_UNBLOCKING_COMMENT = "UPDATE assessment SET as_status_comment=? WHERE as_uid=?";
	private static final String SQL_ADD_PERSONALITY_SELECT = "SELECT ac_uid FROM actor WHERE ac_f_name=? AND ac_l_name=?";
	private static final String SQL_ADD_PERSONALITY_INSERT = "INSERT INTO actor (ac_f_name,ac_l_name,ac_role ) values (?,?,?)";
	private static final String SQL_ADD_FILM = "INSERT INTO film (fm_title, country_c_uid, fm_rating, fm_year, fm_description, fm_poster, fm_date, fm_count, fm_status) value (?,?,?,?,?,?,?,?,?)";
	private static final String SQL_ADD_FILM_ADD_ACTOR = "INSERT INTO film_has_actor (film_fm_uid, actor_ac_uid) value (LAST_INSERT_ID(),?)";
	private static final String SQL_ADD_FILM_ADD_GENRE = "INSERT INTO film_has_genre (film_fm_uid, genre_gn_uid) value (LAST_INSERT_ID(),?)";
	private static final String SQL_UPDATE_FILM = "UPDATE  film  SET fm_title=?, country_c_uid=?, fm_year=? WHERE fm_uid=?";
	private static final String SQL_UPDATE_FILM_DESCRIPTION = "UPDATE  film  SET  fm_description=? WHERE fm_uid=?";
	private static final String SQL_ADD_FILM_PERSONALITY = "INSERT INTO film_has_actor set film_fm_uid=?, actor_ac_uid=?";
	private static final String SQL_DELETE_FILM_PERSONALITY = "DELETE FROM film_has_actor WHERE film_fm_uid=? AND actor_ac_uid=?";
	private static final String SQL_ADD_FILM_GENRE = "INSERT INTO film_has_genre set film_fm_uid=?, genre_gn_uid=?";
	private static final String SQL_DELETE_FILM_GENRE = "DELETE FROM film_has_genre WHERE film_fm_uid=? AND genre_gn_uid=?";
	private static final String SQL_DELETE_FILM = "UPDATE  film SET fm_status=? WHERE film.fm_uid=?";
	private static final String SQL_FIND_REMOVE_FILM = "SELECT SQL_CALC_FOUND_ROWS fm_uid,  fm_title, fm_year, fm_rating, fm_description, fm_poster, fm_date, c_name, c_uid , fm_status  FROM film LEFT JOIN country on country_c_uid= c_uid LEFT JOIN film_has_actor on film.fm_uid=film_has_actor.film_fm_uid LEFT JOIN actor on actor_ac_uid=ac_uid LEFT JOIN film_has_genre on film.fm_uid=film_has_genre.film_fm_uid LEFT JOIN genre on genre_gn_uid=gn_uid WHERE fm_status ='block'  GROUP BY fm_uid limit ?,?";
	private static final String SQL_FIND_REMOVE_FILM_PERSONALITY = "SELECT ac_f_name, ac_l_name, ac_role from film JOIN film_has_actor on fm_uid=film_fm_uid JOIN actor on actor_ac_uid=ac_uid WHERE fm_uid =?";
	private static final String SQL_FIND_REMOVE_FILM_GENRE = "SELECT gn_name,  gn_uid FROM film JOIN film_has_genre on fm_uid=film_fm_uid JOIN genre on genre_gn_uid=gn_uid WHERE fm_uid =?";
	private static final String SQL_FIND_USER_BY_LOGIN = "SELECT  us_f_name, us_login, us_email, us_role, (Select count(as_assessment) FROM assessment WHERE user_us_uid = us_uid GROUP BY (user_us_uid)) as us_assessment,(Select count(as_recal) FROM assessment WHERE user_us_uid = us_uid GROUP BY (user_us_uid))as us_recal, us_status, us_image, us_rating, us_reg_date, us_uid FROM user LEFT JOIN assessment on user.us_uid=assessment.user_us_uid WHERE us_login=? ";
	private static final String SQL_TAKE_REQUESTS = "SELECT  rq_uid, rq_text, rq_status, us_uid, us_f_name, rq_date FROM request JOIN user on user_us_uid =us_uid WHERE rq_status ='new' GROUP BY rq_uid limit ?,?";
	private static final String SQL_MARKER_REQUEST = "UPDATE request SET rq_status=? WHERE rq_uid=?";
	private static final String SQL_SELECT_ROWS = "SELECT FOUND_ROWS()";
	private static final String SQL_FIND_AMOUNT_FILMS = "Select COUNT(*) FROM film;";
	private static final String SQL_FIND_AMOUNT_USERS = "Select COUNT(*) FROM user;";

	/**
	 * Appeal to the database with a request to block or unblock user.
	 * 
	 * @param id
	 *            identification number user ID, which you want to lock or
	 *            unlock
	 * @param status
	 *            determines what operation (lock or unlock) need to produce
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws DAOException
	 */

	@Override
	public boolean blockUser(int id, String status) throws DAOException {
		boolean result = false;
		ProxyConnection con = null;
		PreparedStatement ps = null;
		int rs;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_BLOKING);
			ps.setString(1, status);
			ps.setInt(2, id);
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

	/**
	 * Find a actor or director in the database.
	 * 
	 * @param firstname
	 *            first name of personality
	 * @param lastname
	 *            last name of personality
	 * @param role
	 *            defines an actor or director to add to the database
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws DAOException
	 */

	@Override
	public boolean findPersonality(String firstname, String lastname, RoleOfActor role) throws DAOException {
		boolean result = false;
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet resultSet;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_ADD_PERSONALITY_SELECT);
			ps.setString(1, firstname);
			ps.setString(2, lastname);
			resultSet = ps.executeQuery();
			if (!resultSet.next()) {
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
	 * Add a new actor or new director to the database.
	 * 
	 * @param firstname
	 *            first name of personality
	 * @param lastname
	 *            last name of personality
	 * @param role
	 *            defines an actor or director to add to the database
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws DAOException
	 */

	@Override
	public boolean addNewPersonality(String firstname, String lastname, RoleOfActor role) throws DAOException {
		boolean result = false;
		ProxyConnection con = null;
		PreparedStatement ps = null;
		int res;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_ADD_PERSONALITY_INSERT);
			ps.setString(1, firstname);
			ps.setString(2, lastname);
			ps.setString(3, role.toString());
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
	 * Add a new film to database.
	 * 
	 * @param title
	 * @param country
	 * @param actors
	 * @param producers
	 * @param year
	 * @param genre
	 * @param name
	 * @param description
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws DAOException
	 * @throws SQLException
	 * 
	 */
	@Override
	public boolean addFilm(String title, String country, String[] actors, String[] producers, String year,
			String[] genre, String name, String description) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		Date d = new Date();
		SimpleDateFormat format = new SimpleDateFormat(DAOParameter.FORMAT_DATA);
		String data = format.format(d);
		boolean result = false;
		int res;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			con.setAutoCommit(false);
			try {
				ps = con.prepareStatement(SQL_ADD_FILM);
				ps.setString(1, title);
				ps.setString(2, country);
				ps.setFloat(3, DAOParameter.START_RATING);
				ps.setString(4, year);
				ps.setString(5, description);
				ps.setString(6, name);
				ps.setString(7, data);
				ps.setInt(8, DAOParameter.COUNT);
				ps.setString(9, DAOParameter.UNBLOCK);
				res = ps.executeUpdate();
				if (res != 0) {
					result = true;
				}
			} finally {
				IDAO.closeStatement(ps);
			}
			try {
				for (String p : actors) {
					ps = con.prepareStatement(SQL_ADD_FILM_ADD_ACTOR);
					ps.setString(1, p);
					ps.executeUpdate();
				}
			} finally {
				IDAO.closeStatement(ps);
			}
			try {
				for (String p : producers) {
					ps = con.prepareStatement(SQL_ADD_FILM_ADD_ACTOR);
					ps.setString(1, p);
					ps.executeUpdate();
				}
			} finally {
				IDAO.closeStatement(ps);
			}
			try {
				for (String p : genre) {
					ps = con.prepareStatement(SQL_ADD_FILM_ADD_GENRE);
					ps.setString(1, p);
					ps.executeUpdate();
				}
			} finally {
				IDAO.closeStatement(ps);
			}
			con.commit();
		} catch (ConnectionPoolException e) {

			throw new DAOException("Wrong in ConnectionPool", e);

		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new DAOException("Wrong in DB", e);
			}
			throw new DAOException("Wrong in DB", e);

		} finally {
			IDAO.closeConnection(con);
		}
		return result;
	}

	/**
	 * Film data changes in the database.
	 * 
	 * @param title
	 *            the name of the film
	 * @param country
	 *            the country of the film
	 * @param year
	 *            the year of the film
	 * @param fmUid
	 *            identification number of film
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws DAOException
	 */
	@Override
	public boolean updateDataFilm(String title, String country, String year, int fmUid) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		boolean result = false;
		int res;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_UPDATE_FILM);
			ps.setString(1, title);
			ps.setString(2, country);
			ps.setString(3, year);
			ps.setInt(4, fmUid);
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
	 * Add a new actor or director for the film to database.
	 * 
	 * @param filmUid
	 *            identification number of the film
	 * @param personUid
	 *            identification number of the person
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws DAOException
	 */
	@Override
	public boolean addPersonality(int filmUid, int personUid) throws DAOException {

		ProxyConnection con = null;
		PreparedStatement ps = null;
		boolean result = false;
		int res;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_ADD_FILM_PERSONALITY);
			ps.setInt(1, filmUid);
			ps.setInt(2, personUid);
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
	 * Removal of an actor or director from database.
	 * 
	 * @param filmUid
	 *            identification number of the film
	 * @param personUid
	 *            identification number of the person
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws DAOException
	 */
	@Override
	public boolean deletePersonality(int filmUid, int personUid) throws DAOException {

		ProxyConnection con = null;
		PreparedStatement ps = null;
		boolean result = false;
		int res;

		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_DELETE_FILM_PERSONALITY);
			ps.setInt(1, filmUid);
			ps.setInt(2, personUid);
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
	 * Adding a new genre for the film to database.
	 * 
	 * @param filmUid
	 *            identification number of the film
	 * @param genreUid
	 *            identification number of the genre
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws DAOException
	 */
	@Override
	public boolean addGenre(int filmUid, int genreUid) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		boolean result = false;
		int res;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_ADD_FILM_GENRE);
			ps.setInt(1, filmUid);
			ps.setInt(2, genreUid);
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
	 * Removal a new genre for the film from database.
	 * 
	 * @param filmUid
	 *            identification number of the film
	 * @param genreUid
	 *            identification number of the genre
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws DAOException
	 */
	@Override
	public boolean deleteGenre(int filmUid, int genreUid) throws DAOException {

		ProxyConnection con = null;
		PreparedStatement ps = null;
		boolean result = false;
		int res;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_DELETE_FILM_GENRE);
			ps.setInt(1, filmUid);
			ps.setInt(2, genreUid);
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
	 * It changes the status film thus placing it in a category archive in database.
	 * 
	 * @param filmUid
	 *            identification number of the film
	 * @param type
	 *            the type of fthe film
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws DAOException
	 */

	@Override
	public boolean deleteFilm(int filmUid, String type) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		boolean result = false;
		int res;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_DELETE_FILM);
			ps.setString(1, type);
			ps.setInt(2, filmUid);
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
	 * Update description film in database.
	 * 
	 * @param description
	 *            the description of the film
	 * @param fmUid
	 *            identification number of the film
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws DAOException
	 */
	@Override
	public boolean updateFilmDescription(String description, int fmUid) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		boolean result = false;
		int res;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_UPDATE_FILM_DESCRIPTION);
			ps.setString(1, description);
			ps.setInt(2, fmUid);
			res = ps.executeUpdate();
			if (res != 0) {
				result = true;
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong", e);
		} finally {
			IDAO.closeStatement(ps);
			IDAO.closeConnection(con);
		}
		return result;
	}

	/**
	 * Search in the database of all new comments on film.
	 * 
	 * @param comments
	 *            list which will be posted comments found
	 * @param offset
	 *            the displacement of the records in the list
	 * @param noOfRecords
	 *            the maximum number of records returned returned
	 * @return number of records retrieved
	 * @throws DAOException
	 */

	@SuppressWarnings("resource")
	@Override
	public int findAddedComment(List<Comment> comments, int offset, int noOfRecords) throws DAOException {
		int k = 0;
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_TAKE_COMMENTS);
			ps.setInt(1, offset);
			ps.setInt(2, noOfRecords);
			rs = ps.executeQuery();
			Comment comment = null;
			while (rs.next()) {
				comment = new Comment();
				comment.getFilm().setTitle(rs.getString(DAOParameter.FM_TITLE));
				comment.setDate(rs.getDate(DAOParameter.AS_DATE));
				comment.setText(rs.getString(DAOParameter.AS_RECAL));
				comment.setIdComment(rs.getInt(DAOParameter.AS_UID));
				comment.getFilm().setPoster(rs.getString(DAOParameter.FM_POSTER));
				comment.getUser().setFirstName(rs.getString(DAOParameter.US_F_NAME));
				comment.getUser().setIdUser(rs.getInt(DAOParameter.US_UID));
				comments.add(comment);
			}
			rs.close();
			ps = con.prepareStatement(SQL_SELECT_ROWS);
			rs = ps.executeQuery();
			if (rs.next()) {
				k = rs.getInt(1);
			}
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
	 * Comment status change in database.
	 * 
	 * @param idComment
	 *            identification number of the comment
	 * @param type
	 *           the status of the comment
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws DAOException
	 * @throws DAOException
	 */
	@Override
	public boolean changeStatusComment(int idComment, String type) throws DAOException {
		boolean result = false;
		ProxyConnection con = null;
		PreparedStatement ps = null;
		int res;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_UNBLOCKING_COMMENT);
			ps.setString(1, type);
			ps.setInt(2, idComment);
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
	 * Search for movies that have been deleted in the archive in database.
	 * 
	 * @param list
	 *            the list which will be posted archive  film
	 * @param offset
	 *            the displacement of the records in the list
	 * @param noOfRecords
	 *            the maximum number of records returned returned
	 * @return number of records retrieved
	 * @throws DAOException
	 */
	@SuppressWarnings("resource")
	@Override
	public int findRemoveFilm(List<Film> list, int offset, int noOfRecords) throws DAOException {
		int k = 0;
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			try {
				ps = con.prepareStatement(SQL_FIND_REMOVE_FILM);
				ps.setInt(1, offset);
				ps.setInt(2, noOfRecords);
				rs = ps.executeQuery();

				Film film;
				while (rs.next()) {
					film = new Film();
					film.setIdFilm(rs.getInt(DAOParameter.FM_UID));
					film.getCountry().setName(rs.getString(DAOParameter.C_NAME));
					film.getCountry().setIdCountry(rs.getInt(DAOParameter.C_UID));
					film.setDescription(rs.getString(DAOParameter.FM_DESCRIPTION));
					film.setPoster(rs.getString(DAOParameter.FM_POSTER));
					film.setRating(rs.getFloat(DAOParameter.FM_RATING));
					film.setYear(rs.getString(DAOParameter.FM_YEAR));
					film.setTitle(rs.getString(DAOParameter.FM_TITLE));
					film.setDate(rs.getDate(DAOParameter.FM_DATE));
					film.setStatusOfFilm(Status.valueOf(rs.getString(DAOParameter.FM_STATUS).toUpperCase()));
					list.add(film);
				}
				rs.close();
				ps = con.prepareStatement(SQL_SELECT_ROWS);
				rs = ps.executeQuery();
				if (rs.next())
					k = rs.getInt(1);

			} finally {
				IDAO.closeStatement(ps);
			}
			if (!list.isEmpty()) {
				for (Film f : list) {
					try {
						Personality personality;
						ps = con.prepareStatement(SQL_FIND_REMOVE_FILM_PERSONALITY);
						ps.setInt(1, f.getIdFilm());
						rs = ps.executeQuery();
						while (rs.next()) {
							personality = new Personality();
							personality.setFirstName(rs.getString(DAOParameter.AC_F_NAME));
							personality.setLastName(rs.getString(DAOParameter.AC_L_NAME));
							personality.setRole(RoleOfActor.valueOf(rs.getString(DAOParameter.AC_ROLE)));
							f.getListPersonality().add(personality);
						}
					} finally {
						IDAO.closeStatement(ps);
					}
					try {
						ps = con.prepareStatement(SQL_FIND_REMOVE_FILM_GENRE);
						ps.setInt(1, f.getIdFilm());
						rs = ps.executeQuery();
						Genre gen;

						while (rs.next()) {
							gen = new Genre();
							gen.setName(rs.getString(DAOParameter.GN_NAME));
							gen.setIidGenre(rs.getInt(DAOParameter.GN_UID));
							f.getListGenre().add(gen);
						}
					} finally {
						IDAO.closeStatement(ps);
					}
				}
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {
			IDAO.closeConnection(con);
		}
		return k;
	}

	/**
	 * Search for user login in database.
	 * 
	 * @param login
	 *            the login of the user
	 * @return the type of user object or null
	 * @throws DAOException
	 */
	@Override
	public User findUserLogin(String login) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		User result = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
			ps.setString(1, login);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = new User();
				result.setFirstName(rs.getString(DAOParameter.US_F_NAME));
				result.setIdUser(rs.getInt(DAOParameter.US_UID));
				result.setStatus(Status.valueOf(rs.getString(DAOParameter.US_STATUS).toUpperCase()));
				result.setRating(Integer.parseInt(rs.getString(DAOParameter.US_RATING)));
				result.setImage(rs.getString(DAOParameter.US_IMAGE));
				result.setDateReg(rs.getDate(DAOParameter.US_DATE));
				result.setLogin(rs.getString(DAOParameter.US_LOGIN));
				result.setEmail(rs.getString(DAOParameter.US_EMAIL));
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
	 * Search for all user requests to the administrator in database.
	 * 
	 * @param requests
	 *           the list which will be posted the requests
	 * @param offset
	 *            the displacement of the records in the list
	 * @param noOfRecords
	 *            the maximum number of records returned returned
	 * @return number of records retrieved
	 * @throws DAOException
	 */

	@SuppressWarnings("resource")
	@Override
	public int findUserRequests(List<RequestOfUser> requests, int offset, int noOfRecords) throws DAOException {

		int k = 0;
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_TAKE_REQUESTS);
			ps.setInt(1, offset);
			ps.setInt(2, noOfRecords);
			rs = ps.executeQuery();
			RequestOfUser request = null;
			while (rs.next()) {
				request = new RequestOfUser();
				request.setIdRequest(rs.getInt(DAOParameter.RQ_UID));
				request.getUser().setFirstName(rs.getString(DAOParameter.US_F_NAME));
				request.setText(rs.getString(DAOParameter.RQ_TEXT));
				request.setStatus(StatusOfRequest.valueOf(rs.getString(DAOParameter.RQ_STATUS).toUpperCase()));
				request.setDate(rs.getDate(DAOParameter.RQ_DATE));
				request.getUser().setIdUser(rs.getInt(DAOParameter.US_UID));
				requests.add(request);
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
	 * Changing the status of a user request in database.
	 * 
	 * @param idRequest
	 *            identification number of the request
	 * @param type
	 *            the status of the request
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws DAOException
	 */
	@Override
	public boolean changeStatusRequest(int idRequest, String type) throws DAOException {
		boolean result = false;
		ProxyConnection con = null;
		PreparedStatement ps = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_MARKER_REQUEST);
			ps.setString(1, type);
			ps.setInt(2, idRequest);
			ps.executeUpdate();
			result = true;

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
	 * Search in the database of registered users and added films.
	 * 
	 * @param amount
	 *            map where as values are placed the values found
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws DAOException
	 */
	@Override
	public void findAmount(Map<String, Integer> amount) throws DAOException {
		ProxyConnection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			try {
				st = con.createStatement();
				rs = st.executeQuery(SQL_FIND_AMOUNT_FILMS);
				if (rs.next())
					amount.put(CommandParameter.AMOUNT_FILMS, rs.getInt(1));
			} finally {
				IDAO.closeStatement(st);
			}
			st = con.createStatement();
			rs = st.executeQuery(SQL_FIND_AMOUNT_USERS);
			if (rs.next())
				amount.put(CommandParameter.AMOUNT_USERS, rs.getInt(1));
		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {
			IDAO.closeStatement(st);
			IDAO.closeConnection(con);
		}

	}

	/**
	 * Change the user's role in database from the administrator to the user and
	 * from the user to the administrator.
	 * 
	 * @param idUser
	 *            identification number of the user
	 * @param role
	 *            the role of the user
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws DAOException
	 */
	@Override
	public boolean changeRole(int idUser, String role) throws DAOException {
		boolean result = false;
		ProxyConnection con = null;
		PreparedStatement ps = null;
		int rs;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_CHANGE_ROLE);
			ps.setString(1, role);
			ps.setInt(2, idUser);
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
