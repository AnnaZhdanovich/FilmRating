package by.zhdanovich.rat.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import by.zhdanovich.rat.dao.ICommonDao;
import by.zhdanovich.rat.dao.IDAO;
import by.zhdanovich.rat.dao.pool.ConnectionPool;
import by.zhdanovich.rat.dao.pool.ConnectionPoolException;
import by.zhdanovich.rat.dao.pool.ProxyConnection;
import by.zhdanovich.rat.dao.exception.DAOException;
import by.zhdanovich.rat.dao.util.DAOParameter;
import by.zhdanovich.rat.entity.Comment;
import by.zhdanovich.rat.entity.Film;
import by.zhdanovich.rat.entity.Genre;
import by.zhdanovich.rat.entity.Personality;
import by.zhdanovich.rat.entity.Personality.RoleOfActor;
import by.zhdanovich.rat.entity.User;
import by.zhdanovich.rat.entity.User.Role;
import by.zhdanovich.rat.entity.User.Status;

/**
 * class {@code CommonDaoImpl}implements all the methods of the interface
 * {@code ICommonDao}.
 * 
 * @author Anna
 */
public class CommonDaoImpl implements ICommonDao {

	private static final String SQL_REGISTRATION_INSERT = "INSERT INTO user (us_f_name,  us_email, us_login, us_password, us_status, us_role, us_rating, us_image, us_reg_date) value (?,?,?,?,?,?,?,?,?)";
	private static final String SQL_REGISTRATION_SELECT = "SELECT us_uid, us_f_name, us_role FROM user WHERE user.us_login=? AND user.us_password=? ";
	private static final String SQL_AUTHORISARION = "SELECT us_uid, us_f_name, us_status, us_role, us_image FROM user WHERE us_login=? AND us_password=?";
	private static final String SQL_TAKE_COUNT_VOICE = "SELECT fm_uid, count(film_fm_uid) as sum, format(avg(as_assessment),1) as avg FROM film LEFT JOIN assessment ON fm_uid=film_fm_uid GROUP BY fm_title";
	private static final String SQL_UPDATE_RATING_FILM = "UPDATE film SET fm_rating = ? WHERE fm_uid=?";
	private static final String SQL_SELECT_PREVIOS_COUNT = "SELECT fm_uid, fm_count FROM film";
	private static final String SQL_UPDATE_PREVIOS_COUNT = "UPDATE film SET fm_count=? WHERE fm_uid=?";
	private static final String SQL_USER_UID = "SELECT user_us_uid FROM assessment WHERE film_fm_uid=?";
	private static final String SQL_SELECT_FILM_MAIN = "SELECT SQL_CALC_FOUND_ROWS fm_uid,  fm_title, fm_year, fm_rating, fm_description, fm_poster, fm_date, c_name, c_uid   FROM film left JOIN country ON country_c_uid= c_uid WHERE fm_status !='block'  ORDER BY fm_uid DESC limit ?,?";
	private static final String SQL_UPDATE_RATING_USER = "Update user  SET us_rating = us_rating + (CASE WHEN 0 < ABS(?-(SELECT as_assessment FROM assessment WHERE film_fm_uid=? And user_us_uid=?)) <1 THEN   8   WHEN  1 < ABS(?-(SELECT as_assessment FROM assessment WHERE film_fm_uid=? And user_us_uid=?)) < 2  THEN  7    WHEN  2< ABS(?-(Select as_assessment FROM assessment WHERE film_fm_uid=? And user_us_uid=?)) < 3 THEN   4       WHEN  3< ABS(?-(Select as_assessment FROM assessment WHERE film_fm_uid=? And user_us_uid=?)) < 4  THEN  1       WHEN 4<  ABS(?-(Select as_assessment FROM assessment WHERE film_fm_uid=? And user_us_uid=?)) < 5 THEN   0      WHEN  5< ABS(?-(Select as_assessment FROM assessment WHERE film_fm_uid=? And user_us_uid=?)) <6  THEN  -4         WHEN  6< ABS(?-(Select as_assessment FROM assessment WHERE film_fm_uid=? And user_us_uid=?)) < 7 THEN  -7   WHEN 8< ABS(?-(SELECT as_assessment FROM assessment WHERE film_fm_uid=? And user_us_uid=?)) < 9 THEN   -8     	  WHEN  9< ABS(?-(Select as_assessment from assessment where film_fm_uid=? And user_us_uid=?))< 10  THEN  -10 ELSE 0 END) where us_uid=? ";
	private static final String SQL_CHEK_LOGIN = "SELECT us_uid FROM user where user.us_login=?";
	private static final String SQL_TAKE_PERSONALITY = "SELECT ac_uid, ac_f_name, ac_l_name, ac_role FROM actor";
	private static final String SQL_FIND_BY_ID = "SELECT fm_uid, fm_title, fm_rating, fm_description, fm_poster, fm_date, fm_year, c_name, c_uid  FROM film join country on country_c_uid= c_uid WHERE fm_uid=?";
	private static final String SQL_FIND_BY_ID_PERSONALITY = "Select ac_uid, ac_f_name, ac_l_name, ac_role FROM film JOIN film_has_actor ON fm_uid=film_fm_uid JOIN actor ON actor_ac_uid=ac_uid WHERE fm_uid =?";
	private static final String SQL_FIND_BY_ID_GENRE = "Select gn_name, gn_uid FROM film join film_has_genre ON fm_uid=film_fm_uid JOIN genre ON genre_gn_uid=gn_uid WHERE fm_uid =?";
	private static final String SQL_GET_ALL_FILMS_UP = "SELECT SQL_CALC_FOUND_ROWS fm_uid,  fm_title,fm_year, fm_rating, fm_description, fm_poster, fm_date, c_name, c_uid,  gn_uid, gn_name  FROM film LEFT JOIN country ON country_c_uid= c_uid LEFT JOIN film_has_actor ON film.fm_uid=film_has_actor.film_fm_uid LEFT JOIN actor on actor_ac_uid=ac_uid LEFT JOIN film_has_genre ON film.fm_uid=film_has_genre.film_fm_uid LEFT JOIN genre on genre_gn_uid=gn_uid WHERE fm_status !='block'  GROUP BY fm_uid ORDER BY  fm_rating limit ?,?";
	private static final String SQL_GET_ALL_FILMS_DOWN = "SELECT SQL_CALC_FOUND_ROWS fm_uid,  fm_title,fm_year, fm_rating, fm_description, fm_poster, fm_date, c_name, c_uid,  gn_uid, gn_name  FROM film LEFT JOIN country ON country_c_uid= c_uid LEFT JOIN film_has_actor ON film.fm_uid=film_has_actor.film_fm_uid LEFT JOIN actor ON actor_ac_uid=ac_uid LEFT JOIN film_has_genre ON film.fm_uid=film_has_genre.film_fm_uid LEFT JOIN genre ON genre_gn_uid=gn_uid WHERE fm_status !='block' GROUP BY fm_uid ORDER BY  fm_rating DESC limit ?,?";
	private static final String SQL_GET_NEW_FILMS = "SELECT fm_uid, fm_title, fm_poster, fm_rating FROM film  WHERE fm_status !='block' ORDER BY fm_uid DESC LIMIT 15";
	private static final String SQL_GET_DISCUSED_FILMS = "SELECT fm_uid, fm_title, fm_poster, fm_rating FROM (  (SELECT count(as_assessment) as am, film_fm_uid FROM assessment  GROUP BY (film_fm_uid) ORDER BY count(as_assessment) DESC limit 4) as nn ) JOIN film ON film_fm_uid=fm_uid WHERE fm_status !='block' ";
	private static final String SQL_FIND_FILM_FASTY = "SELECT fm_uid, fm_title, fm_poster, fm_rating FROM film WHERE  fm_status !='block' AND MATCH (fm_title) AGAINST (?) GROUP BY fm_uid limit ?,?";
	private static final String SQL_GET_LIST_USERS_UP = "SELECT SQL_CALC_FOUND_ROWS us_uid, us_login, us_f_name, us_email, us_status, us_rating, us_role, us_image, us_reg_date  FROM user WHERE us_role='user'  ORDER BY  us_rating limit ?,?";
	private static final String SQL_GET_LIST_USERS_DOWN = "SELECT SQL_CALC_FOUND_ROWS us_uid, us_login, us_f_name, us_email, us_status, us_rating, us_role, us_image, us_reg_date  FROM user WHERE us_role='user'  ORDER BY us_rating DESC limit ?,?";
	private static final String SQL_FIND_LIST_FILM = "SELECT SQL_CALC_FOUND_ROWS fm_uid,  fm_title, fm_year, fm_rating, fm_description, fm_poster, fm_date, c_name, c_uid   FROM film LEFT JOIN country on country_c_uid= c_uid LEFT JOIN film_has_actor on film.fm_uid=film_has_actor.film_fm_uid LEFT JOIN actor ON actor_ac_uid=ac_uid LEFT JOIN film_has_genre ON film.fm_uid=film_has_genre.film_fm_uid LEFT JOIN genre ON genre_gn_uid=gn_uid WHERE fm_status !='block' AND (fm_title=? OR country_c_uid=? OR fm_year=? OR actor_ac_uid=? OR actor_ac_uid=? OR genre_gn_uid=?) GROUP BY fm_uid limit ?,?";
	private static final String SQL_FIND_LIST_FILM_PERSONALITY = "SELECT ac_f_name, ac_l_name, ac_role FROM film JOIN film_has_actor ON fm_uid=film_fm_uid JOIN actor ON actor_ac_uid=ac_uid WHERE fm_uid =?";
	private static final String SQL_FIND_LIST_FILM_GENRE = "SELECT gn_name,  gn_uid from film JOIN film_has_genre on fm_uid=film_fm_uid JOIN genre ON genre_gn_uid=gn_uid WHERE fm_uid =?";
	private static final String SQL_GET_LAST_COMMENT = "SELECT fm_uid, us_uid , us_f_name, fm_title, fm_poster, as_recal, as_date FROM  user  JOIN assessment ON us_uid=user_us_uid JOIN film ON film_fm_uid=fm_uid  WHERE as_status_comment = 'allowed' AND fm_status !='block' AND as_recal IS NOT NULL ORDER BY as_uid DESC LIMIT 4";
	private static final String SQL_TAKE_COMMENTS = "SELECT SQL_CALC_FOUND_ROWS fm_uid,  us_f_name, us_uid , fm_title, fm_poster, as_recal, as_date FROM  user  JOIN assessment ON us_uid=user_us_uid JOIN film ON film_fm_uid=fm_uid WHERE as_status_comment = 'allowed' AND film_fm_uid=? AND as_recal IS NOT NULL limit ?,?";
	private static final String SQL_SELECT_ROWS = "SELECT FOUND_ROWS()";
	private static final String SQL_FIND_USERS_LIST = "SELECT SQL_CALC_FOUND_ROWS us_uid, us_login, us_f_name, us_email, us_status, us_rating, us_role, us_image, us_reg_date  FROM user  ORDER BY  us_rating limit ?,?";
	private static final String SQL_UPDATE_IMAGE = "UPDATE film SET  fm_poster=? WHERE fm_uid=?";

	/**
	 * User authentication by comparing the input parameters ID and password in
	 * database. with those that are in the database.
	 * 
	 * @param login
	 *            the login of the user
	 * @param password
	 *            the password of the user
	 * @return the type of user object or null
	 * @throws DAOException
	 */

	@Override
	public User authorizeUser(String login, String password) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		User user = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_AUTHORISARION);
			ps.setString(1, login);
			ps.setString(2, password);
			rs = ps.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setIdUser(rs.getInt(DAOParameter.US_UID));
				user.setFirstName(rs.getString(DAOParameter.US_F_NAME));
				user.setImage(rs.getString(DAOParameter.US_IMAGE));
				user.setStatus(Status.valueOf(rs.getString(DAOParameter.US_STATUS).toUpperCase()));
				user.setRole(Role.valueOf(rs.getString(DAOParameter.US_ROLE).toUpperCase()));
			}

		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {
			IDAO.closeStatement(ps);
			IDAO.closeConnection(con);
		}
		return user;

	}

	/**
	 * User registration in the database. Saving personal data in the database.
	 * 
	 * @param name
	 *            the name and type of the picture
	 * @param login
	 *            the login of the user
	 * @param password
	 *            the password of the user
	 * @param firstname
	 *            first name of the user
	 * @return the type of user object or null
	 * @throws DAOException
	 * @throws SQLException
	 */
	@Override
	public User registrUser(String name, String firstname, String login, String password, String email)
			throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		Date d = new Date();
		SimpleDateFormat format = new SimpleDateFormat(DAOParameter.FORMAT_DATA);
		String data = format.format(d);

		User result = null;

		try {
			con = ConnectionPool.getInstance().takeConnection();
			con.setAutoCommit(false);
			try {
				ps = con.prepareStatement(SQL_REGISTRATION_INSERT);
				ps.setString(1, firstname);
				ps.setString(2, email);
				ps.setString(3, login);
				ps.setString(4, password);
				ps.setString(5, DAOParameter.UNBLOCK);
				ps.setString(6, DAOParameter.USER);
				ps.setInt(7, DAOParameter.START_RATING_USER);
				ps.setString(8, name);
				ps.setString(9, data);
				ps.executeUpdate();
			} finally {
				IDAO.closeStatement(ps);
			}
			try {
				ps = con.prepareStatement(SQL_REGISTRATION_SELECT);
				ps.setString(1, login);
				ps.setString(2, password);
				rs = ps.executeQuery();
				if (rs.next()) {
					result = new User();
					result.setIdUser(Integer.parseInt(rs.getString(DAOParameter.US_UID)));
					result.setFirstName(rs.getString(DAOParameter.US_F_NAME));
					result.setRole(Role.valueOf(rs.getString(DAOParameter.US_ROLE).toUpperCase()));
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
	 * Check of the login values in the database.
	 * 
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws DAOException
	 */
	@Override
	public boolean checkLogin(String login) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		boolean result = false;
		try {
			con = ConnectionPool.getInstance().takeConnection();

			ps = con.prepareStatement(SQL_CHEK_LOGIN);
			ps.setString(1, login);
			rs = ps.executeQuery();

			if (rs.next()) {
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
	 * Search in the database of all the actors and producers.
	 * 
	 * @return list of actors and producers
	 * @throws DAOException
	 */
	@Override
	public List<Personality> takePersonality() throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		List<Personality> result = new ArrayList<Personality>();
		Personality personality;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_TAKE_PERSONALITY);
			rs = ps.executeQuery();
			while (rs.next()) {
				personality = new Personality();
				personality.setFirstName(rs.getString(DAOParameter.AC_F_NAME));
				personality.setLastName(rs.getString(DAOParameter.AC_L_NAME));
				personality.setIdPersonality(rs.getInt(DAOParameter.AC_UID));
				personality.setRole(RoleOfActor.valueOf(rs.getString(DAOParameter.AC_ROLE)));
				result.add(personality);
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
	 * Search film by ID in the database.
	 * 
	 * @param id
	 *            identification number of the film
	 * @return the type of user object or null
	 * @throws DAOException
	 */

	@Override
	public Film findFilmById(int id) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		Film film = null;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			try {
				ps = con.prepareStatement(SQL_FIND_BY_ID);
				ps.setInt(1, id);
				rs = ps.executeQuery();
				if (rs.next()) {
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
				} else
					return film;
			} finally {
				IDAO.closeStatement(ps);
			}
			try {
				Personality personality;
				ps = con.prepareStatement(SQL_FIND_BY_ID_PERSONALITY);
				ps.setInt(1, id);
				rs = ps.executeQuery();
				while (rs.next()) {
					personality = new Personality();
					personality.setIdPersonality(rs.getInt(DAOParameter.AC_UID));
					personality.setFirstName(rs.getString(DAOParameter.AC_F_NAME));
					personality.setLastName(rs.getString(DAOParameter.AC_L_NAME));
					personality.setRole(RoleOfActor.valueOf(rs.getString(DAOParameter.AC_ROLE)));
					film.getListPersonality().add(personality);
				}
			} finally {
				IDAO.closeStatement(ps);
			}
			try {
				ps = con.prepareStatement(SQL_FIND_BY_ID_GENRE);
				ps.setInt(1, id);
				rs = ps.executeQuery();
				Genre genre;
				while (rs.next()) {
					genre = new Genre();
					genre.setName(rs.getString(DAOParameter.GN_NAME));
					genre.setIidGenre(rs.getInt(DAOParameter.GN_UID));
					film.getListGenre().add(genre);
				}
			} finally {
				IDAO.closeStatement(ps);
			}
			return film;
		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {
			IDAO.closeConnection(con);
		}
	}

	/**
	 * Search comments on film in the database.
	 * 
	 * @param list
	 *            a list where will be posted comments
	 * @param id
	 *            identification number of the film
	 * @param offset
	 *            the displacement of the records in the list
	 * @param noOfRecords
	 *            the maximum number of records returned returned
	 * @return number of records retrieved
	 * @throws DAOException
	 */
	@SuppressWarnings("resource")
	@Override
	public int takeCommentOfFilm(int id, List<Comment> list, int offset, int noOfRecords) throws DAOException {
		int k = 0;
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
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
				comment.getUser().setIdUser(rs.getInt(DAOParameter.US_UID));
				comment.getUser().setFirstName(rs.getString(DAOParameter.US_F_NAME));
				comment.getFilm().setTitle(rs.getString(DAOParameter.FM_TITLE));
				comment.setDate(rs.getDate(DAOParameter.AS_DATE));
				comment.setText(rs.getString(DAOParameter.AS_RECAL));
				comment.getFilm().setIdFilm(rs.getInt(DAOParameter.FM_UID));
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
	 * Search the list of films in the database on the basis of the input data.
	 * 
	 * @param list
	 *            a list where will be posted films
	 * @param title
	 *            the name of the film
	 * @param country
	 *            the country of the film
	 * @param actors
	 *            the actors of the film
	 * @param producers
	 *            the producers of the film
	 * @param year
	 *            the year of the film
	 * @param genre
	 *            the genres of the film
	 * @param offset
	 *            the displacement of the records in the list
	 * @param noOfRecords
	 *            the maximum number of records returned
	 * @return number of records retrieved
	 * @throws DAOException
	 */
	@SuppressWarnings("resource")
	@Override
	public int findListOfFilms(List<Film> list, String title, String country, String actor, String producer,
			String year, String genre, int offset, int noOfRecords) throws DAOException {
		int k = 0;
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			try {
				ps = con.prepareStatement(SQL_FIND_LIST_FILM);
				ps.setString(1, title);
				ps.setString(2, country);
				ps.setString(3, year);
				ps.setString(4, producer);
				ps.setString(5, actor);
				ps.setString(6, genre);
				ps.setInt(7, offset);
				ps.setInt(8, noOfRecords);
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
						ps = con.prepareStatement(SQL_FIND_LIST_FILM_PERSONALITY);
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
						ps = con.prepareStatement(SQL_FIND_LIST_FILM_GENRE);
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
	 * Search film by rating.
	 * 
	 * @param list
	 *            a list where will be posted films
	 * @param type
	 *            parameter that defines how the sorting will be rated films
	 * @param offset
	 *            the displacement of the records in the list
	 * @param noOfRecords
	 *            the maximum number of records returned
	 * @return number of records retrieved
	 * @throws DAOException
	 */
	@SuppressWarnings("resource")
	@Override
	public int findFilmsByRating(List<Film> list, int offset, int noOfRecords, String type) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		int k = 0;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			try {
				if (type.equals(DAOParameter.DOWN)) {
					ps = con.prepareStatement(SQL_GET_ALL_FILMS_UP);
				} else {
					ps = con.prepareStatement(SQL_GET_ALL_FILMS_DOWN);
				}
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
						ps = con.prepareStatement(SQL_FIND_LIST_FILM_PERSONALITY);
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
						ps = con.prepareStatement(SQL_FIND_LIST_FILM_GENRE);
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
	 * Search for movies that have been added in the latest database.
	 * 
	 * @param list
	 *            a list where will be posted films
	 * @param offset
	 *            the displacement of the records in the list
	 * @param noOfRecords
	 *            the maximum number of records returned
	 * @return number of records retrieved
	 * @throws DAOException
	 */
	@Override
	public void findNewFilms(List<Film> list) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_GET_NEW_FILMS);
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
		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {
			IDAO.closeStatement(ps);
			IDAO.closeConnection(con);
		}

	}

	/**
	 * Search films in the database that have the largest number of comments.
	 * 
	 * @param list
	 *            a list where will be posted films
	 * 
	 * @throws DAOException
	 */

	@Override
	public void findFilm(List<Film> films) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_GET_DISCUSED_FILMS);
			rs = ps.executeQuery();
			Film film;
			while (rs.next()) {
				film = new Film();
				film.setIdFilm(rs.getInt(DAOParameter.FM_UID));
				film.setPoster(rs.getString(DAOParameter.FM_POSTER));
				film.setRating(rs.getFloat(DAOParameter.FM_RATING));
				film.setTitle(rs.getString(DAOParameter.FM_TITLE));
				films.add(film);
			}

		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {
			IDAO.closeStatement(ps);
			IDAO.closeConnection(con);

		}

	}

	/**
	 * Finds the commentary to film that the user recently left.
	 * 
	 * @param list
	 *            a list where will be posted films
	 * 
	 * @throws DAOException
	 */
	@Override
	public void findFilmComment(List<Comment> comments) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		try {
			con = ConnectionPool.getInstance().takeConnection();

			ps = con.prepareStatement(SQL_GET_LAST_COMMENT);
			rs = ps.executeQuery();
			Comment com;
			while (rs.next()) {
				com = new Comment();
				com.getFilm().setIdFilm(rs.getInt(DAOParameter.FM_UID));
				com.getUser().setIdUser(rs.getInt(DAOParameter.US_UID));
				com.getUser().setFirstName(rs.getString(DAOParameter.US_F_NAME));
				com.getFilm().setTitle(rs.getString(DAOParameter.FM_TITLE));
				com.setText(rs.getString(DAOParameter.AS_RECAL));
				com.setDate(rs.getDate(DAOParameter.AS_DATE));
				comments.add(com);
			}

		} catch (SQLException e) {
			throw new DAOException("Wrong", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong", e);
		} finally {
			IDAO.closeStatement(ps);
			IDAO.closeConnection(con);
		}
	}

	/**
	 * Search film by title.
	 * 
	 * @param list
	 *            a list where will be posted films
	 * 
	 * @param offset
	 *            the displacement of the records in the list
	 * @param noOfRecords
	 *            the maximum number of records returned
	 * @return number of records retrieved
	 * @throws DAOException
	 */
	@SuppressWarnings("resource")
	@Override
	public int findFilmFasty(List<Film> list, String text, int offset, int noOfRecords) throws DAOException {

		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		int k = 0;
		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_FIND_FILM_FASTY);
			ps.setString(1, text);
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
	 * Calculation of film ratings and user ratings.
	 * 
	 * @param newCountFilm
	 *            the calculated number of assessments
	 * @param newRatingFilm
	 *            new recalculated rated film
	 * @throws DAOException
	 */

	@Override
	public void colculateFilmRating(Map<Integer, Integer> newCountFilm, Map<Integer, Float> newRatingFilm)
			throws DAOException {

		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;

		try {
			con = ConnectionPool.getInstance().takeConnection();
			try {
				ps = con.prepareStatement(SQL_TAKE_COUNT_VOICE);
				rs = ps.executeQuery();
				while (rs.next()) {
					newCountFilm.put(rs.getInt(DAOParameter.FM_UID), rs.getInt(DAOParameter.SUM));
					newRatingFilm.put(rs.getInt(DAOParameter.FM_UID), rs.getFloat(DAOParameter.AVG));
				}
			} finally {
				IDAO.closeStatement(ps);
			}
			ps = con.prepareStatement(SQL_UPDATE_RATING_FILM);
			Set<Integer> keys = newRatingFilm.keySet();
			for (Integer s : keys) {
				ps.setFloat(1, newRatingFilm.get(s));
				ps.setInt(2, s);
				ps.executeUpdate();
			}

		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {
			IDAO.closeStatement(ps);
			IDAO.closeConnection(con);
		}

	}

	/**
	 * Search the parameter "Counter assessment" of films in the database.
	 * 
	 */
	@Override
	public void takeCountFilm(Map<Integer, Integer> prevCountFilm) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;

		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_SELECT_PREVIOS_COUNT);
			rs = ps.executeQuery();
			while (rs.next()) {
				prevCountFilm.put(rs.getInt(DAOParameter.FM_UID), rs.getInt(DAOParameter.FM_COUNT));
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {
			IDAO.closeStatement(ps);
			IDAO.closeConnection(con);
		}
	}

	/**
	 * Change the parameter "Counter assessments" of films in the database.
	 * 
	 * @param filmUid
	 *            identification number of the film
	 * @param count
	 *            the count of the film
	 */
	@Override
	public void updateCountFilm(Integer filmUid, Integer count) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;

		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_UPDATE_PREVIOS_COUNT);
			ps.setInt(1, count);
			ps.setInt(2, filmUid);
			ps.executeUpdate();

		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {
			IDAO.closeStatement(ps);
			IDAO.closeConnection(con);
		}

	}

	/**
	 * Recalculation user ratings.
	 * 
	 * @param filmUid
	 *            identification number of the film
	 * @param newRating
	 *            new rating of film
	 */
	@Override
	public void colculateUserRating(Integer filmUid, Float newRating) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		List<Integer> listUser = new ArrayList<Integer>();
		try {
			con = ConnectionPool.getInstance().takeConnection();
			try {
				ps = con.prepareStatement(SQL_USER_UID);
				ps.setInt(1, filmUid);
				rs = ps.executeQuery();
				while (rs.next()) {
					listUser.add(rs.getInt(DAOParameter.USER_US_UID));
				}
			} finally {
				IDAO.closeStatement(ps);
			}

			for (Integer userUid : listUser) {
				ps = con.prepareStatement(SQL_UPDATE_RATING_USER);
				ps.setFloat(1, newRating);
				ps.setInt(2, filmUid);
				ps.setInt(3, userUid);
				ps.setFloat(4, newRating);
				ps.setInt(5, filmUid);
				ps.setInt(6, userUid);
				ps.setFloat(7, newRating);
				ps.setInt(8, filmUid);
				ps.setInt(9, userUid);
				ps.setFloat(10, newRating);
				ps.setInt(11, filmUid);
				ps.setInt(12, userUid);
				ps.setFloat(13, newRating);
				ps.setInt(14, filmUid);
				ps.setInt(15, userUid);
				ps.setFloat(16, newRating);
				ps.setInt(17, filmUid);
				ps.setInt(18, userUid);
				ps.setFloat(19, newRating);
				ps.setInt(20, filmUid);
				ps.setInt(21, userUid);
				ps.setFloat(22, newRating);
				ps.setInt(23, filmUid);
				ps.setInt(24, userUid);
				ps.setFloat(25, newRating);
				ps.setInt(26, filmUid);
				ps.setInt(27, userUid);
				ps.setInt(28, userUid);
				ps.executeUpdate();
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {
			IDAO.closeStatement(ps);
			IDAO.closeConnection(con);
		}
	}

	/**
	 * Search list of film in the database.
	 * 
	 * @param list
	 *            a list where will be posted films
	 * 
	 * @param offset
	 *            the displacement of the records in the list
	 * @param noOfRecords
	 *            the maximum number of records returned
	 * @return number of records retrieved
	 * @throws DAOException
	 */
	@SuppressWarnings("resource")
	@Override
	public int findListMain(List<Film> list, int offset, int noOfRecords) throws DAOException {
		int k = 0;
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;

		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_SELECT_FILM_MAIN);
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
	 * Search user by rating in the database.
	 * 
	 * @param list
	 *            a list where will be posted user
	 * @param type
	 *            parameter that defines how the sorting will be rated users
	 * @param offset
	 *            the displacement of the records in the list
	 * @param noOfRecords
	 *            the maximum number of records returned
	 * @return number of records retrieved
	 * @throws DAOException
	 */
	
	@Override
	public void findUsersByRating(List<User> list, String type) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;

		try {
			con = ConnectionPool.getInstance().takeConnection();
			if (type.equals(DAOParameter.DOWN)) {
				ps = con.prepareStatement(SQL_GET_LIST_USERS_DOWN);
			} else {
				ps = con.prepareStatement(SQL_GET_LIST_USERS_UP);
			}
			ps.setInt(1, DAOParameter.OFFSET_USER);
			ps.setInt(2, DAOParameter.LIMIT_USER);
			rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setIdUser(Integer.parseInt(rs.getString(DAOParameter.US_UID)));
				user.setLogin(rs.getString(DAOParameter.US_LOGIN));
				user.setFirstName(rs.getString(DAOParameter.US_F_NAME));
				user.setStatus(Status.valueOf(rs.getString(DAOParameter.US_STATUS).toUpperCase()));
				user.setEmail(rs.getString(DAOParameter.US_EMAIL));
				user.setRating(Integer.parseInt(rs.getString(DAOParameter.US_RATING)));
				user.setRole(Role.valueOf(rs.getString(DAOParameter.US_ROLE).toUpperCase()));
				user.setImage(rs.getString(DAOParameter.US_IMAGE));
				user.setDateReg(rs.getDate(DAOParameter.US_DATE));
				list.add(user);
			}

		} catch (ConnectionPoolException e) {
			throw new DAOException("Wrong in ConnectionPool", e);
		} catch (SQLException e) {
			throw new DAOException("Wrong in DB", e);
		} finally {
			IDAO.closeStatement(ps);
			IDAO.closeConnection(con);

		}

	}

	/**
	 * Search all users in the database.
	 * 
	 * @param list
	 *            a list where will be posted user
	 * 
	 * @param offset
	 *            the displacement of the records in the list
	 * @param noOfRecords
	 *            the maximum number of records returned
	 * @return number of records retrieved
	 * @throws DAOException
	 */

	@SuppressWarnings("resource")
	@Override
	public int findUsersList(List<User> list, int offset, int noOfRecords) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		int k = 0;

		try {
			con = ConnectionPool.getInstance().takeConnection();
			ps = con.prepareStatement(SQL_FIND_USERS_LIST);
			ps.setInt(1, offset);
			ps.setInt(2, noOfRecords);
			rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setIdUser(Integer.parseInt(rs.getString(DAOParameter.US_UID)));
				user.setLogin(rs.getString(DAOParameter.US_LOGIN));
				user.setFirstName(rs.getString(DAOParameter.US_F_NAME));
				user.setStatus(Status.valueOf(rs.getString(DAOParameter.US_STATUS).toUpperCase()));
				user.setEmail(rs.getString(DAOParameter.US_EMAIL));
				user.setRating(Integer.parseInt(rs.getString(DAOParameter.US_RATING)));
				user.setRole(Role.valueOf(rs.getString(DAOParameter.US_ROLE).toUpperCase()));
				user.setImage(rs.getString(DAOParameter.US_IMAGE));
				user.setDateReg(rs.getDate(DAOParameter.US_DATE));
				list.add(user);
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
	 * Adds a poster for the film to the database.
	 * 
	 * @param name
	 *            the name and type of the file
	 * @param id
	 *            identification number of the film
	 * @return if the operation is successful it returns true if not successful
	 *         then false
	 * @throws DAOException
	 */
	@Override
	public boolean addImage(String name, int id) throws DAOException {
		ProxyConnection con = null;
		PreparedStatement ps = null;
		boolean result = false;
		int res;
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
			IDAO.closeConnection(con);
			IDAO.closeStatement(ps);
		}
		return result;
	}

}
